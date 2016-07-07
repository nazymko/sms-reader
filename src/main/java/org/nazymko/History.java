package org.nazymko;

import org.nazymko.utils.CurrencyRegistry;
import org.nazymko.utils.DateParser;
import org.nazymko.utils.MoneyParser;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Andrew Nazymko
 */
public class History {
    private String sms;
    private LocalDateTime deviceDate;
    private Meta meta = new Meta();
    private LocalDateTime smsDate;

    public LocalDateTime getSmsDate() {
        return smsDate;
    }

    public History(String sms, LocalDateTime time) {
        this.sms = sms;
        this.deviceDate = time;
    }

    public static History of(Sms sms) {
        return of(sms.getText(), sms.getTime());
    }

    public static History of(String sms, LocalDateTime deviceTime) {
        History history = new History(sms, deviceTime);
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

    public String getSms() {
        return sms;
    }

    public LocalDateTime getDeviceDate() {
        return deviceDate;
    }

    public Meta getMeta() {
        return meta;
    }

    public List<Long> getDigits() {
        return meta.getDigits();
    }

    public void setDigits(List<Long> digits) {
        this.meta.setDigits(digits);
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
        return "History{" +
                "sms='" + sms + '\'' +
                ", deviceDate=" + deviceDate +
                ", meta=" + meta +
                ", smsDate=" + smsDate +
                '}';
    }

    public void setSmsDate(LocalDateTime smsDate) {
        this.smsDate = smsDate;
    }
}
