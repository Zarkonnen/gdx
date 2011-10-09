package com.badlogic.helloworld;

public interface CoordinateSystem<T> {
	public boolean adjacent(T c0, T c1);
	public double dist(T c0, T c1);
	public Pt pos(T c);
	public T find(Pt p);
}
