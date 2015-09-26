package com.qwertyness.sexymotdengine.variable;

public class IP extends Variable {

	public IP() {
		super("ip", VariableType.NORMAL);
	}

	public String getValue(String playerName, String ip) {
		return ip;
	}
	
	public Value handleOperators(String operatorString, String playerName, String ip) {
		return new Value(this.getValue(playerName, ip), "%" + this.name + "%");
	}
}
