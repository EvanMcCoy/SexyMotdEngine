package com.qwertyness.sexymotdengine.util;

import com.qwertyness.sexymotdengine.variable.Value;
import com.qwertyness.sexymotdengine.variable.Variable;

public class VariableUtil {
	
	public static Value checkForOperators(Variable variable, String conditionalInput, String playerName, String ip) {
		return variable.handleOperators(conditionalInput, playerName, ip);
	}
	
	public static double trimNumber(String input) {
		input = input.substring(1, input.length());
		String output = "";
		for (char c : input.toCharArray()) {
			if (c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' 
					|| c == '8' || c == '9' || c == '.') {
				output += c;
			}
			else {
				break;
			}
		}
		return Double.parseDouble(output);
	}
}
