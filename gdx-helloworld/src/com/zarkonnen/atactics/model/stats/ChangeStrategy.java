package com.zarkonnen.atactics.model.stats;

public abstract class ChangeStrategy<T> {
	public abstract void change(Stat<T> stat, T delta, StatObject so);
	
	public static <T> Set<T> set(Stat<T> stat) { return new Set<T>(); }
	public static AddInt add(Stat<Integer> stat, GetStrategy<Integer> def) { return new AddInt(def); }
	public static AddIntWithBoundaries addWithBoundaries(Stat<Integer> stat, GetStrategy<Integer> def, GetStrategy<Integer> lowerBoundary, GetStrategy<Integer> upperBoundary) { return new AddIntWithBoundaries(def, lowerBoundary, upperBoundary); }
	
	public static class Set<T> extends ChangeStrategy<T> {
		@Override
		public void change(Stat<T> stat, T delta, StatObject so) {
			so.stats.put(stat, delta);
		}
	}
	
	public static class AddInt extends ChangeStrategy<Integer> {
		private final GetStrategy<Integer> def;
		
		public AddInt(GetStrategy<Integer> def) { this.def = def; }
		
		@Override
		public void change(Stat<Integer> stat, Integer delta, StatObject so) {
			int value = so.stats.containsKey(stat) ? (Integer) so.stats.get(stat) : def.get(so);
			so.stats.put(stat, value + delta);
		}
	}
	
	public static class AddIntWithBoundaries extends ChangeStrategy<Integer> {
		private final GetStrategy<Integer> def;
		private final GetStrategy<Integer> lowerBoundary;
		private final GetStrategy<Integer> upperBoundary;
		
		public AddIntWithBoundaries(GetStrategy<Integer> def, GetStrategy<Integer> lowerBoundary, GetStrategy<Integer> upperBoundary) {
			this.def = def;
			this.lowerBoundary = lowerBoundary;
			this.upperBoundary = upperBoundary;
		}
		
		@Override
		public void change(Stat<Integer> stat, Integer delta, StatObject so) {
			int value = so.stats.containsKey(stat) ? (Integer) so.stats.get(stat) : def.get(so);
			int newValue = value + delta;
			int lowerB = lowerBoundary.get(so);
			int upperB = upperBoundary.get(so);
			if (newValue < lowerB) {
				newValue = lowerB;
			}
			if (newValue > upperB) {
				newValue = upperB;
			}
			so.stats.put(stat, newValue);
		}
	}
}
