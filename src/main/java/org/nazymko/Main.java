package org.nazymko;

import org.nazymko.stategy.*;
import org.nazymko.utils.DateParser;
import org.nazymko.utils.MoneyParser;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.nazymko.utils.MoneyParser.format;

public class Main {

    public static final double INTEREST = 0.07;
    public static final String ZERO = "0.00";
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    private static List<Strategy> bulkStrategies = Arrays.asList(

    );

    private static long balance = 0;

    private static void balance(List<History> histories) {
        for (History history : histories) {
            System.out.println(String.format("[\"%s\",%s,%s]",
                    DateParser.format(history),
                    MoneyParser.format(MoneyParser.byType(Money.Type.BALANCE, history).getValue()),
                    MoneyParser.format(MoneyParser.byType(Money.Type.OPERATION, history).getValue())
            ));
            ;
        }
    }

    public static void main(String[] args) throws IOException {

        read(/*"sms.log",*/ 1);
    }

    public static List<Change> read(/*String file,*/ int step) throws IOException {
        List<History> histories = HistoryConverter.convertIntoHistory(new SmsReader().open(/*file*/));

        sortByTime(histories);

        apply(histories, new CleanUpRegularDigits());
        apply(histories, new CurrencyMoneyStrategy());
        apply(histories, new RemoveZeroMoneyStrategy());
        apply(histories, new TwoMoneyRequiredFilter());
        apply(histories, new TwoMoneysStrategy());
        apply(histories, new BalanceTransactionEqualFixStrategy());
        apply(histories, new CardIs4Digits());
        apply(histories, new RelatedOperationStrategy());
        apply(histories, new OperationTimeStrategy());
        apply(histories, new InitialWithoutRelatedIsApprove());
        apply(histories, new GroupByDatesStrategy());

        printAll(histories);
        return null;

    }

    private static void printAll(List<History> histories) {
        System.out.println(" [\"Date\", \"Balance\",\"Income\",\"Outcome\"],");
        for (History history : histories) {
            Money balance = MoneyParser.byType(Money.Type.BALANCE, history);
            Money operation = MoneyParser.byType(Money.Type.OPERATION, history);

            String balanceMoneyString = balance == null ? "0.00" : format(balance.getValue());
            String operationMoneyStringNegative = operation == null ? "0.00" : Operation.OUTCOME.equals(history.getMeta().getOperation()) ? format(operation.getValue()) : "0.00";
            String operationMoneyStringPositive = operation == null ? "0.00" : Operation.INCOME.equals(history.getMeta().getOperation()) ? format(operation.getValue()) : "0.00";

            System.out.println(String.format("[\"%s\",%s,%s,%s],", history.getSmsDate().format(FORMATTER), balanceMoneyString, operationMoneyStringPositive, operationMoneyStringNegative));
        }
    }

    private static void print(List<History> histories, Money.Type type) {
        histories.sort(new HistoryTimeComparator());
        for (History history : histories) {
            Money money = MoneyParser.byType(type, history);
            if (money != null) {
                System.out.println(String.format("[\"%s\",%s],", history.getSmsDate().format(FORMATTER), MoneyParser.format(money.getValue())));
            } else {
                System.out.println(String.format("[\"%s\",0.00],", history.getSmsDate().format(FORMATTER)));
            }
        }

    }

    private static void printMoneys(List<History> histories) {
        for (History history : histories) {
            System.out.println(history.getMeta().getMoneys());
        }
    }

    private static void groupBalance(List<History> histories) {
        History prev = null;
        for (int i = 0; i < histories.size(); i++) {
            History history = histories.get(i);

            Money balance = MoneyParser.byType(Money.Type.BALANCE, history);
            if (balance != null) {
                Main.balance = balance.getValue();
            }

//            printHistoryBalance(history);
        }
    }

