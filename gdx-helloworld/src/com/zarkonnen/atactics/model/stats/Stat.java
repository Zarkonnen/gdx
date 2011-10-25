package com.zarkonnen.atactics.model.stats;

public class Stat<T> {
	public static Stat<String> NAME = new Stat<String>("Name");
	
	public final String name;
	public Stat(String name) { this.name = name; }
	
	@SuppressWarnings("unchecked")
	public boolean equals(Object o2) {
		if (!(o2 instanceof Stat)) { return false; }
		Stat s2 = (Stat) o2;
		return name.equals(s2.name);
	}
	
	public int hashCode() {
		return name.hashCode();
	}
}
