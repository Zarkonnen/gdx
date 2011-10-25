package com.zarkonnen.atactics.model;

import com.zarkonnen.atactics.model.stats.Stat;
import com.zarkonnen.atactics.model.stats.StatObject;
import static com.zarkonnen.atactics.model.Stats.*;
import static com.zarkonnen.atactics.model.stats.GetStrategy.*;

public class ShipEffect extends StatObject {
	public ShipEffect() { super(true); }
	
	public ShipEffect(String name) {
		super(true);
		set(Stat.NAME, name);
		stat(SPEED, withDefault(var(SPEED), constant(SPEED, 0)));
		stat(EVASION, withDefault(var(EVASION), constant(EVASION, 0)));
		stat(DAMAGE, withDefault(var(DAMAGE), constant(DAMAGE, 0)));
		stat(NUM_ATTACKS, withDefault(var(NUM_ATTACKS), constant(NUM_ATTACKS, 0)));
	}
	
	public static ShipEffect DAMAGED_ENGINES = new ShipEffect("Damaged Engines"); static {
		DAMAGED_ENGINES.set(SPEED, -1);
	}
}
