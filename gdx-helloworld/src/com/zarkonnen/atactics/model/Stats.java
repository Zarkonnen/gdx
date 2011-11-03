package com.zarkonnen.atactics.model;

import java.util.List;

import com.zarkonnen.atactics.model.stats.Stat;

public class Stats {
	public static final Stat<String> NAME = Stat.NAME;
	public static final Stat<ShipType> SHIP_TYPE = new Stat<ShipType>("Type");
	
	public static final Stat<Integer> MAX_HP = new Stat<Integer>("Max HP");
	public static final Stat<Integer> HP = new Stat<Integer>("HP");
	public static final Stat<Integer> DIRECTION = new Stat<Integer>("Direction");
	public static final Stat<Integer> SPEED = new Stat<Integer>("Speed");
	public static final Stat<Integer> MOVES_LEFT = new Stat<Integer>("Moves Left");
	public static final Stat<Integer> EVASION = new Stat<Integer>("Evasion");
	public static final Stat<Integer> DAMAGE = new Stat<Integer>("Damage");
	public static final Stat<Integer> NUM_ATTACKS = new Stat<Integer>("Number of Attacks");
	public static final Stat<Range> RANGE = new Stat<Range>("Range");
	public static final Stat<List<ShipEffect>> EFFECTS = new Stat<List<ShipEffect>>("Effects");
}
