package org.nazymko.stategy;

import org.nazymko.History;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Andrew Nazymko
 */
public class CardIs4Digits implements Strategy<History> {
    @Override
    public void apply(History mutableTarget) {
        Pattern pattern = Pattern.compile("\\D*(\\d{4})$");
        String[] split = mutableTarget.getSms().split(" ");
        for (String word : split) {
            Matcher matcher = pattern.matcher(word);
            if (matcher.matches()) {
                mutableTarget.addCard(matcher.group(1));
            }
        }
    }
}
