package org.nazymko.stategy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrew Nazymko
 */
public class HistoryConverter {

    public static List<History> convertIntoHistory(List<Sms> slsList) {
        List<History> histories = new ArrayList<>();
        for (Sms sms : slsList) {
            histories.add(History.of(sms));
        }
        return histories;
    }
}
