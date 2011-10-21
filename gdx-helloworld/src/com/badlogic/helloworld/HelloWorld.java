package com.badlogic.helloworld;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.helloworld.display.Display;
import com.badlogic.helloworld.model.GameWorld;

public class HelloWorld implements ApplicationListener {
	private GameWorld w;
	private Display d;
	private MyInput input;
	private Controls c;
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
		c = new Controls(input, w, d);
		ServerLink sl = new ServerLink();
		sl.d = d;
		sl.w = w;
		w.server = sl;
		im.addProcessor(d.stage);
		//Gdx.input.setInputProcessor(d.stage);
	}
}