package org.nazymko.utils;

import org.nazymko.History;
import org.nazymko.Money;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Created by Andrew Nazymko
 */
public class MoneyParser {

    public static final int SCALE = 100;
    private static final Pattern PATTERN = Pattern.compile("\\d*\\.{0,1}\\d{1,2}");

    public static boolean isDigits(String text) {
        return PATTERN.matcher(text).matches();

    }

    public static Long normalize(String text) {
        if (text.contains(".")) {
            String[] strings = text.split("\\.");

            Long big = Long.valueOf(strings[0]);
            Long small = Long.valueOf(strings[1]);

            if (strings[1].length() == 1) {
                small = small * 10;
            }

            if (strings[1].length() > 2) {
                small = Long.valueOf(strings[1].substring(0, 2));
            }

            if (small <= 100) {
                return big * SCALE + small;
            } else {
                throw new IllegalArgumentException(text);
            }
        } else {
            return Long.valueOf(text) * SCALE;
        }
    }

    public static boolean inRange(List<Long> prevDigits, long dif, int accuracy) {
        for (Long prevDigit : prevDigits) {
            if (inRange(prevDigit, dif, accuracy)) {
                return true;
            }
        }
        return false;
    }

    public static boolean inRange(List<Long> prevDigits, long dif, double accuracy) {
        for (Long prevDigit : prevDigits) {
            if (inRange(prevDigit, dif, (int) (dif * accuracy))) {
                return true;
            }
        }
        return false;
    }

    public static boolean inRange(long value, long operation, long accuracy) {
        if (value >= (operation - accuracy) && value <= (operation + accuracy)) {
            System.out.println("inRange(true): value = [" + value + "], operation = [" + operation + "], accuracy = [" + accuracy + "]");
            return true;
        }
        return false;
    }

    public static boolean isOperationComponents(long lastBalance, long currentBalance, long operationValue, double operationInterest, String currency) {
        if (_isOperationBalance(lastBalance, currentBalance, operationValue, operationInterest)) {
            System.out.println("lastBalance = [" + format(lastBalance, currency) + "], currentBalance = [" + format(currentBalance, currency) + "], operationValue = [" + format(operationValue, currency) + "]");
            return true;
        }
        return false;
    }

    private static boolean _isOperationBalance(long lastBalance, long currentBalance, long operationValue, double operationInterest) {
        if (Math.abs(lastBalance - operationValue) <= (currentBalance + (operationValue * operationInterest)) ||
                Math.abs(lastBalance + operationValue) >= (currentBalance + (operationValue * operationInterest))
                ) {
            return true;
        }
        return false;
    }

    public static String format(Long value, String currency) {
        String valueString = value.toString();
        if (valueString.length() > 2) {
            return valueString.substring(0, valueString.length() - 2) + "." + valueString.substring(valueString.length() - 2) + " " + currency;
        } else {
            if (valueString.length() == 0) {
                return "0.00 " + currency;
            } else {
                return (double) (value / 100) + " " + currency;
            }

        }
    }

    public static Money byType(Money.Type type, History history) {
        return _byType(type, history.getMeta().getMoneys());
    }

    private static Money _byType(Money.Type type, List<Money> moneys) {
        return moneys.stream().filter(x -> type.equals(x.getType())).findFirst().orElse(null);
    }

    public static List<Money> sum(List<Money> source1, List<Money> source2) {
        List<Money> newMoney = new ArrayList<>();

        for (Money money : source1) {
            Money money1 = _byType(money.getType(), source2);
            if (money1 == null) {
                continue;
            }
            if (!Objects.equals(money.getCurrency(), money1.getCurrency())) {
                throw new IllegalArgumentException(money.getCurrency() + " and  " + money1.getCurrency() + " is not the same");
            }
            newMoney.add(new Money(money.getCurrency(), money.getValue() + money1.getValue()));
        }

        return newMoney;
    }
}
