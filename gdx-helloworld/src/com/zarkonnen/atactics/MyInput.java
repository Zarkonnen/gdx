package com.zarkonnen.atactics;

import com.badlogic.gdx.input.GestureDetector;
import com.zarkonnen.atactics.model.Pt;

public class MyInput implements GestureDetector.GestureListener {
	public Pt pan = new Pt(0, 0);

	@Override
	public boolean fling(float vx, float vy) {
		return false;
	}

	@Override
	public boolean longPress(int x, int y) {
		return false;
	}

	@Override
	public boolean pan(int x, int y, int dx, int dy) {
		pan = new Pt(dy, dx);
		return true;
	}

	@Override
	public boolean tap(int x, int y, int count) { return false; }

	@Override
	public boolean touchDown(int x, int y, int ptr) {
		return false;
	}

	@Override
	public boolean zoom(float oDist, float cDist) {
		return false;
	}	
}
