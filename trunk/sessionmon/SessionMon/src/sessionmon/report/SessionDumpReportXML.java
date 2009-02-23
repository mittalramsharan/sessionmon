package sessionmon.report;

import org.json.JSONObject;
import org.json.XML;

public class SessionDumpReportXML extends Report {
	
	public String generate(Object info) {
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
