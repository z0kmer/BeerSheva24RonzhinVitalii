package telran.interview;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class InterviewTasksTest {
    @Test
    void hasSumTwoTest() {
        int[] array1 = {1, 2, 3, 4, 5};
        int[] array2 = {10, 20, 30, 40, 50};
        int[] array3 = {-1, -2, -3, -4, -5};

        assertTrue(InterviewTasks.hasSumTwo(array1, 5));
        assertFalse(InterviewTasks.hasSumTwo(array1, 10));
        assertTrue(InterviewTasks.hasSumTwo(array2, 30));
        assertFalse(InterviewTasks.hasSumTwo(array2, 100));
        assertTrue(InterviewTasks.hasSumTwo(array3, -3));
        assertFalse(InterviewTasks.hasSumTwo(array3, 0));
    }
}