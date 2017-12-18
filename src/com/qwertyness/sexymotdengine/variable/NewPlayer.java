package com.qwertyness.sexymotdengine.variable;

import com.qwertyness.sexymotdengine.MotdState;

public class NewPlayer extends Variable {
	
	public NewPlayer() {
		super("newplayer", VariableType.NORMAL);
	}

	public String getValue(String playerName, String ip) {
		return new Boolean(MotdState.getActivePlugin().newPlayer(ip)).toString();
	}
	
	public boolean getRawValue(String ip) {
		return MotdState.getActivePlugin().newPlayer(ip);
	}
	
	public Value handleOperators(String operatorString, String playerName, String ip) {
		return new Value(this.getValue(playerName, ip), "%" + this.name + "%");
	}
}
