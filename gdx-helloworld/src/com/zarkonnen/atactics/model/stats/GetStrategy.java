package com.zarkonnen.atactics.model.stats;

import com.zarkonnen.atactics.model.stats.Explanation.Change;

public abstract class GetStrategy<T> {
	public abstract T get(StatObject so);
	public abstract Explanation<T> explain(StatObject so);
	
	public static <T> Const<T> constant(Stat<T> stat, T value) { return new Const<T>(value); }
	public static <T> Var<T> var(Stat<T> stat) { return new Var<T>(stat); }
	public static <T> WithDefault<T> withDefault(GetStrategy<T> main, GetStrategy<T> def) { return new WithDefault<T>(main, def); }
	public static <T> Ref<T> ref(Stat<? extends StatObject> ref, Stat<T> statAtRef) { return new Ref<T>(ref, statAtRef); }
	
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
}
