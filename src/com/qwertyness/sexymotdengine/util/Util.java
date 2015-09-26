package com.qwertyness.sexymotdengine.util;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;


import com.qwertyness.sexymotdengine.ActivePlugin;
import com.qwertyness.sexymotdengine.response.Info;
import com.qwertyness.sexymotdengine.util.ImageUtil;
import com.qwertyness.sexymotdengine.variable.CustomVariable;
import com.qwertyness.sexymotdengine.variable.CustomVariable.Operator;
import com.qwertyness.sexymotdengine.variable.Value;
import com.qwertyness.sexymotdengine.variable.Variable;

public class Util {
	public static List<BufferedImage> overlay = new ArrayList<BufferedImage>();
	
	public static String replaceVariables(String input, String playerName, String address) {
		String[] variableStrings = input.split("%");
		for (int i = 1;i < variableStrings.length;i++) {
			for (Variable variable : Info.variables) {
				if (variableStrings[i].equalsIgnoreCase(variable.name)) {
					Value value = VariableUtil.checkForOperators(variable, ((i+1) < variableStrings.length) ? variableStrings[i+1] : "^", playerName, address);
					input = input.replace(value.replacing, value.value);
				}
			}
		}
		return input;
	}
	
	public static String replaceStaticCustomVariables(String input, Info info) {
		for (CustomVariable variable : info.customVariables) {
			if (!input.toLowerCase().contains(variable.name.toLowerCase())) {
				continue;
			}
			if (variable.builtInVariable == null) {
				continue;
			}
			else if (variable.builtInVariable.name.contains("maxplayers".toLowerCase())) {
				if (checkNumericCondition(variable, ActivePlugin.activePlugin.maxPlayers())) {
					input = replaceCustomVariable(variable, input, false);
				}
				else {
					input = replaceCustomVariable(variable, input, true);
				}
			}
			else if (variable.builtInVariable.name.contains("version".toLowerCase())) {
				if (checkStringCondition(variable, ActivePlugin.activePlugin.version())) {
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
		boolean newplayer = ActivePlugin.activePlugin.newPlayer(playerName);
		
		for (CustomVariable variable : Info.getActiveInfo().customVariables) {
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
					for (String group : ActivePlugin.activePlugin.groupNames(playerName)) {
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
		}
		return input;
	}
	
	public static String replaceDynamicCustomVariables(String input) {
		int onlinePlayers = ActivePlugin.activePlugin.onlinePlayers();
		
		for (CustomVariable variable : Info.getActiveInfo().customVariables) {
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
	
	public static List<BufferedImage> getFavicon(String playerName) {
		Info info = Info.getActiveInfo();
		List<BufferedImage> images = new ArrayList<BufferedImage>();
		BufferedImage avatar = null;
		BufferedImage icon = null;
		
		//Create avatar image
		if (info.ENABLE_AVATAR_ICON) {
			try {
				avatar = ImageIO.read(new URL("https://minotar.net/helm/" + ((!playerName.equals(info.DEFAULT_PLAYER_NAME)) ?  playerName : "steve") + "/64.png"));
			} catch (IOException e) {e.printStackTrace();}
		}
		
		if (info.ENABLE_AVATAR_ICON && info.ENABLE_OVERLAY_IMAGE) {
			for (BufferedImage overlay : overlay) {
				icon = ImageUtil.resizeImage(avatar);
				icon.getGraphics().drawImage(overlay, 0, 0, 64, 64, null);
				images.add(icon);
			} 
		}
		else if (info.ENABLE_AVATAR_ICON) {
			images.add(avatar);
		}
		else if (info.ENABLE_OVERLAY_IMAGE) {
			images = overlay;
		}
		return images;
	}
	
	public static void checkOverlayOn() {
		Info info = Info.getActiveInfo();
		//Create overlay image
		if (info.ENABLE_OVERLAY_IMAGE) {
			if (info.GIF_OPTIMIZED) {
				overlay = ImageUtil.getOptimizedGIFFrames();
			}
			else {
				overlay = ImageUtil.getGIFFrames();
			}
		}
	}
}
