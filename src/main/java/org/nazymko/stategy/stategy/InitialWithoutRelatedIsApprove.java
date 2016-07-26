package org.nazymko.stategy.stategy;

import org.nazymko.stategy.History;
import org.nazymko.stategy.State;

/**
 * Created by 1 on 10.07.2016.
 */
public class InitialWithoutRelatedIsApprove implements Strategy<History> {

    @Override
    public void apply(History mutableTarget) {
        if (mutableTarget.getMeta().getState() == State.INITIAL && mutableTarget.getDependencies().isEmpty()) {
            mutableTarget.getMeta().setState(State.APPROVE);
        }

    }

    @Override
    public String description() {
        return "Force mark single sms with 'initial' status as 'approve'";
    }
}
