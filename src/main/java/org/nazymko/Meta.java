package org.nazymko;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nazymko.patronus@gmail.com
 */
public class Meta {
    private List<Money> moneys = new ArrayList<>();
    private List<Long> digits = new ArrayList<>();
    private String currency;
    private Long balance;
    private Operation operation;
    private Long change;

    public long getChange() {
        return change;
    }

    public void setChange(long change) {
        this.change = change;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public List<Long> getDigits() {
        return digits;
    }

    public void setDigits(List<Long> digits) {
        this.digits = digits;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public List<Money> getMoneys() {
        return moneys;
    }

    public void addMoney(Money money) {
        moneys.add(money);
    }

    @Override
    public String toString() {
        return "Meta{" +
                "moneys=" + moneys +
                ", digits=" + digits +
                ", currency='" + currency + '\'' +
                ", balance=" + balance +
                ", operation=" + operation +
                ", change=" + change +
                '}';
    }
}
