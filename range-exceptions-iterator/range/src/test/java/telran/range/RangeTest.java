package telran.range;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Iterator;

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
    range.setPredicate(null); // No predicate, should iterate all numbers
    Iterator<Integer> iterator = range.iterator();
    for (int i = MIN; i <= MAX; i++) {
        assertTrue(iterator.hasNext());
        assertEquals(i, iterator.next());
    }
    assertFalse(iterator.hasNext());

    range.setPredicate(n -> n % 2 == 0); // Only even numbers
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
