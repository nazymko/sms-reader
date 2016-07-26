package org.nazymko.stategy.stategy;

import org.nazymko.stategy.History;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Andrew Nazymko
 */
public class TwoMoneyRequiredFilter implements BulkStrategy {
    @Override
    public void apply(List<History> mutableTarget) {
        Iterator<History> iterator = mutableTarget.iterator();
        while (iterator.hasNext()) {
            History next = iterator.next();
            if (next.getMeta().getMoneys().size() != 2) {
                System.err.println("Removed:" + next);
                iterator.remove();
            }
        }
    }

    @Override
    public String description() {
        return "Remove sms that doesn't have two moneys";
    }
}
