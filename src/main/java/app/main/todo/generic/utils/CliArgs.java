package app.main.todo.generic.utils;

import java.util.HashMap;
import java.util.TreeSet;

/**
 */
public class CliArgs {

	private String[] args = null;

	private HashMap<String, Integer> switchIndexes = new HashMap<String, Integer>();
	private TreeSet<Integer> takenIndexes = new TreeSet<Integer>();

	public CliArgs(String[] args) {
		parse(args);
	}

	public void parse(String[] arguments) {
		this.args = arguments;
		switchIndexes.clear();
		takenIndexes.clear();
		for (int i = 0; i < args.length; i++) {
			if (args[i].startsWith("--")) {
				switchIndexes.put(args[i], i);
				takenIndexes.add(i);
			}
		}
	}

	public String[] args() {
		return args;
	}

	public String arg(int index) {
		return args[index];
	}

	public boolean switchPresent(String switchName) {
		return switchIndexes.containsKey(switchName);
	}

	public String switchValue(String switchName) {
		return switchValue(switchName, null);
	}

	public String switchValue(String switchName, String defaultValue) {
		if (!switchIndexes.containsKey(switchName))
			return defaultValue;

		int switchIndex = switchIndexes.get(switchName);
		if (switchIndex + 1 < args.length) {
			takenIndexes.add(switchIndex + 1);
			return args[switchIndex + 1];
		}
		return defaultValue;
	}

	public Integer switchIntegerValue(String switchName) {
		return switchIntegerValue(switchName, null);
	}

	public Integer switchIntegerValue(String switchName, Integer defaultValue) {
		String switchValue = switchValue(switchName, null);

		if (switchValue == null)
			return defaultValue;
		return Integer.parseInt(switchValue);
	}
}