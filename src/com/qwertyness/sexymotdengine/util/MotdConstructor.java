package com.qwertyness.sexymotdengine.util;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.qwertyness.sexymotdengine.MotdState;
import com.qwertyness.sexymotdengine.response.Mode;
import com.qwertyness.sexymotdengine.response.Response;

public class MotdConstructor {
	public InetAddress address;
	private String predynamicMotd;
	private String predynamicVersion;
	private List<String> predynamicPlayerMessage;
	private Response response;
	
	public MotdConstructor(InetAddress address) {
		this.address = address;
		this.predynamicPlayerMessage = new ArrayList<String>();
	}
	
	public void preBuild() {
		this.response = new Response(this.address);
		Mode mode = MotdState.getActiveMode();
		String address = this.address.getHostAddress();
		
		long time = new Date().getTime();
		this.predynamicMotd = response.motd.variableBuild(response.playerName, address);
		if (mode.performanceLogging) {
			PerformanceReport.preMotdBuild.add(new Date().getTime() - time);
		}
		if (mode.ENABLE_FAKE_VERSION) {
			this.predynamicVersion = response.fakeVersion.variableBuild(response.playerName, address);
		}
		if (mode.performanceLogging) {
			PerformanceReport.prePlayerMessageBuild.add(new Date().getTime() - time - PerformanceReport.preMotdBuild.get(PerformanceReport.preMotdBuild.size()-1));
		}
		if (mode.ENABLE_PLAYER_MESSAGE) {
			this.predynamicPlayerMessage = response.message.variableBuild(response.playerName, address);
		}
		if (mode.performanceLogging) {
			PerformanceReport.preVersionBuild.add(new Date().getTime() - time - PerformanceReport.preMotdBuild.get(PerformanceReport.preMotdBuild.size()-1)
				- PerformanceReport.prePlayerMessageBuild.get(PerformanceReport.prePlayerMessageBuild.size()-1));
		}
	}
	
	public MotdPackage dynamicBuild() {
		MotdPackage motdPackage = new MotdPackage();
		Mode mode = MotdState.getActiveMode();
		//Final build of ping components
		long time = new Date().getTime();
		String address = this.address.getHostAddress();
		//Build MOTD
		motdPackage.motd = response.motd.dynamicBuild(response.playerName, address, predynamicMotd);
		if (mode.performanceLogging) {
			PerformanceReport.motdBuild.add(new Date().getTime() - time);
		}
		
		//Build player data (player message + online + max)
		motdPackage.players = response.players;
		motdPackage.maxPlayers = response.maxPlayers;
		if (mode.ENABLE_PLAYER_MESSAGE) {
			motdPackage.maxPlayers = response.maxPlayers;
			motdPackage.players = response.players;
			motdPackage.playerMessage = response.preparePlayerInfo(response.playerName, address, predynamicPlayerMessage);
		}
		if (mode.performanceLogging) {
			PerformanceReport.playerMessageBuild.add(new Date().getTime() - time - PerformanceReport.motdBuild.get(PerformanceReport.motdBuild.size()-1));
		}
		
		//Build fake version
		if (mode.ENABLE_FAKE_VERSION) {
			motdPackage.version = response.fakeVersion.dynamicBuild(response.playerName, address, predynamicVersion);
		}
		if (mode.performanceLogging) {
			PerformanceReport.versionBuild.add(new Date().getTime() - time - PerformanceReport.motdBuild.get(PerformanceReport.motdBuild.size()-1)
					- PerformanceReport.playerMessageBuild.get(PerformanceReport.playerMessageBuild.size()-1));
		}
		
		//Set favicon
		if (mode.ENABLE_AVATAR_ICON || mode.ENABLE_OVERLAY_IMAGE) {
			motdPackage.favicon = response.favicon;
		}
		return motdPackage;
	}
}
