package com.badlogic.helloworld;

public interface CoordinateSystem<T> {
	public int numberOfDirections();
	public boolean adjacent(T c0, T c1);
	public int direction(T c0, T c1);
	public T inDirection(T c, int direction);
	public double dist(T c0, T c1);
	public Pt pos(T c);
	public T find(Pt p);
}
