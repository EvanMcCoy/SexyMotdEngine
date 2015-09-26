package com.qwertyness.sexymotdengine.variable;

public class Newline extends Variable {
	
	public Newline() {
		super("newline", VariableType.STATIC);
	}
	
	public String getValue(String playerName, String ip) {
		return "\n";
	}
	
	public Value handleOperators(String operatorString, String playerName, String ip) {
		return new Value(this.getValue(playerName, ip), "%" + this.name + "%");
	}

}
