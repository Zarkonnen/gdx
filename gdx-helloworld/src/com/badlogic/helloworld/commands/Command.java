package com.badlogic.helloworld.commands;

import com.badlogic.helloworld.display.Display;
import com.badlogic.helloworld.model.GameWorld;

public interface Command {
	public void run(Display d, GameWorld w);
}
