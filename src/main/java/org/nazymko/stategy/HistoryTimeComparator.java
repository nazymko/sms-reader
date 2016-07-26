package org.nazymko.stategy;

import java.util.Comparator;

/**
 * Created by 1 on 10.07.2016.
 */
public class HistoryTimeComparator implements Comparator<History> {
    @Override
    public int compare(History first, History second) {
        if (first.getSmsDate() == null && second.getSmsDate() == null) {
            return 0;
        }
        if (first.getSmsDate() == null) {
            return -1;
        }
        if (second.getSmsDate() == null) {
            return 1;
        }
        return first.getSmsDate().isEqual(second.getSmsDate()) ? 0 : (first.getSmsDate().isBefore(second.getSmsDate()) ? -1 : 1);
    }
}
