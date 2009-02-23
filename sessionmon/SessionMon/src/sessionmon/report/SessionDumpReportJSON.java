package sessionmon.report;

import org.json.JSONObject;

public class SessionDumpReportJSON extends Report {
	
	public String generate(Object info) {
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
