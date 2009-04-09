package sessionmon.test;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import sessionmon.Configuration;
import sessionmon.SessionAttribute;
import sessionmon.SessionInfo;
import sessionmon.SessionMonServlet;
import sessionmon.util.RandomString;

public class Test {
	private static final Logger LOGGER = Logger.getLogger(Test.class);
	
	private HttpServletRequest request = null;
	
	public Test(HttpServletRequest request) {
		this.request = request;
		addStringAttributes(3);
	}
	
	public ArrayList testReplication(String sessionDumpURI) throws Exception {
		Configuration config = (Configuration)request.getSession().getServletContext().getAttribute(SessionMonServlet.CONTEXT_PARAMETER_CONFIGURATION);
		int numOfNodes = config.getServers().size();
		if(numOfNodes < 2)
			throw new OnlyOneNodeException();
		
		ArrayList sessionFromAllNodes = new ArrayList(numOfNodes);
		ArrayList attributesFromAllNodes = new ArrayList();
		HttpClient httpclient = new HttpClient();
		Iterator iterator = config.getServers().iterator();
		while(iterator.hasNext()) {
			String server = (String)iterator.next();
			String url = server + sessionDumpURI + "?command=dump&type=json";		
			GetMethod httpget = new GetMethod(url);
			httpget.addRequestHeader("Cookie", request.getHeader("Cookie"));
			
			try {
				int statusCode = httpclient.executeMethod(httpget);
			    if(statusCode != HttpStatus.SC_OK) {
			        LOGGER.error("Method failed: " + httpget.getStatusLine());
			    }

				String output = httpget.getResponseBodyAsString();
				JSONObject json = new JSONObject(output);
				SessionInfo sessionInfo = new SessionInfo(json);
				sessionFromAllNodes.add(sessionInfo);
				attributesFromAllNodes.addAll(sessionInfo.getAttributes());
			} catch(Exception e) {
				sessionFromAllNodes.add(new SessionInfo());
				LOGGER.error("Could not get session info from " + server, e);
			} finally {
				httpget.releaseConnection();
			}
		}
		
		//analyze - remove properly replicated attributes
		ArrayList replicatoinFailedAtt = new ArrayList();
		for(int i=0; i<attributesFromAllNodes.size(); i++) {
			SessionAttribute sa = (SessionAttribute)attributesFromAllNodes.get(i);
			boolean foundMatch = false;
			for(int j=0; j<attributesFromAllNodes.size(); j++) {
				if(j != i) {
					SessionAttribute sa2 = (SessionAttribute)attributesFromAllNodes.get(j);
					if(sa2.getName().equals(sa.getName())) {
						if(sa2.getObjectType().equals(sa.getObjectType()) &&
								sa2.getObjectGraphSizeInBytes() == sa.getObjectGraphSizeInBytes() &&
								sa2.getObjectSerializedSizeInBytes() == sa.getObjectSerializedSizeInBytes()) {
							foundMatch = true;
						}
						break;
					}
				}
			}
			if(!foundMatch)
				replicatoinFailedAtt.add(sa);
		}
		replicatoinFailedAtt.trimToSize();
		((SessionInfo)sessionFromAllNodes.get(0)).setReplicationFailedAttributes(replicatoinFailedAtt);
		
		return sessionFromAllNodes;
	}
	
	public boolean invalidateSession() {
		try {
			request.getSession().invalidate();
			return true;
		} catch(Exception e) {
			return false;
		}
	}
	
	public void addStringAttributes(int numOfAttributes) {
		for(int i=0; i<numOfAttributes; i++) {
			String name = "sessionAttribute_" + i;
			Random random = new Random();
			int randomLength = random.nextInt(100);
			String value = RandomString.randomAlphanumeric(randomLength);
			
			request.getSession().setAttribute(name, value);
		}
	}
	
	public void removeAttributes() {
		HttpSession session = request.getSession();
		Enumeration e = session.getAttributeNames();
		while(e.hasMoreElements()) {
			session.removeAttribute((String)e.nextElement());
		}
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
}