package sessionmon;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

import sessionmon.util.RandomString;

public class Test {
	
	public static void testReplication(HttpServletRequest request, String sessionDumpURI) throws Exception {
		Configuration config = (Configuration)request.getSession().getServletContext().getAttribute(SessionMonServlet.CONTEXT_PARAMETER_CONFIGURATION);
		HttpClient httpclient = new HttpClient();
		Iterator iterator = config.getServers().iterator();
		HashMap responses = new HashMap(); 
		while(iterator.hasNext()) {
			String server = (String)iterator.next();
			GetMethod httpget = new GetMethod(server + sessionDumpURI);
			httpget.addRequestHeader("Cookie", request.getHeader("Cookie"));
			httpclient.executeMethod(httpget);
			String response = httpget.getResponseBodyAsString();
			responses.put(server, response);
			System.out.println(response);
		}
	}
	
	public static void invalidateSession(HttpServletRequest request) {
		request.getSession().invalidate();
	}
	
	public static void addStringAttributes(int numOfAttributes, HttpSession session) {
		for(int i=0; i<numOfAttributes; i++) {
			String name = "sessionAttribute_" + i;
			Random random = new Random();
			int randomLength = random.nextInt(100);
			String value = RandomString.randomAlphanumeric(randomLength);
			
			session.setAttribute(name, value);
		}
	}
	
	public static void removeAttributes(HttpSession session) {
		Enumeration e = session.getAttributeNames();
		while(e.hasMoreElements()) {
			session.removeAttribute((String)e.nextElement());
		}
	}
}