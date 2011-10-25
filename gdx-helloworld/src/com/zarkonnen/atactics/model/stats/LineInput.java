package com.zarkonnen.atactics.model.stats;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LineInput implements Input {
	private BufferedReader r;
	
	public LineInput(Reader r) { this.r = new BufferedReader(r); }
	
	@SuppressWarnings("unchecked")
	@Override
	public IOObject read() throws IOException {
		String className = null;
		ID id = null;
		HashMap<Stat<?>, Object> mapping = new HashMap<Stat<?>, Object>();
		String line = r.readLine();
		if (line == null) { return null; }
		List<String> tokens = tokenize(line);
		if (tokens.size() != 3 || !tokens.get(2).equals("{")) {
			throw new IOException("Bad line: " + line);
		}
		className = (String) tokens.get(0);
		id = new ID((String) tokens.get(1));
		while (true) {
			line = r.readLine();
			if (line == null) { throw new IOException("Unexpected end of file."); }
			tokens = tokenize(line);
			if (tokens.size() == 1 && tokens.get(0).equals("}")) {
				break;
			}
			if (tokens.isEmpty() || tokens.get(0).startsWith("#")) {
				continue;
			}
			if (tokens.size() != 3 || !tokens.get(1).equals("=")) {
				throw new IOException("Bad line: " + line);
			}
			Object value = interpretValue(tokens.get(2));
			mapping.put(new Stat(tokens.get(0)), value);
		}
		return new IOObject(className, id, mapping);
	}
	
	private static List<String> tokenize(String s) {
		ArrayList<String> os = new ArrayList<String>();
		StringBuilder sb = new StringBuilder();
		boolean quoted = false;
		boolean escaped = false;
		boolean newlyEscaped = false;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			switch (c) {
				case ' ':
					if (quoted || escaped) {
						sb.append(c);
					} else {
						if (sb.length() > 0) {
							os.add(sb.toString());
							sb = new StringBuilder();
						}
					}
					break;
				case '\\':
					if (escaped) {
						sb.append(c);
					} else {
						newlyEscaped = true;
					}
					break;
				case '"':
					if (!escaped) {
						quoted = !quoted;
					}
					sb.append(c);
					break;
				default:
					sb.append(c);
					break;
			}
			escaped = newlyEscaped;
			newlyEscaped = false;
		}
		if (sb.length() > 0) {
			os.add(sb.toString());
		}
		return os;
	}
	
	private static Object interpretValue(String s) {
		if (s.startsWith("\"") && s.endsWith("\"")) {
			return s.substring(1, s.length() - 1);
		}
		if (s.startsWith(">")) {
			return new ID(s.substring(1));
		}
		if (s.endsWith("d")) {
			return Double.parseDouble(s.substring(0, s.length() - 1));
		}
		if (s.endsWith("f")) {
			return Float.parseFloat(s.substring(0, s.length() - 1));
		}
		if (s.endsWith("l")) {
			return Long.parseLong(s.substring(0, s.length() - 1));
		}
		if (s.equals("true") || s.equals("false")) {
			return s.equals("true") ? Boolean.TRUE : Boolean.FALSE;
		}
		return Integer.parseInt(s);
	}
}
