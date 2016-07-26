package org.nazymko.stategy.stategy;

import org.nazymko.stategy.History;

/**
 * Created by Andrew Nazymko
 */
public class BalanceIsBiggestValueStrategy implements Strategy<History> {
    @Override
    public void apply(History mutableTarget) {

    }

    @Override
    public String description() {
        return "Select biggest money value as 'current balance'";
    }
}
