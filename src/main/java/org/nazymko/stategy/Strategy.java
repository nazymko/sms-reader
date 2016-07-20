package org.nazymko.stategy;

import java.util.Collection;
import java.util.List;

/**
 * Created by Andrew Nazymko
 */
public interface Strategy<T> {

    void apply(T mutableTarget);

    String description();

}
