package com.qwertyness.sexymotdengine.variable;

import com.qwertyness.sexymotdengine.ActivePlugin;
import com.qwertyness.sexymotdengine.response.Info;

public class PlayerName extends Variable {
	public static PlayerName instance;

	public PlayerName() {
		super("playername", VariableType.NORMAL);
		instance = this;
	}
	
	public String getValue(String playerName, String ip) {
		if (ActivePlugin.activePlugin.newPlayer(ip)) {
			return Info.getActiveInfo().DEFAULT_PLAYER_NAME;
		}
		return ActivePlugin.activePlugin.playerName(ip);
	}
	
	public Value handleOperators(String operatorString, String playerName, String ip) {
		return new Value(this.getValue(playerName, ip), "%" + this.name + "%");
	}
}
