package org.nazymko;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Created by nazymko.patronus@gmail.com
 */

public class ChronoTest {

    @Test
    public void testChronoFromPast() {
        long between = ChronoUnit.DAYS.between(LocalDateTime.now().minusDays(1), LocalDateTime.now());
        assert between == 1;
    }


    @Test
    public void testChronoForFuture() {
        long between = ChronoUnit.DAYS.between(LocalDateTime.now().plusDays(3), LocalDateTime.now());
        assert between == -3;

    }


    @Test
    public void testChronoForNow() {
        long between = ChronoUnit.DAYS.between(LocalDateTime.now(), LocalDateTime.now());
        assert between == 0;

    }

    @Test
    public void testChronoForTomorrow() {
        long between = ChronoUnit.DAYS.between(LocalDateTime.now().plusDays(1), LocalDateTime.now());
        assert between == 0;

    }
}