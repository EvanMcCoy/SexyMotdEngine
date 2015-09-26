package com.qwertyness.sexymotdengine.variable;

import java.util.Random;

import com.qwertyness.sexymotdengine.ActivePlugin;
import com.qwertyness.sexymotdengine.response.Info;

public class RandomPlayer extends Variable {

	public RandomPlayer() {
		super("randomplayer", VariableType.DYNAMIC);
	}
	
	public String getValue(String playerName, String ip) {
		String[] players = ActivePlugin.activePlugin.playerNames();
		if (players.length < 1) {
			return Info.getActiveInfo().DEFAULT_PLAYER_NAME;
		}
		int index = new Random().nextInt(players.length);
		return players[index];
	}
	
	public Value handleOperators(String operatorString, String playerName, String ip) {
		return new Value(this.getValue(playerName, ip), "%" + this.name + "%");
	}
}