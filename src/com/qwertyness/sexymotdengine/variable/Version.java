package com.qwertyness.sexymotdengine.variable;

import com.qwertyness.sexymotdengine.MotdState;

public class Version extends Variable {

	public Version() {
		super("version", VariableType.STATIC);
	}
	
	public String getValue(String playerName, String ip) {
		return MotdState.getActivePlugin().version();
	}
	
	public Value handleOperators(String operatorString, String playerName, String ip) {
		return new Value(this.getValue(playerName, ip), "%" + this.name + "%");
	}
}
