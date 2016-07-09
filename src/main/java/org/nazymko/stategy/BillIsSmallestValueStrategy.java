package org.nazymko.stategy;

import org.nazymko.History;
import org.nazymko.Money;

import java.util.Optional;

/**
 * Created by Andrew Nazymko
 */
public class BillIsSmallestValueStrategy implements Strategy<History> {
    @Override
    public void apply(History mutableTarget) {
        if (mutableTarget.getMeta().getMoneys().size() < 2) {
            return;
        }
        Optional<Money> max = mutableTarget.getMeta().getMoneys().stream().min((o1, o2) -> o1.getValue().compareTo(o2.getValue()));
        if (max.isPresent()) {
            max.get().setType(Money.Type.TRANSACTION);
        }
    }
}
