package com.qwertyness.sexymotdengine.util;

import com.qwertyness.sexymotdengine.variable.Value;
import com.qwertyness.sexymotdengine.variable.Variable;

public class VariableUtil {
	
	public static Value checkForOperators(Variable variable, String conditionalInput, String playerName, String ip) {
		return variable.handleOperators(conditionalInput, playerName, ip);
	}
	
	public static int trimNumber(String input) {
		String output = "";
		boolean inNumber = false;
		for (char c : input.toCharArray()) {
			if (c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '0') {
				inNumber = true;
				output += c;
			}
			else if (inNumber) {
				break;
			}
		}
		return Integer.parseInt(output);
	}
}
