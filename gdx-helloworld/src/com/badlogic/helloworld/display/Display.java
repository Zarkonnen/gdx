package com.badlogic.helloworld.display;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.helloworld.Sounds;
import com.badlogic.helloworld.model.GameWorld;
import com.badlogic.helloworld.model.Pt;
import com.badlogic.helloworld.model.Ship;
import com.badlogic.helloworld.model.Tile;

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
		for (Pt tileIndex : w.map.pts()) {
			Pt screenPt = w.cs.pos(tileIndex);
			scrollables.addActor(new TileActor(w.map.get(tileIndex), this, w, screenPt.x, screenPt.y));
		}
		for (Pt tileIndex : w.map.pts()) {
			Tile t = w.map.get(tileIndex);
			Pt screenPt = w.cs.pos(tileIndex);
			if (t.ship != null) {
				ShipActor sa = new ShipActor(t.ship, this, w, t.ship.direction, screenPt.x, screenPt.y);
				shipToActor.put(t.ship, sa);
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
