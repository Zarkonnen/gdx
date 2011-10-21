package com.badlogic.helloworld.commands;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.helloworld.display.ShipActor;

public class ChangeDirectionAction extends Action {
	int direction;
	ShipActor sa;
	boolean done = false;
	float timePassed = 0f;

	public ChangeDirectionAction(int direction) {
		this.direction = direction;
	}

	@Override
	public void act(float t) {
		timePassed += t;
		if (timePassed > 0.1f) {
			done = true;
			sa.direction = direction;
		}
	}

	@Override
	public Action copy() {
		ChangeDirectionAction cda = new ChangeDirectionAction(direction);
		cda.sa = sa;
		cda.done = done;
		return cda;
	}
	
	@Override
	public void reset() { done = false; }

	@Override
	public Actor getTarget() { return sa; }

	@Override
	public boolean isDone() { return done; }

	@Override
	public void setTarget(Actor a) { sa = (ShipActor) a; }
}
