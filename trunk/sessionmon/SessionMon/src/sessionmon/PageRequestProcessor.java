package sessionmon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.DateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;

import sessionmon.report.Report;
import sessionmon.report.ReportFactory;

public class PageRequestProcessor {
	private static final String PAGE_INDEX = "web/index.html";
	private static final String PAGE_TEST = "web/test.html";
	private static final String PAGE_MONITOR = "web/monitor.html";
	
	public static String process(HttpServletRequest request, HttpServletResponse response) 
	throws IOException, ServletException {
		String content = null;
		String contextPath = request.getContextPath();
		String path = request.getRequestURI();
		
		String resourcePath = path.replaceAll(contextPath, "web");
		if(path.indexOf("/css/") != -1 || path.indexOf("/js/") != -1) {
			content = getContentAsString(resourcePath);
		} else {
			if(path.indexOf("test.html") != -1) {
				content = getContentAsString(PAGE_TEST);
			} else if(path.indexOf("monitor.html") != -1) {
				content = getContentAsString(PAGE_MONITOR);
			} else {
				content = getContentAsString(PAGE_INDEX);
				content = evaluateContent(PAGE_INDEX, content, request);
			}
		}

		return content;
	}
	
	private static String evaluateContent(String page, String c, HttpServletRequest request) {
		
		if(page.equals(PAGE_INDEX)) {
			c = c.replaceAll("[$]{1}title", "Dump");
			c = c.replaceAll("[$]{1}contextpath", request.getContextPath());
			c = c.replaceAll("[$]{1}server", request.getServerName() + ":" + request.getServerPort());
			c = c.replaceAll("[$]{1}sessionid", request.getSession().getId());
			c = c.replaceAll("[$]{1}timestamp", getTimestamp());
			c = c.replaceAll("[$]{1}content", getIndexHTMLContent(request));
		} else if(page.equals(PAGE_TEST)) {
			c = c.replaceAll("[$]{1}title", "Test");
			
		} else if(page.equals(PAGE_MONITOR)) {
			
		}
		return c;
	}
	
	private static String getIndexHTMLContent(HttpServletRequest request) {
		String html = null;
		try {
			SessionInfo sessionInfo = new SessionInfo(request);
			Report report = ReportFactory.create(CommandEnum.DUMP, ReportFactory.REPORT_TYPE_XML);
			StringReader reader = new StringReader(report.generate(sessionInfo));
			StringWriter writer = new StringWriter();
			
			//xml to html transformation
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer = tFactory.newTransformer(new javax.xml.transform.stream.StreamSource(getContentAsInputStream("web/index.xsl")));
			transformer.transform(new javax.xml.transform.stream.StreamSource(reader), 
					new javax.xml.transform.stream.StreamResult(writer));

			html = writer.toString();
		} catch(Exception e) {
			html = "Sorry, internal error has occured.";
		}
		return html;
	}
	
	private static String getTimestamp() {
		Date now = new Date();
		return now.toString();
	}
	
	private static InputStream getContentAsInputStream(String cName) {
		InputStream is = null;
		try { 
		      is = PageRequestProcessor.class.getResourceAsStream(cName);
		} catch (Exception e) {
		      e.printStackTrace();
		}
		return is;
	}
	
	private static String getContentAsString(String cName) {
		InputStream is = null;
		BufferedReader br = null;
		String line;
		StringBuffer content = new StringBuffer();

		try { 
			is = PageRequestProcessor.class.getResourceAsStream(cName);
			br = new BufferedReader(new InputStreamReader(is));
			while (null != (line = br.readLine())) {
				content.append(line);
				content.append("\n");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (br != null) br.close();
				if (is != null) is.close();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		return content.toString();
	}
}