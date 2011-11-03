package com.zarkonnen.atactics.model;

import com.zarkonnen.atactics.model.stats.StatObject;
import static com.zarkonnen.atactics.model.Stats.*;
import static com.zarkonnen.atactics.model.stats.GetStrategy.*;

public class ShipType extends StatObject {
	public ShipType() {
		stat(MAX_HP, var(MAX_HP));
		stat(SPEED, var(SPEED));
		stat(EVASION, withDefault(var(EVASION), constant(EVASION, 0)));
		stat(DAMAGE, withDefault(var(DAMAGE), constant(DAMAGE, 0)));
		stat(NUM_ATTACKS, withDefault(var(NUM_ATTACKS), constant(NUM_ATTACKS, 1)));
		stat(RANGE, var(RANGE));
	}
}
