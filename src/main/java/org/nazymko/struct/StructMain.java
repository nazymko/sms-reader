package org.nazymko.struct;

import org.nazymko.Sms;
import org.nazymko.SmsReader;

import java.io.IOException;
import java.util.List;

/**
 * Created by Andrew Nazymko
 */
public class StructMain {
    public static void main(String[] args) throws IOException {
        List<Sms> open = new SmsReader().open();

    }
}
