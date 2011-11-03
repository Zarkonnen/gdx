package com.zarkonnen.atactics;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.utils.Logger;
import com.zarkonnen.atactics.display.Display;
import com.zarkonnen.atactics.model.Fight;
import com.zarkonnen.atactics.model.GameMap;
import com.zarkonnen.atactics.model.GameWorld;
import com.zarkonnen.atactics.model.Pt;
import com.zarkonnen.atactics.model.Ship;
import com.zarkonnen.atactics.model.SpaceTile;
import com.zarkonnen.atactics.model.stats.IO;
import com.zarkonnen.atactics.model.stats.LineInput;
import com.zarkonnen.atactics.model.stats.LineOutput;
import com.zarkonnen.atactics.model.stats.StatObject;

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
		//Gdx.input.setInputProcessor(new GestureDetector(input));
		Gdx.input.setInputProcessor(im);
		im.addProcessor(new GestureDetector(input));
		w = new GameWorld();
		w.f = new Fight();
		GameMap<SpaceTile> gm = new GameMap<SpaceTile>(4, 4, SpaceTile.class);
		w.f.set(Fight.MAP, gm);
		for (int y = 0; y < 4; y++) { for (int x = 0; x < 4; x++) {
			Pt p = new Pt(y, x);
			gm.set(p, new SpaceTile(p));
		}}
		gm.get(new Pt(1, 2)).set(SpaceTile.SHIP, new Ship());
		gm.get(new Pt(2, 2)).set(SpaceTile.SHIP, new Ship());
		d = new Display(w);
		c = new ScrollControls(input, d);
		ServerLink sl = new ServerLink();
		sl.d = d;
		sl.w = w;
		w.server = sl;
		im.addProcessor(d.stage);
		//Gdx.input.setInputProcessor(d.stage);
		try {
			StringWriter sw = new StringWriter();
			HashMap<String, StatObject> heads = new HashMap<String, StatObject>();
			heads.put("fight", w.f);
			new IO().write(new LineOutput(sw), heads);
			System.out.println(sw.getBuffer().toString());
			heads = new IO().read(new LineInput(new StringReader(sw.getBuffer().toString())));
			sw = new StringWriter();
			new IO().write(new LineOutput(sw), heads);
			System.out.println(sw.getBuffer().toString());
		} catch (Throwable e) { d.console = e.getClass().getSimpleName() + " " + e.getMessage(); }
	}
}