    private static void printHistoryBalance(History history) {

        if (isEmptyHistory(history)) {
            System.out.println(String.format("[\"%s\", %s, 0.00,0.00],", DateParser.format(history), format(balance)));
            return;
        }

        Money balanceMoney = MoneyParser.byType(Money.Type.BALANCE, history);
        Money billMoney = MoneyParser.byType(Money.Type.OPERATION, history);
        if (balanceMoney == null || billMoney == null) {
            System.out.println("bill = " + billMoney);
            System.out.println("balance = " + balanceMoney);
            return;
        }
        String outcome = null;
        String income = null;

        if (history.getMeta().getOperation() == Operation.OUTCOME) {
            outcome = MoneyParser.format(billMoney.getValue(), "");
            income = ZERO;
        } else if (history.getMeta().getOperation() == Operation.INCOME) {
            outcome = ZERO;
            income = MoneyParser.format(billMoney.getValue(), "");
        }

        String balanceCurrency = MoneyParser.format(balanceMoney.getValue(), "");


        String date = history.getSmsDate().format(FORMATTER);
        System.out.println(String.format("[\"%s\", %5s, %5s,%5s],", date, format(balance), outcome, income));
    }


    private static boolean isEmptyHistory(History history) {
        return "null".equals(history.getSms());
    }

    private static void applySingleStrategy(List<History> histories, List<Strategy> strategies) {
        strategies.stream().forEach(strategy -> {
            log(strategy);
            histories.stream().forEach(strategy::apply);
        });
    }

    private static void applySingleStrategy(List<History> histories, Strategy strategy) {
        log(strategy);
        histories.stream().forEach(strategy::apply);
    }

    private static void apply(List<History> histories, Strategy strategy) {

        if (strategy instanceof BulkStrategy) {
            applyBulkStrategy(histories, (BulkStrategy) strategy);
        } else {
            applySingleStrategy(histories, strategy);
        }
    }

    private static void applyBulkStrategy(List<History> histories, List<Strategy> strategies) {
        strategies.stream().forEach(strategy -> {
            log(strategy);
            strategy.apply(histories);
        });
    }

    private static void applyBulkStrategy(List<History> histories, BulkStrategy strategy) {
        log(strategy);
        strategy.apply(histories);
    }

    private static void log(Strategy strategy) {
        System.err.println("STRATEGY: " + strategy.description());
    }

    private static void sortByTime(List<History> histories) {
        Collections.sort(histories, new HistoryTimeComparator());
    }

    private static List<History> removeDuplicatesByMoney(List<History> histories) {
        HashMap<List<Money>, History> container = new HashMap<>();
        for (History history : histories) {
            container.put(history.getMeta().getMoneys(), history);
        }

        return new ArrayList<>(container.values());
    }

    private static void print(List<? extends History> histories) {
        for (History history : histories) {
            System.out.println(history);
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
                                System.out.println("CURRENT : " + current);
                                System.out.println("PREVIOUS: " + prev);
                                System.out.println("CURRENT");
                                log(_current);
                                System.out.println("PREVIOUS:");
                                log(_prev_money_2);
                                log(_prev);

                                if (Money.Type.BALANCE.equals(_current.getType())) {
                                    System.out.println("\tINTEREST:\t" + MoneyParser.format(
                                            bankInterest(_current, find(Money.Type.BALANCE, prevMoney), find(Money.Type.OPERATION, prevMoney)),
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

    private static Money find(Money.Type type, List<Money> prevMoney) {
        return find(type, prevMoney.toArray(new Money[prevMoney.size()]));
    }

    private static Money find(Money.Type type, Money... moneys) {
        for (Money money : moneys) {
            if (type.equals(money.getType())) {
                return money;
            }
        }
        throw new IllegalArgumentException(String.format("Type %s not found in given list", type));
    }


    private static void log(Money _current) {
        System.out.println("\t" + _current.getType() + "\t:\t" + MoneyParser.format(_current.getValue(), _current.getCurrency()));
    }

    private static long interest(Long prev_money_2, double interest) {
        return (long) (prev_money_2 * interest);
    }

}
