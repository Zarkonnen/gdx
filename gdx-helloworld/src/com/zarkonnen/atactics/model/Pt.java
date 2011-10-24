package com.zarkonnen.atactics.model;

public final class Pt {
	public static final Pt ORIGIN = new Pt(0, 0);
	
	public final int y;
	public final int x;
	
	public Pt(int y, int x) {
		this.y = y;
		this.x = x;
	}
	
	public boolean equals(Object o2) {
		if (!(o2 instanceof Pt)) { return false; }
		Pt p2 = (Pt) o2;
		return y == p2.y && x == p2.x;
	}
	
	public int hashCode() {
		return 71 + 17 * x + 431 * y;
	}
	
	public double dist(Pt p2) {
		return Math.sqrt((y - p2.y) * (y - p2.y) + (x - p2.x) * (x - p2.x));
	}
	
	public String toString() {
		return "{\"y\":" + y + "\", \"x\":" + x + "\"}";
	}
}
