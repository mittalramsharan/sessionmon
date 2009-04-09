package sessionmon;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sessionmon.report.Report;
import sessionmon.report.ReportFactory;
import sessionmon.test.Test;
import sessionmon.util.File;

public class PageRequestProcessor {
	private static final String CSS_COMMON = "web/css/common.css";
	private static final String FRAGMENT_HEADER = "web/fragment/header.html";
	private static final String FRAGMENT_FOOTER = "web/fragment/footer.html";
	
	private static final String PAGE_DUMP_SESSION = "web/dump-session.html";
	private static final String PAGE_TEST_REPLICATION = "web/test-replication.html";
	private static final String PAGE_INVALIDATE_SESSION = "web/invalidate-session.html";
	private static final String PAGE_MONITOR = "web/monitor.html";
	
	public static String process(HttpServletRequest request, HttpServletResponse response) 
	throws IOException, ServletException {
		StringBuffer content = new StringBuffer(File.readFileAsString(FRAGMENT_HEADER));
		String page = request.getParameter("smpage");
		
		if(page == null) {
			page = PAGE_DUMP_SESSION;
		}
		content.append(File.readFileAsString(page));
		content.append(File.readFileAsString(FRAGMENT_FOOTER));
		return evaluateContent(page, content.toString(), request);
	}
	
	private static String evaluateContent(String page, String c, HttpServletRequest request) {
		c = c.replaceAll("[$]{1}requesturi", getRequestURI(request, true));
		
		String queryString = getRequestQueryString(request);
		if(queryString != null && queryString.trim().length() != 0) {
			c = c.replaceAll("[$]{1}requestquerystring", queryString);
			c = c.replaceAll("[$]{1}appendrequestquerystring", "&" + queryString);
		} else {
			c = c.replaceAll("[$]{1}requestquerystring", "");
			c = c.replaceAll("[$]{1}appendrequestquerystring", "");
		}
		
		if(page.equals(PAGE_DUMP_SESSION)) {
			c = c.replaceFirst("Dump Session", "&gt;&gt;Dump Session");
			c = c.replaceAll("[$]{1}title", "Dump Session");
			c = c.replaceAll("[$]{1}contextpath", request.getContextPath());
			c = c.replaceAll("[$]{1}server", request.getServerName() + ":" + request.getServerPort());
			c = c.replaceAll("[$]{1}sessionid", request.getSession().getId());
			c = c.replaceAll("[$]{1}timestamp", getTimestamp());
			
			c = c.replaceFirst("[$]{1}maincontent", getDumpSessionHTMLContent(request));	
			c = c.replaceFirst("[$]{1}csscontent", File.readFileAsString(CSS_COMMON));
		} else if(page.equals(PAGE_TEST_REPLICATION)) {
			c = c.replaceFirst("Test Replication", "&gt;&gt;Test Replication");
			c = c.replaceAll("[$]{1}title", "Test Replication");
			c = c.replaceAll("[$]{1}contextpath", request.getContextPath());
			c = c.replaceAll("[$]{1}server", request.getServerName() + ":" + request.getServerPort());
			c = c.replaceAll("[$]{1}sessionid", request.getSession().getId());
			c = c.replaceAll("[$]{1}timestamp", getTimestamp());
			c = c.replaceAll("[$]{1}nodes", getConfiguredNodes(request));
			
			c = c.replaceFirst("[$]{1}maincontent", getTestReplicationHTMLContent(request));
			c = c.replaceFirst("[$]{1}csscontent", File.readFileAsString(CSS_COMMON));
		} else if(page.equals(PAGE_INVALIDATE_SESSION)) {
			c = c.replaceFirst("Invalidate Session", "&gt;&gt;Invalidate Session");
			c = c.replaceAll("[$]{1}title", "Invalidate Session");
			c = c.replaceAll("[$]{1}contextpath", request.getContextPath());
			c = c.replaceAll("[$]{1}server", request.getServerName() + ":" + request.getServerPort());
			c = c.replaceAll("[$]{1}sessionid", request.getSession().getId());
			c = c.replaceAll("[$]{1}timestamp", getTimestamp());
			
			c = c.replaceFirst("[$]{1}maincontent", getInvalidateSessionHTMLContent(request));
			c = c.replaceFirst("[$]{1}csscontent", File.readFileAsString(CSS_COMMON));
		} else if(page.equals(PAGE_MONITOR)) {
			c = c.replaceFirst("Setup Monitor", "&gt;&gt;Setup Monitor");
			c = c.replaceAll("[$]{1}title", "Setup Monitor");
			c = c.replaceFirst("[$]{1}maincontent", "<br/><strong><p>Coming soon in version 2.0!</p></strong>");
			c = c.replaceFirst("[$]{1}csscontent", File.readFileAsString(CSS_COMMON));
		}
		return c;
	}
	
	public static String getRequestQueryString(HttpServletRequest request) {
		String queryString = request.getQueryString();
		if(queryString != null && queryString.trim().length() != 0) {
			queryString = queryString.replaceAll("smpage[=]{1}(.+?).html[&]?", "");
		}
		return queryString;
	}
	
	public static String getConfiguredNodes(HttpServletRequest request) {
		Configuration config = (Configuration)request.getSession().getServletContext().getAttribute(SessionMonServlet.CONTEXT_PARAMETER_CONFIGURATION);
		String list = config.getCsvListOfServers();
		if(list != null)
			list = list.replaceAll("[,]", ", ");
		return list;
	}
	
	public static String getRequestURI(HttpServletRequest request, boolean onlyPath) {
		String uri = null;
		Configuration config = (Configuration)request.getSession().getServletContext().getAttribute(SessionMonServlet.CONTEXT_PARAMETER_CONFIGURATION);
		if(config != null && config.getOverridePath() != null) {
			uri = config.getOverridePath();
		} else {
			uri = request.getRequestURI();
			if(uri.indexOf(".") != -1) {
				int index = uri.lastIndexOf("/");
				uri = uri.substring(0, index);
			}
		}
		if(uri.charAt(uri.length() - 1) == '/')
			uri = uri.substring(0, uri.length() - 1);
		return uri;
	}
	
	private static String getInvalidateSessionHTMLContent(HttpServletRequest request) {
		String html = "<br/><strong><p>Your session has been invalidated successfully!</p></strong>";
		try {
			request.getSession().invalidate();
		} catch(Exception e) {
			html = "<br/><strong><p><font color=\"red\">Error occurred invalidating the session.</font></p></strong>";
		}
		
		return html;
	}
	
	private static String getTestReplicationHTMLContent(HttpServletRequest request) {
		String html = null;
		try {
			Test test = new Test(request);
			Report report = ReportFactory.create(CommandEnum.TEST_REPLICATION, ReportFactory.REPORT_TYPE_HTML);
			html = report.generate(test);
		} catch(Exception e) {
			html = "<html><title>SessionMon - Test Replication</title><body>Sorry, internal error has occurred.</body></html>";
		}
		return html;
	}
	
	private static String getDumpSessionHTMLContent(HttpServletRequest request) {
		String html = null;
		try {
			SessionInfo sessionInfo = new SessionInfo(request);
			Report report = ReportFactory.create(CommandEnum.DUMP, ReportFactory.REPORT_TYPE_HTML);
			html = report.generate(sessionInfo);
		} catch(Exception e) {
			html = "<html><title>SessionMon - Dump Session</title><body>Sorry, internal error has occured.</body></html>";
		}
		return html;
	}
	
	public static String getTimestamp() {
		Date now = new Date();
		return now.toString();
	}
}