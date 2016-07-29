package org.nazymko.testing;

import java.util.HashMap;

/**
 * Created by Andrew Nazymko
 */
public class Stat {
    private String sms;
    private HashMap<String, Integer> counter = new HashMap<>();

    public Stat(String sms) {
        this.sms = sms;
    }

    public String getSms() {
        return sms;
    }

    public void setSms(String sms) {
        this.sms = sms;
    }

    public void count(String word, Integer count) {
        counter.put(word, count);
    }
}
