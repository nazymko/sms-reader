package org.nazymko.utils;

import org.nazymko.Money;

/**
 * Created by nazymko.patronus@gmail.com
 */
public class ObjectsUtils {
    public static Money firstNonNullOrNull(Money moneyHistory1, Money moneyHistory2) {
        return moneyHistory1 != null ? moneyHistory1 : moneyHistory2;
    }

    public static boolean isNotNull(Money moneyHistory1, Money moneyHistory2) {
        return moneyHistory1 != null && moneyHistory2 != null;
    }
}
