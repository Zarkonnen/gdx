package com.badlogic.helloworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Display {
	Texture hexT;
	Texture shipT;
	GameWorld w;
    int xScroll = 32;
    int yScroll = 23;
    private SpriteBatch batch;

	int[] diry = { 0,   0,   0,  0, 64,  64 };
	int[] dirx = { 192, 128, 64, 0, 64,  0  };

	public Display(GameWorld w) {
		this.w = w;
		hexT = new Texture(Gdx.files.internal("space.png"));
		shipT = new Texture(Gdx.files.internal("corvette.png"));
		batch = new SpriteBatch();
	}
	
	public void draw() {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
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
		batch.end();
	}
}
