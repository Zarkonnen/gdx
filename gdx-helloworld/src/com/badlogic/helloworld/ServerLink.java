package com.badlogic.helloworld;

import com.badlogic.helloworld.commands.Command;
import com.badlogic.helloworld.display.Display;
import com.badlogic.helloworld.model.GameWorld;

public class ServerLink {
	public Display d;
	public GameWorld w;
	
	public void submitCommand(Command c) {
		c.run(d, w);
	}
}
