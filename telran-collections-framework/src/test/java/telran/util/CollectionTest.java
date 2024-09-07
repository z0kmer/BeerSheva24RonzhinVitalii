package telran.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
<<<<<<< HEAD
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.stream.IntStream;
=======
import java.util.stream.Stream;
>>>>>>> origin/main

import org.junit.jupiter.api.Test;

public abstract class CollectionTest {
<<<<<<< HEAD
    private static final int N_ELEMENTS = 2_000_000;
    protected Collection<Integer> collection;
    Random random = new Random();
    Integer[] array = {3, -10, 20, 1, 10, 8, 100 , 17};
    void setUp() {
        Arrays.stream(array).forEach(collection::add);
    }
    @Test
    void removeIfTest() {
        assertTrue(collection.removeIf(n -> n % 2 == 0));
        assertFalse(collection.removeIf(n -> n % 2 == 0));
        assertTrue(collection.stream().allMatch(n -> n % 2 != 0));
    }
    @Test
    void clearTest() {
        collection.clear();
        assertTrue(collection.isEmpty());
    }
    @Test
    void addNonExistingTest() {
        assertTrue(collection.add(200));

        runTest(new Integer[]{3, -10, 20, 1, 10, 8, 100 , 17, 200});
    }
    @Test
    void addExistingTest() {
        assertTrue(collection.add(17));
        runTest(new Integer[]{3, -10, 20, 1, 10, 8, 100 , 17, 17});
    }
=======
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

>>>>>>> origin/main
    @Test
    void sizeTest() {
        assertEquals(array.length, collection.size());
    }
<<<<<<< HEAD
@Test
    void iteratorTest() {
        Integer[] actual = new Integer[array.length];
        int index = 0;
        Iterator<Integer> it = collection.iterator();

        while(it.hasNext()) {
            actual[index++] = it.next();
        }
        
        assertThrowsExactly(NoSuchElementException.class, it::next );
    }
    @Test
    void removeInIteratorTest(){
        Iterator<Integer> it = collection.iterator();
        assertThrowsExactly(IllegalStateException.class, () -> it.remove());
        Integer n = it.next();
        it.remove();
        it.next();
        it.next();
        it.remove();
        assertFalse(collection.contains(n));
        assertThrowsExactly(IllegalStateException.class, () -> it.remove());

    }

    protected void runTest(Integer[] expected) {
        assertArrayEquals(expected, collection.stream().toArray(Integer[]::new));
        assertEquals(expected.length, collection.size());
    }
    @Test
    void streamTest() {
        runTest(array);
    }
    @Test
    void removeTest() {
        Integer[] expected = {-10, 20, 1,  8, 100 };
        assertTrue(collection.remove(10));
        assertTrue(collection.remove(3));
        assertTrue(collection.remove(17));
        runTest(expected);
        assertFalse(collection.remove(10));
        assertFalse(collection.remove(3));
        assertFalse(collection.remove(17));
        clear();
        runTest(new Integer[0]);

    }
    private void clear() {
        Arrays.stream(array).forEach(n -> collection.remove(n));
    }
    @Test
    void isEmptyTest() {
        assertFalse(collection.isEmpty());
        clear();
        assertTrue(collection.isEmpty());
    }
    @Test
    void containsTest(){
        Arrays.stream(array).forEach(n -> assertTrue(collection.contains(n)));
        assertFalse(collection.contains(10000000));
    }
    @Test 
    void performanceTest() {
        collection.clear();
        IntStream.range(0, N_ELEMENTS).forEach(i -> collection.add(random.nextInt()));
        collection.removeIf(n -> n % 2 == 0);
        assertTrue(collection.stream().allMatch(n -> n % 2 != 0));
        collection.clear();
        assertTrue(collection.isEmpty());
    }

}
=======

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
>>>>>>> origin/main
