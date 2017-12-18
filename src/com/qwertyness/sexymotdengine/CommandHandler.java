package com.qwertyness.sexymotdengine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class CommandHandler {
	private static String indexColor = "&a";
	private static String itemColor = "&b";
	private static String errorColor = "&c";
	private static String command = "/motd";

	public static List<String> onCommand(UUID uuid, String[] args, boolean console) {
		List<String> messages = new ArrayList<String>();
		
		if (args.length < 1) {
			messages = printHelp();
		}
		else if (args[0].equalsIgnoreCase("help")) {
			messages = printHelp();
		}
		else if (args[0].equalsIgnoreCase("reload")) {
			if (!console && !MotdState.getActivePlugin().hasPermission(uuid, "sexymotd.reload")) {
				messages = Arrays.asList(errorColor + "You do not have permission to execute that command!");
				return messages;
			}
			try {
				MotdState.reload();
			} catch (Exception e) {
				messages = Arrays.asList(errorColor + "A problem occured while reading your configuration! Check for syntax errors.");
			}
			messages = Arrays.asList(indexColor + "SexyMotd Engine reloaded!");
		}
		else if (args[0].equalsIgnoreCase("mode")) {
			if (!console && !MotdState.getActivePlugin().hasPermission(uuid, "sexymotd.mode")) {
				messages = Arrays.asList(errorColor + "You do not have permission to execute that command!");
				return messages;
			}
			if (args.length < 2) {
				messages = Arrays.asList(errorColor + "Too few arguments! /motd mode <mode>");
				return messages;
			}
			if (MotdState.setMode(args[1]) && !args[1].equals("config")) {
				messages = Arrays.asList(indexColor + args[1] + " mode enabled!");
				for (String name : MotdState.getActivePlugin().playerNames()) {
					UUID playerUUID = MotdState.getActivePlugin().getPlayerUUID(name);
					if (playerUUID != null) {
						if (!MotdState.getActivePlugin().hasPermission(playerUUID, "sexymotd.join." + MotdState.getActiveMode().getMode()) && !MotdState.getActivePlugin().hasPermission(playerUUID, "sexymotd.join.*")) {
							MotdState.getActivePlugin().kickPlayer(playerUUID, MotdState.getActiveMode().KICK_MESSAGE);
						}
					}
				}
			}
		}
		
		else {
			messages = Arrays.asList(errorColor + "Unknown command! Use /motd help to display valid commands.");
		}
		return messages;
	}
	
	private static List<String> printHelp() {
		return Arrays.asList(indexColor + "----- " + itemColor + "SexyMotd Engine Help" + indexColor + " -----",
				indexColor + command + " help " + itemColor + "- displays this page.",
				indexColor + command + " reload " + itemColor + "- reloads the plugin's configuration values from the file.",
				indexColor + command + " maintenance " + itemColor + "- toggles maintenance mode.",
				indexColor + command + " addmaintainer " + itemColor + "- allows a player to join the server while in maintenance mode.",
				indexColor + command + " removemaintainer " + itemColor + "- removes a player from the maintainer list.");
	}
	
	public static List<String> toStringList(List<UUID> uuids) {
		List<String> strings = new ArrayList<String>();
		for (UUID uuid : uuids) {
			strings.add(uuid.toString());
		}
		return strings;
	}
}
