package com.qwertyness.sexymotdengine.util;

import java.util.ArrayList;
import java.util.List;

import com.qwertyness.sexymotdengine.ActivePlugin;

public class ScrollTextConstructor {
	public static final char xa7 = '§';
	
	public enum Position {
		MOTD, VERSION, PLAYER_MESSAGE;
		public static int motd;
		public static int version;
		public static int playerMessage;
		
		public int widthLimit() {
			switch(this) {
				default: return motd;
				case VERSION: return version;
				case PLAYER_MESSAGE: return playerMessage;
			}
		}
	}

	public static List<String> buildScrollList(String fullText, Position position) {
		List<String> frames = new ArrayList<String>();
		//Add color code reset at the beginning and end of the string to stabilize coloring cross-frame.
		fullText = "&r" + fullText + "&r";
		int spacing = 10;
		if (GlyphWidths.getStringWidth(fullText) < position.widthLimit()) {
			spacing += (position.widthLimit()-GlyphWidths.getStringWidth(fullText))/2;
		}
		for (int counter = 0;counter < spacing;counter++) {
			fullText += " ";
		}
		String tempText = fullText;
		boolean skipNextCharacter = false;
		List<Character> currentColorCodes = new ArrayList<Character>();
		for (int counter = 0;counter < fullText.length();counter++) {
			if (fullText.charAt(counter) == '&') {
				skipNextCharacter = true;
				char firstCharacter = tempText.charAt(0);
				tempText = tempText.substring(1) + firstCharacter;
				continue;
			}
			if (skipNextCharacter) {
				skipNextCharacter = false;
				currentColorCodes.add(fullText.charAt(counter));
				char firstCharacter = tempText.charAt(0);
				tempText = tempText.substring(1) + firstCharacter;
				continue;
			}
			String colorCodes = "";
			for (char colorCode : currentColorCodes) {
				colorCodes += "&" + colorCode;
			}
			char firstCharacter = tempText.charAt(0);
			tempText = tempText.substring(1) + firstCharacter;
			frames.add(ActivePlugin.activePlugin.color(colorCodes + GlyphWidths.trimString(position.widthLimit(), tempText)));
		}
		return frames;
	}
	
	public static String supplementColorCodes(String string) {
		String output = "";
		List<Character> currentColorCodes = new ArrayList<Character>();
		boolean grabNextCharacter = false;
		for (char character : string.toCharArray()) {
			if (character == '&') {
				grabNextCharacter = true;
				continue;
			}
			if (grabNextCharacter) {
				if (character == 'r') {
					currentColorCodes = new ArrayList<Character>();
				}
				currentColorCodes.add(character);
				grabNextCharacter = false;
				continue;
			}
			for (char char1 : currentColorCodes) {
				output += "&" + char1;
			}
			output += character;
		}
		return output;
	}
}
