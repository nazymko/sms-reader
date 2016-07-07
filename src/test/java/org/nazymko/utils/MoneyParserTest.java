package org.nazymko.utils;

import org.assertj.core.api.Assertions;
import org.junit.Test;

/**
 * Created by nazymko.patronus@gmail.com
 */
public class MoneyParserTest {
    @org.junit.Test
    public void isDigits() throws Exception {

    }

    @org.junit.Test
    public void normalize1() throws Exception {
        Long normalize = MoneyParser.normalize("555.222");
        Assertions.assertThat(normalize).isEqualTo(55522);
    }


    @org.junit.Test
    public void normalize2() throws Exception {
        Long normalize = MoneyParser.normalize("555.2");
        Assertions.assertThat(normalize).isEqualTo(55520);
    }

    @org.junit.Test(expected = IllegalArgumentException.class)
    public void normalizeAndFail() throws Exception {
        MoneyParser.normalize("555R2");
    }

    @Test
    public void inRange0() throws Exception {
        boolean inRange = MoneyParser.inRange(50, 45, 10);
        Assertions.assertThat(inRange).isTrue();
    }

    @Test
    public void inRangeLowBorder() throws Exception {
        boolean inRange = MoneyParser.inRange(50, 40, 10);
        Assertions.assertThat(inRange).isTrue();
    }

    @Test
    public void inRangeTopBorder() throws Exception {
        boolean inRange = MoneyParser.inRange(50, 60, 10);
        Assertions.assertThat(inRange).isTrue();
    }

    @Test
    public void inRangeFalseLowerBorder() throws Exception {
        boolean inRange = MoneyParser.inRange(50, 39, 10);
        Assertions.assertThat(inRange).isFalse();
    }

    @Test
    public void inRangeFalseUpperBorder() throws Exception {
        boolean inRange = MoneyParser.inRange(50, 61, 10);
        Assertions.assertThat(inRange).isFalse();
    }
}