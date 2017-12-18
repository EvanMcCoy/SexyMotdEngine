package com.qwertyness.sexymotdengine.response;

import com.qwertyness.sexymotdengine.MotdState;
import com.qwertyness.sexymotdengine.util.Util;

public class VariableText {
	public String text;
	
	public VariableText(String text, Mode mode) {
		this.text = text;
		this.buildText(mode);
	}
	
	public void buildText(Mode mode) {
		this.text = Util.replaceStaticCustomVariables(this.text, mode);
	}
	
	public String variableBuild(String playerName, String address) {
		return Util.replaceCustomVariables(this.text, playerName, address);
	}
	
	public String dynamicBuild(String playerName, String address, String decodedInput) {
		return MotdState.color(Util.replaceVariables(Util.replaceDynamicCustomVariables(decodedInput), playerName, address));
	}
}
