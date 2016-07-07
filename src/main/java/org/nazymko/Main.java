package org.nazymko;

import org.jetbrains.annotations.NotNull;
import org.nazymko.stategy.BalanceIsBiggestValueStrategy;
import org.nazymko.stategy.BillIsSmallestValueStrategy;
import org.nazymko.stategy.CurrencyMoneyStrategy;
import org.nazymko.stategy.Strategy;
import org.nazymko.utils.MoneyParser;

import java.io.IOException;
import java.util.*;

public class Main {

    public static final double INTEREST = 0.07;
    private static List<Strategy> strategies = Arrays.asList(
            new CurrencyMoneyStrategy(),
            new BalanceIsBiggestValueStrategy(),
            new BillIsSmallestValueStrategy()
    );

    public static void main(String[] args) throws IOException {

        read("sms.log", 1);
    }

    public static List<Change> read(String file, int step) throws IOException {
        List<Sms> slsList = new SmsReader().open(file);

        List<History> histories = convertIntoHistory(slsList);

        sortByTime(histories);
        cleanUpDigits(histories, 0.5);
        applyStrategies(histories, strategies);
        analise(histories, step);
        print(histories);

        return null;

    }

    @NotNull
    private static List<History> convertIntoHistory(List<Sms> slsList) {
        List<History> histories = new ArrayList<>();
        for (Sms sms : slsList) {
            histories.add(History.of(sms));
        }
        return histories;
    }

    private static void applyStrategies(List<History> histories, List<Strategy> strategies) {
        strategies.stream().forEach(strategy -> histories.stream().forEach(strategy::apply));
    }

    private static void sortByTime(List<History> histories) {
        Collections.sort(histories, (first, second) -> first.getSmsDate().isEqual(second.getSmsDate()) ? 0 : first.getSmsDate().isBefore(second.getSmsDate()) ? -1 : 1);
    }

    private static List<History> removeDuplicatesByMoney(List<History> histories) {
        HashMap<List<Money>, History> container = new HashMap<>();
        for (History history : histories) {
            container.put(history.getMeta().getMoneys(), history);
        }

        return new ArrayList<>(container.values());
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

        counter.entrySet().stream().filter(entry -> entry.getValue() > limit).forEach(entry -> {
            removeDigit(histories, entry.getKey());
        });

    }

    private static void removeDigit(List<History> histories, Long key) {
        for (History history : histories) {
            history.getDigits().remove(key);
        }
    }

    private static List<Change> analise(List<History> histories, int stepBack) {
        System.out.println("step = [" + stepBack + "]");
        System.out.println("----------------------");
        List<Change> changes = new ArrayList<>();
        for (int index = 0; index < histories.size(); index++) {
            History prev;
            History current;

            if (index <= stepBack) {//skip first
                continue;
            }
            current = histories.get(index);
            prev = histories.get(index - stepBack);

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

//                      _prev - probably it is the operation
//                      _current - current amount of money
                        if (_current.getType().equals(Money.Type.BALANCE)) {
                            if (MoneyParser.isOperationComponents(_prev.getValue(), _current.getValue(), _prev_money_2.getValue(), INTEREST, current.getCurrency())) {
                                System.out.println(current);
                                System.out.println(prev);
                                System.out.println("CURRENT");
                                info(_current);
                                System.out.println("PREVIOUS:");
                                info(_prev_money_2);
                                info(_prev);

                                if (Money.Type.BALANCE.equals(_current.getType())) {
                                    System.out.println("\tINTEREST:\t" + MoneyParser.format(
                                            bankInterest(_current, find(Money.Type.BALANCE, _prev_money_2, _prev), find(Money.Type.BILL, _prev_money_2, _prev)),
                                            current.getCurrency()));
                                }

                                System.out.println();
                            }

                        }
                    }
                }
            }


        }
        return changes;
    }

    private static Long bankInterest(Money currentBalance, Money prevBalance, Money prevBill) {
        return Math.abs(Math.abs(currentBalance.getValue() - prevBalance.getValue()) - Math.abs(prevBill.getValue()));
    }


    private static Money find(Money.Type type, Money... moneys) {
        for (Money money : moneys) {
            if (type.equals(money.getType())) {
                return money;
            }
        }
        throw new IllegalArgumentException(String.format("Type %s not found in given list", type));
    }


    private static void info(Money _current) {
        System.out.println("\t" + _current.getType() + " :\t" + MoneyParser.format(_current.getValue(), _current.getCurrency()));
    }

    private static long interest(Long prev_money_2, double interest) {
        return (long) (prev_money_2 * interest);
    }

}
