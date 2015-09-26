package com.qwertyness.sexymotdengine.response;

import java.util.ArrayList;
import java.util.List;

import com.qwertyness.sexymotdengine.ActivePlugin;
import com.qwertyness.sexymotdengine.variable.CustomVariable;
import com.qwertyness.sexymotdengine.variable.Variable;

public class Info {
	public boolean animations;
    public List<AnimatedText> MOTDS;
    public boolean ENABLE_MAX_PLAYERS;
    public int MAX_PLAYERS;
    public String STRING_MAX_PLAYERS = null;
    public boolean ENABLE_PLAYER_COUNT;
    public List<Integer> PLAYER_COUNT;
    public List<String> STRING_PLAYER_COUNT = null;
    public String DEFAULT_PLAYER_NAME;
    public boolean ENABLE_AVATAR_ICON;
    public boolean ENABLE_OVERLAY_IMAGE;
    public String IMAGE_PATH;
    public boolean GIF_OPTIMIZED;
    public boolean ENABLE_PLAYER_MESSAGE;
    public PlayerMessage PLAYER_MESSAGE;
    public boolean ENABLE_FAKE_VERSION;
    public AnimatedText FAKE_VERSION;
    public boolean performanceLogging;
    public boolean pingLogging;
    public int frameCount;
    public int frameRate;
    public static List<Variable> variables = new ArrayList<Variable>();
    public List<CustomVariable> customVariables = new ArrayList<CustomVariable>();
    
    private static Info info;
    private static Maintenance maintenance;
    public String fileName;
    
    public static void init() {
    	maintenance = new Maintenance();
    	ActivePlugin.activePlugin.loadConfig(maintenance);
    	info = new Info();
    	ActivePlugin.activePlugin.loadConfig(info);
    }
    
    private Info() {
    	this("config.yml");
    }
    
    public Info(String fileName) {
    	this.fileName = fileName;
    }
    
    public static Info getInfo() {
    	return info;
    }
    public static Maintenance getMaintenance() {
    	return maintenance;
    }
    public static Info getActiveInfo() {
    	return getMaintenance().ENABLED ? getMaintenance() : getInfo();
    }
}
