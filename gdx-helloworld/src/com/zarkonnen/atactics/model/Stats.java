package com.zarkonnen.atactics.model;

import com.zarkonnen.atactics.model.stats.Stat;

public class Stats {
	public static final Stat<String> NAME = Stat.NAME;
	public static final Stat<ShipType> SHIP_TYPE = new Stat<ShipType>("Type", ShipType.class);
	
	public static final Stat<Integer> MAX_HP = new Stat<Integer>("Max HP", Integer.class);
	public static final Stat<Integer> HP = new Stat<Integer>("HP", Integer.class);
	public static final Stat<Integer> DIRECTION = new Stat<Integer>("Direction", Integer.class);
	public static final Stat<Integer> SPEED = new Stat<Integer>("Speed", Integer.class);
	public static final Stat<Integer> MOVES_LEFT = new Stat<Integer>("Moves Left", Integer.class);
	public static final Stat<Integer> EVASION = new Stat<Integer>("Evasion", Integer.class);
	public static final Stat<Integer> DAMAGE = new Stat<Integer>("Damage", Integer.class);
	public static final Stat<Integer> NUM_ATTACKS = new Stat<Integer>("Number of Attacks", Integer.class);
}
