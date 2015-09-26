package com.qwertyness.sexymotdengine.variable;

import com.qwertyness.sexymotdengine.ActivePlugin;

public class ServerName extends Variable {

	public ServerName() {
		super("servername", VariableType.STATIC);
	}
	
	public String getValue(String playerName, String ip) {
		return ActivePlugin.activePlugin.serverName();
	}
	
	public Value handleOperators(String operatorString, String playerName, String ip) {
		return new Value(this.getValue(playerName, ip), "%" + this.name + "%");
	}
}
