package com.zarkonnen.atactics.commands;

import com.zarkonnen.atactics.display.Display;
import com.zarkonnen.atactics.model.GameWorld;

public interface Command {
	public void run(Display d, GameWorld w);
}
