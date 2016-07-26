package org.nazymko.stategy;

import java.util.*;

/**
 * Created by nazymko.patronus@gmail.com
 */
public class Meta {
    private List<Money> moneys = new ArrayList<>();
    private Set<Long> digits = new HashSet<>();
    private String currency;
    private Long balance;
    private Operation operation;
    private Long change;
    private State state;

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Long getChange() {
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

    public Set<Long> getDigits() {
        return digits;
    }

    public void addDigits(Collection<Long> digits) {
        this.digits.addAll(digits);
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

    public void addMoney(List<Money> moneys) {
        this.moneys.addAll(moneys);
    }
}
