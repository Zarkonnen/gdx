package com.zarkonnen.atactics.collections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Utils {
	public static <T> List<T> l(T... elements) {
		ArrayList<T> l = new ArrayList<T>();
		for (T e : elements) { l.add(e); }
		return Collections.unmodifiableList(l);
	}
}
