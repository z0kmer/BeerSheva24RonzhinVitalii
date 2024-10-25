package telran.interview;

import java.util.EmptyStackException;
import java.util.Random;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class MyStackIntTest {
private static final long N_ELEMENTS = 1000;
MyStackInt stack;
@BeforeEach
void setUp() {
	stack = new MyStackInt();
}
	@Test
	void testPush() {
		stack.push(100);
		assertEquals(100, stack.getMaxElement());
		stack.push(200);
		assertEquals(200, stack.getMaxElement());
		stack.push(200);
		assertEquals(200, stack.getMaxElement());
		stack.pop();
		assertEquals(200, stack.getMaxElement());
		
	}

	@Test
	void testPop() {
		assertThrowsExactly(EmptyStackException.class, () -> stack.pop());
		stack.push(100); stack.push(200);
		assertEquals(200, stack.pop());
		assertEquals(100, stack.getMaxElement());
	}

	@Test
	void testPeek() {
		assertThrowsExactly(EmptyStackException.class, () -> stack.peek());
		stack.push(100);
		assertEquals(100, stack.peek());
	}

	@Test
	void testIsEmpty() {
		assertTrue(stack.isEmpty());
		stack.push(0);
		assertFalse(stack.isEmpty());
	}

	@Test
	void testGetMaxElement() {
		int[] randomAr = new Random().ints().limit(N_ELEMENTS).toArray();
		TreeSet<Integer> treeSet = new TreeSet<>();
		assertThrowsExactly(EmptyStackException.class, () -> stack.getMaxElement());
		for(int num: randomAr) {
			stack.push(num);
			treeSet.add(num);
		}
		assertEquals(treeSet.last(), stack.getMaxElement());
	}

}