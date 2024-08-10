package telran.time;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class TimePointUnitTest {

//Tests for all being written methods
@Test
    void testCompareTo() {
        TimePoint p1 = new TimePoint(1, TimeUnit.HOUR);
        TimePoint p2 = new TimePoint(60, TimeUnit.MINUTE);
        TimePoint p3 = new TimePoint(61, TimeUnit.MINUTE);

        assertEquals(0, p1.compareTo(p2));
        assertTrue(p1.compareTo(p3) < 0);
        assertTrue(p3.compareTo(p1) > 0);
    }

    @Test
    void testEquals() {
        TimePoint p1 = new TimePoint(1, TimeUnit.HOUR);
        TimePoint p2 = new TimePoint(60, TimeUnit.MINUTE);
        TimePoint p3 = new TimePoint(61, TimeUnit.MINUTE);

        assertTrue(p1.equals(p2));
        assertFalse(p1.equals(p3));
    }

    @Test
    void testConvert() {
        TimePoint p1 = new TimePoint(1, TimeUnit.HOUR);
        TimePoint p2 = p1.convert(TimeUnit.MINUTE);

        assertEquals(60, p2.getAmount());
        assertEquals(TimeUnit.MINUTE, p2.getTimeUnit());
    }

    @Test
    void testWithAdjuster() {
        TimePoint p1 = new TimePoint(1, TimeUnit.HOUR);
        TimePointAdjuster adjuster = new PlusTimePointAdjuster(30, TimeUnit.MINUTE);
        TimePoint p2 = p1.with(adjuster);

        assertEquals(90, p2.convert(TimeUnit.MINUTE).getAmount());
    }

    @Test
    void testFutureProximityAdjuster() {
        TimePoint p1 = new TimePoint(1, TimeUnit.HOUR);
        TimePoint p2 = new TimePoint(2, TimeUnit.HOUR);
        TimePoint p3 = new TimePoint(3, TimeUnit.HOUR);
        TimePoint[] points = {p3, p1, p2};

        FutureProximityAdjuster adjuster = new FutureProximityAdjuster(points);
        TimePoint result = adjuster.adjust(p1);

        assertEquals(p2, result);
    }

    @Test
    void testTimeUnitBetween() {
        TimePoint p1 = new TimePoint(1, TimeUnit.HOUR);
        TimePoint p2 = new TimePoint(61, TimeUnit.MINUTE);

        assertEquals(60, TimeUnit.SECOND.between(p1, p2));
        assertEquals(1, TimeUnit.MINUTE.between(p1, p2));
        assertEquals(1 / 60f, TimeUnit.HOUR.between(p1, p2));
    }
}