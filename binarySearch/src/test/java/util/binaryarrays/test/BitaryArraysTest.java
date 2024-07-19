package util.binaryarrays.test;

import java.util.Comparator;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import static util.binaryarrays.BinaryArrays.binarySearch;
import static util.binaryarrays.BinaryArrays.find;
import static util.binaryarrays.BinaryArrays.insertSorted;
import static util.binaryarrays.BinaryArrays.isOneSwap;
import static util.binaryarrays.BinaryArrays.isOneSwapVTeacher;
import static util.binaryarrays.BinaryArrays.isOneSwapVTwo;
import static util.binaryarrays.BinaryArrays.removeIf;
import static util.binaryarrays.BinaryArrays.sort;


public class BitaryArraysTest {
    private static final int N_ELEMENTS = 1_000;
    int[] numbers = {-4, 3, 7, 10,  12,  13,  14};

    @Test
    void binarySearchSearch() {
        assertEquals(2, binarySearch(numbers, 7));
        assertEquals(0, binarySearch(numbers, -4));
        assertEquals(6, binarySearch(numbers, 14));
        assertEquals(6, binarySearch(numbers, 15));
        assertEquals(0, binarySearch(numbers, -6));
        assertEquals(0, binarySearch(numbers, 0));
    }

    @Test
    void insertSortedTest() {

        int[] expected0 = {-4, 3, 7, 10,  12, 12,  13,  14};
        assertArrayEquals(expected0, insertSorted(numbers, 12));
        int[] expected1 = {-6, -4, 3, 7, 10,  12,  13,  14};
        assertArrayEquals(expected1, insertSorted(numbers, -6));
        int[] expected2 = {-4, 3, 7, 10,  12,  13,  14, 15};
        assertArrayEquals(expected2, insertSorted(numbers, 15));
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
        assertEquals(false, isOneSwap(expected6));
    }

    @Test
    void isOneSwapVTwoTest() {
        assertEquals(false, isOneSwapVTwo(numbers));
        int [] expected4 = {0, -4, 3, 7, 10,  12, 13, 14};
        assertEquals(true, isOneSwapVTwo(expected4));
        int [] expected = {8, -4, 3, 7, 10,  12, 13,  14};
        assertEquals(false, isOneSwapVTwo(expected));
        int [] expected1 = {100, 3, 7, 10,  12,  13,  14, 0};
        assertEquals(true, isOneSwapVTwo(expected1));
        int [] expected2 = {12, 3, 7, 10,  -4,  13,  14, 100};
        assertEquals(true, isOneSwapVTwo(expected2));
        int [] expected3 = {-6, -4, 3, 8, 14,  12, 13, 10};
        assertEquals(true, isOneSwapVTwo(expected3));
        int [] expected5 = {-4, 3, 7, 11, 10,  12, 13,  14};
        assertEquals(true, isOneSwapVTwo(expected5));
        int [] expected6 = {12, 3, 7, 10,  11,  13,  -2, 100};
        assertEquals(false, isOneSwapVTwo(expected6));
    }

    @Test
    void isOneSwapTestVTeacher() {
        
    int [] arTrue1 = {1, 2, 10, 4, 7, 3};
    int [] arTrue2 = {1, 2, 10, 4, 4, 20};
    int [] arTrue3 = {1, 2, 10, 4, 20, 30};
    int [] arTrue4 = {10, 2, 1, 10, 20, 30};
    int [] arFalse1 = {20, 3, 3, 10, 20, 30};
    int []arFalse2 = {1, 2, 10, 4, 7, 5};
    int []arFalse3 = {1, 2, 3, 4, 5, 10};
    int [][] arraysTrue = {arTrue1, arTrue2, arTrue3, arTrue4};
    int [][] arraysFalse = {arFalse1, arFalse2, arFalse3};
    for(int i = 0; i < arraysTrue.length; i++) {
        assertTrue(isOneSwapVTeacher(arraysTrue[i]), "" + (i + 1));
    }
    for(int i = 0; i < arraysFalse.length; i++) {
        assertFalse(isOneSwapVTeacher(arraysFalse[i]), "" + (i + 1));
    }
    }

