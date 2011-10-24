package com.zarkonnen.atactics.model.stats;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public abstract class ChangeStrategy<T> {
	public abstract void add(Stat<T> stat, T delta, StatObject so);
	public abstract void subtract(Stat<T> stat, T delta, StatObject so);
	public abstract void set(Stat<T> stat, T value, StatObject so);
	
	public static <T> SetOnly<T> setOnly(Stat<T> stat) { return new SetOnly<T>(); }
	public static Int integer(Stat<Integer> stat, GetStrategy<Integer> def) { return new Int(def); }
	public static IntWithBoundaries integerWithBoundaries(Stat<Integer> stat, GetStrategy<Integer> def, GetStrategy<Integer> lowerBoundary, GetStrategy<Integer> upperBoundary) { return new IntWithBoundaries(def, lowerBoundary, upperBoundary); }
	public static <T> ListStrategy<T> list(Stat<List<T>> stat, GetStrategy<List<T>> def) { return new ListStrategy<T>(def); }
	
	public static class SetOnly<T> extends ChangeStrategy<T> {
		@Override
		public void set(Stat<T> stat, T value, StatObject so) {
			so.stats.put(stat, value);
		}

		@Override
		public void add(Stat<T> stat, T delta, StatObject so) {
			throw new RuntimeException("Can't add " + stat + ".");
		}

		@Override
		public void subtract(Stat<T> stat, T delta, StatObject so) {
			throw new RuntimeException("Can't subtract " + stat + ".");
		}
	}
	
	public static class Int extends ChangeStrategy<Integer> {
		private final GetStrategy<Integer> def;
		
		public Int(GetStrategy<Integer> def) { this.def = def; }
		
		@Override
		public void add(Stat<Integer> stat, Integer delta, StatObject so) {
			int value = so.stats.containsKey(stat) ? (Integer) so.stats.get(stat) : def.get(so);
			set(stat, value + delta, so);
		}
		
		@Override
		public void subtract(Stat<Integer> stat, Integer delta, StatObject so) {
			int value = so.stats.containsKey(stat) ? (Integer) so.stats.get(stat) : def.get(so);
			set(stat, value - delta, so);
		}
		
		@Override
		public void set(Stat<Integer> stat, Integer value, StatObject so) {
			so.stats.put(stat, value);
		}
	}
	
	public static class IntWithBoundaries extends Int {
		private final GetStrategy<Integer> lowerBoundary;
		private final GetStrategy<Integer> upperBoundary;
		
		public IntWithBoundaries(GetStrategy<Integer> def, GetStrategy<Integer> lowerBoundary, GetStrategy<Integer> upperBoundary) {
			super(def);
			this.lowerBoundary = lowerBoundary;
			this.upperBoundary = upperBoundary;
		}
		
		@Override
		public void set(Stat<Integer> stat, Integer value, StatObject so) {
			int lowerB = lowerBoundary.get(so);
			int upperB = upperBoundary.get(so);
			if (value < lowerB) {
				value = lowerB;
			}
			if (value > upperB) {
				value = upperB;
			}
			so.stats.put(stat, value);
		}
	}
	
	public static class ListStrategy<T> extends ChangeStrategy<List<T>> {
		private final GetStrategy<List<T>> def;
		
		public ListStrategy(GetStrategy<List<T>> def) { this.def = def; }
		
		@SuppressWarnings("unchecked")
		@Override
		public void add(Stat<List<T>> stat, List<T> delta, StatObject so) {
			List<T> oldL = (List<T>) so.stats.get(stat);
			if (oldL == null) { oldL = def.get(so); }
			ArrayList<T> l = new ArrayList<T>(oldL);
			l.addAll(delta);
			so.stats.put(stat, Collections.unmodifiableList(l));
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public void subtract(Stat<List<T>> stat, List<T> delta, StatObject so) {
			List<T> oldL = (List<T>) so.stats.get(stat);
			if (oldL == null) { oldL = def.get(so); }
			ArrayList<T> l = new ArrayList<T>(oldL);
			l.removeAll(delta);
			so.stats.put(stat, Collections.unmodifiableList(l));
		}

		@Override
		public void set(Stat<List<T>> stat, List<T> value, StatObject so) {
			ArrayList<T> l = new ArrayList<T>();
			l.addAll(value);
			so.stats.put(stat, Collections.unmodifiableList(l));
		}
	}
}
