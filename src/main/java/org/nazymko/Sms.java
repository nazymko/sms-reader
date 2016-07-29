package org.nazymko;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by Andrew Nazymko
 */
public class Sms {
    LocalDateTime time;
    String text;
    String[] data;

    public Sms(LocalDateTime time, String text) {
        this.time = time;
        this.text = text;
        if (text != null) {
            data = text.split(" ");
        }
    }

    public String[] getData() {
        return data;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "Sms{" +
                "time=" + time.format(DateTimeFormatter.ofPattern("dd HH:mm:ss ms")) +
                ", text='" + text + '\'' +
                "}\n";
    }
}
