package com.zarkonnen.atactics.model.stats;

public class Stat<T> {
	public static Stat<String> NAME = new Stat<String>("Name", String.class);
	
	public final String name;
	public final Class<T> clazz;
	public Stat(String name, Class<T> clazz) { this.name = name; this.clazz = clazz; }
	
	@SuppressWarnings("unchecked")
	public boolean equals(Object o2) {
		if (!(o2 instanceof Stat)) { return false; }
		Stat s2 = (Stat) o2;
		return name.equals(s2.name) && clazz.equals(s2.clazz);
	}
	
	public int hashCode() {
		return 99 + name.hashCode() + 7 * clazz.hashCode();
	}
}
