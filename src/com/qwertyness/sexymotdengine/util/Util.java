package com.qwertyness.sexymotdengine.util;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import javax.imageio.ImageIO;

import com.qwertyness.sexymotdengine.MotdState;
import com.qwertyness.sexymotdengine.response.Mode;
import com.qwertyness.sexymotdengine.variable.ArgumentVariable;
import com.qwertyness.sexymotdengine.variable.CustomVariable;
import com.qwertyness.sexymotdengine.variable.CustomVariable.Operator;
import com.qwertyness.sexymotdengine.variable.Value;
import com.qwertyness.sexymotdengine.variable.Variable;

public class Util {
	public static BufferedImage overlay;
	
	public static String replaceVariables(String input, String playerName, String address) {
		String[] variableStrings = input.split("%");
		for (int i = 1;i < variableStrings.length;i++) {
			for (Variable variable : Mode.variables) {
				if (variable instanceof ArgumentVariable) {
					List<Value> values = ((ArgumentVariable)variable).evaluate(playerName, address, input);
					for (Value value : values) {
						input = input.replace(value.replacing, value.value);
					}
				}
				else if (variableStrings[i].equalsIgnoreCase(variable.name)) {
					Value value = VariableUtil.checkForOperators(variable, ((i+1) < variableStrings.length) ? variableStrings[i+1] : "^", playerName, address);
					input = input.replace(value.replacing, value.value);
				}
			}
		}
		return input;
	}
	
	public static String replaceStaticCustomVariables(String input, Mode mode) {
		for (CustomVariable variable : mode.customVariables) {
			if (!input.toLowerCase().contains(variable.name.toLowerCase())) {
				continue;
			}
			if (variable.builtInVariable == null) {
				continue;
			}
			else if (variable.builtInVariable.name.contains("maxplayers".toLowerCase())) {
				if (checkNumericCondition(variable, MotdState.getActivePlugin().maxPlayers())) {
					input = replaceCustomVariable(variable, input, false);
				}
				else {
					input = replaceCustomVariable(variable, input, true);
				}
			}
			else if (variable.builtInVariable.name.contains("version".toLowerCase())) {
				if (checkStringCondition(variable, MotdState.getActivePlugin().version())) {
					input = replaceCustomVariable(variable, input, false);
				}
				else {
					input = replaceCustomVariable(variable, input, true);
				}
			}
		}
		return input;
	}
	
	public static String replaceCustomVariables(String input, String playerName, String address) {
		boolean newplayer = MotdState.getActivePlugin().newPlayer(playerName);
		for (CustomVariable variable : MotdState.getActiveMode().customVariables) {
			if (!input.toLowerCase().contains(variable.name.toLowerCase())) {
				continue;
			}
			if (variable.builtInVariable == null) {
				continue;
			}
			if (variable.builtInVariable.name.contains("playername".toLowerCase())) {
				if (checkStringCondition(variable, playerName)) {
					input = replaceCustomVariable(variable, input, false);
				}
				else {
					input = replaceCustomVariable(variable, input, true);
				}
			}
			else if (variable.builtInVariable.name.contains("newplayer".toLowerCase())) {
				if (variable.operator == Operator.EQUAL) {
					if ((variable.condition.equalsIgnoreCase("true") && newplayer) || (variable.condition.equalsIgnoreCase("false") && !newplayer)) {
						input = replaceCustomVariable(variable, input, false);
					}
					else {
						input = replaceCustomVariable(variable, input, true);
					}
				}
			}
			else if (variable.builtInVariable.name.contains("groupname".toLowerCase())) {
				if (variable.operator == Operator.EQUAL) {
					boolean validGroup = false;
					for (String group : MotdState.getActivePlugin().groupNames(playerName)) {
						if (checkStringCondition(variable, group)) {
							input = replaceCustomVariable(variable, input, false);
							validGroup = true;
							break;
						}
					}
					if (!validGroup) {
						input = replaceCustomVariable(variable, input, true);
					}
				}
			}
			else if (variable.builtInVariable.name.contains("ip".toLowerCase())) {
				if (variable.operator == Operator.EQUAL) {
					if (checkStringCondition(variable, address)) {
						input = replaceCustomVariable(variable, input, false);
					}
					else {
						input = replaceCustomVariable(variable, input, true);
					}
				}
			}
			else {
				if (variable.operator == Operator.EQUAL) {
					if (variable.builtInVariable instanceof ArgumentVariable) {
						List<Value> values = ((ArgumentVariable)variable.builtInVariable).evaluate(playerName, address, "%" + variable.rawBuiltInVariable + "%");
						for (Value value : values) {
							if (checkStringCondition(variable, value.value)) {
								input = replaceCustomVariable(variable, input, false);
							}
							else {
								input = replaceCustomVariable(variable, input, true);
							}
						}
					}
					else {
						if (checkStringCondition(variable, variable.builtInVariable.getValue(playerName, address))) {
							input = replaceCustomVariable(variable, input, false);
						}
						else {
							input = replaceCustomVariable(variable, input, true);
						}
					}
				}
			}
		}
		return input;
	}
	
