package com.badlogic.helloworld.display;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.helloworld.model.GameWorld;
import com.badlogic.helloworld.model.Pt;
import com.badlogic.helloworld.model.Ship;
import com.badlogic.helloworld.model.Tile;

public class Display {
	public Texture hexT;
	public Texture shipT;
	GameWorld w;
    private Stage stage;
    public Group scrollables;
    public HashMap<Ship, ShipActor> shipToActor = new HashMap<Ship, ShipActor>();

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
		for (Pt tileIndex : w.map.pts()) {
			Pt screenPt = w.cs.pos(tileIndex);
			scrollables.addActor(new TileActor(w.map.get(tileIndex), this, screenPt.x, screenPt.y));
		}
		for (Pt tileIndex : w.map.pts()) {
			Tile t = w.map.get(tileIndex);
			Pt screenPt = w.cs.pos(tileIndex);
			if (t.ship != null) {
				ShipActor sa = new ShipActor(this, t.ship.direction, screenPt.x, screenPt.y);
				shipToActor.put(t.ship, sa);
				scrollables.addActor(sa);
			}
		}
	}
	
	public void draw() {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		stage.draw();
		
		/*Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		batch.begin();
		for (Pt c : w.map.pts()) {
			Pt l = w.cs.pos(c);
			batch.draw(hexT, l.x - xScroll, l.y - yScroll);
			Tile t = w.map.get(c);
			if (t.ship != null) {
				if (c.equals(w.selection)) {
					batch.setColor(0.8f, 1.0f, 0.8f, 1.0f);
				}
				batch.draw(shipT, l.x - xScroll, l.y - yScroll,
						dirx[t.ship.direction],
						192 - diry[t.ship.direction],
						64,
						64);
				batch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
			}
		}
		batch.end();*/
	}
}
