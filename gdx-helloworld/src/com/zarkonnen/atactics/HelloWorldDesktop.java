package com.zarkonnen.atactics;
import java.io.StringWriter;
import java.util.HashMap;

import com.badlogic.gdx.backends.jogl.JoglApplication;
import com.zarkonnen.atactics.model.Ship;
import com.zarkonnen.atactics.model.ShipEffect;
import com.zarkonnen.atactics.model.ShipType;
import com.zarkonnen.atactics.model.Stats;
import com.zarkonnen.atactics.model.stats.IO;
import com.zarkonnen.atactics.model.stats.LineOutput;
import com.zarkonnen.atactics.model.stats.StatObject;
import static com.zarkonnen.atactics.collections.Utils.*;

public class HelloWorldDesktop {
	public static void main(String[] argv) {
		// qqDPS
		ShipType frigate = new ShipType();
		frigate.set(Stats.MAX_HP, 10);
		frigate.set(Stats.SPEED, 2);
		frigate.set(Stats.DAMAGE, 1);
		frigate.set(Stats.NUM_ATTACKS, 4);
		frigate.set(Stats.NAME, "Frigate");
		Ship s1 = new Ship();
		s1.set(Stats.SHIP_TYPE, frigate);
		s1.add(Stats.EFFECTS, l(ShipEffect.DAMAGED_ENGINES));
		Ship s2 = new Ship();
		s2.set(Stats.SHIP_TYPE, frigate);
		s2.subtract(Stats.MOVES_LEFT, 1);
		System.out.println(s1.get(Stats.SPEED));
		System.out.println(s1.explain(Stats.SPEED).explanation);
		StringWriter sw = new StringWriter();
		HashMap<String, StatObject> heads = new HashMap<String, StatObject>();
		heads.put("s1", s1);
		heads.put("s2", s2);
		new IO().write(new LineOutput(sw), heads);
		System.out.println(sw.getBuffer().toString());
		
		new JoglApplication(new HelloWorld(), "Hello World", 320, 480, false);
	}
}