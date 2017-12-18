package com.qwertyness.sexymotdengine.variable;

import com.qwertyness.sexymotdengine.MotdState;

public class PlayerName extends Variable {
	public static PlayerName instance;

	public PlayerName() {
		super("playername", VariableType.NORMAL);
		instance = this;
	}
	
	public String getValue(String playerName, String ip) {
		if (MotdState.getActivePlugin().newPlayer(ip)) {
			return MotdState.getActiveMode().DEFAULT_PLAYER_NAME;
		}
		return MotdState.getActivePlugin().playerName(ip);
	}
	
	public Value handleOperators(String operatorString, String playerName, String ip) {
		return new Value(this.getValue(playerName, ip), "%" + this.name + "%");
	}
}
