package com.qwertyness.sexymotdengine.variable;

import java.util.ArrayList;
import java.util.List;

public abstract class ArgumentVariable extends Variable {

	public ArgumentVariable(String name, VariableType type) {
		super(name, type);
	}

	public String getValue(String playerName, String ip) {
		return null;
	}
	
	public abstract String getValue(String playerName, String ip, String argument);
	
	public List<Value> evaluate(String playerName, String ip, String input) {
		List<Value> values = new ArrayList<Value>();
		while (input.contains("%" + this.name + ":")) {
			String argument = input.substring(input.indexOf("%" + this.name + ":") + 2 + this.name.length());
			argument = argument.substring(0, argument.indexOf("%"));
			input = input.replace("%" + this.name + ":" + argument + "%", getValue(playerName, ip, argument));
			values.add(new Value(getValue(playerName, ip, argument), "%" + this.name + ":" + argument + "%"));
		}
		return values;
	}
}
