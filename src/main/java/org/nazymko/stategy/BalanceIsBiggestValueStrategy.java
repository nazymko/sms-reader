package org.nazymko.stategy;

import org.nazymko.History;
import org.nazymko.Money;

import java.util.List;
import java.util.Optional;

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
