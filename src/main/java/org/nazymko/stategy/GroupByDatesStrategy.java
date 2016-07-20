package org.nazymko.stategy;

import org.nazymko.History;
import org.nazymko.HistoryTimeComparator;
import org.nazymko.Money;
import org.nazymko.Operation;
import org.nazymko.utils.MoneyParser;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

import static org.nazymko.State.APPROVE;

/**
 * Created by Andrew Nazymko
 */
public class GroupByDatesStrategy implements BulkStrategy {
    @Override
    public void apply(List<History> result) {
        ArrayList<History> histories = new ArrayList<>(result);
        result.clear();
        histories.sort(new HistoryTimeComparator());

        HashMap<Operation, List<History>> groupedByOperation = new HashMap<>();

        Function<? super Operation, ? extends List<History>> listSupply = operation -> new ArrayList<>();

        for (History history : histories) {
            groupedByOperation.computeIfAbsent(history.getMeta().getOperation(), listSupply).add(history);
        }
        for (Operation operation : groupedByOperation.keySet()) {
            List<History> grouped = groupedByOperation.get(operation);
            History last = null;
            for (History next : grouped) {
                if (last == null) {
                    last = next;
                    continue;
                }
                if (inSameDay(last, next)) {
                    last = merge(last, next);
                } else {
                    result.add(last);
                    last = next;
                }
            }
        }
        result.sort(new HistoryTimeComparator());

    }

    private History merge(History last, History next) {
        if (isFromSameGroup(last, next)) {
            return APPROVE.equals(last.getMeta().getState()) ? last : next;
        }

        History _last = last.getSmsDate().isBefore(next.getSmsDate()) ? last : next;
        History _next = last.getSmsDate().isAfter(next.getSmsDate()) ? last : next;


        Money moneyFromLast = MoneyParser.byType(Money.Type.OPERATION, _last);
        Money moneyFromNext = MoneyParser.byType(Money.Type.OPERATION, _next);


        _next.getMeta().getMoneys().remove(moneyFromNext);

        Money money = new Money(moneyFromNext.getCurrency(), newValue(moneyFromLast, moneyFromNext));
        money.setType(moneyFromNext.getType());
        _next.getMeta().addMoney(money);


        return _next;
    }

    private long newValue(Money moneyFromLast, Money moneyFromNext) {
        return moneyFromNext.getValue() + moneyFromLast.getValue();
    }

    private boolean isFromSameGroup(History last, History next) {
        return last.getDependencies().contains(next) || next.getDependencies().contains(last);
    }

    private boolean inSameDay(History current, History history) {
        return ChronoUnit.DAYS.between(current.getSmsDate().truncatedTo(ChronoUnit.DAYS), history.getSmsDate().truncatedTo(ChronoUnit.DAYS)) == 0;
    }

    @Override
    public String description() {
        return "Group sms by day";
    }
}
