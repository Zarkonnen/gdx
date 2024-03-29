package com.zarkonnen.atactics.model;

import com.zarkonnen.atactics.CoordinateSystem;

public class HexCoordinateSystem implements CoordinateSystem<Pt> {
	private final int w;
	private final int h;
	private final int o;
	
	public HexCoordinateSystem(int h, int w, int o) {
		this.w = w;
		this.h = h;
		this.o = o;
	}

	@Override
	public boolean adjacent(Pt c0, Pt c1) {
		if (even(c0.x)) {
			if (c1.y == c0.y - 1 || c1.y == c0.y) {
				return Math.abs(c0.x - c1.x) <= 1;
			} else {
				return c1.y == c0.y + 1 && c0.x == c1.x;
			}
		} else {
			if (c1.y == c0.y || c1.y == c0.y + 1) {
				return Math.abs(c0.x - c1.x) <= 1;
			} else {
				return c1.y == c0.y - 1 && c0.x == c1.x;
			}
		}
	}
	
	@Override
	public int direction(Pt c0, Pt c1) {
		for (int d = 0; d < numberOfDirections(); d++) {
			if (inDirection(c0, d).equals(c1)) {
				return d;
			}
		}
		return -1;
	}

	@Override
	public Pt inDirection(Pt c, int direction) {
		if (even(c.x)) {
			switch (direction) {
				case 0:
					return new Pt(c.y - 1, c.x);
				case 1:
					return new Pt(c.y - 1, c.x + 1);
				case 2:
					return new Pt(c.y, c.x + 1);
				case 3:
					return new Pt(c.y + 1, c.x);
				case 4:
					return new Pt(c.y, c.x - 1);
				case 5:
					return new Pt(c.y - 1, c.x - 1);
			}
		} else {
			switch (direction) {
				case 0:
					return new Pt(c.y - 1, c.x);
				case 1:
					return new Pt(c.y, c.x + 1);
				case 2:
					return new Pt(c.y + 1, c.x + 1);
				case 3:
					return new Pt(c.y + 1, c.x);
				case 4:
					return new Pt(c.y + 1, c.x - 1);
				case 5:
					return new Pt(c.y, c.x -1);
			}
		}
		return null;
	}

	@Override
	public int numberOfDirections() { return 6; }

	@Override
	public Pt find(Pt p) {
		if (p.x % (w - o) > o) {
			return new Pt(fy(p), fx(p));
		} else {
			int ly = p.y % h;
			int lx = p.x % (w - o);
			if (even(fx(p))) {
				if (ly < h / 2) {
					if (lx < o - ly * 2 * o / h) {
						return new Pt(fy(p) - 1, fx(p) - 1);
					} else {
						return new Pt(fy(p), fx(p));
					}
				} else {
					if (lx < (ly - h / 2) * 2 * o / h) {
						return new Pt(fy(p), fx(p) - 1);
					} else {
						return new Pt(fy(p), fx(p));
					}
				}
			} else {
				if (ly < h / 2) {
					if (lx < (ly) * 2 * o / h) {
						return new Pt(fy(p) + 1, fx(p) - 1);
					} else {
						return new Pt(fy(p), fx(p));
					}
				} else {
					if (lx < o - (ly - h / 2) * 2 * o / h) {
						return new Pt(fy(p), fx(p) - 1);
					} else {
						return new Pt(fy(p), fx(p));
					}
				}
			}
		}
	}
	
	private int fx(Pt p) {
		return p.x / (w - o);
	}
	
	private int fy(Pt p) {
		return even(fx(p)) ? p.y / h : (p.y - h / 2) / h;
	}

	@Override
	public Pt pos(Pt c) {
		return new Pt(
			even(c.x) ? (c.y * h) : (c.y * h + h / 2),
			c.x * (w - o)
		);
	}

	@Override
	public double dist(Pt c0, Pt c1) {
		return c0.dist(c1) / h;
	}
	
	static boolean even(int i) { return i % 2 == 0; }
}
