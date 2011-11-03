package com.zarkonnen.atactics.model;

import com.zarkonnen.atactics.ServerLink;
import com.zarkonnen.atactics.model.stats.Stat;
import com.zarkonnen.atactics.model.stats.StatObject;
import static com.zarkonnen.atactics.model.stats.GetStrategy.*;

public class GameWorld extends StatObject {
	public transient ServerLink sl;
	
	public static final Stat<Mission> MISSION = new Stat<Mission>("Mission");
	public GameWorld() {
		stat(MISSION, var(MISSION));
	}
}
