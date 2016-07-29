package org.nazymko.testing;

import org.nazymko.Sms;
import org.nazymko.SmsReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrew Nazymko
 */
public class StatApproach {

    public static void main(String[] args) throws IOException {
        List<Sms> open = new SmsReader().open();
        List<StatApproach> stats = new ArrayList<>();
        for (Sms sms : open) {
            for (String word : sms.getText().split(" ")) {

            }
        }
    }
}
