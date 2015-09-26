package com.qwertyness.sexymotdengine;

import java.util.UUID;

import com.qwertyness.sexymotdengine.response.Info;
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

public class ActivePlugin {
	public static SexyMotdPlugin activePlugin;
	
	public static void initialize(SexyMotdPlugin plugin) {
		activePlugin = plugin;
		Info.variables.add(new ServerName());
		Info.variables.add(new Version());
		Info.variables.add(new MaxPlayers());
		Info.variables.add(new OnlinePlayers());
		Info.variables.add(new PlayerName());
		Info.variables.add(new NewPlayer());
		Info.variables.add(new IP());
		Info.variables.add(new GroupName());
		Info.variables.add(new Newline());
		Info.variables.add(new PlayerNames());
		Info.init();
		Util.checkOverlayOn();
		
	}
	
	public static void disable() {
		activePlugin.saveConfig(Info.getInfo());
		activePlugin.saveConfig(Info.getMaintenance());
		activePlugin = null;
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
		
		public abstract String color(String input);
		
		public abstract void loadConfig(Info info);
		public abstract void setConfigValue(Info info, String key, Object value);
		public abstract void saveConfig(Info info);
	}
}
