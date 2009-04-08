package sessionmon.report;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.json.XML;

import sessionmon.Configuration;
import sessionmon.SessionInfo;
import sessionmon.SessionMonServlet;
import sessionmon.test.Test;
import sessionmon.util.File;

public class TestReportHTML extends Report {
	private static final Logger LOGGER = Logger.getLogger(TestReportHTML.class);
	
	private static final String REQUEST_PARAMETER_TEST = "test";
	
	public String generate(Object info) {
		Test test = (Test)info;
		HttpServletRequest request = test.getRequest();
		if(request.getParameter(REQUEST_PARAMETER_TEST) == null) {
			return "test type not specified";
		}
		
		if(request.getParameter(REQUEST_PARAMETER_TEST).equals("invalidate")) {
			return Boolean.toString(test.invalidateSession());
		} else if(request.getParameter(REQUEST_PARAMETER_TEST).equals("replication")) {
			try {
				Iterator sessionFromAllNodes = test.testReplication(getRequestURI(request)).iterator();
				
				StringBuffer xml = new StringBuffer("<replicationTest>");
				while(sessionFromAllNodes.hasNext()) {
					SessionInfo session = (SessionInfo)sessionFromAllNodes.next();
					JSONObject jo = new JSONObject(session);
					xml.append(XML.toString(jo, "session"));
				}
				xml.append("</replicationTest>");
				
				//xml to html transformation
				StringReader reader = new StringReader(xml.toString());
				StringWriter writer = new StringWriter();
				TransformerFactory tFactory = TransformerFactory.newInstance();
				Transformer transformer = tFactory.newTransformer(new javax.xml.transform.stream.StreamSource(File.readFileAsInputStream("report/testReplicationReportHTML.xsl")));
				transformer.transform(new javax.xml.transform.stream.StreamSource(reader), 
						new javax.xml.transform.stream.StreamResult(writer));

				return writer.toString();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	private static String getRequestURI(HttpServletRequest request) {
		String uri = null;
		Configuration config = (Configuration)request.getSession().getServletContext().getAttribute(SessionMonServlet.CONTEXT_PARAMETER_CONFIGURATION);
		if(config != null && config.getOverridePath() != null) {
			uri = config.getOverridePath();
		} else {
			uri = request.getRequestURI();
		}
		
		LOGGER.debug("getRequestURI: " + uri);
		return uri;
	}
	
	public String getMIMEType() {
		return "text";
		//return "application/json";
	}
}
