package com.zarkonnen.atactics.model.stats;

import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

public class IO {
	public void write(Output o, HashMap<String, StatObject> heads) {
		IdentityHashMap<Object, ID> objectToID = new IdentityHashMap<Object, ID>();
		for (Map.Entry<String, StatObject> kv : heads.entrySet()) {
			objectToID.put(kv.getValue(), new ID(kv.getKey()));
		}
		int idCounter = 0;
		for (Map.Entry<String, StatObject> kv : heads.entrySet()) {
			idCounter = write(o, new ID(kv.getKey()), kv.getValue(), objectToID, idCounter);
		}
	}

	private int write(Output o, ID id, StatObject so, IdentityHashMap<Object, ID> objectToID, int idCounter) {
		HashMap<Stat<?>, Object> mapping = new HashMap<Stat<?>, Object>();
		for (Map.Entry<Stat<?>, Object> kv : so.stats.entrySet()) {
			if (objectToID.containsKey(kv.getValue())) {
				mapping.put(kv.getKey(), objectToID.get(kv.getValue()));
			} else {
				if (isComplexObject(kv.getValue())) {
					StatObject mySO = toStatObject(kv.getValue());
					String myIDStr = mySO.get(Stat.NAME);
					if (myIDStr == null) {
						myIDStr = kv.getValue().getClass().getSimpleName() + idCounter++;
					} else {
						if (objectToID.containsValue(new ID(myIDStr))) {
							myIDStr = myIDStr + idCounter++;
						}
					}
					ID myID = new ID(myIDStr);
					objectToID.put(kv.getValue(), myID);
					idCounter = write(o, myID, mySO, objectToID, idCounter);
					mapping.put(kv.getKey(), myID);
				} else {
					mapping.put(kv.getKey(), kv.getValue());
				}
			}
		}
		o.write(so.getClass().getName(), id, mapping);
		return idCounter;
	}
	
	static boolean isComplexObject(Object o) {
		if (o instanceof String)  { return false; }
		if (o instanceof Integer) { return false; }
		if (o instanceof Boolean) { return false; }
		if (o instanceof Double)  { return false; }
		if (o instanceof Float)   { return false; }
		return true;
	}
	
	@SuppressWarnings("unchecked")
	static StatObject toStatObject(Object o) {
		if (o instanceof StatObject) {
			return (StatObject) o;
		}
		if (o instanceof List<?>) {
			return new AList<Object>((List<Object>) o);
		}
		throw new RuntimeException("Don't know how to serialize a " + o.getClass().getName() + ".");
	}

	public HashMap<String, StatObject> read(Reader r) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private static class AList<T> extends StatObject {
		public AList() {}
		
		public AList(List<T> l) {
			stats.put(new Stat<Integer>("size", Integer.class), l.size());
			for (int i = 0; i < l.size(); i++) {
				stats.put(new Stat<Object>("" + i, Object.class), l.get(i));
			}
		}
		
		public List<Object> getList() {
			ArrayList<Object> l = new ArrayList<Object>();
			int size = get(new Stat<Integer>("size", Integer.class));
			for (int i = 0; i < size; i++) {
				l.add(get(new Stat<Object>("" + i, Object.class)));
			}
			return Collections.unmodifiableList(l);
		}
	}
}
