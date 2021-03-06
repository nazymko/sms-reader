package org.nazymko.stategy.stategy;

import org.nazymko.stategy.History;
import org.nazymko.stategy.HistoryTimeComparator;
import org.nazymko.stategy.HistoryUtil;
import org.nazymko.stategy.utils.DateParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nazymko.patronus@gmail.com
 */
public class GroupByDate implements BulkStrategy {
    @Override
    public void apply(List<History> mutableTarget) {
        List<History> temp = new ArrayList<>(mutableTarget);
        mutableTarget.clear();

        temp.sort(new HistoryTimeComparator());
        History prev = temp.get(0);
        for (int i = 1; i < temp.size(); i++) {
            History current = temp.get(i);


            if (DateParser.inSameDay(prev.getSmsDate(), current.getSmsDate())) {
                History result = HistoryUtil.merge(prev, current);
                System.out.println("Merge:\n\t" + prev + "\n\t" + current + "\nInto:\n\t" + result);
                prev = result;
            } else {
                mutableTarget.add(prev);
                prev = current;
            }

        }
        mutableTarget.sort(new HistoryTimeComparator());
    }

    @Override
    public String description() {
        return "Group by date";
    }
}
