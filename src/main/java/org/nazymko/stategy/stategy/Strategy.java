package org.nazymko.stategy.stategy;

/**
 * Created by Andrew Nazymko
 */
public interface Strategy<T> {

    void apply(T mutableTarget);

    String description();

}
