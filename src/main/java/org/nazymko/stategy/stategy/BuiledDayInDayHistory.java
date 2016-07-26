package org.nazymko.stategy.stategy;

import org.nazymko.stategy.History;

import java.util.List;

/**
 * Created by nazymko.patronus@gmail.com
 */
public class BuiledDayInDayHistory implements BulkStrategy {
    @Override
    public void apply(List<History> mutableTarget) {

    }

    @Override
    public String description() {
        return "Fill gaps in history";
    }
}
