package com.zarkonnen.atactics.model.stats;

public abstract class HelperObject<T> extends StatObject {
	public abstract T createRealObject();
	public abstract void completeRealObject(T obj);
}
