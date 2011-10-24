package com.zarkonnen.atactics.model;

import java.util.ArrayList;

import com.zarkonnen.atactics.model.stats.StatObject;
import static com.zarkonnen.atactics.model.Stats.*;
import static com.zarkonnen.atactics.model.stats.GetStrategy.*;
import static com.zarkonnen.atactics.model.stats.ChangeStrategy.*;

public class Ship extends StatObject {
	public Ship() {
		stat(SHIP_TYPE, var(SHIP_TYPE));
		stat(HP, withDefault(var(HP), ref(SHIP_TYPE, MAX_HP)), integerWithBoundaries(HP, ref(SHIP_TYPE, MAX_HP), constant(HP, 0), ref(SHIP_TYPE, MAX_HP)));
		stat(MAX_HP, ref(SHIP_TYPE, MAX_HP));
		stat(DIRECTION, withDefault(var(DIRECTION), constant(DIRECTION, 0)));
		stat(MOVES_LEFT, withDefault(var(MOVES_LEFT), ref(SHIP_TYPE, SPEED)), integerWithBoundaries(MOVES_LEFT, ref(SHIP_TYPE, SPEED), constant(MOVES_LEFT, 0), ref(SHIP_TYPE, SPEED)));
		stat(SPEED, modifiedBy(ref(SHIP_TYPE, SPEED), var(EFFECTS), SPEED));
		stat(EVASION, modifiedBy(ref(SHIP_TYPE, EVASION), var(EFFECTS), EVASION));
		stat(DAMAGE, modifiedBy(ref(SHIP_TYPE, DAMAGE), var(EFFECTS), DAMAGE));
		stat(NUM_ATTACKS, modifiedBy(ref(SHIP_TYPE, NUM_ATTACKS), var(EFFECTS), NUM_ATTACKS));
		stat(EFFECTS, withDefault(var(EFFECTS), constant(EFFECTS, new ArrayList<ShipEffect>())), list(EFFECTS, constant(EFFECTS, new ArrayList<ShipEffect>())));
	}
}
