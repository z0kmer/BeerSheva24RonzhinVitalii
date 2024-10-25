package telran.interview;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.EmptyStackException;

//All methods should have complexity O[1]
public class MyStackInt {
	private Deque<Integer> stack = new ArrayDeque<>();
    private Deque<Integer> maxStack = new ArrayDeque<>();
   
	public void push(int num) {
		//adds num into top of stack (last element)
		stack.push(num);
        if (maxStack.isEmpty() || num >= maxStack.peek()) {
            maxStack.push(num);
        }
	}
	public int pop() {
		//removes element from top of stack (last element)
		//returns being removed number
		//throws exception if the stack is empty
		if (stack.isEmpty()) {
            throw new EmptyStackException();
        }
        int value = stack.pop();
        if (value == maxStack.peek()) {
            maxStack.pop();
        }
        return value;
	}
	public int peek() {
		//returns last number
		//throws exception if the stack is empty
		if (stack.isEmpty()) {
            throw new EmptyStackException();
        }
        return stack.peek();
	}
	public boolean isEmpty() {
		//returns true if the stack is empty, otherwise false
		return stack.isEmpty();
	}
	public int getMaxElement() {
		//returns the max number from the stack
		//throws exception if the stack is empty
		if (maxStack.isEmpty()) {
            throw new EmptyStackException();
        }
        return maxStack.peek();
	}
}