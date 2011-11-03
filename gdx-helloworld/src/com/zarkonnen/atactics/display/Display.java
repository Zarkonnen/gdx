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
import com.zarkonnen.atactics.model.ShipEffect;
import com.zarkonnen.atactics.model.Stats;
import com.zarkonnen.atactics.model.SpaceTile;
import com.zarkonnen.atactics.model.stats.Stat;
import static com.zarkonnen.atactics.model.Stats.*;


public class Display {
	public String console = "";
	public Texture hexT;
	public Texture shipT;
	GameWorld w;
    public Stage stage;
    public Group scrollables;
    public Group info;
    	public Label nameL;
    	public Label attackL;
    	public Label hpL;
    	public Label speedL;
    	public Label fxL;
    public Label consoleLabel;
    public HashMap<Ship, ShipActor> shipToActor = new HashMap<Ship, ShipActor>();
    public Sounds sounds = new Sounds();
    
    static final int LABEL_SPACING = 20;

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
		
		info = new Group("Info");
		stage.addActor(info);
			info.addActor(nameL = new Label("", ls));
				nameL.y = LABEL_SPACING * 4;
				info.addActor(attackL = new Label("", ls));
				attackL.y = LABEL_SPACING * 3;
				info.addActor(hpL = new Label("", ls));
				hpL.y = LABEL_SPACING * 2;
				info.addActor(speedL = new Label("", ls));
				speedL.y = LABEL_SPACING;
				info.addActor(fxL = new Label("", ls));
	}
	
	void updateInfo() {
		Ship s = w.get(GameWorld.MISSION).get(Mission.SELECTION);
		if (s == null) {
			
		} else {
			nameL.setText(s.get(Stat.NAME));
			int na = s.get(NUM_ATTACKS);
			attackL.setText(na == 0 ? "unarmed" : (s.get(DAMAGE) + " dmg" + (na > 1 ? "x" + na : "")) + " " + s.get(RANGE).name);
			hpL.setText(s.get(HP) + "/" + s.get(MAX_HP) + " HP");
			int evade = s.get(EVASION);
			speedL.setText(s.get(MOVES_LEFT) + "/" + s.get(SPEED) + " moves" + (evade > 0 ? evade + "% evade" : ""));
			StringBuilder fxSB = new StringBuilder();
			for (ShipEffect e : s.get(EFFECTS)) {
				fxSB.append(e.get(NAME));
				fxSB.append("\n");
			}
			fxL.setText(fxSB.toString());
		}
	}
	
	public void draw() {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		consoleLabel.setText(console);
		updateInfo();
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}
}
