package org.nazymko.stategy;

import org.nazymko.History;
import org.nazymko.Money;
import org.nazymko.utils.MoneyParser;

/**
 * Created by 1 on 09.07.2016.
 */
public class FillMetaWithBalance implements Strategy<History> {
    @Override
    public void apply(History mutableTarget) {
        Money balance = MoneyParser.byType(Money.Type.BALANCE, mutableTarget);
        Money transaction = MoneyParser.byType(Money.Type.OPERATION, mutableTarget);

        mutableTarget.getMeta().setBalance(balance.getValue());
        mutableTarget.getMeta().setChange(transaction.getValue());

    }
}
