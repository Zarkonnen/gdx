package com.badlogic.helloworld;

import java.util.Iterator;

public class GameMap<T> {
	private T[][] tiles;
	
	@SuppressWarnings("unchecked")
	public GameMap(int h, int w) {
		tiles = (T[][]) new Object[h][w];
	}
	
	public T get(Pt c) {
		return tiles[c.y][c.x];
	}
	
	public void set(Pt c, T t) {
		tiles[c.y][c.x] = t;
	}
	
	public boolean has(Pt c) {
		return c.y >= 0 && c.y < tiles.length && c.x >= 0 && c.x < tiles[0].length;
	}
	
	public Iterable<Pt> pts() {
		return new Iterable<Pt>() {
			@Override
			public Iterator<Pt> iterator() {
				return new Iterator<Pt>() {
					int y = 0;
					int x = 0;
					
					@Override
					public boolean hasNext() {
						return y != tiles.length;
					}

					@Override
					public Pt next() {
						Pt p = new Pt(y, x);
						x++;
						if (x == tiles[0].length - 1) {
							y++;
							x = 0;
						}
						return p;
					}

					@Override
					public void remove() {
						throw new RuntimeException("Not implemented.");
					}
				};
			}
		};
		
	}
}
