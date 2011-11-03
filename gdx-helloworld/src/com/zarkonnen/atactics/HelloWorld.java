package com.zarkonnen.atactics;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.input.GestureDetector;
import com.zarkonnen.atactics.display.Display;
import com.zarkonnen.atactics.model.Mission;
import com.zarkonnen.atactics.model.GameMap;
import com.zarkonnen.atactics.model.GameWorld;
import com.zarkonnen.atactics.model.Pt;
import com.zarkonnen.atactics.model.Range;
import com.zarkonnen.atactics.model.Ship;
import com.zarkonnen.atactics.model.ShipType;
import com.zarkonnen.atactics.model.SpaceTile;
import com.zarkonnen.atactics.model.stats.IO;
import com.zarkonnen.atactics.model.stats.LineInput;
import com.zarkonnen.atactics.model.stats.LineOutput;
import com.zarkonnen.atactics.model.stats.StatObject;
import static com.zarkonnen.atactics.model.Stats.*;


public class HelloWorld implements ApplicationListener {
	private GameWorld w;
	private Display d;
	private MyInput input;
	private ScrollControls c;
	private InputMultiplexer im = new InputMultiplexer();
	
	@Override
	public void resume() {
		
	}
	
	@Override
	public void resize(int width, int height) {
		
	}
	
	
	@Override
	public void render() {
		c.run();
		d.draw();
	}
	
	@Override
	public void pause() {
		
	}
	
	@Override
	public void dispose() {
		
	}
	
	@Override
	public void create() {
		input = new MyInput();
		Gdx.input.setInputProcessor(im);
		im.addProcessor(new GestureDetector(input));
		w = new GameWorld();
		Mission f = new Mission();
		w.set(GameWorld.MISSION, f);
		GameMap<SpaceTile> gm = new GameMap<SpaceTile>(4, 4, SpaceTile.class);
		f.set(Mission.MAP, gm);
		for (int y = 0; y < 4; y++) { for (int x = 0; x < 4; x++) {
			Pt p = new Pt(y, x);
			gm.set(p, new SpaceTile(p));
		}}
		ShipType corvette = new ShipType();
		corvette.set(NAME, "Corvette");
		corvette.set(SPEED, 3);
		corvette.set(RANGE, Range.MEDIUM);
		corvette.set(DAMAGE, 3);
		corvette.set(MAX_HP, 6);
		Ship s1 = new Ship();
		s1.set(SHIP_TYPE, corvette);
		s1.set(NAME, "Fruitbat");
		Ship s2 = new Ship();
		s2.set(SHIP_TYPE, corvette);
		s2.set(NAME, "Iguanadon");
		gm.get(new Pt(1, 2)).set(SpaceTile.SHIP, s1);
		gm.get(new Pt(2, 2)).set(SpaceTile.SHIP, s2);
		d = new Display(w);
		c = new ScrollControls(input, d);
		w.sl = new ServerLink();
		w.sl.d = d;
		w.sl.w = w;
		im.addProcessor(d.stage);
		
		//Gdx.input.setInputProcessor(d.stage);
		/*try {
			StringWriter sw = new StringWriter();
			HashMap<String, StatObject> heads = new HashMap<String, StatObject>();
			heads.put("gamestate", w);
			new IO().write(new LineOutput(sw), heads);
			System.out.println(sw.getBuffer().toString());
			heads = new IO().read(new LineInput(new StringReader(sw.getBuffer().toString())));
			sw = new StringWriter();
			new IO().write(new LineOutput(sw), heads);
			System.out.println(sw.getBuffer().toString());
		} catch (Throwable e) { d.console = e.getClass().getSimpleName() + " " + e.getMessage(); }*/
	}
}