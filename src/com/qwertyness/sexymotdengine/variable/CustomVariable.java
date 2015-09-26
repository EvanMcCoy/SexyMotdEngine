package com.qwertyness.sexymotdengine.variable;

import com.qwertyness.sexymotdengine.response.Info;
import com.qwertyness.sexymotdengine.variable.Variable.VariableType;

public class CustomVariable {
	public String name;
	public VariableType type;
	public Variable builtInVariable;
	public Operator operator;
	public String condition;
	public String value;
	public String negValue;
	
	private boolean negStatus = false;
	
	public CustomVariable(String name, String variable, String operator, String condition, String value, String negValue) {
		this.name = name;
		for (Variable builtInVariable : Info.variables) {
			if (builtInVariable.name.equalsIgnoreCase(variable)) {
				this.builtInVariable = builtInVariable;
			}
		}
		this.operator = Operator.getOperatorFromSymbol(operator);
		this.condition = condition;
		this.value = value;
		this.negValue = negValue;
	}
	
	public enum Operator {
		EQUAL, DOES_NOT_EQUAL, LESS_THAN, GREATER_THAN, LESS_THAN_OR_EQUAL_TO, GREATER_THAN_OR_EQUAL_TO;
		
		public static Operator getOperatorFromSymbol(String symbol) {
			if (symbol.contains(">=")) {
				return Operator.GREATER_THAN_OR_EQUAL_TO;
			}
			else if (symbol.contains("<=")) {
				return Operator.LESS_THAN_OR_EQUAL_TO;
			}
			if (symbol.contains(">")) {
				return Operator.GREATER_THAN;
			}
			else if (symbol.contains("<")) {
				return Operator.LESS_THAN;
			}
			else if (symbol.contains("!=")) {
				return Operator.DOES_NOT_EQUAL;
			}
			else {
				return Operator.EQUAL;
			}
		}
	}
	
	public CustomVariable neg() {
		CustomVariable var = null;
		try {
			var = (CustomVariable) this.clone();
		} catch (CloneNotSupportedException e) {}
		var.negStatus = true;
		return var;
	}

	public String getValue(String playerName, String ip) {
		if (!negStatus) {
			return value;
		}
		return negValue;
	}
}
