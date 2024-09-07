package telran.util;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;

public class LinkedHashSetTest extends SetTest{
        @BeforeEach
    @Override
    void setUp() {
        collection = new LinkedHashSet<>();
        super.setUp();
    }
    @Override
    protected void runTest(Integer[] expected) {
        
        Integer[] actual = collection.stream().toArray(Integer[]::new);
        assertArrayEquals(expected, actual);
        assertEquals(expected.length, collection.size());
    }
}