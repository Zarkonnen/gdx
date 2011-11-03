package com.zarkonnen.atactics.model;

public enum Range {
	SHORT("Short", 20),
	MEDIUM("Medium", 10),
	LONG("Long", 5),
	MISSILE("Missile", 0);
	public final String name;
	public final int toHitDecreasePerDistance;
	
	private Range(String name, int toHitDecreasePerDistance) {
		this.name = name; this.toHitDecreasePerDistance = toHitDecreasePerDistance;
	}
}
