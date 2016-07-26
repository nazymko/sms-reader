package org.nazymko.stategy.stategy;

import org.nazymko.stategy.History;
import org.nazymko.stategy.State;

import java.util.List;

/**
 * Created by 1 on 09.07.2016.
 */
public class RelatedOperationStrategy implements BulkStrategy {
    @Override
    public void apply(List<History> histories) {
        int baseOffset = 1;

        for (int index = baseOffset; index < histories.size(); index++) {
            int previousIndex = index - baseOffset;
            boolean relationWasFound = false;
            History history = histories.get(index);
            History prev = histories.get(previousIndex);
            if (isRelatedOperations(history, prev)) {
                prev.getMeta().setState(State.INITIAL);
                history.getMeta().setState(State.APPROVE);
                relationWasFound = true;

            } else {
                int reversedCounter = index - baseOffset;
                while (reversedCounter >= 0) {
                    prev = histories.get(reversedCounter);

                    if (isRelatedOperations(history, prev)) {
                        prev.getMeta().setState(State.INITIAL);
                        history.getMeta().setState(State.APPROVE);
                        relationWasFound = true;
                        break;
                    }
                    reversedCounter--;
                }
            }

            if (relationWasFound) {
                prev.setDependencies(history);
                history.setRelated(prev);
            }
        }
    }

    @Override
    public String description() {
        return "Bank send 'HOLD' & 'DEBIT' operations (initial and final approve) ";
    }

    private boolean isRelatedOperations(History history, History prev) {
        return prev.getMeta().getChange().equals(history.getMeta().getChange());
    }
}
