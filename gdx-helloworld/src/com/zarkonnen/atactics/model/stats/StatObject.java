package com.zarkonnen.atactics.model.stats;

import java.util.HashMap;

public class StatObject {
	final HashMap<Stat<?>, Object> stats = new HashMap<Stat<?>, Object>();
	final HashMap<Stat<?>, GetStrategy<?>> getStrategies = new HashMap<Stat<?>, GetStrategy<?>>();
	final HashMap<Stat<?>, ChangeStrategy<?>> changeStrategies = new HashMap<Stat<?>, ChangeStrategy<?>>();
	private final boolean sameStatsMeansEquals;
	
	protected StatObject() { this(false); }
	protected StatObject(boolean sameStatsMeansEquals) {
		this.sameStatsMeansEquals = sameStatsMeansEquals;
		stat(Stat.NAME, GetStrategy.var(Stat.NAME));
	}
	
	protected <T> void stat(Stat<T> stat, GetStrategy<T> getStrategy) {
		stat(stat, getStrategy, ChangeStrategy.setOnly(stat));
	}
	
	protected <T> void stat(Stat<T> stat, GetStrategy<T> getStrategy, ChangeStrategy<T> changeStrategy) {
		getStrategies.put(stat, getStrategy);
		changeStrategies.put(stat, changeStrategy);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T get(Stat<T> stat) { return ((GetStrategy<T>) getStrategies.get(stat)).get(this); }
	
	@SuppressWarnings("unchecked")
	public <T> Explanation<T> explain(Stat<T> stat) { return ((GetStrategy<T>) getStrategies.get(stat)).explain(this); }
	
	@SuppressWarnings("unchecked")
	public <T> void add(Stat<T> stat, T delta) { ((ChangeStrategy<T>) changeStrategies.get(stat)).add(stat, delta, this); }
	
	@SuppressWarnings("unchecked")
	public <T> void subtract(Stat<T> stat, T delta) { ((ChangeStrategy<T>) changeStrategies.get(stat)).subtract(stat, delta, this); }
	
	@SuppressWarnings("unchecked")
	public <T> void set(Stat<T> stat, T value) { ((ChangeStrategy<T>) changeStrategies.get(stat)).set(stat, value, this); }

	public boolean equals(Object o2) {
		if (sameStatsMeansEquals) {
			if (!o2.getClass().equals(getClass())) { return false; }
			StatObject so2 = (StatObject) o2;
			return stats.equals(so2.stats);
		} else {
			return super.equals(o2);
		}
	}
}
