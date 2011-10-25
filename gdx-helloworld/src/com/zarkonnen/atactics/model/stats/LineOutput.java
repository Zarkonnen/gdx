package com.zarkonnen.atactics.model.stats;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.Map;

public class LineOutput implements Output {
	private final PrintWriter w;
	
	public LineOutput(Writer w) { this.w = new PrintWriter(w); }

	@Override
	public void write(IOObject ioo) {
		w.print(escape(ioo.className, false));
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
	
	static String encode(Object o) {
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
