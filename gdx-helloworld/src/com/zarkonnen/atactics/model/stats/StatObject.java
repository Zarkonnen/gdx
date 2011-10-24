package com.zarkonnen.atactics.model.stats;

import java.util.HashMap;

public class StatObject {
	final HashMap<Stat<?>, Object> stats = new HashMap<Stat<?>, Object>();
	final HashMap<Stat<?>, GetStrategy<?>> getStrategies = new HashMap<Stat<?>, GetStrategy<?>>();
	final HashMap<Stat<?>, ChangeStrategy<?>> changeStrategies = new HashMap<Stat<?>, ChangeStrategy<?>>();
	
	protected <T> void stat(Stat<T> stat, GetStrategy<T> getStrategy) {
		getStrategies.put(stat, getStrategy);
	}
	
	protected <T> void statAndChange(Stat<T> stat, GetStrategy<T> getStrategy, ChangeStrategy<T> changeStrategy) {
		getStrategies.put(stat, getStrategy);
		changeStrategies.put(stat, changeStrategy);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T get(Stat<T> stat) { return ((GetStrategy<T>) getStrategies.get(stat)).get(this); }
	
	@SuppressWarnings("unchecked")
	public <T> void change(Stat<T> stat, T delta) { ((ChangeStrategy<T>) changeStrategies.get(stat)).change(stat, delta, this); }
}
