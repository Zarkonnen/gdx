package com.zarkonnen.atactics.display;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.zarkonnen.atactics.Sounds;
import com.zarkonnen.atactics.model.Mission;
import com.zarkonnen.atactics.model.GameWorld;
import com.zarkonnen.atactics.model.Pt;
import com.zarkonnen.atactics.model.Ship;
import com.zarkonnen.atactics.model.Stats;
import com.zarkonnen.atactics.model.SpaceTile;

public class Display {
	public String console = "";
	public Texture hexT;
	public Texture shipT;
	GameWorld w;
    public Stage stage;
    public Group scrollables;
    public Label consoleLabel;
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
		Mission m = w.get(GameWorld.MISSION);
		for (Pt tileIndex : m.get(Mission.MAP).pts()) {
			Pt screenPt = m.cs.pos(tileIndex);
			scrollables.addActor(new TileActor(m.get(Mission.MAP).get(tileIndex), this, w, screenPt.x, screenPt.y));
		}
		for (Pt tileIndex : m.get(Mission.MAP).pts()) {
			SpaceTile t = m.get(Mission.MAP).get(tileIndex);
			Pt screenPt = m.cs.pos(tileIndex);
			Ship ship = t.get(SpaceTile.SHIP);
			if (ship != null) {
				ShipActor sa = new ShipActor(ship, this, w, ship.get(Stats.DIRECTION), screenPt.x, screenPt.y);
				shipToActor.put(ship, sa);
				scrollables.addActor(sa);
			}
		}
		BitmapFont bmf = new BitmapFont();
		LabelStyle ls = new LabelStyle(bmf, new Color(1.0f, 1.0f, 1.0f, 1.0f));
		consoleLabel = new Label("", ls);
		stage.addActor(consoleLabel);
	}
	
	public void draw() {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		consoleLabel.setText(console);
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}
}
