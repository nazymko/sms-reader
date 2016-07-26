package org.nazymko.stategy.stategy;

import org.nazymko.stategy.History;
import org.nazymko.stategy.HistoryTimeComparator;
import org.nazymko.stategy.HistoryUtil;
import org.nazymko.stategy.utils.DateParser;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrew Nazymko
 */
public class FillGapsInDates implements BulkStrategy {
    @Override
    public void apply(List<History> mutableTarget) {
        List<History> copy = new ArrayList<>(mutableTarget);
        copy.sort(new HistoryTimeComparator());

        History prev = copy.get(0);
        for (int i = 1; i < copy.size(); i++) {
            History current = copy.get(i);

            mutableTarget.add(prev);
            long between = DateParser.between(prev.getSmsDate(), current.getSmsDate(), ChronoUnit.DAYS);
            if (between > 1) {
                for (int days = 0; days < between; days++) {
                    History gapCopy = HistoryUtil.gapCopy(prev, days + 1);
                    mutableTarget.add(gapCopy);
                }
            }
            prev = current;
            if (copy.size() - 1 == i) {//Last element
                mutableTarget.add(current);
            }

        }


    }


    @Override
    public String description() {
        return "Fill gaps in dates";
    }
}
