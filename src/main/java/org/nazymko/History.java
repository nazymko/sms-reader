package org.nazymko;

import com.google.common.base.MoreObjects;
import org.nazymko.utils.CurrencyRegistry;
import org.nazymko.utils.DateParser;
import org.nazymko.utils.MoneyParser;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Created by Andrew Nazymko
 */
public class History {
    private List<History> related = new ArrayList<>();
    private List<History> dependencies = new ArrayList<>();
    private List<String> sms = new ArrayList<>();
    private LocalDateTime deviceDate;
    private Meta meta = new Meta();
    private LocalDateTime smsDate;
    private Set<String> cards = new HashSet<>();
    private List<String> mergedSms = new ArrayList<>();

    public History(LocalDateTime time, String... sms) {
        this.sms.addAll(Arrays.asList(sms));
        this.deviceDate = time;
    }

    public History(LocalDateTime smsDate, List<String> allSms, List<String> allSms1) {
        sms.addAll(allSms);
        sms.addAll(allSms1);

        this.deviceDate = smsDate;
    }

    public static History empty(LocalDateTime smsDate) {
        History history = new History(null, "null");
        history.setSmsDate(smsDate);
        return history;
    }

    public static History of(Sms sms) {
        return of(sms.getText(), sms.getTime());
    }

    public static History of(String sms, LocalDateTime deviceTime) {
        History history = new History(deviceTime, sms);
        if (DateParser.hasDate(sms)) {
            history.setSmsDate(DateParser.parse(sms));
        }
        for (String text : sms.split(" ")) {
            if (CurrencyRegistry.isCurrency(text)) {
                history.setCurrency(text);
            } else if (MoneyParser.isDigits(text)) {
                Long money = MoneyParser.normalize(text);
                history.getDigits().add(money);
            }
        }
        return history;
    }

    public List<String> getMergedSms() {
        return mergedSms;
    }

    public void mergeSms(String mergedSms) {
        this.mergedSms.add(mergedSms);
    }

    public Set<String> getCards() {
        return cards;
    }

    public void addCard(String card) {
        this.cards.add(card);
    }

    public List<History> getDependencies() {
        return dependencies;
    }

    public void setDependencies(History dependencies) {
        this.dependencies.add(dependencies);
    }

    public List<History> getRelated() {
        return related;
    }

    public void setRelated(History related) {
        this.related.add(related);
    }

    public LocalDateTime getSmsDate() {
        return smsDate;
    }

    public void setSmsDate(LocalDateTime smsDate) {
        this.smsDate = smsDate;
    }

    public String getSms() {
        return sms.get(0);
    }

    public List<String> getAllSms() {
        return sms;
    }


    public LocalDateTime getDeviceDate() {
        return deviceDate;
    }

    public Meta getMeta() {
        return meta;
    }

    public Collection<Long> getDigits() {
        return meta.getDigits();
    }

    public void setDigits(List<Long> digits) {
        this.meta.addDigits(digits);
    }

    public String getCurrency() {
        return meta.getCurrency();
    }

    public void setCurrency(String currency) {
        this.meta.setCurrency(currency);
    }

    public Long getAmount() {
        return meta.getBalance();
    }

    public void setAmount(Long amount) {
        this.meta.setBalance(amount);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("sms", sms)
                .add("deviceDate", deviceDate)
                .add("meta", meta)
                .add("smsDate", smsDate)
                .add("cards", cards)
                .toString();
    }

    public void addCards(Collection<String> cards) {
        this.cards.addAll(cards);

    }
}
