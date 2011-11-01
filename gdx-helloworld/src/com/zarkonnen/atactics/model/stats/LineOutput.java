package com.zarkonnen.atactics.model.stats;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class LineOutput implements Output {
	private final PrintWriter w;
	public HashMap<String, String> aliases = new HashMap<String, String>();
	
	public LineOutput(Writer w) { this.w = new PrintWriter(w); }
	
	String createAlias(String cl) {
		String[] bits = cl.split("[.]");
		String alias = bits[bits.length - 1].split("[$]")[0].toLowerCase();
		if (!aliases.containsKey(alias)) {
			return alias;
		}
		return cl;
	}

	@Override
	public void write(IOObject ioo) {
		if (!aliases.containsKey(ioo.className)) {
			String alias = createAlias(ioo.className);
			aliases.put(ioo.className, alias);
			w.println("alias " + alias + " " + ioo.className);
		}
		for (Object o : ioo.mapping.values()) {
			if (o instanceof HasStringRepresentation) {
				if (!aliases.containsKey(o.getClass().getName())) {
					String alias = createAlias(o.getClass().getName());
					aliases.put(o.getClass().getName(), alias);
					w.println("alias " + alias + " " + o.getClass().getName());
				}
			}
		}
		w.print(escape(aliases.get(ioo.className), false));
		w.print(" ");
		w.print(escape(ioo.id.id, false));
		w.println(" {");
		for (Map.Entry<Stat<?>, Object> kv : ioo.mapping.entrySet()) {
			w.print("  ");
			w.print(escape(kv.getKey().name, false));
			w.print(" = ");
			w.println(encode(kv.getValue()));
		}
		w.println("}");
	}
	
	String encode(Object o) {
		if (o instanceof String) {
			return escape((String) o, true);
		}
		if (o instanceof ID) {
			return ">" + escape(((ID) o).id, false);
		}
		if (o instanceof Integer) {
			return o + "";
		}
		if (o instanceof Double) {
			return o + "d";
		}
		if (o instanceof Float) {
			return o + "f";
		}
		if (o instanceof Long) {
			return o + "l";
		}
		if (o instanceof Boolean) {
			return ((Boolean) o) ? "true" : "false";
		}
		if (o instanceof HasStringRepresentation) {
			return "#" + aliases.get(o.getClass().getName()) + " " + escape(((HasStringRepresentation) o).getRepresentation(), false);
		}
		
		throw new RuntimeException("Don't know how to represent a " + o.getClass().getName() + ".");
	}
	
	static String escape(String s, boolean quote) {
		StringBuilder sb = new StringBuilder();
		if (quote) { sb.append("\""); }
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			switch (c) {
				case '\\': 
				case ' ':
					sb.append("\\");
				default:
					sb.append(c);
					break;
				case '\n':
					sb.append("\\n");
					break;
				case '"':
					if (quote) {
						sb.append("\\\"");
					} else {
						sb.append("\"");
					}
					break;
			}
		}
		if (quote) { sb.append("\""); }
		return sb.toString();
	}
}
