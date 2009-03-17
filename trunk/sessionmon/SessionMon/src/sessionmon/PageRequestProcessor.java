package sessionmon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PageRequestProcessor {
	private static final String PAGE_TEST = "test.html";
	private static final String PAGE_DUMP = "dump.html";
	
	public static String process(HttpServletRequest request, HttpServletResponse response) 
	throws IOException, ServletException {
		String content = null;
		String contextPath = request.getContextPath();
		String path = request.getRequestURI();
		
		String resourcePath = path.replaceAll(contextPath, "web");
		if(path.indexOf("/js/") != -1) {
			content = getContent(resourcePath);
		} else {
			if(path.indexOf(PAGE_TEST) != -1) {
				
			} else {
				content = getContent("web/index.html");
				content = evaluateContent(content, request);
			}
		}

		return content;
	}
	
	private static String evaluateContent(String c, HttpServletRequest request) {
		c = c.replaceAll("[$]{1}contextPath", request.getContextPath());
		
		return c;
	}
	
	private static String getContent(String cName) {
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
