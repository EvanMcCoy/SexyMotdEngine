package com.qwertyness.sexymotdengine.response;

import java.util.List;
import java.util.UUID;

public class Maintenance extends Info {
	public boolean ENABLED;
    public List<UUID> maintainers;
    public String KICK_MESSAGE;
    
    public Maintenance() {
    	super("maintenance.yml");
    }
}
