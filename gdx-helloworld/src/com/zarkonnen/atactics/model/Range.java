package com.zarkonnen.atactics.model;

public enum Range {
	SHORT("short range", 20),
	MEDIUM("med. range", 10),
	LONG("long range", 5),
	MISSILE("missile", 0);
	public final String name;
	public final int toHitDecreasePerDistance;
	
	private Range(String name, int toHitDecreasePerDistance) {
		this.name = name; this.toHitDecreasePerDistance = toHitDecreasePerDistance;
	}
}
