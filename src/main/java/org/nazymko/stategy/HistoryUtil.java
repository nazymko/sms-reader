package org.nazymko.stategy;

import org.nazymko.stategy.utils.MoneyParser;
import org.nazymko.stategy.utils.ObjectsUtils;

import static java.time.temporal.ChronoUnit.DAYS;

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

        history.setCurrency(history1.getCurrency());
        history.setSmsDate(history1.getSmsDate().truncatedTo(DAYS));
        history.setAmount(MoneyParser.byType(Money.Type.OPERATION, history).getValue());
        history.getMeta().setCurrency(history1.getMeta().getCurrency());
        history.getMeta().setBalance(Math.max(history1.getMeta().getBalance(), history2.getMeta().getBalance()));
        history.getMeta().setOperation(history1.getMeta().getOperation());
        history.getMeta().setChange(history1.getMeta().getChange() + history2.getMeta().getChange());
        history.getMeta().addDigits(history1.getMeta().getDigits());

        history.addCards(history1.getCards());
        history.addCards(history2.getCards());


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

    public static History gapCopy(History history) {
        History merge = merge(history, history);
        Money balance = MoneyParser.byType(Money.Type.BALANCE, merge);
        merge.getMeta().getMoneys().clear();
        merge.getMeta().addMoney(balance);
        merge.getMeta().addMoney(new Money("", 0L, Money.Type.OPERATION));
        merge.getMeta().setChange(0);

        return merge;
    }

    public static History gapCopy(History prev, int days) {
        History gapCopy = gapCopy(prev);
        gapCopy.setSmsDate(gapCopy.getSmsDate().plus(days, DAYS).truncatedTo(DAYS));
        return gapCopy;
    }
}
