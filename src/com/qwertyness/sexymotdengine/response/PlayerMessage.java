package com.qwertyness.sexymotdengine.response;

import java.util.ArrayList;
import java.util.List;

public class PlayerMessage {
	public List<AnimatedText> lines;
	
	public PlayerMessage(List<AnimatedText> lines, Info info) {
		this.lines = lines;
		this.buildPlayerMessage(info);
	}
	
	public void buildPlayerMessage(Info info) {
		for (AnimatedText text : this.lines) {
			text.buildText(info);
		}
	}
	
	public List<List<String>> variableBuild(String playerName, String address) {
		List<List<String>> builtLines = new ArrayList<List<String>>();
		for (AnimatedText text : this.lines) {
			builtLines.add(text.variableBuild(playerName, address));
		}
		return builtLines;
	}
	
	public List<String> dynamicBuild(String playerName, String address, List<List<String>> preText, int iteration) {
		List<String> builtLines = new ArrayList<String>();
		for (List<String> text : preText) {
			builtLines.add(lines.get(preText.indexOf(text)).dynamicBuild(playerName, address, text, iteration));
		}
		return builtLines;
	}
}
