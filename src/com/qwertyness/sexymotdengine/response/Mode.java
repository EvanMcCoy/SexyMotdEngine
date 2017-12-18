package com.qwertyness.sexymotdengine.response;

import java.util.ArrayList;
import java.util.List;

import com.qwertyness.sexymotdengine.variable.CustomVariable;
import com.qwertyness.sexymotdengine.variable.Variable;

public class Mode {
	public String mode;
    public String KICK_MESSAGE;
    
	public boolean ENABLED;
	public List<VariableText> MOTDS;
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
    public boolean ENABLE_PLAYER_MESSAGE;
    public PlayerMessage PLAYER_MESSAGE;
    public boolean ENABLE_FAKE_VERSION;
    public VariableText FAKE_VERSION;
    public boolean performanceLogging;
    public boolean pingLogging;
    public static List<Variable> variables = new ArrayList<Variable>();
    public List<CustomVariable> customVariables = new ArrayList<CustomVariable>();
    
    public Mode(String mode) {
    	this.mode = mode;
    }
    
    public String getMode() {
    	return this.mode;
    }
}
