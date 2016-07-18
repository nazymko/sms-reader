package org.nazymko.stategy;

import org.nazymko.History;
import org.nazymko.Money;

import java.util.Optional;

/**
 * Created by Andrew Nazymko
 */
public class BalanceIsBiggestValueStrategy implements Strategy<History> {
    @Override
    public void apply(History mutableTarget) {
        Optional<Money> max = mutableTarget.getMeta().getMoneys().stream().max((o1, o2) -> o1.getValue().compareTo(o2.getValue()));
        if (max.isPresent()) {
            max.get().setType(Money.Type.BALANCE);
        }
    }

    @Override
    public String description() {
        return "Select biggest money value as 'current balance'";
    }
}
