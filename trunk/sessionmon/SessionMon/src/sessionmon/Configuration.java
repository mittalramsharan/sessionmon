package sessionmon;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.json.JSONObject;

public class Configuration {
	private static final Logger LOGGER = Logger.getLogger(Configuration.class);
	
	private Vector servers = new Vector();
	
	public Configuration(HttpServletRequest request) {
		StringBuffer sb = new StringBuffer(request.getServerName());
		sb.append(":");
		sb.append(request.getServerPort());
		servers.add(sb.toString());
	}
	
	public void addServer(String serverAddress) {
		if(servers.contains(serverAddress)) {
			servers.add(serverAddress);
		}
	}
	
	public void removeServer(String serverAddress) {
		servers.remove(serverAddress);
	}
	
	public String toJSON() {
		JSONObject json = new JSONObject();
		try {
			json.append("servers", servers);
		} catch(Exception e) {
			LOGGER.error("toJSON: error generating JSON", e);
		}
		return json.toString();
	}
}
