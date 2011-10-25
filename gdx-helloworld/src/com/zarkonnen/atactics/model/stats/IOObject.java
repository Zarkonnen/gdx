package com.zarkonnen.atactics.model.stats;

import java.util.HashMap;

public class IOObject {
	public final String className;
	public final ID id;
	public final HashMap<Stat<?>, Object> mapping;
	
	public IOObject(String className, ID id, HashMap<Stat<?>, Object> mapping) {
		this.className = className;
		this.id = id;
		this.mapping = mapping;
	}
}
