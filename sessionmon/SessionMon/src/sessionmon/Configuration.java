package sessionmon;

import java.io.Serializable;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.json.JSONObject;

public class Configuration implements Serializable {
	private static final Logger LOGGER = Logger.getLogger(Configuration.class);
	
	private boolean enabled = false;
	private Vector servers = new Vector();
	private String csvListOfServers = null;
	private String overridePath = null;
	
	public Configuration(){}
	
	public Configuration(String csvListOfServers) {
		this.csvListOfServers = csvListOfServers;
		
		String[] serversArray = csvListOfServers.split("[,]");
		for(int i=0; i<serversArray.length; i++) {
			addServer(serversArray[i]);
		}
	}
	
	public void addServer(String serverAddress) {
		//if(!servers.contains(serverAddress)) {
			servers.add(serverAddress);
		//}
	}
	
	public void removeServer(String serverAddress) {
		servers.remove(serverAddress);
	}
	
	public String toJSON() {
		JSONObject json = new JSONObject();
		try {
			json.append("servers", servers);
		} catch(Exception e) {
			LOGGER.error("[sessionmon]toJSON: error generating JSON", e);
		}
		return json.toString();
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getOverridePath() {
		return overridePath;
	}

	public void setOverridePath(String overridePath) {
		this.overridePath = overridePath;
	}

	public String getCsvListOfServers() {
		return csvListOfServers;
	}

	public Vector getServers() {
		return servers;
	}
}
