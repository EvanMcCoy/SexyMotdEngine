package com.qwertyness.sexymotdengine.variable;

import com.qwertyness.sexymotdengine.MotdState;

public class ServerName extends Variable {

	public ServerName() {
		super("servername", VariableType.STATIC);
	}
	
	public String getValue(String playerName, String ip) {
		return MotdState.getActivePlugin().serverName();
	}
	
	public Value handleOperators(String operatorString, String playerName, String ip) {
		return new Value(this.getValue(playerName, ip), "%" + this.name + "%");
	}
}
