package com.zarkonnen.atactics;

import com.zarkonnen.atactics.display.Display;
import com.zarkonnen.atactics.model.Pt;

public class ScrollControls {
	private MyInput input;
	private Display d;
	
	public ScrollControls(MyInput input, Display d) {
		this.input = input;
		this.d = d;
	}
	
	public void run() {
		d.scrollables.x += input.pan.x;
		d.scrollables.y -= input.pan.y;
		input.pan = Pt.ORIGIN;
	}
}
