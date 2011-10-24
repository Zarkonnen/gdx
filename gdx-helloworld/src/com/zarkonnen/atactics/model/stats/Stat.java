package com.zarkonnen.atactics.model.stats;

public class Stat<T> {
	public static Stat<String> NAME = new Stat<String>("Name", String.class);
	
	public final String name;
	public final Class<T> clazz;
	public Stat(String name, Class<T> clazz) { this.name = name; this.clazz = clazz; }
}
