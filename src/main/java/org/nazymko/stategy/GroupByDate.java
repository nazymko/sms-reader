package org.nazymko.stategy;

import org.nazymko.History;
import org.nazymko.HistoryTimeComparator;
import org.nazymko.HistoryUtil;
import org.nazymko.utils.DateParser;

import java.util.List;

/**
 * Created by nazymko.patronus@gmail.com
 */
public class GroupByDate implements BulkStrategy {
    @Override
    public void apply(List<History> mutableTarget) {
        mutableTarget.sort(new HistoryTimeComparator());

        for (int i = 1; i < mutableTarget.size(); i++) {
            History prev = mutableTarget.get(i - 1);
            History current = mutableTarget.get(i);


            if (DateParser.inSameDay(prev.getSmsDate(), current.getSmsDate())) {
                HistoryUtil.merge(prev, current);
            }

        }
    }

    @Override
    public String description() {
        return "Sum income and outcome by date";
    }
}
