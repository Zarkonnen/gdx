package com.zarkonnen.atactics.model;

import com.zarkonnen.atactics.CoordinateSystem;
import com.zarkonnen.atactics.model.stats.Stat;
import com.zarkonnen.atactics.model.stats.StatObject;
import static com.zarkonnen.atactics.model.stats.GetStrategy.*;
import static com.zarkonnen.atactics.model.stats.ChangeStrategy.*;

public class Fight extends StatObject {
	public final int csW = 64;
	public final int csH = 47;
	public static final Stat<GameMap<SpaceTile>> MAP = new Stat<GameMap<SpaceTile>>("Map");
	public static final Stat<Ship> SELECTION = new Stat<Ship>("Selection");
	public static final Stat<Ship> TARGET = new Stat<Ship>("Target");
	
	public CoordinateSystem<Pt> cs = new HexCoordinateSystem(csH, csW, 12);
	
	public Pt find(Ship s) {
		GameMap<SpaceTile> map = get(MAP);
		for (Pt p : map.pts()) {
			if (map.get(p).get(SpaceTile.SHIP) == s) {
				return p;
			}
		}
		return null;
	}
	
	public Fight() {
		stat(MAP, var(MAP));
		stat(SELECTION, var(SELECTION), setOnly(SELECTION));
		stat(TARGET, var(TARGET), setOnly(TARGET));
	}
}
