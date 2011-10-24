package com.zarkonnen.atactics.model;

import com.zarkonnen.atactics.model.stats.StatObject;
import static com.zarkonnen.atactics.model.Stats.*;
import static com.zarkonnen.atactics.model.stats.GetStrategy.*;
import static com.zarkonnen.atactics.model.stats.ChangeStrategy.*;

public class Ship extends StatObject {
	public Ship() {
		statAndChange(SHIP_TYPE, var(SHIP_TYPE), set(SHIP_TYPE));
		statAndChange(HP, withDefault(var(HP), ref(SHIP_TYPE, MAX_HP)), addWithBoundaries(HP, ref(SHIP_TYPE, MAX_HP), constant(HP, 0), ref(SHIP_TYPE, MAX_HP)));
		stat(MAX_HP, ref(SHIP_TYPE, MAX_HP));
		statAndChange(DIRECTION, withDefault(var(DIRECTION), constant(DIRECTION, 0)), set(DIRECTION));
		statAndChange(MOVES_LEFT, withDefault(var(MOVES_LEFT), ref(SHIP_TYPE, SPEED)), addWithBoundaries(MOVES_LEFT, ref(SHIP_TYPE, SPEED), constant(MOVES_LEFT, 0), ref(SHIP_TYPE, SPEED)));
		stat(SPEED, ref(SHIP_TYPE, SPEED));
		stat(EVASION, ref(SHIP_TYPE, EVASION));
		stat(DAMAGE, ref(SHIP_TYPE, DAMAGE));
		stat(NUM_ATTACKS, ref(SHIP_TYPE, NUM_ATTACKS));
		stat(NAME, withDefault(var(NAME), constant(NAME, "?")));
	}
}
