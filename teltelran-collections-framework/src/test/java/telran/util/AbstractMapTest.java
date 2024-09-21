<<<<<<< HEAD
package telran.util;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import telran.util.Map.Entry;
@SuppressWarnings("unchecked")
public abstract class AbstractMapTest {
Integer[] keys = {1, -20, 10, 20, 12, 3};
Map<Integer, Integer> map;
void setUp() {
   Arrays.stream(keys).forEach(k -> map.put(k, k * k));
}
abstract <T> void runTest(T [] expected, T [] actual);
@Test
void keySetTest() {
Integer[] actual = map.keySet().stream().toArray(Integer[]::new);
runTest(keys, actual);
}
@Test
void entrySetTest() {
   
    Entry<Integer, Integer>[] entriesExpected = Arrays.stream(keys)
    .map(k -> new Entry<Integer,Integer>(k, k * k)).toArray(Entry[]::new);
    Entry<Integer, Integer>[] actualEntries = map.entrySet().stream().toArray(Entry[]::new);
    runTest(entriesExpected, actualEntries);
}
@Test
void getTest() {
    Arrays.stream(keys).forEach(k -> assertEquals(k * k, map.get(k)));
    assertNull(map.get(10000000));
}
@Test
void getOrDefaultTest() {
    Integer defaultValue = 0;
    Arrays.stream(keys).forEach(k -> assertEquals(k * k, map.getOrDefault(k, defaultValue)));
    assertEquals(defaultValue, map.getOrDefault(1000000, defaultValue));
}
@Test
void containsKeyTest() {
    Arrays.stream(keys).forEach(k -> assertTrue(map.containsKey(k)));
    assertFalse(map.containsKey(1000000));
}
@Test
void containsValueTest() {
    Arrays.stream(keys).forEach(k -> assertTrue(map.containsValue(k * k)));
    assertFalse(map.containsValue(1000000));
}
@Test
void valuesTest() {
    Integer[] expectedValues = Arrays.stream(keys).map(k -> k * k).sorted().toArray(Integer[]::new);
    Integer[] actualValues = map.values().stream().sorted().toArray(Integer[]::new);
    assertArrayEquals(expectedValues, actualValues);
}
@Test
void sizeTest(){
    assertEquals(keys.length, map.size());
}
@Test
void emptyTest(){
    assertFalse(map.isEmpty());
    map.entrySet().clear();
    assertTrue(map.isEmpty());
}
@Test
void putTest() {
    int key = 100;
    int wrongSquareValue = key * key / 10;
    int rightSquareValue = key * key; 
    assertNull(map.put(key, wrongSquareValue)); //new assotiation 
    int newSize = keys.length + 1;
    assertEquals(newSize, map.size());
    assertEquals(wrongSquareValue, map.put(key, rightSquareValue));
    assertEquals(newSize, map.size());

}
@Test
void putIfAbsentTest() {
    int keyExisting = keys[0];
    int newKey = 80;
    int newValue = newKey * newKey;
    assertEquals(keyExisting * keyExisting, map.putIfAbsent(keyExisting, 100000));
    assertEquals(keys.length, map.size()); //size has not changed
    assertNull(map.putIfAbsent(newKey, newValue));
    assertEquals(newValue, map.get(newKey));
    assertEquals(keys.length + 1, map.size());

}
=======
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
>>>>>>> 8f30c783dc7088979be7b15044264186a756433e
}