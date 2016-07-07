package org.nazymko.utils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Andrew Nazymko
 */
public class CurrencyRegistry {
    static final Set<String> currency = new HashSet<>(Arrays.asList("UAH", "USD"));

    public static boolean isCurrency(String text) {
        return currency.contains(text);
    }

}
