package com.badlogic.helloworld.display;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ShipActor extends Actor {
	int[] diry = { 0,   0,   0,  0, 64,  64 };
	int[] dirx = { 192, 128, 64, 0, 64,  0  };
	
	final Display d;
	public int direction = 0;
	
	public ShipActor(Display d, int direction, int x, int y) {
		this.x = x;
		this.y = y;
		this.d = d;
		this.direction = direction;
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		batch.draw(d.shipT, x, y,
				dirx[direction],
				192 - diry[direction],
				64,
				64);
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
