package com.qwertyness.sexymotdengine.variable;

import com.qwertyness.sexymotdengine.ActivePlugin;

public class NewPlayer extends Variable {
	
	public NewPlayer() {
		super("newplayer", VariableType.NORMAL);
	}

	public String getValue(String playerName, String ip) {
		return new Boolean(ActivePlugin.activePlugin.newPlayer(ip)).toString();
	}
	
	public boolean getRawValue(String ip) {
		return ActivePlugin.activePlugin.newPlayer(ip);
	}
	
	public Value handleOperators(String operatorString, String playerName, String ip) {
		return new Value(this.getValue(playerName, ip), "%" + this.name + "%");
	}
}
