package org.nazymko.stategy;

import org.nazymko.History;
import org.nazymko.Money;

import java.util.Iterator;

/**
 * Created by Andrew Nazymko
 */
public class RemoveZeroMoneyStrategy implements Strategy<History> {
    @Override
    public void apply(History mutableTarget) {
        Iterator<Money> iterator = mutableTarget.getMeta().getMoneys().iterator();
        while (iterator.hasNext()) {
            Money next = iterator.next();
            if (next.getValue() == 0) {
                System.err.println("Remove zero moneys:" + next);
                iterator.remove();
            }
        }
    }

    @Override
    public String description() {
        return "Sms sometimes have unnecessary zero money (0.00 USD)";
    }


}
