package com.zarkonnen.atactics.model.stats;

import java.io.Reader;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;

public class IO {
	public void write(Output o, HashMap<String, StatObject> heads) {
		IdentityHashMap<Object, String> objectToID = new IdentityHashMap<Object, String>();
		for (Map.Entry<String, StatObject> kv : heads.entrySet()) {
			objectToID.put(kv.getValue(), kv.getKey());
		}
		int idCounter = 0;
		for (Map.Entry<String, StatObject> kv : heads.entrySet()) {
			idCounter = write(o, kv.getKey(), kv.getValue(), objectToID, idCounter);
		}
	}

	private int write(Output o, String id, StatObject so, IdentityHashMap<Object, String> objectToID, int idCounter) {
		HashMap<Stat<?>, String> mapping = new HashMap<Stat<?>, String>();
		for (Map.Entry<Stat<?>, Object> kv : so.stats.entrySet()) {
			if (objectToID.containsKey(kv.getValue())) {
				mapping.put(kv.getKey(), objectToID.get(kv.getValue()));
			} else {
				if (isComplexObject(kv.getValue())) {
					StatObject mySO = toStatObject(kv.getValue());
					String myID = mySO.get(Stat.NAME);
					if (myID == null) {
						myID = kv.getValue().getClass().getSimpleName() + idCounter++;
					} else {
						if (objectToID.containsValue(myID)) {
							myID = myID + idCounter++;
						}
					}
					objectToID.put(kv.getValue(), myID);
					idCounter = write(o, myID, mySO, objectToID, idCounter);
					mapping.put(kv.getKey(), myID);
				} else {
					mapping.put(kv.getKey(), toString(kv.getValue()));
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
	
	static String toString(Object o) {
		return "" + o;
	}
	
	static StatObject toStatObject(Object o) {
		if (o instanceof StatObject) {
			return (StatObject) o;
		}
		
		throw new RuntimeException("Don't know how to serialize a " + o.getClass().getName() + ".");
	}

	public HashMap<String, StatObject> read(Reader r) {
		// TODO Auto-generated method stub
		return null;
	}
}
