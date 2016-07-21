package org.nazymko.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * Created by nazymko.patronus@gmail.com
 */
public class DateParserTest {

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");

    @org.junit.Test
    public void difference24Hours() throws Exception {
        boolean isSameDay = DateParser.inSameDay(LocalDateTime.parse("2016/12/30 15:00", FORMATTER), LocalDateTime.parse("2016/12/29 15:00", FORMATTER));
        assert !isSameDay;
    }

    @org.junit.Test
    public void difference1Hour() throws Exception {
        boolean isSameDay = DateParser.inSameDay(LocalDateTime.parse("2016/12/30 23:30", FORMATTER), LocalDateTime.parse("2016/12/29 00:30", FORMATTER));
        assert !isSameDay;
    }

    @org.junit.Test
    public void inSameDay() throws Exception {
        boolean isSameDay = DateParser.inSameDay(LocalDateTime.parse("2016/12/30 23:59", FORMATTER), LocalDateTime.parse("2016/12/30 00:00", FORMATTER));
        assert isSameDay;
    }
}