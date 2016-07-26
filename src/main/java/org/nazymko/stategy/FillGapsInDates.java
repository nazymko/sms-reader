package org.nazymko.stategy;

import org.nazymko.History;
import org.nazymko.HistoryTimeComparator;
import org.nazymko.HistoryUtil;
import org.nazymko.utils.DateParser;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * Created by Andrew Nazymko
 */
public class FillGapsInDates implements BulkStrategy {
    @Override
    public void apply(List<History> mutableTarget) {
        List<History> copy = new ArrayList<>(mutableTarget);
        mutableTarget.clear();
        copy.sort(new HistoryTimeComparator());

        History prev = copy.get(0);
        for (int index = 1; index < copy.size(); index++) {
            History current = copy.get(index);

            mutableTarget.add(prev);
            long between = DateParser.between(prev.getSmsDate().truncatedTo(DAYS), current.getSmsDate().truncatedTo(DAYS), DAYS);
            if (between > 0) {
                for (int days = 1; days < between; days++) {
                    History gapCopy = HistoryUtil.gapCopy(prev, days);
                    mutableTarget.add(gapCopy);
                }
            }
            prev = current;
            if (copy.size() - 1 == index) {//Last element
                mutableTarget.add(current);
            }

        }

        mutableTarget.sort(new HistoryTimeComparator());


    }


    @Override
    public String description() {
        return "Fill gaps in dates";
    }
}
