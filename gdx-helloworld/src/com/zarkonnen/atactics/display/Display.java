package com.zarkonnen.atactics.display;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.zarkonnen.atactics.Sounds;
import com.zarkonnen.atactics.model.Fight;
import com.zarkonnen.atactics.model.GameWorld;
import com.zarkonnen.atactics.model.Pt;
import com.zarkonnen.atactics.model.Ship;
import com.zarkonnen.atactics.model.Stats;
import com.zarkonnen.atactics.model.SpaceTile;

public class Display {
	public Texture hexT;
	public Texture shipT;
	GameWorld w;
    public Stage stage;
    public Group scrollables;
    public HashMap<Ship, ShipActor> shipToActor = new HashMap<Ship, ShipActor>();
    public Sounds sounds = new Sounds();

	public Display(GameWorld w) {
		this.w = w;
		hexT = new Texture(Gdx.files.internal("space.png"));
		shipT = new Texture(Gdx.files.internal("corvette.png"));
		init();
	}
	
	public void init() {
		stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
		scrollables = new Group("Scrollables");
		stage.addActor(scrollables);
		scrollables.touchable = true;
		for (Pt tileIndex : w.f.get(Fight.MAP).pts()) {
			Pt screenPt = w.f.cs.pos(tileIndex);
			scrollables.addActor(new TileActor(w.f.get(Fight.MAP).get(tileIndex), this, w, screenPt.x, screenPt.y));
		}
		for (Pt tileIndex : w.f.get(Fight.MAP).pts()) {
			SpaceTile t = w.f.get(Fight.MAP).get(tileIndex);
			Pt screenPt = w.f.cs.pos(tileIndex);
			Ship ship = t.get(SpaceTile.SHIP);
			if (ship != null) {
				ShipActor sa = new ShipActor(ship, this, w, ship.get(Stats.DIRECTION), screenPt.x, screenPt.y);
				shipToActor.put(ship, sa);
				scrollables.addActor(sa);
			}
		}
	}
	
	public void draw() {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}
}
