package com.badlogic.helloworld.model;

import com.badlogic.helloworld.CoordinateSystem;

public class GameWorld {
	public GameMap<Tile> map = new GameMap<Tile>(20, 20);
	public CoordinateSystem<Pt> cs = new HexCoordinateSystem(47, 64, 12);
	public Pt selection = null;
	public Pt target = null;
	
	public GameWorld() {
		for (int y = 0; y < 20; y++) {
			for (int x = 0; x < 20; x++) {
				map.set(new Pt(y, x), new Tile());
			}
		}
		
		map.get(new Pt(5, 5)).ship = new Ship();
		map.get(new Pt(2, 4)).ship = new Ship();
	}
}
