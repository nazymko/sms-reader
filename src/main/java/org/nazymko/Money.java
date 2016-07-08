package org.nazymko;

/**
 * Created by Andrew Nazymko
 */
public class Money {
    private String currency;
    private Long value;
    private Type type = Type.UNKNOWN;

    private Money() {

    }

    public Money(String currency, Long value) {
        this.currency = currency;
        this.value = value;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Money{" +
                "currency='" + currency + '\'' +
                ", value=" + value +
                ", type=" + type +
                '}';
    }

    public String getCurrency() {
        return currency;
    }

    public Long getValue() {
        return value;
    }

    public enum Type {
        BALANCE, BILL, UNKNOWN, INCOME
    }

}
