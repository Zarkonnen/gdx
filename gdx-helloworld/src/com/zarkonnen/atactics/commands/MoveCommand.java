package com.zarkonnen.atactics.commands;

import com.badlogic.gdx.scenes.scene2d.actions.MoveBy;
import com.badlogic.gdx.scenes.scene2d.actions.Sequence;
import com.zarkonnen.atactics.display.Display;
import com.zarkonnen.atactics.display.ShipActor;
import com.zarkonnen.atactics.model.Mission;
import com.zarkonnen.atactics.model.GameWorld;
import com.zarkonnen.atactics.model.Pt;
import com.zarkonnen.atactics.model.Ship;
import com.zarkonnen.atactics.model.Stats;
import com.zarkonnen.atactics.model.SpaceTile;

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
		Mission m = w.get(GameWorld.MISSION);
		Pt shipC = m.find(ship);
		int dir = m.cs.direction(shipC, targetC);
		Pt shipP = m.cs.pos(shipC);
		Pt targetP = m.cs.pos(targetC);
		Sequence seq = Sequence.$(new ChangeDirectionAction(dir), MoveBy.$(targetP.x - shipP.x, targetP.y - shipP.y, 0.2f));
		sa.action(seq);
		SpaceTile srcT = m.get(Mission.MAP).get(shipC);
		SpaceTile dstT = m.get(Mission.MAP).get(targetC);
		dstT.set(SpaceTile.SHIP, srcT.get(SpaceTile.SHIP));
		srcT.set(SpaceTile.SHIP, null);
		dstT.get(SpaceTile.SHIP).set(Stats.DIRECTION, dir);
	}
}
