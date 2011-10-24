package com.zarkonnen.atactics.model;

import com.zarkonnen.atactics.model.stats.StatObject;
import static com.zarkonnen.atactics.model.Stats.*;
import static com.zarkonnen.atactics.model.stats.GetStrategy.*;
import static com.zarkonnen.atactics.model.stats.ChangeStrategy.*;

public class ShipType extends StatObject {
	public ShipType() {
		statAndChange(MAX_HP, var(MAX_HP), set(MAX_HP));
		statAndChange(SPEED, var(SPEED), set(SPEED));
		statAndChange(EVASION, withDefault(var(EVASION), constant(EVASION, 0)), set(EVASION));
		statAndChange(DAMAGE, withDefault(var(DAMAGE), constant(DAMAGE, 0)), set(DAMAGE));
		statAndChange(NUM_ATTACKS, withDefault(var(NUM_ATTACKS), constant(NUM_ATTACKS, 1)), set(NUM_ATTACKS));
		statAndChange(NAME, var(NAME), set(NAME));
	}
}
