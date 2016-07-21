package org.nazymko.utils;

import org.nazymko.History;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Andrew Nazymko
 */
public class DateParser {

    private static final String[][] TEMPLATES = {
            {"\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}", "yyyy-MM-dd HH:mm:ss"},//2016-07-03 21:44:06
            {"\\d{4}\\.\\d{2}\\.\\d{2}\\s\\d{2}:\\d{2}", "yyyy.MM.dd HH:mm"},//22.07.15 14:18
            {"\\d{2}\\.\\d{2}\\.\\d{4}\\s\\d{2}:\\d{2}", "dd.MM.yyyy HH:mm"},//22.07.2015 14:18
            {"\\d{2}\\.\\d{2}\\.\\d{2}\\s\\d{2}:\\d{2}", "dd.MM.yy HH:mm"},//22.07.15 14:18
            {"\\d{4}\\.\\d{2}\\.\\d{2}\\s\\d{2}:\\d{2}:\\d{2}", "yyyy.MM.dd HH:mm:ss"},//2015.07.15 14:18:ss
            {"\\d{4}\\.\\d{2}\\.\\d{2}\\s\\d{2}:\\d{2}", "yyyy.MM.dd HH:mm"}//2015.07.15 14:18
    };
    private static final DateTimeFormatter[] FORMATS = new DateTimeFormatter[TEMPLATES.length];
    private static final Pattern[] PATTERNS = new Pattern[TEMPLATES.length];
    private static final DateTimeFormatter DEFAULT_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    static {
        for (int i = 0; i < TEMPLATES.length; i++) {
            FORMATS[i] = DateTimeFormatter.ofPattern(TEMPLATES[i][1]);
            PATTERNS[i] = Pattern.compile(wrapTemplate(TEMPLATES[i][0]));
        }
    }

    private static String wrapTemplate(String template) {
        return String.format(".*(%s).*", template);
    }

    public static LocalDateTime parse(String text) {
        for (int i = 0; i < PATTERNS.length; i++) {
            Matcher matcher = PATTERNS[i].matcher(text);
            if (matcher.matches()) {
                return LocalDateTime.parse(matcher.group(1), FORMATS[i]);
            }
        }
        throw new IllegalArgumentException(text);

    }

    public static boolean hasDate(String sms) {
        for (Pattern pattern : PATTERNS) {
            if (pattern.matcher(sms).matches()) {
                return true;
            }
        }
        return false;

    }


    public static String format(History history) {
        return format(history.getSmsDate());
    }

    public static String format(LocalDateTime date) {
        return date.format(DEFAULT_DATE_FORMAT);
    }

    public static boolean inSameDay(LocalDateTime date1, LocalDateTime date2) {
        if (date1 == null || date2 == null) {
            return false;
        }

        long difference = between(date1, date2, ChronoUnit.DAYS);

        return difference == 0;
    }

    private static long between(LocalDateTime date1, LocalDateTime date2, ChronoUnit chronoUnit) {
        LocalDateTime localDateTime1 = date1.truncatedTo(chronoUnit);
        LocalDateTime localDateTime2 = date2.truncatedTo(chronoUnit);

        return Math.abs(ChronoUnit.DAYS.between(localDateTime1, localDateTime2));
    }
}
