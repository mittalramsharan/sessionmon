package sessionmon.report;

import org.json.JSONObject;
import org.json.XML;

import sessionmon.SessionInfo;

public class XMLReport extends Report {
	
	public String generate(SessionInfo info) {
		JSONObject jo = new JSONObject(info);
		try {
			return XML.toString(jo, "session");
		} catch(Exception e) {
			return null;
		}
	}

	public String getMIMEType() {
		return "text/xml";
	}
}
