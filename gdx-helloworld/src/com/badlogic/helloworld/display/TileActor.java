package com.badlogic.helloworld.display;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.helloworld.model.Tile;

public class TileActor extends Actor {
	final Display d;
	final Tile t;
	
	public TileActor(Tile t, Display d, int x, int y) {
		this.x = x;
		this.y = y;
		this.t = t;
		this.d = d;
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		batch.draw(d.hexT, x, y);
	}

	@Override
	public Actor hit(float arg0, float arg1) { return null; }
	@Override
	public boolean touchDown(float arg0, float arg1, int arg2) { return false; }
	@Override
	public void touchDragged(float arg0, float arg1, int arg2) { }
	@Override
	public void touchUp(float arg0, float arg1, int arg2) { }
}
