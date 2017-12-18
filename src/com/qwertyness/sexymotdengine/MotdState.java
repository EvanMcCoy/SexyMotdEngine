package com.qwertyness.sexymotdengine;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.qwertyness.sexymotdengine.response.Mode;
import com.qwertyness.sexymotdengine.util.Util;
import com.qwertyness.sexymotdengine.variable.GroupName;
import com.qwertyness.sexymotdengine.variable.IP;
import com.qwertyness.sexymotdengine.variable.MaxPlayers;
import com.qwertyness.sexymotdengine.variable.NewPlayer;
import com.qwertyness.sexymotdengine.variable.Newline;
import com.qwertyness.sexymotdengine.variable.OnlinePlayers;
import com.qwertyness.sexymotdengine.variable.PlayerName;
import com.qwertyness.sexymotdengine.variable.PlayerNames;
import com.qwertyness.sexymotdengine.variable.ServerName;
import com.qwertyness.sexymotdengine.variable.Version;

public class MotdState {
	private static SexyMotdPlugin activePlugin;
	private static List<Mode> modes = new ArrayList<Mode>();
	private static Mode activeMode;
	
	public static void initialize(SexyMotdPlugin plugin) {
		activePlugin = plugin;
		Mode.variables.add(new ServerName());
		Mode.variables.add(new Version());
		Mode.variables.add(new MaxPlayers());
		Mode.variables.add(new OnlinePlayers());
		Mode.variables.add(new PlayerName());
		Mode.variables.add(new NewPlayer());
		Mode.variables.add(new IP());
		Mode.variables.add(new GroupName());
		Mode.variables.add(new Newline());
		Mode.variables.add(new PlayerNames());
		
		Util.checkOverlayOn();
	}
	
	public static void reload() {
		modes = new ArrayList<Mode>();
	}
	
	public static void disable() {
		activePlugin = null;
		activeMode = null;
		modes = null;
	}
	
	public static SexyMotdPlugin getActivePlugin() {
		return activePlugin;
	}
 	
	public static Mode getActiveMode() {
		return activeMode;
	}
	
	public static boolean setMode(String mode) {
		boolean success = false;
		for (Mode m : modes) {
			if (m.getMode().equals(mode)) {
				activeMode = m;
				success = true;
			}
		}
		return success;
	}
	
	public static void registerMode(Mode mode) {
		modes.add(mode);
	}
	
	public static String color(String input) {
		return activePlugin.color(input);
	}
	
	public interface SexyMotdPlugin {
		public abstract String serverName();
		public abstract int maxPlayers();
		public abstract String version();
		
		public abstract boolean newPlayer(String ip);
		public abstract String playerName(String ip);
		public abstract String[] groupNames(String playerName);
		
		public abstract int onlinePlayers();
		public abstract String[] playerNames();
		public abstract UUID getPlayerUUID(String name);
		public abstract boolean hasPermission(UUID uuid, String permission);
		public abstract void kickPlayer(UUID uuid, String reason);
		
		public abstract String color(String input);
		
		public abstract void loadConfig(Mode mode);
	}
}
