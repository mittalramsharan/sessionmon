package sessionmon.report;

import org.json.JSONObject;

import sessionmon.SessionInfo;

public class JSONReport extends Report {
	
	public String generate(SessionInfo info) {
		JSONObject jo = new JSONObject(info);
		try {
			return jo.toString(4);
		} catch(Exception e) {
			return null;
		}
	}

	public String getMIMEType() {
		return "text";
		//return "application/json";
	}
}