	public static String replaceDynamicCustomVariables(String input) {
		int onlinePlayers = MotdState.getActivePlugin().onlinePlayers();
		
		for (CustomVariable variable : MotdState.getActiveMode().customVariables) {
			if (!input.toLowerCase().contains(variable.name.toLowerCase())) {
				continue;
			}
			if (variable.builtInVariable == null) {
				continue;
			}
			else if (variable.builtInVariable.name.contains("onlineplayers".toLowerCase())) {
				if (checkNumericCondition(variable, onlinePlayers)) {
					input = replaceCustomVariable(variable, input, false);
				}
				else {
					input = replaceCustomVariable(variable, input, true);
				}
			}
		}
		return input;
	}
	
	public static boolean checkNumericCondition(CustomVariable variable, int condition) {
		if (variable.operator == Operator.EQUAL) {
			if (Integer.parseInt(variable.condition) == condition) {
				return true;
			}
		}
		if (variable.operator == Operator.DOES_NOT_EQUAL) {
			if (Integer.parseInt(variable.condition) != condition) {
				return true;
			}
		}
		else if (variable.operator == Operator.GREATER_THAN) {
			if (condition > Integer.parseInt(variable.condition)) {
				return true;
			}
		}
		else if (variable.operator == Operator.GREATER_THAN_OR_EQUAL_TO) {
			if (condition >= Integer.parseInt(variable.condition)) {
				return true;
			}
		}
		else if (variable.operator == Operator.LESS_THAN) {
			if (condition < Integer.parseInt(variable.condition)) {
				return true;
			}
		}
		else if (variable.operator == Operator.LESS_THAN_OR_EQUAL_TO) {
			if (condition <= Integer.parseInt(variable.condition)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean checkStringCondition(CustomVariable variable, String condition) {
		if (variable.operator == Operator.DOES_NOT_EQUAL) {
			if (!condition.equalsIgnoreCase(variable.condition)) {
				return true;
			}
		}
		else {
			if (condition.equalsIgnoreCase(variable.condition)) {
				return true;
			}
		}
		return false;
	}
	
	public static String replaceCustomVariable(CustomVariable variable, String input, boolean neg) {
		if (!neg) {
			input = input.replace("%" + variable.name + "%", variable.value);
		}
		else {
			input = input.replace("%" + variable.name + "%", variable.negValue);
		}
		return input;
	}
	
	public static BufferedImage getFavicon(String playerName) {
		Mode mode = MotdState.getActiveMode();
		BufferedImage image = null;
		BufferedImage avatar = null;
		BufferedImage icon = null;
		
		//Create avatar image
		if (mode.ENABLE_AVATAR_ICON) {
			try {
				avatar = ImageIO.read(new URL("https://minotar.net/helm/" + ((!playerName.equals(mode.DEFAULT_PLAYER_NAME)) ?  playerName : "steve") + "/64.png"));
			} catch (IOException e) {e.printStackTrace();}
		}
		
		if (mode.ENABLE_AVATAR_ICON && mode.ENABLE_OVERLAY_IMAGE) {
			icon = ImageUtil.resizeImage(avatar);
			icon.getGraphics().drawImage(overlay, 0, 0, 64, 64, null);
			image = icon;
		}
		else if (mode.ENABLE_AVATAR_ICON) {
			image = avatar;
		}
		else if (mode.ENABLE_OVERLAY_IMAGE) {
			image = overlay;
		}
		return image;
	}
	
	public static void checkOverlayOn() {
		Mode mode = MotdState.getActiveMode();
		//Create overlay image
		if (mode.ENABLE_OVERLAY_IMAGE) {
			overlay = ImageUtil.getFavicon();
		}
	}
}
