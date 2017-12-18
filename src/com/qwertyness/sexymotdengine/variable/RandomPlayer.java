package com.qwertyness.sexymotdengine.variable;

import java.util.Random;

import com.qwertyness.sexymotdengine.MotdState;

public class RandomPlayer extends Variable {

	public RandomPlayer() {
		super("randomplayer", VariableType.DYNAMIC);
	}
	
	public String getValue(String playerName, String ip) {
		String[] players = MotdState.getActivePlugin().playerNames();
		if (players.length < 1) {
			return MotdState.getActiveMode().DEFAULT_PLAYER_NAME;
		}
		int index = new Random().nextInt(players.length);
		return players[index];
	}
	
	public Value handleOperators(String operatorString, String playerName, String ip) {
		return new Value(this.getValue(playerName, ip), "%" + this.name + "%");
	}
}