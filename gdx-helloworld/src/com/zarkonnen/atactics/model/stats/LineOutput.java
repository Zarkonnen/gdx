package com.zarkonnen.atactics.model.stats;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class LineOutput implements Output {
	private final PrintWriter w;
	
	public LineOutput(Writer w) { this.w = new PrintWriter(w); }

	@Override
	public void write(String className, String id, HashMap<Stat<?>, String> mapping) {
		w.print(escape(className));
		w.print(" ");
		w.print(escape(id));
		w.println(" {");
		for (Map.Entry<Stat<?>, String> kv : mapping.entrySet()) {
			w.print(escape(kv.getKey().name));
			w.print("=");
			w.println(escape(kv.getValue()));
		}
		w.println("}");
	}
	
	static String escape(String s) {
		StringBuilder sb = new StringBuilder();
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
			}
		}
		return sb.toString();
	}
}
