package org.nazymko;

import org.nazymko.stategy.CurrencyMoneyStrategy;
import org.nazymko.stategy.Strategy;
import org.nazymko.utils.MoneyParser;

import java.io.IOException;
import java.util.*;

public class Main {

    public static final int ACCURACY = 100;
    public static final double INTEREST = 0.07;
    private static List<Strategy> strategies = Arrays.asList(new CurrencyMoneyStrategy());

    public static void main(String[] args) throws IOException {

        read("sms.log", 2);
        read("sms.log", 3);
        read("sms.log", 4);


    }

    public static List<Change> read(String file, int step) throws IOException {
        List<Sms> slsList = new SmsReader().open(file);
        Collections.sort(slsList, (first, second) -> first.time.isEqual(second.time) ? 0 : first.time.isBefore(second.time) ? -1 : 1);

        List<History> histories = new ArrayList<>();
        for (Sms sms : slsList) {
            histories.add(History.of(sms));
        }

        cleanUpDigits(histories, 0.5);
        analise(histories, step);
        System.out.println();
        print(histories);
        System.out.println();

        return null;

    }

    private static void print(List<?> histories) {
        for (Object history : histories) {
            System.out.println(history);
        }
    }

    private static void cleanUpDigits(List<History> histories, double threshold) {
        HashMap<Long, Long> counter = new HashMap<>();
        for (History history : histories) {
            for (Long aLong : history.getDigits()) {
                counter.put(aLong, counter.getOrDefault(aLong, 0L) + 1);
            }
        }
        int limit = (int) (histories.size() * threshold);

        for (Map.Entry<Long, Long> entry : counter.entrySet()) {
            if (entry.getValue() > limit) {
                removeDigit(histories, entry.getKey());
            }
        }

    }

    private static void removeDigit(List<History> histories, Long key) {
        for (History history : histories) {
            history.getDigits().remove(key);
        }
    }

    private static List<Change> analise(List<History> histories, int step) {
        System.out.println("step = [" + step + "]");
        System.out.println("----------------------");
        List<Change> changes = new ArrayList<>();
        int index = 0;
        for (int i = 0; i < histories.size(); i++) {
            History prev;
            History current;

            current = histories.get(i);
            if (i <= step - 1) {//skip first
                strategies.stream().forEach(x -> x.apply(current));
                continue;
            }
            strategies.stream().forEach(x -> x.apply(current));

            prev = histories.get(step - 1);

            List<Money> prevMoney = prev.getMeta().getMoneys();
            List<Money> currentMoney = current.getMeta().getMoneys();

            for (Money _current : currentMoney) {
                for (Money _prev : prevMoney) {
                    for (Money _prev_money_2 : prevMoney) {
                        if (_prev == _prev_money_2) {
                            continue;
                        }
                        if (_current.getValue().equals(_prev.getValue())) {
                            //skip equal values
                            continue;
                        }

                        if (MoneyParser.inRange(_current.getValue(), _prev.getValue(), interest(_prev_money_2.getValue(), INTEREST))) {
                            System.out.println("We found something: " +
                                    format(_current.getValue(), _current.getCurrency()) + " and \n\t" +
                                    format(_prev.getValue(), _prev.getCurrency()) + "\n\t" +
                                    format(_prev_money_2.getValue(), _prev_money_2.getCurrency()) + "\n\t"

                            );

                            System.out.println(current);
                            System.out.println(prev);
                            System.out.println();
                            System.out.println();
                            System.out.println();
                        }

                    }
                }
            }


        }
        return changes;
    }

    private static String format(Long value, String currency) {
        String valueString = value.toString();
        return valueString.substring(0, valueString.length() - 2) + "." + valueString.substring(valueString.length() - 2) + " " + currency;
    }

    private static long interest(Long prev_money_2, double interest) {
        return (long) (prev_money_2 * interest);
    }

}
