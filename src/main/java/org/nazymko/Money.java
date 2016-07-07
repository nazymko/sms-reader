package org.nazymko;

/**
 * Created by Andrew Nazymko
 */
public class Money {
    private String currency;
    private Long value;
    private Type type;

    private Money() {

    }

    public Money(String currency, Long value) {
        this.currency = currency;
        this.value = value;
    }

    public Type getType() {
        return type;
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

    enum Type {
        BALANCE, CHANGE
    }

}
