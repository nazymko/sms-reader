package org.nazymko.stategy;

import org.nazymko.History;
import org.nazymko.Money;
import org.nazymko.utils.MoneyParser;

import java.util.List;
import java.util.Optional;

/**
 * Created by Andrew Nazymko
 */
public class TwoMoneysStrategy implements Strategy<History> {
    @Override
    public void apply(History mutableTarget) {
        if (mutableTarget.getMeta().getMoneys().size() != 2) {
            return;
        }
        Optional<Money> min = mutableTarget.getMeta().getMoneys().stream().min((o1, o2) -> o1.getValue().compareTo(o2.getValue()));
        Optional<Money> max = mutableTarget.getMeta().getMoneys().stream().max((o1, o2) -> o1.getValue().compareTo(o2.getValue()));

        if (max.isPresent() && min.isPresent()) {
            if (!min.get().equals(max.get())) {
                min.get().setType(Money.Type.OPERATION);
                mutableTarget.getMeta().setOperation(min.get().getValue());

                max.get().setType(Money.Type.BALANCE);
                mutableTarget.getMeta().setBalance(max.get().getValue());
            } else {
                List<Money> moneys = mutableTarget.getMeta().getMoneys();

                moneys.get(0).setType(Money.Type.BALANCE);
                moneys.get(1).setType(Money.Type.OPERATION);

                mutableTarget.getMeta().setBalance(moneys.get(0).getValue());
                mutableTarget.getMeta().setOperation(moneys.get(1).getValue());
            }
        }


    }

    @Override
    public String description() {
        return "Mark smallest money as 'operation'";
    }
}
