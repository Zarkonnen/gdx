package com.zarkonnen.atactics;

import com.zarkonnen.atactics.commands.Command;
import com.zarkonnen.atactics.display.Display;
import com.zarkonnen.atactics.model.GameWorld;

public class ServerLink {
	public Display d;
	public GameWorld w;
	
	public void submitCommand(Command c) {
		c.run(d, w);
	}
}
