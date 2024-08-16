package telran.range;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import telran.range.exceptions.OutOfRangeMaxValueException;
import telran.range.exceptions.OutOfRangeMinValueException;

public class RangeTest {
    private static final int MIN = 50;
    private static final int MAX = 100;
    Range range = Range.getRange(MIN, MAX);
    @Test
    void wrongRangeCtreatingTest() {
        assertThrowsExactly(IllegalArgumentException.class, () -> Range.getRange(MAX, MIN) );
    }
    @Test
    void rightNumberTest() throws Exception {
        range.checkNumber(55);
    }
    void wrongNumberTest() throws Exception {
        assertThrowsExactly(OutOfRangeMaxValueException.class,
        () -> range.checkNumber(MAX + 1));
        assertThrowsExactly(OutOfRangeMinValueException.class, () -> range.checkNumber(MIN - 1));
    }

    @Test
    void iteratorTest() {
        range.setPredicate(null); // Без условия, должны перебираться все числа
        Iterator<Integer> iterator = range.iterator();
        for (int i = MIN; i <= MAX; i++) {
            assertTrue(iterator.hasNext());
            assertEquals(i, iterator.next());
        }
        assertFalse(iterator.hasNext());

        range.setPredicate(n -> n % 2 == 0); // Только четные числа
        iterator = range.iterator();
        for (int i = MIN; i <= MAX; i++) {
            if (i % 2 == 0) {
                assertTrue(iterator.hasNext());
                assertEquals(i, iterator.next());
            }
        }
        assertFalse(iterator.hasNext());
    }
}
