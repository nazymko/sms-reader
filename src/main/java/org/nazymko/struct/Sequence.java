package org.nazymko.struct;

import org.nazymko.Sms;

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
