package org.nazymko.stategy;

import org.nazymko.History;
import org.nazymko.State;

/**
 * Created by 1 on 10.07.2016.
 */
public class InitialWithoutRelatedIsAproved implements Strategy<History> {


    @Override
    public void apply(History mutableTarget) {
        if (mutableTarget.getMeta().getState() == State.INITIAL && mutableTarget.getDependencies().isEmpty()) {
            mutableTarget.getMeta().setState(State.APPROVE);
        }

    }
}
