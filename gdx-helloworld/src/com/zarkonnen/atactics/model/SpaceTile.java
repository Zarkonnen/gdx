package com.zarkonnen.atactics.model;

import com.zarkonnen.atactics.model.stats.Stat;
import static com.zarkonnen.atactics.model.stats.GetStrategy.*;
import static com.zarkonnen.atactics.model.stats.ChangeStrategy.*;

public class SpaceTile extends Tile {
	public static final Stat<Ship> SHIP = new Stat<Ship>("Ship");
	
	public SpaceTile() { stat(SHIP, withDefault(var(SHIP), constant(SHIP, null)), setOnly(SHIP)); }
	
	public SpaceTile(Pt c) {
		super(c);
		stat(SHIP, withDefault(var(SHIP), constant(SHIP, null)), setOnly(SHIP));
	}
}
