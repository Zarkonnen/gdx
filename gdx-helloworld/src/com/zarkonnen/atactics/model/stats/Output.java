package com.zarkonnen.atactics.model.stats;

import java.util.HashMap;

public interface Output {
	public void write(String className, ID id, HashMap<Stat<?>, Object> mapping);
}
