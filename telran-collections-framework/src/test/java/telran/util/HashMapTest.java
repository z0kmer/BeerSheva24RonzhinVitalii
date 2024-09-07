package telran.util;

import static org.junit.jupiter.api.Assertions.*;

<<<<<<< HEAD
import java.util.Arrays;

=======
>>>>>>> origin/main
import org.junit.jupiter.api.BeforeEach;

public class HashMapTest extends AbstractMapTest {

    @Override
    <T> void runTest(T[] expected, T[] actual) {
<<<<<<< HEAD
        T[] expectedSorted = Arrays.copyOf(expected, expected.length);
        Arrays.sort(expectedSorted);
        T[] actualSorted = Arrays.copyOf(actual, actual.length);
        Arrays.sort(actualSorted);
        assertArrayEquals(expectedSorted, actualSorted);
=======
        assertArrayEquals(expected, actual);
>>>>>>> origin/main

    }
    
    @BeforeEach
    @Override
    void setUp() {
        map = new HashMap<>();
        super.setUp();
    }

}