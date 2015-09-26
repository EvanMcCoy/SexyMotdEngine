package com.qwertyness.sexymotdengine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.qwertyness.sexymotdengine.response.Info;

public class CommandHandler {
	private static String indexColor = "&a";
	private static String itemColor = "&b";
	private static String errorColor = "&c";
	private static String command = "/motd";

	public static List<String> onCommand(String[] args) {
		List<String> messages = new ArrayList<String>();
		
		if (args.length < 1) {
			messages = printHelp();
		}
		else if (args[0].equalsIgnoreCase("help")) {
			messages = printHelp();
		}
		else if (args[0].equalsIgnoreCase("reload")) {
			try {
				ActivePlugin.activePlugin.loadConfig(Info.getInfo());
				ActivePlugin.activePlugin.loadConfig(Info.getMaintenance());
			} catch (Exception e) {
				messages = Arrays.asList(errorColor + "A problem occured while reading your configuration! Check for syntax errors.");
			}
			messages = Arrays.asList(indexColor + "SexyMotd Engine reloaded!");
		}
		else if (args[0].equalsIgnoreCase("maintenance")) {
			if (Info.getMaintenance().ENABLED) {
				Info.getMaintenance().ENABLED = false;
				ActivePlugin.activePlugin.setConfigValue(Info.getMaintenance(), "enabled", false);
				messages = Arrays.asList(errorColor + "Maintenance Mode disabled!");
			}
			else {
				Info.getMaintenance().ENABLED = true;
				ActivePlugin.activePlugin.setConfigValue(Info.getMaintenance(), "enabled", true);
				messages = Arrays.asList(indexColor + "Maintenance Mode enabled!");
			}
		}
		else if (args[0].equalsIgnoreCase("addmaintainer")) {
			if (args.length < 2) {
				return Arrays.asList(errorColor + "Invalid syntax! /motd addmaintainer <player_name>");
			}
			UUID newMaintainer = ActivePlugin.activePlugin.getPlayerUUID(args[1]);
			if (newMaintainer == null) {
				return Arrays.asList(errorColor + "Player not found.");
			}
			else if (newMaintainer.toString().equals("")) {
				return Arrays.asList(errorColor + "Player not found.");
			}
			for (UUID maintainer : Info.getMaintenance().maintainers) {
				if (maintainer.compareTo(newMaintainer) == 0) {
					return Arrays.asList(errorColor + "Player already a maintainer.");
				}
			}
			Info.getMaintenance().maintainers.add(newMaintainer);
			ActivePlugin.activePlugin.setConfigValue(Info.getMaintenance(), "maintainers", toStringList(Info.getMaintenance().maintainers));
			return Arrays.asList(indexColor + "Added maintainer " + args[1] + "!");
		}
		else if (args[0].equalsIgnoreCase("removemaintainer")) {
			if (args.length < 2) {
				return Arrays.asList(errorColor + "Invalid syntax! /motd removemaintainer <player_name>");
			}
			UUID removeMaintainer = ActivePlugin.activePlugin.getPlayerUUID(args[1]);
			if (removeMaintainer == null) {
				return Arrays.asList(errorColor + "Maintainer not found.");
			}
			for (UUID maintainer : Info.getMaintenance().maintainers) {
				if (maintainer.compareTo(removeMaintainer) == 0) {
					Info.getMaintenance().maintainers.remove(removeMaintainer);
					
					ActivePlugin.activePlugin.setConfigValue(Info.getMaintenance(), "maintainers", toStringList(Info.getMaintenance().maintainers));
					return Arrays.asList(errorColor + "Removed maintainer " + args[1] + ".");
				}
			}
			return Arrays.asList(errorColor + "Not a maintainer");
		}
		else {
			messages = Arrays.asList(errorColor + "Unknown command! Use /motd help to display valid commands.");
		}
		return messages;
	}
	
	private static List<String> printHelp() {
		return Arrays.asList(indexColor + "----- " + itemColor + "SexyMotd Engine Help" + indexColor + " -----",
				indexColor + command + " help " + itemColor + "- displays this page.",
				indexColor + command + " reload " + itemColor + "- reloads the plugin's configuration values from the file.");
	}
	
	public static List<String> toStringList(List<UUID> uuids) {
		List<String> strings = new ArrayList<String>();
		for (UUID uuid : uuids) {
			strings.add(uuid.toString());
		}
		return strings;
	}
}