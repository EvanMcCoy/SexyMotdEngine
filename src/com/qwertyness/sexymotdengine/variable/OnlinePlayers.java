package com.qwertyness.sexymotdengine.variable;

import com.qwertyness.sexymotdengine.MotdState;
import com.qwertyness.sexymotdengine.util.VariableUtil;

public class OnlinePlayers extends Variable {
	
	public OnlinePlayers() {
		super("onlineplayers", VariableType.DYNAMIC);
	}

	@Override
	public String getValue(String playerName, String ip) {
		return new Integer(MotdState.getActivePlugin().onlinePlayers()).toString();
	}

	public int getRawValue() {
		return MotdState.getActivePlugin().onlinePlayers();
	}
	
	public Value handleOperators(String operatorString, String playerName, String ip) {
		String value = this.getValue(playerName, ip);
		String operator = "";
		char operatorChar = operatorString.charAt(0);
		boolean hasOperator = false;
		if (operatorChar == '+') {
			value = new Integer((int)Math.round(this.getRawValue() + VariableUtil.trimNumber(operatorString))).toString();
			hasOperator = true;
		}
		else if (operatorChar == '-') {
			value = new Integer((int)Math.round(this.getRawValue() - VariableUtil.trimNumber(operatorString))).toString();
			hasOperator = true;
		}
		else if (operatorChar == '*') {
			value = new Integer((int)Math.round(this.getRawValue() * VariableUtil.trimNumber(operatorString))).toString();
			hasOperator = true;
		}
		else if (operatorChar == '/') {
			value = new Integer((int)Math.round(this.getRawValue() / VariableUtil.trimNumber(operatorString))).toString();
			hasOperator = true;
		}
		
		if (hasOperator) {
			operator = "" + operatorString.charAt(0) + VariableUtil.trimNumber(operatorString);
		}
		return new Value(value, "%" + this.name + "%" + operator);
	}
}
