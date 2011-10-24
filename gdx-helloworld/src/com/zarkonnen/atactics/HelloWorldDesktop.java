package com.zarkonnen.atactics;
import java.io.StringWriter;
import java.util.HashMap;

import com.badlogic.gdx.backends.jogl.JoglApplication;
import com.zarkonnen.atactics.model.Ship;
import com.zarkonnen.atactics.model.ShipType;
import com.zarkonnen.atactics.model.Stats;
import com.zarkonnen.atactics.model.stats.IO;
import com.zarkonnen.atactics.model.stats.LineOutput;
import com.zarkonnen.atactics.model.stats.StatObject;

public class HelloWorldDesktop {
	public static void main(String[] argv) {
		// qqDPS
		ShipType frigate = new ShipType();
		frigate.change(Stats.MAX_HP, 10);
		frigate.change(Stats.SPEED, 2);
		frigate.change(Stats.DAMAGE, 1);
		frigate.change(Stats.NUM_ATTACKS, 4);
		frigate.change(Stats.NAME, "Frigate");
		Ship s1 = new Ship();
		s1.change(Stats.SHIP_TYPE, frigate);
		Ship s2 = new Ship();
		s2.change(Stats.SHIP_TYPE, frigate);
		s2.change(Stats.MOVES_LEFT, -1);
		StringWriter sw = new StringWriter();
		HashMap<String, StatObject> heads = new HashMap<String, StatObject>();
		heads.put("s1", s1);
		heads.put("s2", s2);
		new IO().write(new LineOutput(sw), heads);
		System.out.println(sw.getBuffer().toString());
		
		new JoglApplication(new HelloWorld(), "Hello World", 320, 480, false);
	}
}