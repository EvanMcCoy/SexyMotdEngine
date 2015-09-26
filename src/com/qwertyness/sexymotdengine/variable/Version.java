package com.qwertyness.sexymotdengine.variable;

import com.qwertyness.sexymotdengine.ActivePlugin;

public class Version extends Variable {

	public Version() {
		super("version", VariableType.STATIC);
	}
	
	public String getValue(String playerName, String ip) {
		return ActivePlugin.activePlugin.version();
	}
	
	public Value handleOperators(String operatorString, String playerName, String ip) {
		return new Value(this.getValue(playerName, ip), "%" + this.name + "%");
	}
}
