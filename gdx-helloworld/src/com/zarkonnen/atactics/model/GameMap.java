package com.zarkonnen.atactics.model;

import java.util.Iterator;

import com.zarkonnen.atactics.model.stats.HasHelper;
import com.zarkonnen.atactics.model.stats.HelperObject;
import com.zarkonnen.atactics.model.stats.Stat;

public class GameMap<T extends Tile> implements HasHelper<GameMap<T>> {
	private T[][] tiles;
	private Class<T> tileClass;
	
	@SuppressWarnings("unchecked")
	public GameMap(int h, int w, Class<T> tileClass) {
		tiles = (T[][]) new Tile[h][w];
		this.tileClass = tileClass;
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

	@Override
	public HelperObject<GameMap<T>> getHelper() {
		return new H<T>(this);
	}
	
	public static class H<T extends Tile> extends HelperObject<GameMap<T>> {
		public H() {}
		private H(GameMap<T> gm) {
			stats.put(new Stat<Integer>("h"), gm.tiles.length);
			stats.put(new Stat<Integer>("w"), gm.tiles[0].length);
			stats.put(new Stat<String>("tileClass"), gm.tileClass.getName());
			for (int y = 0; y < gm.tiles.length; y++) { for (int x = 0; x < gm.tiles[y].length; x++) {
				if (gm.tiles[y][x].getStats().size() > 1) {
					stats.put(new Stat<T>(y + "/" + x), gm.tiles[y][x]);
				}
			}}
		}
				
		@SuppressWarnings("unchecked")
		@Override
		public void completeRealObject(GameMap<T> obj) {
			try {
				final int h = (Integer) stats.get(new Stat<Integer>("h"));
				final int w = (Integer) stats.get(new Stat<Integer>("w"));
				obj.tiles = (T[][]) new Tile[h][w];
				obj.tileClass = (Class<T>) Class.forName((String) stats.get(new Stat<String>("tileClass")));
				for (int y = 0; y < h; y++) { for (int x = 0; x < w; x++) {
					obj.tiles[y][x] = (T) stats.get(new Stat<T>(y + "/" + x));
					if (obj.tiles[y][x] == null) {
						obj.tiles[y][x] = obj.tileClass.getConstructor(Pt.class).newInstance(new Pt(y, x));
					}
				}}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		@Override
		public GameMap<T> createRealObject() {
			return new GameMap<T>(0, 0, null);
		}
		
	}
}
