package com.qwertyness.sexymotdengine.util;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.qwertyness.sexymotdengine.response.Info;
import com.qwertyness.sexymotdengine.response.Response;

public class MotdConstructor {
	public InetAddress address;
	private List<String> predynamicMotd;
	private List<String> predynamicVersion;
	private List<List<String>> predynamicPlayerMessage;
	private Response response;
	
	public MotdConstructor(InetAddress address) {
		this.address = address;
		this.predynamicMotd = new ArrayList<String>();
		this.predynamicVersion = new ArrayList<String>();
		this.predynamicPlayerMessage = new ArrayList<List<String>>();
	}
	
	public void preBuild() {
		this.response = new Response(this.address);
		Info info = Info.getActiveInfo();
		String address = this.address.getHostAddress();
		
		long time = new Date().getTime();
		this.predynamicMotd = response.motd.variableBuild(response.playerName, address);
		if (info.performanceLogging) {
			PerformanceReport.preMotdBuild.add(new Date().getTime() - time);
		}
		if (info.ENABLE_FAKE_VERSION) {
			this.predynamicVersion = response.fakeVersion.variableBuild(response.playerName, address);
		}
		if (info.performanceLogging) {
			PerformanceReport.prePlayerMessageBuild.add(new Date().getTime() - time - PerformanceReport.preMotdBuild.get(PerformanceReport.preMotdBuild.size()-1));
		}
		if (info.ENABLE_PLAYER_MESSAGE) {
			this.predynamicPlayerMessage = response.message.variableBuild(response.playerName, address);
		}
		if (info.performanceLogging) {
			PerformanceReport.preVersionBuild.add(new Date().getTime() - time - PerformanceReport.preMotdBuild.get(PerformanceReport.preMotdBuild.size()-1)
				- PerformanceReport.prePlayerMessageBuild.get(PerformanceReport.prePlayerMessageBuild.size()-1));
		}
	}
	
	public MotdPackage dynamicBuild(int iteration) {
		MotdPackage motdPackage = new MotdPackage();
		Info info = Info.getActiveInfo();
		//Final build of ping components
		long time = new Date().getTime();
		String address = this.address.getHostAddress();
		//Build MOTD
		motdPackage.motd = response.motd.dynamicBuild(response.playerName, address, predynamicMotd, iteration);
		if (info.performanceLogging) {
			PerformanceReport.motdBuild.add(new Date().getTime() - time);
		}
		
		//Build player data (player message + online + max)
		motdPackage.players = response.players;
		motdPackage.maxPlayers = response.maxPlayers;
		if (info.ENABLE_PLAYER_MESSAGE) {
			motdPackage.maxPlayers = response.maxPlayers;
			motdPackage.players = response.players;
			motdPackage.playerMessage = response.preparePlayerInfo(response.playerName, address, predynamicPlayerMessage, iteration);
		}
		if (info.performanceLogging) {
			PerformanceReport.playerMessageBuild.add(new Date().getTime() - time - PerformanceReport.motdBuild.get(PerformanceReport.motdBuild.size()-1));
		}
		
		//Build fake version
		if (info.ENABLE_FAKE_VERSION) {
			motdPackage.version = response.fakeVersion.dynamicBuild(response.playerName, address, predynamicVersion, iteration);
		}
		if (info.performanceLogging) {
			PerformanceReport.versionBuild.add(new Date().getTime() - time - PerformanceReport.motdBuild.get(PerformanceReport.motdBuild.size()-1)
					- PerformanceReport.playerMessageBuild.get(PerformanceReport.playerMessageBuild.size()-1));
		}
		
		//Set favicon
		if (info.ENABLE_AVATAR_ICON || info.ENABLE_OVERLAY_IMAGE) {
			if (response.favicons.size() > 0) {
				motdPackage.favicon = response.favicons.get(iteration%response.favicons.size());
			}
		}
		return motdPackage;
	}
}
