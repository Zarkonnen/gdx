package com.zarkonnen.atactics.display;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.zarkonnen.atactics.commands.MoveCommand;
import com.zarkonnen.atactics.model.Mission;
import com.zarkonnen.atactics.model.GameWorld;
import com.zarkonnen.atactics.model.Pt;
import com.zarkonnen.atactics.model.SpaceTile;

public class TileActor extends Actor {
	final Display d;
	final GameWorld w;
	final SpaceTile t;
	
	public TileActor(SpaceTile t, Display d, GameWorld w, int x, int y) {
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
		Mission m = w.get(GameWorld.MISSION);
		if (x < 0 || y < 0 || x > m.csW || y > m.csH) { return null; }
		return m.cs.find(new Pt((int) y, (int) x)).equals(Pt.ORIGIN) ? this : null;
	}
	
	@Override
	public boolean touchDown(float x, float y, int ptr) { return true; }
	
	@Override
	public void touchDragged(float x, float y, int ptr) {}
	
	@Override
	public void touchUp(float x, float y, int ptr) {
		Mission m = w.get(GameWorld.MISSION);
		if (m.get(Mission.SELECTION) != null) {
			Pt shipC = m.find(m.get(Mission.SELECTION));
			int dir = m.cs.direction(shipC, t.get(SpaceTile.COORD));
			if (dir != -1) {
				w.sl.submitCommand(new MoveCommand(m.get(Mission.SELECTION), t.get(SpaceTile.COORD)));
			}
		}
	}
}
