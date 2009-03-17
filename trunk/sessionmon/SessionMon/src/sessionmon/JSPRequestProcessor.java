package sessionmon;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JSPRequestProcessor {
	public static final String PAGE_TITLE = "PAGE_TITLE";
	
	private static final String PAGE_CONFIGURE = "configure";
	private static final String PAGE_TEST = "test";
	private static final String PAGE_DUMP = "dump";
	
	public static String process(HttpServletRequest request, HttpServletResponse response) 
	throws IOException, ServletException {
		String path = request.getRequestURI();
		
		if(path.equals(PAGE_CONFIGURE)) {
			prepareCommon(request, PAGE_CONFIGURE);
		} else if(path.equals(PAGE_TEST)) {
			prepareCommon(request, PAGE_CONFIGURE);
		} else if(path.equals(PAGE_DUMP)) {
			prepareCommon(request, PAGE_CONFIGURE);
		} else {
			return getWebContent(request.getParameter("file"));
		}
		return null;
	}
	
	private static void prepareCommon(HttpServletRequest request, String page) {
		request.setAttribute(PAGE_TITLE, "");
	}
	
	private static String getWebContent(String cName) {
	    InputStream is = null;
	    BufferedReader br = null;
	    String line;
	    StringBuffer content = new StringBuffer();

	    try { 
	      is = JSPRequestProcessor.class.getResourceAsStream(cName);
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
	
	public static void main(String[] args) {
		System.out.println(getWebContent("web/prototype-1.6.0.3.js"));
	}
}
