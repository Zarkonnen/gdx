package com.zarkonnen.atactics.model;

import static com.zarkonnen.atactics.model.stats.GetStrategy.var;

import java.util.HashMap;

import com.zarkonnen.atactics.model.stats.Stat;
import com.zarkonnen.atactics.model.stats.StatObject;

public class Tile extends StatObject {
	public static final Stat<Pt> COORD = new Stat<Pt>("Coord");
	
	public Tile() {
		stat(COORD, var(COORD));
	}
	
	public Tile(Pt c) {
		this();
		set(COORD, c);
	}
	
	HashMap<Stat<?>, Object> getStats() { return stats; }
}
