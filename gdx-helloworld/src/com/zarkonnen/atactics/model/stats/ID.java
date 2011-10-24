package com.zarkonnen.atactics.model.stats;

public final class ID {
	public final String id;
	public ID(String id) { this.id = id; }
	public boolean equals(Object o2) {
		if (!(o2 instanceof ID)) { return false; }
		return ((ID) o2).id.equals(id);
	}
	public int hashCode() { return id.hashCode(); }
}
