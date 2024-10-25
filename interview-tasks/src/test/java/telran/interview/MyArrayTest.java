package telran.interview;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class MyArrayTest {
private static final int N_ELEMENTS = 100_000_000;
private static final Integer ALL_VALUES = 5;
MyArray<Integer> myArray;
@BeforeEach
void setUp() {
	myArray = new MyArray<>(N_ELEMENTS);
	
}
	@Test
	void setAllTest() {
		runSetAllTest();
	}
	@Test
	void setGetTest() {
		int index = 10;
		int value = 1000;
		myArray.set(index, value);
		assertEquals(value, myArray.get(index));
		assertNull(myArray.get(index + 1));
		assertThrowsExactly(ArrayIndexOutOfBoundsException.class,
				() -> myArray.set(-index, value));
		assertThrowsExactly(ArrayIndexOutOfBoundsException.class,
				() -> myArray.set(N_ELEMENTS, value));
		assertThrowsExactly(ArrayIndexOutOfBoundsException.class,
				() -> myArray.get(-index));
		assertThrowsExactly(ArrayIndexOutOfBoundsException.class,
				() -> myArray.get(N_ELEMENTS));
		runSetAllTest();
		
	}
	private void runSetAllTest() {
		myArray.setAll(ALL_VALUES);
		for(int i = 0; i < N_ELEMENTS; i++) {
			assertEquals(ALL_VALUES, myArray.get(i));
		}
	}

}