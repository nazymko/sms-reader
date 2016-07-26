package org.nazymko.stategy.stategy;

import org.nazymko.stategy.History;
import org.nazymko.stategy.Money;
import org.nazymko.stategy.Operation;
import org.nazymko.stategy.utils.MoneyParser;

import java.util.List;

/**
 * Created by 1 on 10.07.2016.
 */
public class OperationTimeStrategy implements BulkStrategy {
    @Override
    public void apply(List<History> histories) {

        for (int index = 0; index < histories.size(); index++) {
            History current = histories.get(index);

            if (!current.getRelated().isEmpty() && current.getRelated().get(0).getMeta().getOperation() != null) {
                current.getMeta().setOperation(current.getRelated().get(0).getMeta().getOperation());
            } else {
                int reversedIndex = index - 1;
                while (reversedIndex >= 0) {
                    History prev = histories.get(reversedIndex);
                    Long prevBalance = MoneyParser.byType(Money.Type.BALANCE, prev).getValue();
                    Long currentBalance = current.getMeta().getBalance();
                    if (!prevBalance.equals(currentBalance)) {
                        if (prevBalance > currentBalance) {
                            current.getMeta().setOperation(Operation.OUTCOME);
                        } else {
                            current.getMeta().setOperation(Operation.INCOME);
                        }
                    }

                    reversedIndex--;
                }
            }
        }

    }

    @Override
    public String description() {
        return "Recognize income/outcome operation based on balance value";
    }
}
