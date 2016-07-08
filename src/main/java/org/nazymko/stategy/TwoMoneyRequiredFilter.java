package org.nazymko.stategy;

import org.nazymko.History;

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
            if (iterator.next().getMeta().getMoneys().size() < 2) {
                iterator.remove();
            }
        }
    }
}
