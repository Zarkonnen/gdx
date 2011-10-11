package com.badlogic.helloworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class Controls {
	private MyInput input;
	private GameWorld w;
	private Display d;
	
    private Sound thruster;

	
	public Controls(MyInput input, GameWorld w, Display d) {
		this.input = input;
		this.w = w;
		this.d = d;
		thruster = Gdx.audio.newSound(Gdx.files.internal("thruster.ogg"));
	}
	
	public void run() {
		int h = Gdx.graphics.getHeight();
		Pt touchL = input.clickP;
		input.clickP = null;
		touchL = touchL == null ? null : new Pt(h - touchL.y + d.yScroll, touchL.x + d.xScroll);
		d.yScroll += input.pan.y;
		d.xScroll -= input.pan.x;
		input.pan = Pt.ORIGIN;
		Pt clP = touchL == null ? null : w.cs.find(touchL);
		if (clP != null && w.map.has(clP)) {
			Tile t = w.map.get(clP);
			if (t.ship != null) {
				w.selection = clP;
			} else {
				int dir = w.selection == null ? -1 : w.cs.direction(w.selection, clP);
				if (dir != -1) {
					Tile selT = w.map.get(w.selection);
					thruster.play();
					selT.ship.direction = dir;
					t.ship = selT.ship;
					selT.ship = null;
					w.selection = clP;
				}
			}
		}
	}
}
