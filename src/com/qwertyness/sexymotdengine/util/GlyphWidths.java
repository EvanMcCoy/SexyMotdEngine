package com.qwertyness.sexymotdengine.util;

public class GlyphWidths {
	public static int I = 3;
	public static int i = 1;
	public static int f = 4;
	public static int l = 2;
	public static int t = 3;
	public static int EXCLAIMATION_POINT = 1;
	public static int ASTERISK = 4;
	public static int COMMA = 1;
	public static int PERIOD = 1;
	public static int LESS_THAN = 4;
	public static int GREATER_THAN = 4;
	public static int LEFT_BRACKET = 3;
	public static int RIGHT_BRACKET = 3;
	public static int COLON = 1;
	public static int APOSTROPHE = 2;
	public static int SEMICOLON = 1;
	public static int HYPHEN = 2;
	public static int LEFT_BRACE = 4;
	public static int RIGHT_BRACE = 4;
	public static int LEFT_PARENTHESIS = 4;
	public static int RIGHT_PARENTHESIS = 4;
	public static int SPACE = 2;
	public static int DEFAULT = 5;
	
	public static String trimString(int maxWidth, String string) {
		while (getStringWidth(string) > maxWidth) {
			string = string.substring(0, string.length()-1);
		}
		if (string.charAt(string.length()-1) == '&') {
			string = string.substring(0, string.length()-1);
		}
		return string;
	}
	
	public static int getStringWidth(String string) {
		int width = 0;
		for (char character : string.toCharArray()) {
			width += getWidth(character);
		}
		return width;
	}
	
	public static int getWidth(char character) {
		switch(character) {
			default: return 5;
			case 'I': return I;
			case 'i': return i;
			case 'f': return f;
			case 'l': return l;
			case 't': return t;
			case '!': return EXCLAIMATION_POINT;
			case '*': return ASTERISK;
			case ',': return COMMA;
			case '.': return PERIOD;
			case '<': return LESS_THAN;
			case '>': return GREATER_THAN;
			case '[': return LEFT_BRACKET;
			case ']': return RIGHT_BRACKET;
			case ':': return COLON;
			case '\'': return APOSTROPHE;
			case ';': return SEMICOLON;
			case '-': return HYPHEN;
			case '{': return LEFT_BRACE;
			case '}': return RIGHT_BRACE;
			case '(': return LEFT_PARENTHESIS;
			case ')': return RIGHT_PARENTHESIS;
			case ' ': return SPACE;
		}
	}
}
