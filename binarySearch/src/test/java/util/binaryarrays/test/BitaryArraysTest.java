package util.binaryarrays.test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import static util.binaryarrays.BinaryArrays.binarySearch;
import static util.binaryarrays.BinaryArrays.insertSorted;
import static util.binaryarrays.BinaryArrays.isOneSwap;
public class BitaryArraysTest {
private static final int N_ELEMENTS = 1_000;
int[] numbers = {-4, 3, 7, 10,  12,  13,  14};

@Test
void binarySearchSearch() {
    assertEquals(2, binarySearch(numbers, 7));
    assertEquals(0, binarySearch(numbers, -4));
    assertEquals(6, binarySearch(numbers, 14));
    assertEquals(-1, binarySearch(numbers, 0));
}

@Test
void insertSortedTest() {

    int[] expected0 = {-4, 3, 7, 10,  12, 12,  13,  14};
    assertArrayEquals(expected0, insertSorted(numbers, 12));
    int[] expected1 = {-6, -4, 3, 7, 10,  12,  13,  14};
    assertArrayEquals(expected1, insertSorted(numbers, -6));
    int[] expected2 = {-4, 3, 7, 10,  12,  13,  14, 100};
    assertArrayEquals(expected2, insertSorted(numbers, 100));
}    

@Test
void isOneSwapTest() {
    assertEquals(false, isOneSwap(numbers));
    int [] expected = {8, -4, 3, 7, 10,  12, 13,  14};
    assertEquals(false, isOneSwap(expected));
    int [] expected1 = {100, 3, 7, 10,  12,  13,  14, 0};
    assertEquals(true, isOneSwap(expected1));
    int [] expected2 = {12, 3, 7, 10,  -4,  13,  14, 100};
    assertEquals(true, isOneSwap(expected2));
    int [] expected3 = {-6, -4, 3, 8, 14,  12, 13, 10};
    assertEquals(true, isOneSwap(expected3));
    int [] expected4 = {0, -4, 3, 7, 10,  12, 13, 14};
    assertEquals(true, isOneSwap(expected4));
    int [] expected5 = {-4, 3, 7, 11, 10,  12, 13,  14};
    assertEquals(true, isOneSwap(expected5));
    int [] expected6 = {12, 3, 7, 10,  11,  13,  -2, 100};
    assertEquals(false, isOneSwap(expected2));
}
}