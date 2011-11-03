package com.zarkonnen.atactics.display;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.zarkonnen.atactics.model.Mission;
import com.zarkonnen.atactics.model.GameWorld;
import com.zarkonnen.atactics.model.Pt;
import com.zarkonnen.atactics.model.Ship;

public class ShipActor extends Actor {
	int[] diry = { 0,   0,   0,  0, 64,  64 };
	int[] dirx = { 192, 128, 64, 0, 64,  0  };
	
	final Ship ship;
	final Display d;
	final GameWorld w;
	public int direction = 0;
	
	public ShipActor(Ship ship, Display d, GameWorld w, int direction, int x, int y) {
		this.ship = ship;
		this.x = x;
		this.y = y;
		this.d = d;
		this.w = w;
		this.direction = direction;
		touchable = true;
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		Mission m = w.get(GameWorld.MISSION);
		if (m.get(Mission.SELECTION) == ship) {
			batch.setColor(0.8f, 1.0f, 0.8f, 1.0f);
		}
		batch.draw(d.shipT, x, y,
				dirx[direction],
				192 - diry[direction],
				64,
				64);
		if (m.get(Mission.SELECTION) == ship) {
			batch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		}
	}

	@Override
	public Actor hit(float x, float y) {
		Mission m = w.get(GameWorld.MISSION);
		if (x < 0 || y < 0 || x > m.csW || y > m.csH) { return null; }
		return m.cs.find(new Pt((int) y, (int) x)).equals(Pt.ORIGIN) ? this : null;
	}
	
	@Override
	public boolean touchDown(float x, float y, int ptr) {
		Mission m = w.get(GameWorld.MISSION);
		m.set(Mission.SELECTION, ship);
		return true;
	}
	@Override
	public void touchDragged(float arg0, float arg1, int arg2) { }
	@Override
	public void touchUp(float x, float y, int ptr) {}
}
