package telran.stream;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class StreamTasksTest {
    @Test
    void shuffleTest() {
        int[] original = {1, 2, 3, 4, 5};
        int[] shuffled = StreamTasks.shuffle(original);

        // Check that the shuffled array has the same elements as the original
        assertEquals(original.length, shuffled.length);
        assertTrue(Arrays.stream(original).allMatch(x -> Arrays.stream(shuffled).anyMatch(y -> y == x)));

        // Check that the shuffled array is not in the same order as the original
        assertFalse(Arrays.equals(original, shuffled));
    }

    @Test
    void shuffleEmptyArrayTest() {
        int[] emptyArray = {};
        int[] shuffled = StreamTasks.shuffle(emptyArray);

        // Check that the shuffled array is also empty
        assertEquals(0, shuffled.length);
    }
}
