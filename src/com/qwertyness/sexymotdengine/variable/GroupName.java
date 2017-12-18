package com.qwertyness.sexymotdengine.variable;

import com.qwertyness.sexymotdengine.MotdState;

public class GroupName extends Variable {

	public GroupName() {
		super("groupname", VariableType.NORMAL);
	}
	
	public String getValue(String playerName, String ip) {
		String groupList = "";
		for (String groupName : MotdState.getActivePlugin().groupNames(playerName)) {
			groupList += groupName + ", ";
		}
		groupList = groupList.substring(0, groupList.length()-2);
		return groupList;
	}
	
	public String[] getRawValue(String playerName) {
		return MotdState.getActivePlugin().groupNames(playerName);
	}
	
	public Value handleOperators(String operatorString, String playerName, String ip) {
		String value = this.getValue(playerName, ip);
		String operator = "";
		if (operatorString.startsWith("[") && operatorString.contains("]")) {
			int index = Integer.parseInt(operatorString.substring(1, operatorString.indexOf("]")));
			if (index < this.getRawValue(playerName).length) {
				value = this.getRawValue(playerName)[index];
			}
			else {
				value = "default";
			}
			operator = "[" + index + "]";
		}
		return new Value(value, "%" + this.name + "%" + operator);
	}
}