    @Test
    void sortAnyTypeTest(){
        String [] strings = {"lmn", "cfta", "w", "aa"};
        String [] expectedASCII ={"aa", "cfta", "lmn", "w"};
        String [] expectedLength = {"w", "aa", "lmn", "cfta"};
        sort(strings, new ComparatorASCII());
        assertArrayEquals(expectedASCII, strings);
        sort(strings, new ComparatorLength());
        assertArrayEquals(expectedLength, strings);
    }
    @Test
    void binarySearchObjectTest() {
        String [] strings ={"aa", "cfta", "lmn", "w"};
        Integer[] numbers = {1000, 2000, 3000};
        Comparator<String> compStrings = new ComparatorASCII();
        Comparator<Integer> compInteger = new ComparatorNumbers();
        //Existing keys
        assertEquals(1, binarySearch(strings, "cfta", compStrings));
        assertEquals(0, binarySearch(numbers, 1000, compInteger));
        assertEquals(2, binarySearch(numbers, 3000, compInteger));
        //Not existing keys
        assertEquals(-1, binarySearch(strings, "a", compStrings));
        assertEquals(-5, binarySearch(strings, "ww", compStrings));
        assertEquals(-2, binarySearch(numbers, 1500, compInteger));
    }
    @Test
    void binarySearchNoComparator() {
        String [] strings ={"aa", "cfta", "lmn", "w"};
        Person prs1 = new Person(10, "Vasya");
        Person prs2 = new Person(20, "Itay");
        Person prs3 = new Person(30, "Sara");
        Person [] persons = {
            prs1, prs2, prs3
        };
        assertEquals(1, binarySearch(strings, "cfta"));
        assertEquals(0, binarySearch(persons, prs1));
        assertEquals(-1, binarySearch(persons, new Person(5, "Serg")));
    }
    @Test
    void evenOddSortingTest() {
        Integer[] array = {7, -8, 10, -100, 13, -10, 99};
        Integer[] expected = {-100, -10, -8, 10, 99, 13, 7}; //even numbers in ascending order first, odd numbers in descending order after that
        sort(array, new EvenOddComparator());
        assertArrayEquals(expected, array);
    }
    @Test
    void findTest() {
        Integer[] array = {7, -8, 10, -100, 13, -10, 99};
        Integer [] expected = {7, 13, 99};
        assertArrayEquals(expected, find(array, new OddNumbersPredicate()));
    }

    @Test
    public void testRemoveIfWithComparator() {
        Integer[] array1 = {1, 2, 3, 4, 5};
        Predicate<Integer> predicate1 = n -> n % 2 == 0;
        Integer[] expected1 = {1, 3, 5};

        Integer[] array2 = {};
        Predicate<Integer> predicate2 = n -> n % 2 == 0;
        Integer[] expected2 = {};

        Integer[] array3 = {1, 3, 5};
        Predicate<Integer> predicate3 = n -> n % 2 == 0;
        Integer[] expected3 = {1, 3, 5};

        String[] array4 = {"Sam", "John", "Sara", "Mike"};
        Predicate<String> predicate4 = name -> name.startsWith("S");
        String[] expected4 = {"John", "Mike"};

        String[] array5 = {"Sam", "John", "Sara", "Mike"};
        Predicate<String> predicate5 = name -> name.startsWith("S");
        String[] expected5 = {"John", "Mike"};

        assertArrayEquals(expected1, removeIf(array1, predicate1));
        assertArrayEquals(expected2, removeIf(array2, predicate2));
        assertArrayEquals(expected3, removeIf(array3, predicate3));
        assertArrayEquals(expected4, removeIf(array4, predicate4));
        assertArrayEquals(expected5, removeIf(array5, predicate5));
    }
}
