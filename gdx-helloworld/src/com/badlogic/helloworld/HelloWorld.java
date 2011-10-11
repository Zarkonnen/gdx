package com.badlogic.helloworld;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.input.GestureDetector;

public class HelloWorld implements ApplicationListener {
	private GameWorld w;
	private Display d;
	private MyInput input;
	private Controls c;

	
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
		Gdx.input.setInputProcessor(new GestureDetector(input));
		w = new GameWorld();
		d = new Display(w);
		c = new Controls(input, w, d);
	}
}