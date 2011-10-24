package com.zarkonnen.atactics.display;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.zarkonnen.atactics.commands.MoveCommand;
import com.zarkonnen.atactics.model.GameWorld;
import com.zarkonnen.atactics.model.Pt;
import com.zarkonnen.atactics.model.Tile;

public class TileActor extends Actor {
	final Display d;
	final GameWorld w;
	final Tile t;
	
	public TileActor(Tile t, Display d, GameWorld w, int x, int y) {
		this.x = x;
		this.y = y;
		this.t = t;
		this.d = d;
		this.w = w;
		this.touchable = true;
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		batch.draw(d.hexT, x, y);
	}

	@Override
	public Actor hit(float x, float y) {
		if (x < 0 || y < 0 || x > w.csW || y > w.csH) { return null; }
		return w.cs.find(new Pt((int) y, (int) x)).equals(Pt.ORIGIN) ? this : null;
	}
	
	@Override
	public boolean touchDown(float x, float y, int ptr) { return true; }
	
	@Override
	public void touchDragged(float x, float y, int ptr) {}
	
	@Override
	public void touchUp(float x, float y, int ptr) {
		if (w.selection != null) {
			Pt shipC = w.find(w.selection);
			int dir = w.cs.direction(shipC, t.c);
			if (dir != -1) {
				w.server.submitCommand(new MoveCommand(w.selection, t.c));
			}
		}
	}
}
