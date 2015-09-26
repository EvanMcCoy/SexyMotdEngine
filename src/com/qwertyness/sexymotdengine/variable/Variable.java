package com.qwertyness.sexymotdengine.variable;

public abstract class Variable {
	public String name;
	public VariableType type;

	public Variable(String name, VariableType type) {
		this.name = name;
		this.type = type;
	}
	
	public abstract String getValue(String playerName, String ip);
	public abstract Value handleOperators(String operatorString, String playerName, String ip);
	
	public enum VariableType {
		STATIC, NORMAL, DYNAMIC
	}
}
