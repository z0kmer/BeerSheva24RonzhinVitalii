package telran.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public abstract class ListTest extends CollectionTest {
    List<Integer> list;

    @Override
    void setUp() {
        super.setUp();
        list = (List<Integer>) collection;
    }


    @Test
    void removeTest() {
        Integer removed = list.remove(0);
        assertEquals(3, removed);
        assertEquals(array.length - 1, list.size());
    }

    @Test
    void getTest() {
        assertEquals(3, list.get(0));
        assertEquals(20, list.get(2));
    }

    @Test
    void indexOfTest() {
        assertEquals(2, list.indexOf(20));
        assertEquals(-1, list.indexOf(200));
    }

    @Test
    void lastIndexOfTest() {
        assertEquals(4, list.lastIndexOf(10));
        assertEquals(-1, list.lastIndexOf(200));
    }
}
