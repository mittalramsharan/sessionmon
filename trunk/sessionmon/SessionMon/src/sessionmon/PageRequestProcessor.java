package sessionmon;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sessionmon.report.Report;
import sessionmon.report.ReportFactory;
import sessionmon.util.File;

public class PageRequestProcessor {
	private static final String CSS_COMMON = "web/css/common.css";
	private static final String PAGE_INDEX = "web/index.html";
	private static final String PAGE_TEST = "web/test.html";
	private static final String PAGE_MONITOR = "web/monitor.html";
	
	public static String process(HttpServletRequest request, HttpServletResponse response) 
	throws IOException, ServletException {
		String content = null;
		String path = request.getRequestURI();
		
		if(path.indexOf("test.html") != -1) {
			content = File.readFileAsString(PAGE_TEST);
		} else if(path.indexOf("monitor.html") != -1) {
			content = File.readFileAsString(PAGE_MONITOR);
		} else {
			content = File.readFileAsString(PAGE_INDEX);
			content = evaluateContent(PAGE_INDEX, content, request);
		}

		return content;
	}
	
	private static String evaluateContent(String page, String c, HttpServletRequest request) {
		
		if(page.equals(PAGE_INDEX)) {
			c = c.replaceAll("[$]{1}requesturi", getRequestURI(request));
			c = c.replaceAll("[$]{1}requestquerystring", request.getQueryString());
			c = c.replaceAll("[$]{1}title", "Dump");
			c = c.replaceAll("[$]{1}contextpath", request.getContextPath());
			c = c.replaceAll("[$]{1}server", request.getServerName() + ":" + request.getServerPort());
			c = c.replaceAll("[$]{1}sessionid", request.getSession().getId());
			c = c.replaceAll("[$]{1}timestamp", getTimestamp());
			
			c = c.replaceFirst("[$]{1}maincontent", getIndexHTMLContent(request));			
			c = c.replaceFirst("[$]{1}csscontent", File.readFileAsString(CSS_COMMON));
		} else if(page.equals(PAGE_TEST)) {
			if(request.getParameter("msdo").equals("invalidate")) {
				Test.invalidateSession(request);
			} else if(request.getParameter("msdo").equals("replication")) {
				try {
					Test.testReplication(request, getRequestURI(request) + "?" + request.getQueryString() + "&command=dump&type=json");
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
			c = c.replaceAll("[$]{1}requesturi", getRequestURI(request));
			c = c.replaceAll("[$]{1}requestquerystring", request.getQueryString());
			c = c.replaceAll("[$]{1}title", "Test");
			c = c.replaceAll("[$]{1}contextpath", request.getContextPath());
			c = c.replaceAll("[$]{1}server", request.getServerName() + ":" + request.getServerPort());
			c = c.replaceAll("[$]{1}sessionid", request.getSession().getId());
			c = c.replaceAll("[$]{1}nodes", getListOfNodes(request));
			
			c = c.replaceFirst("[$]{1}csscontent", File.readFileAsString(CSS_COMMON));
		} else if(page.equals(PAGE_MONITOR)) {
			
		}
		return c;
	}
	
	private static String getListOfNodes(HttpServletRequest request) {
		Configuration config = (Configuration)request.getSession().getServletContext().getAttribute(SessionMonServlet.CONTEXT_PARAMETER_CONFIGURATION);
		if(config.getCsvListOfServers() == null || config.getCsvListOfServers().trim().length() == 0) {
			return "n/a";
		} else {
			return config.getCsvListOfServers();
		}
	}
	
	private static String getRequestURI(HttpServletRequest request) {
		Configuration config = (Configuration)request.getSession().getServletContext().getAttribute(SessionMonServlet.CONTEXT_PARAMETER_CONFIGURATION);
		if(config != null && config.getOverridePath() != null) {
			return config.getOverridePath();
		} else {
			return request.getRequestURI();
		}
	}
	
	private static String getIndexHTMLContent(HttpServletRequest request) {
		String html = null;
		try {
			SessionInfo sessionInfo = new SessionInfo(request);
			Report report = ReportFactory.create(CommandEnum.DUMP, ReportFactory.REPORT_TYPE_HTML);
			html = report.generate(sessionInfo);
		} catch(Exception e) {
			html = "Sorry, internal error has occured.";
		}
		return html;
	}
	
	private static String getTimestamp() {
		Date now = new Date();
		return now.toString();
	}
}