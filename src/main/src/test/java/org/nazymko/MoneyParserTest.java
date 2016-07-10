package org.nazymko;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.nazymko.utils.MoneyParser;

/**
 * Created by nazymko.patronus@gmail.com
 */
public class MoneyParserTest {
    @Test
    public void isDigits() throws Exception {

    }

    @Test
    public void normalize1() throws Exception {
        Long normalize = MoneyParser.normalize("555.222");
        Assertions.assertThat(normalize).isEqualTo(55522);
    }


    @Test
    public void normalize2() throws Exception {
        Long normalize = MoneyParser.normalize("555.2");
        Assertions.assertThat(normalize).isEqualTo(55520);
    }

    @Test(expected = IllegalArgumentException.class)
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

    @Test
    public void isDigitsGeneral() throws Exception {
        assert MoneyParser.isDigits("-2.01");
        assert MoneyParser.isDigits("2.01");
        assert MoneyParser.isDigits("0");
        assert MoneyParser.isDigits("1");
        assert MoneyParser.isDigits("-1");
    }


    @Test
    public void notIsDigitsGeneral() throws Exception {
        assert !MoneyParser.isDigits("--2.01");
        assert !MoneyParser.isDigits("2..01");
        assert !MoneyParser.isDigits("1.s");
        assert !MoneyParser.isDigits("ooo.00");
        assert !MoneyParser.isDigits("-1.-11");
    }
}