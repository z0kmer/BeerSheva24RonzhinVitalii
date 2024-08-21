package telran.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

public abstract class CollectionTest {
    protected Collection<Integer> collection;
    Integer[] array = {3, -10, 20, 1, 10, 8, 100, 17};

    void setUp() {
        Arrays.stream(array).forEach(n -> collection.add(n));
    }

    @Test
    void addTest() {
        assertTrue(collection.add(200));
        assertTrue(collection.add(17));
        assertEquals(array.length + 2, collection.size());
    }

    @Test
    void sizeTest() {
        assertEquals(array.length, collection.size());
    }

    @Test
    void removeTest() {
        assertTrue (collection.remove(-10));
        assertFalse(collection.contains(-10));
        assertEquals(array.length - 1, collection.size());
    }

    @Test
    void isEmptyTest() {
        assertFalse(collection.isEmpty());
        collection.remove(3);
        collection.remove(-10);
        collection.remove(20);
        collection.remove(1);
        collection.remove(10);
        collection.remove(8);
        collection.remove(100);
        collection.remove(17);
        assertTrue(collection.isEmpty());
    }

    @Test
    void containsTest() {
        assertTrue(collection.contains(20));
        assertFalse(collection.contains(-20));
    }

    @Test
    void streamTest() {
        Stream<Integer> stream = collection.stream();
        Integer[] result = stream.toArray(Integer[] :: new);
        assertArrayEquals(array, result);
    }

    @Test
    void parallelStraem() {
        Stream<Integer> parallelStream = collection.parallelStream();
        Integer[] result = parallelStream.toArray(Integer[] :: new);
        Arrays.sort(result);
        Integer[] sortedArray = array.clone();
        Arrays.sort(sortedArray);
        assertArrayEquals(sortedArray, result);
    }
}
