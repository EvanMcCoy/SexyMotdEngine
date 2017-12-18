package com.qwertyness.sexymotdengine.response;

import java.util.ArrayList;
import java.util.List;

public class PlayerMessage {
	public List<VariableText> lines;
	
	public PlayerMessage(List<VariableText> lines, Mode mode) {
		this.lines = lines;
		this.buildPlayerMessage(mode);
	}
	
	public void buildPlayerMessage(Mode mode) {
		for (VariableText text : this.lines) {
			text.buildText(mode);
		}
	}
	
	public List<String> variableBuild(String playerName, String address) {
		List<String> builtLines = new ArrayList<String>();
		for (VariableText text : this.lines) {
			builtLines.add(text.variableBuild(playerName, address));
		}
		return builtLines;
	}
	
	public List<String> dynamicBuild(String playerName, String address, List<String> preText) {
		List<String> builtLines = new ArrayList<String>();
		for (String text : preText) {
			builtLines.add(lines.get(preText.indexOf(text)).dynamicBuild(playerName, address, text));
		}
		return builtLines;
	}
}
