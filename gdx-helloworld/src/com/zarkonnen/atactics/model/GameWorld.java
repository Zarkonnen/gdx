package com.zarkonnen.atactics.model;

import com.zarkonnen.atactics.CoordinateSystem;
import com.zarkonnen.atactics.ServerLink;

public class GameWorld {
	public final int csW = 64;
	public final int csH = 47;
	
	public GameMap<Tile> map = new GameMap<Tile>(20, 20);
	public CoordinateSystem<Pt> cs = new HexCoordinateSystem(csH, csW, 12);
	public Ship selection = null;
	public Ship target = null;
	public ServerLink server;
	
	public Pt find(Ship s) {
		for (Pt p : map.pts()) {
			if (map.get(p).ship == s) {
				return p;
			}
		}
		return null;
	}
	
	public GameWorld() {
		for (int y = 0; y < 20; y++) {
			for (int x = 0; x < 20; x++) {
				Pt c = new Pt(y, x);
				map.set(c, new Tile(c));
			}
		}
		
		map.get(new Pt(5, 5)).ship = new Ship();
		map.get(new Pt(2, 4)).ship = new Ship();
	}
}
