package org.nazymko;

/**
 * Created by Andrew Nazymko
 */
public class Money {
    private String currency;
    private Long value;
    private Type type = Type.UNKNOWN;
    private int index;

    private Money() {

    }

    public Money(String currency, Long value) {
        this.currency = currency;
        this.value = value;
    }

    public Money(String currency, Long value, Type type) {
        this.currency = currency;
        this.value = value;
        this.type = type;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
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
        BALANCE, OPERATION, UNKNOWN
    }


    public static class Position {
        private int fromBegin;
        private int fromEnd;
        private int index;

        public int getFromBegin() {
            return fromBegin;
        }

        public void setFromBegin(int fromBegin) {
            this.fromBegin = fromBegin;
        }

        public int getFromEnd() {
            return fromEnd;
        }

        public void setFromEnd(int fromEnd) {
            this.fromEnd = fromEnd;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }
    }
}
