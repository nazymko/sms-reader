package org.nazymko.stategy.stategy;

import org.nazymko.stategy.History;
import org.nazymko.stategy.Money;

import java.util.Optional;

/**
 * Created by nazymko.patronus@gmail.com
 */
public class BalanceTransactionEqualFixStrategy implements Strategy<History> {
    @Override
    public void apply(History mutableTarget) {
        Optional<Money> min = mutableTarget.getMeta().getMoneys().stream().min((o1, o2) -> o1.getValue().compareTo(o2.getValue()));
        Optional<Money> max = mutableTarget.getMeta().getMoneys().stream().max((o1, o2) -> o1.getValue().compareTo(o2.getValue()));

        if (min.isPresent() && max.isPresent() && min.get().equals(max.get())) {
            if (mutableTarget.getMeta().getMoneys().size() == 2) {
                mutableTarget.getMeta().getMoneys().get(0).setType(Money.Type.BALANCE);
                mutableTarget.getMeta().getMoneys().get(1).setType(Money.Type.OPERATION);
            }
        }
    }

    @Override
    public String description() {
        return "Fix issue when operation and balance is equal";
    }
}
