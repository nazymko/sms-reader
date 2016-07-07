package org.nazymko.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Andrew Nazymko
 */
public class DateParser {

    private static final String[][] TEMPLATES = {
            {".*(\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}).*", "YYYY-MM-dd HH:mm:ss"},
    };
    private static final SimpleDateFormat[] FORMATS = new SimpleDateFormat[TEMPLATES.length];
    private static final Pattern[] PATTERNS = new Pattern[TEMPLATES.length];

    static {
        for (int i = 0; i < TEMPLATES.length; i++) {
            FORMATS[i] = new SimpleDateFormat(TEMPLATES[i][1]);
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
                return LocalDateTime.parse(matcher.group(1), DateTimeFormatter.ofPattern(TEMPLATES[i][1]));
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
}
