package com.qwertyness.sexymotdengine.response;

import java.util.ArrayList;
import java.util.List;

import com.qwertyness.sexymotdengine.ActivePlugin;
import com.qwertyness.sexymotdengine.util.ScrollTextConstructor;
import com.qwertyness.sexymotdengine.util.ScrollTextConstructor.Position;
import com.qwertyness.sexymotdengine.util.Util;

public class AnimatedText {
	public List<String> frames;
	public Position position;
	public boolean scrolling;
	public String scrollingText = "";
	public String scrollingText2 = "";
	
	public AnimatedText(List<String> frames, Position position, boolean scrolling, Info info) {
		this.position = position;
		this.scrolling = scrolling;
		if (!scrolling) {
			this.frames = frames;
		}
		else {
			this.scrollingText = frames.get(0);
			if (position == Position.MOTD) {
				this.scrollingText2 = (frames.size() > 1) ? frames.get(1) : "";
			}
			this.frames = new ArrayList<String>();
		}
		this.buildText(info);
	}
	
	public void buildText(Info info) {
		List<String> frames = new ArrayList<String>();
		if (scrolling) { 
			this.scrollingText = Util.replaceStaticCustomVariables(this.scrollingText, info);
			if (this.position == Position.MOTD) {
				this.scrollingText2 = Util.replaceStaticCustomVariables(this.scrollingText2, info);
			}
		}
		
		for (String string : this.frames) {
			frames.add(Util.replaceStaticCustomVariables(string, info));
		}
		this.frames = frames;
	}
	
	public List<String> variableBuild(String playerName, String address) {
		List<String> frames = new ArrayList<String>();
		if (scrolling) {
			frames.add(Util.replaceCustomVariables(this.scrollingText, playerName, address));
			if (this.position == Position.MOTD) {
				frames.add(Util.replaceCustomVariables(this.scrollingText2, playerName, address));
			}
		}
		for (String string : this.frames) {
			frames.add(Util.replaceCustomVariables(string, playerName, address));
		}
		return frames;
	}
	
	public String dynamicBuild(String playerName, String address, List<String> decodedInput, int iteration) {
		List<String> frames = new ArrayList<String>();
		List<String> frames2 = new ArrayList<String>();
		String output = "";
		if (scrolling) {
			if (position == Position.MOTD) {
				String text = decodedInput.get(0);
				String text2 = decodedInput.get(1);
				text = Util.replaceVariables(Util.replaceDynamicCustomVariables(this.scrollingText), playerName, address);
				text2 = Util.replaceVariables(Util.replaceDynamicCustomVariables(this.scrollingText2), playerName, address);
				frames = ScrollTextConstructor.buildScrollList(text, position);
				frames2 = ScrollTextConstructor.buildScrollList(text2, position);
				
				output = ((frames.size() > 0) ? frames.get(iteration%frames.size()) : "") + "\n" + ((frames2.size() > 0) ? frames2.get(iteration%frames2.size()) : "");
			}
			else {
				String text = decodedInput.get(0);
				text = Util.replaceVariables(Util.replaceDynamicCustomVariables(this.scrollingText), playerName, address);
				frames = ScrollTextConstructor.buildScrollList(text, position);
				output = frames.get(iteration%frames.size());
			}
		}
		else {
			output = ActivePlugin.activePlugin.color(Util.replaceVariables(Util.replaceDynamicCustomVariables(decodedInput.get(iteration%decodedInput.size())), playerName, address));
		}
		return output;
	}
}
