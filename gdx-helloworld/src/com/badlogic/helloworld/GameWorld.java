package com.badlogic.helloworld;

public class GameWorld {
	GameMap<Tile> map = new GameMap<Tile>(20, 20);
	CoordinateSystem<Pt> cs = new HexCoordinateSystem(47, 64, 12);
	Pt selection = null;
	Pt target = null;
	
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
