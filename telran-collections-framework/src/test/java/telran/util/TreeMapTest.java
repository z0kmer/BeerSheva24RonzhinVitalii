package telran.util;

import static org.junit.jupiter.api.Assertions.*;

<<<<<<< HEAD
import java.util.Arrays;

=======
>>>>>>> origin/main
import org.junit.jupiter.api.BeforeEach;

public class TreeMapTest extends AbstractMapTest {

    @Override
    <T> void runTest(T[] expected, T[] actual) {
<<<<<<< HEAD
        T[] expectedSorted = Arrays.copyOf(expected, expected.length);
        Arrays.sort(expectedSorted);
        assertArrayEquals(expectedSorted, actual);
=======
        assertArrayEquals(expected, actual);
>>>>>>> origin/main

    }
    
    @BeforeEach
    @Override
    void setUp() {
        map = new TreeMap<>();
        super.setUp();
    }

}