package telran.stream;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Random;

import org.junit.jupiter.api.Test;

public class StreamTasksTest {
    @Test
    void shuffleTest() {
        Random random = new Random();
        int[] original = random.ints(100, 0, 1000).toArray();
        int[] copyOfOriginal = Arrays.copyOf(original, original.length);
        int[] shuffled = StreamTasks.shuffle(original);
        
        // Check if both arrays contain the same elements
        int[] sortedOriginal = Arrays.copyOf(copyOfOriginal, copyOfOriginal.length);
        int[] sortedShuffled = Arrays.copyOf(shuffled, shuffled.length);
        Arrays.sort(sortedOriginal);
        Arrays.sort(sortedShuffled);
        assertArrayEquals(sortedOriginal, sortedShuffled);
        // Check if the arrays are not in the same order
        assertFalse(Arrays.equals(copyOfOriginal, shuffled));

        // Check empty array
        int[] emptyArray = {};
        assertArrayEquals(emptyArray, StreamTasks.shuffle(emptyArray));

        // Check single element array
        int[] singleElementArray = {42};
        assertArrayEquals(singleElementArray, StreamTasks.shuffle(singleElementArray));
    }
}
