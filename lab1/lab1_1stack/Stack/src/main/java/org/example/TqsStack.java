package org.example;

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class TqsStack<T> {

	private LinkedList<T> stack = new LinkedList<T>();
	private Integer bound = null;

	public TqsStack() {
		this.stack = new LinkedList<>();
	};

	public TqsStack(int bound) {
		this.stack = new LinkedList<>();
		this.bound = bound;
	};

	public void push(T element) {
		if ((this.bound != null) && (this.size() > this.bound - 1)) {
			throw new IllegalStateException("Error while pushing element!");

		} else {
			stack.add(element);
		}
	}

	public T pop() {
		if (this.stack.isEmpty()) {
			throw new NoSuchElementException("Error while popping element");

		} else {
			return this.stack.pop();
		}
	}

	public T peek() {
		if (this.stack.isEmpty()) {
			throw new NoSuchElementException("Error peaking element");

		} else {
			return this.stack.getLast();
		}
	}

	public int size() {
		return this.stack.size();
	}

	public boolean isEmpty() {
		return this.size() == 0;
	}

	public void clear() {
		this.stack.clear();
	}

}