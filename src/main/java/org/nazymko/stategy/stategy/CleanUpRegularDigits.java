package org.nazymko.stategy.stategy;

import org.nazymko.stategy.History;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Andrew Nazymko
 */
public class CleanUpRegularDigits implements BulkStrategy {

    public static final double threshold = 0.5;

    private static void removeDigit(List<History> histories, Long key) {

    }

    @Override
    public void apply(List<History> histories) {
        HashMap<Long, Long> counter = new HashMap<>();
        for (History history : histories) {
            for (Long aLong : history.getDigits()) {
                counter.put(aLong, counter.getOrDefault(aLong, 0L) + 1);
            }
        }
        int limit = (int) (histories.size() * threshold);

        counter.entrySet().stream().filter(entry -> entry.getValue() > limit).forEach(entry -> {
            for (History history : histories) {
                history.getDigits().remove(entry.getKey());
            }
        });
    }

    @Override
    public String description() {
        return "Remove frequently encountered digits";
    }
}
