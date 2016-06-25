package com.qwertyness.sexymotdengine.response;

import java.awt.image.BufferedImage;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.qwertyness.sexymotdengine.ActivePlugin;
import com.qwertyness.sexymotdengine.util.PerformanceReport;
import com.qwertyness.sexymotdengine.util.Util;
import com.qwertyness.sexymotdengine.variable.PlayerName;
import com.qwertyness.sexymotdengine.variable.Variable;

public class Response {
	public AnimatedText motd;
	public List<BufferedImage> favicons;
	public int maxPlayers;
	public int players;
	public PlayerMessage message;
	public AnimatedText fakeVersion;
	public int protocol = -1;
	
	public String playerName = "";
	
	public Response(InetAddress playerAddress) {
		this.playerName = PlayerName.instance.getValue(null, playerAddress.getHostAddress());
		Info info = Info.getActiveInfo();
		this.motd = info.MOTDS.get(new Random().nextInt(info.MOTDS.size()));
		Long time = new Date().getTime();
		this.favicons = new ArrayList<BufferedImage>();
		if (info.ENABLE_AVATAR_ICON || info.ENABLE_OVERLAY_IMAGE) {
			try {
				for (BufferedImage image : Util.getFavicon(this.playerName)) {
					this.favicons.add(image);
				}
			} catch (NullPointerException e) {}
		}
		if (info.performanceLogging) {
			PerformanceReport.faviconBuild.add(new Date().getTime() - time);
		}
		
		if (info.ENABLE_MAX_PLAYERS) {
			if (info.STRING_MAX_PLAYERS != null) {
				if (info.STRING_MAX_PLAYERS.contains("%")) {
					String variableName = info.STRING_MAX_PLAYERS.substring(1, info.STRING_MAX_PLAYERS.lastIndexOf("%"));;
					String variableOperator = info.STRING_MAX_PLAYERS.substring(info.STRING_MAX_PLAYERS.lastIndexOf("%")+1);
					for (Variable variable : Info.variables) {
						if (variable.name.equalsIgnoreCase(variableName)) {
							try {
								String value = variable.handleOperators(variableOperator, playerName, playerAddress.getHostAddress()).value;
								this.maxPlayers = Integer.parseInt(value);
							} catch (NumberFormatException e) {this.maxPlayers = ActivePlugin.activePlugin.maxPlayers();}
						}
					}
				}
				else {
					try {
						this.maxPlayers = Integer.parseInt(info.STRING_MAX_PLAYERS);
					} catch(NumberFormatException e) {this.maxPlayers = 0;}
				}
			}
			else {
				this.maxPlayers = info.MAX_PLAYERS;
			}
		} else {this.maxPlayers = ActivePlugin.activePlugin.maxPlayers();}
		
		if (info.ENABLE_PLAYER_COUNT) {
			if (info.STRING_PLAYER_COUNT.size() > 0) {
				String variableString = info.STRING_PLAYER_COUNT.get(new Random().nextInt(info.STRING_PLAYER_COUNT.size()));
				if (variableString.contains("%")) {
					String variableName = variableString.substring(1, variableString.lastIndexOf("%"));
					String variableOperator = variableString.substring(variableString.lastIndexOf("%")+1);
					for (Variable variable : Info.variables) {
						if (variable.name.equalsIgnoreCase(variableName)) {
							try {
								String value = variable.handleOperators(variableOperator, playerName, playerAddress.getHostAddress()).value;
								this.players = Integer.parseInt(value);
							} catch (NumberFormatException e) {this.players = ActivePlugin.activePlugin.onlinePlayers();}
						}
					}
				}
				else {
					try {
						this.players = Integer.parseInt(variableString);
					} catch (NumberFormatException e) {
						this.players = ActivePlugin.activePlugin.onlinePlayers();
					}
				}
			}
			else {
				this.players = info.PLAYER_COUNT.get(new Random().nextInt(info.PLAYER_COUNT.size()));
			}
		} else {this.players = ActivePlugin.activePlugin.onlinePlayers();}
		
		if (info.ENABLE_PLAYER_MESSAGE) {
			this.message = info.PLAYER_MESSAGE;
		}
		
		this.fakeVersion = info.FAKE_VERSION;
	}
	
	public List<String> preparePlayerInfo(String playerName, String address, List<List<String>> preText, int frame) {
		List<String> list = this.message.dynamicBuild(playerName, address, preText, frame);
		List<String> output = new ArrayList<String>();
		for (String string : list) {
			output.add(string);
		}
		return output;
	}
}
