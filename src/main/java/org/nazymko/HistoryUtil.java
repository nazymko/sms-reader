package org.nazymko;

import org.nazymko.utils.MoneyParser;
import org.nazymko.utils.ObjectsUtils;

import java.util.Objects;

/**
 * Created by nazymko.patronus@gmail.com
 */
public class HistoryUtil {
    /**
     * Merge history1 and history2
     */
    public static History merge(History history1, History history2) {
        History history = new History(history2.getSms(), history2.getSmsDate());


        for (Money.Type type : Money.Type.values()) {
            Money moneyHistory1 = MoneyParser.byType(type, history1);
            Money moneyHistory2 = MoneyParser.byType(type, history2);
            Money money;
            if (Money.Type.BALANCE.equals(type)) {

                if (ObjectsUtils.isNotNull(moneyHistory1, moneyHistory2)) {
                    money = bigger(moneyHistory1, moneyHistory2);
                } else {
                    money = ObjectsUtils.firstNonNullOrNull(moneyHistory1, moneyHistory2);
                }
                //Don`t sum balance
            } else if (ObjectsUtils.isNotNull(moneyHistory1, moneyHistory2)) {
                money = new Money(moneyHistory2.getCurrency(), moneyHistory1.getValue() + moneyHistory2.getValue(), type);
            } else {
                money = ObjectsUtils.firstNonNullOrNull(moneyHistory1, moneyHistory2);

            }

            if (money != null) {
                history.getMeta().addMoney(money);
            }
        }


        return history;
    }

    private static Money bigger(Money moneyHistory1, Money moneyHistory2) {
        if (moneyHistory1.getValue() > moneyHistory2.getValue()) {
            return moneyHistory1;
        } else if (moneyHistory1.getValue() < moneyHistory2.getValue()) {
            return moneyHistory2;
        }
        return moneyHistory1;//by default
    }
}
