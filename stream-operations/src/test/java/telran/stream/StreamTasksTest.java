package telran.stream;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class StreamTasksTest {
    @Test
    void shuffleTest() {
        Random random = new Random();
        int length = random.nextInt(100);
        int[] input = random.ints(length, 0, 100).toArray();

        int[] output = StreamTasks.shuffle(input);

        int[] sortedInput = Arrays.copyOf(input, input.length);
        int[] sortedOutput = Arrays.copyOf(output, output.length);
        Arrays.sort(sortedInput);
        Arrays.sort(sortedOutput);
        assertArrayEquals(sortedInput, sortedOutput);

        if (input.length > 1) {
            assertFalse(Arrays.equals(input, output));
        } else {
            assertTrue(Arrays.equals(input, output));
        }
    }
}
