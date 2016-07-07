package org.nazymko.utils;

import java.util.List;
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
}
