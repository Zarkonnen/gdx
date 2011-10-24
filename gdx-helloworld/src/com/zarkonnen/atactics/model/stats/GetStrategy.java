package com.zarkonnen.atactics.model.stats;

import java.util.List;

import com.zarkonnen.atactics.model.stats.Explanation.Change;

public abstract class GetStrategy<T> {
	public abstract T get(StatObject so);
	public abstract Explanation<T> explain(StatObject so);
	
	public static <T> Const<T> constant(Stat<T> stat, T value) { return new Const<T>(value); }
	public static <T> Var<T> var(Stat<T> stat) { return new Var<T>(stat); }
	public static <T> WithDefault<T> withDefault(GetStrategy<T> main, GetStrategy<T> def) { return new WithDefault<T>(main, def); }
	public static <T> Ref<T> ref(Stat<? extends StatObject> ref, Stat<T> statAtRef) { return new Ref<T>(ref, statAtRef); }
	public static <T extends StatObject> IntModifiedBy<T> modifiedBy(GetStrategy<Integer> base, GetStrategy<List<T>> mods, Stat<Integer> modStat) { return new IntModifiedBy<T>(base, mods, modStat); }
	
	public static class Const<T> extends GetStrategy<T> {
		private final T value;
		public Const(T value) { this.value = value; }
		@Override
		public T get(StatObject so) { return value; }
		@Override
		public Explanation<T> explain(StatObject so) {
			return new Explanation<T>(value, value, "" + value, Change.SAME);
		}
	}
	
	public static class Var<T> extends GetStrategy<T> {
		private final Stat<T> stat;
		public Var(Stat<T> stat) { this.stat = stat; }
		@SuppressWarnings("unchecked")
		@Override
		public T get(StatObject so) { return (T) so.stats.get(stat); }
		@Override
		public Explanation<T> explain(StatObject so) {
			return new Explanation<T>(get(so), get(so), "" + get(so), Change.SAME);
		}
	}
	
	public static class Ref<T> extends GetStrategy<T> {
		private final Stat<? extends StatObject> ref;
		private final Stat<T> statAtRef;
		public Ref(Stat<? extends StatObject> ref, Stat<T> statAtRef) { this.ref = ref; this.statAtRef = statAtRef; }
		public T get(StatObject so) { return ((StatObject) so.stats.get(ref)).get(statAtRef); }
		@Override
		public Explanation<T> explain(StatObject so) {
			StatObject refO = (StatObject) so.stats.get(ref);
			String refN = refO.get(Stat.NAME);
			if (refN == null) {
				refN = "";
			} else {
				refN = " (" + refN + ")";
			}
			return new Explanation<T>(get(so), get(so), get(so) + refN, Change.SAME);
		}
	}
	
	public static class WithDefault<T> extends GetStrategy<T> {
		private final GetStrategy<T> main;
		private final GetStrategy<T> def;
		public WithDefault(GetStrategy<T> main, GetStrategy<T> def) { this.main = main; this.def = def; }
		public T get(StatObject so) {
			T result = main.get(so);
			return result == null ? def.get(so) : result;
		}
		@Override
		public Explanation<T> explain(StatObject so) {
			T result = main.get(so);
			return result == null ? def.explain(so) : main.explain(so);
		}
	}
	
	public static class IntModifiedBy<T extends StatObject> extends GetStrategy<Integer> {
		private final GetStrategy<Integer> base;
		private final GetStrategy<List<T>> mods;
		private final Stat<Integer> modStat;
		public IntModifiedBy(GetStrategy<Integer> base, GetStrategy<List<T>> mods, Stat<Integer> modStat) { this.base = base; this.mods = mods; this.modStat = modStat; }
		@Override
		public Integer get(StatObject so) {
			int value = base.get(so);
			for (StatObject modO : mods.get(so)) {
				value += modO.get(modStat);
			}
			return value;
		}
		@Override
		public Explanation<Integer> explain(StatObject so) {
			final int baseValue = base.get(so);
			int value = baseValue;
			StringBuilder sb = new StringBuilder();
			for (StatObject modO : mods.get(so)) {
				int mod = modO.get(modStat);
				String modN = modO.get(Stat.NAME);
				value += mod;
				if (mod > 0) {
					sb.append(" +" + mod);
				}
				if (mod < 0) {
					sb.append(" " + mod);
				}
				if (mod != 0 && modN != null) {
					sb.append(" (" + modN + ")");
				}
			}
			
			return new Explanation<Integer>(baseValue, value, sb.toString(),
					value > baseValue ? Change.BETTER : value < baseValue ? Change.WORSE : Change.SAME);
		}
	}
}
