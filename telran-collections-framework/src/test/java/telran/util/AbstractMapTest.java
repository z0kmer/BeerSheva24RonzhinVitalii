package telran.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public abstract class AbstractMapTest {
    Integer[] keys = {1, -1};
    Map<Integer, Integer> map;

    @BeforeEach
    void setUp() {
        map.put(1, 10);
        map.put(-1, 20);
    }

    abstract <T> void runTest(T[] expected, T[] actual);

    @Test
    void putTest() {
        assertEquals(10, map.put(1, 30));
        assertEquals(30, map.get(1));
        assertEquals(null, map.put(2, 40));
        assertEquals(40, map.get(2));
    }

    @Test
    void containsKeyTest() {
        assertTrue(map.containsKey(1));
        assertTrue(map.containsKey(-1));
        assertTrue(!map.containsKey(2));
    }

    @Test
    void containsValueTest() {
        assertTrue(map.containsValue(10));
        assertTrue(map.containsValue(20));
        assertTrue(!map.containsValue(30));
    }

    @Test
    void keySetTest() {
        Set<Integer> expected = new HashSet<>(Arrays.asList(1, -1));
        Set<Integer> actual = map.keySet().stream().collect(Collectors.toSet());
        runTest(expected.toArray(new Integer[0]), actual.toArray(new Integer[0]));
    }

    @Test
    void valuesTest() {
        List<Integer> expected = Arrays.asList(10, 20);
        List<Integer> actual = map.values().stream().collect(Collectors.toList());
        expected.sort(null); // Сортируем ожидаемые значения
        actual.sort(null);   // Сортируем фактические значения
        runTest(expected.toArray(new Integer[0]), actual.toArray(new Integer[0]));
    }

    @Test
    void sizeTest() {
        assertEquals(2, map.size());
    }

    @Test
    void isEmptyTest() {
        assertTrue(!map.isEmpty());
    }
}