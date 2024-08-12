package telran.time;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.chrono.MinguoDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.util.Locale;

import org.junit.jupiter.api.Test;

public class DateTimeTest {
    @Test
    void localDateTest() {
        LocalDate current = LocalDate.now();
        LocalDateTime currentTime = LocalDateTime.now();
        ZonedDateTime currentZonedTime = ZonedDateTime.now();
        Instant currentInstant = Instant.now();
        LocalTime currentLocalTime = LocalTime.now();
        System.out.printf("Current date is %s in ISO format \n",current);
        System.out.printf("Current date & time is %s in ISO format \n",currentTime);
        System.out.printf("Current zoned date & time is %s in ISO format \n",currentZonedTime);
        System.out.printf("Current instantr %s in ISO format \n",currentInstant);
        System.out.printf("Current time  %s in ISO format \n",currentLocalTime);
        System.out.printf("Current date is %s in dd/mm/yyyy \n",current.format(DateTimeFormatter.ofPattern("dd/MMMM/yyyy", Locale.forLanguageTag("he"))));
    }
    @Test
    void nextFriday13Test() {
        LocalDate current = LocalDate.of(2024,8,11);
        LocalDate expected = LocalDate.of(2024, 9, 13);
        TemporalAdjuster adjuster = new NextFriday13();
        assertEquals(expected, current.with(adjuster));
        assertThrows(RuntimeException.class, () -> LocalTime.now().with(adjuster));

    }

    @Test
    void pastTemporalDateProximityTest() {
        Temporal[] temporals = {
            LocalDate.of(1720, 7, 16),
            LocalDate.of(2023, 5, 10),
            MinguoDate.of(111, 3, 15),
            MinguoDate.of(111, 3, 15),
            LocalDate.of(2024, 1, 20)
        };
        TemporalAdjuster adjuster = new PastTemporalDateProximity(temporals);
        LocalDate current = LocalDate.of(2024, 8, 12);
        Temporal expected = adjuster.adjustInto(current);
        assertEquals(expected, current.with(adjuster));
    }
    

    @Test
    void pastTemporalDateProximityEmptyArrayTest() {
        Temporal[] temporals = {};
        TemporalAdjuster adjuster = new PastTemporalDateProximity(temporals);
        LocalDate current = LocalDate.of(2024, 8, 12);
        assertNull(current.with(adjuster));
    }
}
