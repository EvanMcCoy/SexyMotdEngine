package com.qwertyness.sexymotdengine.util;

import java.util.ArrayList;
import java.util.List;

import com.qwertyness.sexymotdengine.response.Info;

public class PerformanceReport {
	public static long startup = 0;
	public static long configRead = 0;
	public static List<Long> preMotdBuild = new ArrayList<Long>();
	public static List<Long> prePlayerMessageBuild = new ArrayList<Long>();
	public static List<Long> preVersionBuild = new ArrayList<Long>();
	public static List<Long> motdBuild = new ArrayList<Long>();
	public static List<Long> playerMessageBuild = new ArrayList<Long>();
	public static List<Long> versionBuild = new ArrayList<Long>();
	public static List<Long> faviconBuild = new ArrayList<Long>();
	public static List<Long> nettyDecode = new ArrayList<Long>();
	
	public static List<String> logPerformance() {
		List<String> strings = new ArrayList<String>();
		if (!Info.getActiveInfo().performanceLogging) {
			strings.add("[SexyMotdBungee] Performance Logging Disabled.  Enable it in the configuration file to see performance reports.");
			return strings;
		}
		strings.add("----- SexyMotdBungee Performance Report -----");
		strings.add("Last Startup Time: " + startup + "ms");
		strings.add("Last Configuration Load Time: " + configRead + "ms");
		strings.add("---------------------------------------------");
		strings.add("Calculated once (synchronously) every time a player pings the server.");
		strings.add("Last Motd Prebuild Time: " + ((preMotdBuild.size() > 0) ? preMotdBuild.get(preMotdBuild.size()-1) : 0) + "ms");
		strings.add("Average Motd Prebuild Time: " + average(preMotdBuild) + "ms");
		strings.add("Last Player Message Prebuild Time: " + ((prePlayerMessageBuild.size() > 0) ? prePlayerMessageBuild.get(prePlayerMessageBuild.size()-1) : 0) + "ms");
		strings.add("Average Player Message Prebuild Time: " + average(prePlayerMessageBuild) + "ms");
		strings.add("Last Version Prebuild Time: " + ((preVersionBuild.size() > 0) ? preVersionBuild.get(preVersionBuild.size()-1) : 0) + "ms");
		strings.add("Average Version Prebuild Time: " + average(preVersionBuild) + "ms");
		strings.add("Last Favicon Build Time: " + ((faviconBuild.size() > 0) ? faviconBuild.get(faviconBuild.size()-1) : 0) + "ms");
		strings.add("Average Favion Build Time: " + average(faviconBuild) + "ms");
		strings.add("---------------------------------------------");
		strings.add("Calculated every frame (asynchronously) for each open ping packet connection.");
		strings.add("Last Motd Build Time: " + ((motdBuild.size() > 0) ? motdBuild.get(motdBuild.size()-1) : 0) + "ms");
		strings.add("Average Motd Build Time: " + average(motdBuild) + "ms");
		strings.add("Last Player Message Build Time: " + ((playerMessageBuild.size() > 0) ? playerMessageBuild.get(playerMessageBuild.size()-1) : 0) + "ms");
		strings.add("Average Player Message Build Time: " + average(playerMessageBuild) + "ms");
		strings.add("Last Version Build Time: " + ((versionBuild.size() > 0) ? versionBuild.get(versionBuild.size()-1) : 0) + "ms");
		strings.add("Average Version Build Time: " + average(versionBuild) + "ms");
		return strings;
	}
	
	private static long average(List<Long> input) {
		int total = 0;
		for (long integer : input) {
			total += integer;
		}
		if (input.size() == 0) {
			return 0;
		}
		return total/input.size();
	}
}
