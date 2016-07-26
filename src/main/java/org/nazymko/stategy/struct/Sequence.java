package org.nazymko.stategy.struct;

import org.nazymko.stategy.Sms;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrew Nazymko
 */
public class Sequence {
    List<Sms> group = new ArrayList<>();
    Sequence next;
    Sequence previous;
}
