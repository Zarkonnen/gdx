package com.zarkonnen.atactics.model.stats;

public class Explanation<T> {
	public static enum Change { SAME, BETTER, WORSE; }
	public final T original;
	public final T value;
	public final String explanation;
	public final Change change;
	
	public Explanation(T original, T value, String explanation, Change change) {
		this.original = original;
		this.value = value;
		this.explanation = explanation;
		this.change = change;
	}
}
