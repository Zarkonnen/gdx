package com.zarkonnen.atactics.model.stats;

import java.io.IOException;
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
					String myIDStr = smushName(mySO.get(Stat.NAME));
					if (myIDStr == null) {
						myIDStr = smushName(kv.getValue().getClass().getSimpleName());
					}
					if (objectToID.containsValue(new ID(myIDStr))) {
						myIDStr = myIDStr + idCounter++;
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
		o.write(new IOObject(so.getClass().getName(), id, mapping));
		return idCounter;
	}
	
	static String smushName(String name) {
		if (name == null) { return null; }
		name = name.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
		return name.length() > 0 ? name : null;
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
			return new AList((List<Object>) o);
		}
		throw new RuntimeException("Don't know how to serialize a " + o.getClass().getName() + ".");
	}

	@SuppressWarnings("unchecked")
	public HashMap<String, StatObject> read(Input in) throws IOException {
		HashMap<String, Object> idToObj = new HashMap<String, Object>();
		HashMap<IOObject, StatObject> iooToSO = new HashMap<IOObject, StatObject>();
		IOObject ioo = null;
		while ((ioo = in.read()) != null) {
			try {
				StatObject obj = (StatObject) Class.forName(ioo.className).newInstance();
				iooToSO.put(ioo, obj);
				idToObj.put(ioo.id.id, obj instanceof HelperObject ? ((HelperObject) obj).createRealObject() : obj);
			} catch (ClassNotFoundException cnfe) {
				throw new IOException(cnfe);
			} catch (IllegalAccessException iae) {
				throw new IOException(iae);
			} catch (InstantiationException ie) {
				throw new IOException(ie);
			}
		}
		for (Map.Entry<IOObject, StatObject> iooAndSO : iooToSO.entrySet()) {
			ioo = iooAndSO.getKey();
			StatObject so = iooAndSO.getValue();
			for (Map.Entry<Stat<?>, Object> oKV : ioo.mapping.entrySet()) {
				if (oKV.getValue() instanceof ID) {
					Object realObject = idToObj.get(((ID) oKV.getValue()).id);
					so.stats.put(new Stat(oKV.getKey().name), realObject);
				} else {
					so.stats.put(oKV.getKey(), oKV.getValue());
				}
			}
		}
		for (Map.Entry<IOObject, StatObject> iooAndSO : iooToSO.entrySet()) {
			ioo = iooAndSO.getKey();
			StatObject so = iooAndSO.getValue();
			if (so instanceof HelperObject) {
				((HelperObject) so).completeRealObject(idToObj.get(ioo.id.id));
			}
		}
		HashMap<String, StatObject> idToStatObj = new HashMap<String, StatObject>();
		for (Map.Entry<String, Object> e : idToObj.entrySet()) {
			if (e.getValue() instanceof StatObject) {
				idToStatObj.put(e.getKey(), (StatObject) e.getValue());
			}
		}
		return idToStatObj;
	}
	
	@SuppressWarnings("unchecked")
	private static class AList extends HelperObject<List> {
		@SuppressWarnings("unused") // Reflective instantiation.
		public AList() {}
		
		public AList(List<?> l) {
			stats.put(new Stat<Integer>("size"), l.size());
			for (int i = 0; i < l.size(); i++) {
				stats.put(new Stat<Object>("" + i), l.get(i));
			}
		}
		
		private ArrayList createdList;

		@Override
		public void completeRealObject(List obj) {
			int size = (Integer) stats.get(new Stat<Integer>("size"));
			for (int i = 0; i < size; i++) {
				createdList.add(stats.get(new Stat<Object>("" + i)));
			}
		}

		@Override
		public List createRealObject() {
			createdList = new ArrayList();
			return Collections.unmodifiableList(createdList);
		}
	}
}
