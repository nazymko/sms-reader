package org.nazymko.stategy;

/**
 * Created by Andrew Nazymko
 */
public interface Strategy<T> {
    void apply(T mutableTarget);
}
