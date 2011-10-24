package com.zarkonnen.atactics;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.input.GestureDetector;
import com.zarkonnen.atactics.display.Display;
import com.zarkonnen.atactics.model.GameWorld;

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
		d = new Display(w);
		c = new ScrollControls(input, d);
		ServerLink sl = new ServerLink();
		sl.d = d;
		sl.w = w;
		w.server = sl;
		im.addProcessor(d.stage);
		//Gdx.input.setInputProcessor(d.stage);
	}
}