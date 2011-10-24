package com.zarkonnen.atactics.commands;

import com.badlogic.gdx.scenes.scene2d.actions.MoveBy;
import com.badlogic.gdx.scenes.scene2d.actions.Sequence;
import com.zarkonnen.atactics.display.Display;
import com.zarkonnen.atactics.display.ShipActor;
import com.zarkonnen.atactics.model.GameWorld;
import com.zarkonnen.atactics.model.Pt;
import com.zarkonnen.atactics.model.Ship;
import com.zarkonnen.atactics.model.Stats;
import com.zarkonnen.atactics.model.Tile;

public class MoveCommand implements Command {
	Ship ship;
	Pt targetC;
	
	public MoveCommand(Ship ship, Pt targetCoordinate) {
		this.ship = ship;
		this.targetC = targetCoordinate;
	}
	
	@Override
	public void run(Display d, GameWorld w) {
		ShipActor sa = d.shipToActor.get(ship);
		Pt shipC = w.find(ship);
		int dir = w.cs.direction(shipC, targetC);
		Pt shipP = w.cs.pos(shipC);
		Pt targetP = w.cs.pos(targetC);
		Sequence seq = Sequence.$(new ChangeDirectionAction(dir), MoveBy.$(targetP.x - shipP.x, targetP.y - shipP.y, 0.2f));
		sa.action(seq);
		Tile srcT = w.map.get(shipC);
		Tile dstT = w.map.get(targetC);
		dstT.ship = srcT.ship;
		srcT.ship = null;
		dstT.ship.set(Stats.DIRECTION, dir);
	}
}
