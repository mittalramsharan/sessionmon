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
import sessionmon.PageRequestProcessor;
import sessionmon.SessionInfo;
import sessionmon.SessionMonServlet;
import sessionmon.test.OnlyOneNodeException;
import sessionmon.test.Test;
import sessionmon.util.File;

public class TestReplicationReportHTML extends Report {
	private static final Logger LOGGER = Logger.getLogger(TestReplicationReportHTML.class);
	
	public String generate(Object info) {
		Test test = (Test)info;
		HttpServletRequest request = test.getRequest();
		
		StringBuffer xml = null;
		try {
			Iterator sessionFromAllNodes = test.testReplication(PageRequestProcessor.getRequestURI(request, true)).iterator();
			
			xml = new StringBuffer("<replicationTest>");
			while(sessionFromAllNodes.hasNext()) {
				SessionInfo session = (SessionInfo)sessionFromAllNodes.next();
				JSONObject jo = new JSONObject(session);
				xml.append(XML.toString(jo, "session"));
			}
			xml.append("</replicationTest>");
		} catch(OnlyOneNodeException e) {
			xml = new StringBuffer("<error>Only one node was found in the web.xml configuration</error>");
			LOGGER.info("[sessionmon]Replication test did not run: only one node was found in the web.xml configuration");
		} catch(Exception e) {
			xml = new StringBuffer("<error>Exception Occurred:<br/>" + e.getMessage() + "</error>");
			LOGGER.error("[sessionmon]Replication test did not run: " + e.getMessage(), e);
		}
		
		try {
			//xml to html transformation
			StringReader reader = new StringReader(xml.toString());
			StringWriter writer = new StringWriter();
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer = tFactory.newTransformer(new javax.xml.transform.stream.StreamSource(File.readFileAsInputStream("report/testReplicationReportHTML.xsl")));
			transformer.transform(new javax.xml.transform.stream.StreamSource(reader), 
					new javax.xml.transform.stream.StreamResult(writer));

			return writer.toString();
		} catch(Exception e) {
			LOGGER.error("[sessionmon]Could not generate HTML: " + e.getMessage(), e);
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
		return "text/html";
	}
}
