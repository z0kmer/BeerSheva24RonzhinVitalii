package util;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import static util.arrays.Arrays.add;
import static util.arrays.Arrays.insert;
import static util.arrays.Arrays.remove;
import static util.arrays.Arrays.search;

public class ArraysTest {

int[] numbers = {10, 7, 12, -4, 13, 3, 14};

@Test
void searchTest(){
    assertEquals(0, search(numbers, 10));
    assertEquals(6, search(numbers, 14));
    assertEquals(3, search(numbers, -4));
    assertEquals(100, 100);
}

void addTest(){

    int newNumber = 100;
    int [] expected = {10, 7, 12, -4, 13, 3, 14, newNumber} ;  
    assertArrayEquals(expected, add(numbers, newNumber));
}

void insertTest(){

    int newNumber = 100;
    int [] expected1 = {10, 7, 12, newNumber, -4, 13, 3, 14} ;  
    assertArrayEquals(expected1, insert(numbers, 3, newNumber));
    int [] expected2 = {newNumber, 10, 7, 12, -4, 13, 3, 14} ;  
    assertArrayEquals(expected2, insert(numbers, 0, newNumber));
    int [] expected3 = {10, 7, 12, -4, 13, 3, 14, newNumber} ;  
    assertArrayEquals(expected3, insert(numbers, 7, newNumber));
}

void removeTest(){

    int [] expected1 = {10, 7, 12, 13, 3, 14} ;  
    assertArrayEquals(expected1, remove(numbers, 3));
    int [] expected2 = {7, 12, -4, 13, 3, 14} ;  
    assertArrayEquals(expected2, remove(numbers, 0));
    int [] expected3 = {10, 7, 12, -4, 13, 3} ;  
    assertArrayEquals(expected3, remove(numbers, 6));
}


}
