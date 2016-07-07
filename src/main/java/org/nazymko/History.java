package org.nazymko;

import org.nazymko.utils.CurrencyRegistry;
import org.nazymko.utils.MoneyParser;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Andrew Nazymko
 */
public class History {
    public String getSms() {
        return sms;
    }

    private String sms;
    private LocalDateTime deviceDate;

    public LocalDateTime getDeviceDate() {
        return deviceDate;
    }

    private Meta meta = new Meta();

    public History(String sms, LocalDateTime time) {
        this.sms = sms;
        this.deviceDate = time;
    }

    public static History of(Sms sms) {
        return of(sms.getText(), sms.getTime());
    }

    public static History of(String sms, LocalDateTime time) {
        History history = new History(sms, time);
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
        final StringBuilder sb = new StringBuilder("History{");
        sb.append("sms='").append(sms).append('\'');
        sb.append(", deviceDate=").append(deviceDate);
        sb.append(", meta=").append(meta);
        sb.append('}');
        return sb.toString();
    }
}
