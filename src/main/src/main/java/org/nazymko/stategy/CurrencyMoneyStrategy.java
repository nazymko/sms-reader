package org.nazymko.stategy;

import org.nazymko.History;
import org.nazymko.Money;
import org.nazymko.utils.MoneyParser;

/**
 * Created by Andrew Nazymko
 */
public class CurrencyMoneyStrategy implements Strategy<History> {
    @Override
    public void apply(History target) {
        String currency = target.getCurrency();
        String[] words = target.getSms().split(" ");

        if (!isSupportable(target)) {
            return;
        }

        int index, start = 0;
        while ((index = currencyIndex(currency, words, start)) > 0) {
            if (index > 0 && MoneyParser.isDigits(words[index - 1])) {
                target.getMeta().addMoney(new Money(currency, MoneyParser.normalize(words[index - 1])));
            }
            start = index + 1;
        }

    }

    private boolean isSupportable(History target) {
        return target.getCurrency() != null;
    }

    //can be more than one currency
    private int currencyIndex(String currency, String[] words, int offset) {
        if (currency == null) {
            throw new IllegalArgumentException("currency can't be null.");
        }
        if (offset > words.length) {
            return -1;
        }

        for (int index = offset; index < words.length; index++) {
            if (words[index].startsWith(currency)) {//
                return index;
            }
        }

        return -1;
    }
}
