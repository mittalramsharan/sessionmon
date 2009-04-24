package sessionmon;

import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import sessionmon.util.JavaObjectProfiler;

public class SessionInfo {
	
	private String serverName = null;
	private int serverPort = 0;
	private String applicationURL = null;
	
	private String id = null;
	private Collection attributes = null;
	private Collection replicationFailedAttributes = new ArrayList(0);
	private int totalObjectGraphSizeInBytes = 0;
	private int totalObjectSerializedSizeInBytes = 0;
	private Date creationTime = null;
	private Date lastAccessedTime = null;
	private int maxInactiveIntervalInSeconds = 0;
	private boolean isNew = false;
	private String errorMessage = null;
	private int totalNumberOfActiveSessions = 0;
	private Date lastAttributeUpdateTime = null;
	
	public SessionInfo(String serverName, String errorMessage){
		this.serverName = serverName;
		this.errorMessage = errorMessage;
	}
	
	public SessionInfo(JSONObject j) {
		try {
			serverName = j.getString("serverName");
			serverPort = j.getInt("serverPort");
			applicationURL = j.getString("applicationURL");
			id = j.getString("id");
			totalObjectGraphSizeInBytes = j.getInt("totalObjectGraphSizeInBytes");
			totalObjectSerializedSizeInBytes = j.getInt("totalObjectSerializedSizeInBytes");
			creationTime = Constants.DATE_FORMAT_DAY_OF_WEEK_TIME_YEAR.parse(j.getString("creationTime"));
			lastAccessedTime = Constants.DATE_FORMAT_DAY_OF_WEEK_TIME_YEAR.parse(j.getString("lastAccessedTime"));
			maxInactiveIntervalInSeconds = j.getInt("maxInactiveIntervalInSeconds");
			isNew = j.getBoolean("new");
			totalNumberOfActiveSessions = j.getInt("totalNumberOfActiveSessions");
			String temp = j.getString("lastAttributeUpdateTime");
			if(!j.getString("lastAttributeUpdateTime").equals("null"))
				lastAttributeUpdateTime = Constants.DATE_FORMAT_DAY_OF_WEEK_TIME_YEAR.parse(temp);
			
			attributes = new ArrayList();
			JSONArray array = j.getJSONArray("attributes");
			for(int i=0; i<array.length(); i++) {
				JSONObject jatt = (JSONObject)array.get(i);
				SessionAttribute att = new SessionAttribute();
				att.setServer(serverName + ":" + serverPort);
				att.setName(jatt.getString("name"));
				att.setObjectGraphSizeInBytes(jatt.getInt("objectGraphSizeInBytes"));
				att.setObjectSerializedSizeInBytes(jatt.getInt("objectSerializedSizeInBytes"));
				att.setObjectType(jatt.getString("objectType"));
				att.setToStringValue(jatt.getString("toStringValue"));
				attributes.add(att);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public SessionInfo(HttpServletRequest request) throws IOException {
		HttpSession session = request.getSession();
		Enumeration sessionAttributeNames = session.getAttributeNames();
		attributes = new ArrayList();
		
		while(sessionAttributeNames.hasMoreElements()) {
			String attrName = (String)sessionAttributeNames.nextElement();
			Object obj = session.getAttribute(attrName);
			if(obj != null) {
				JavaObjectProfiler p = new JavaObjectProfiler(obj);
				//store as SessionAttribute
				SessionAttribute attr = new SessionAttribute();
				attr.setServer(serverName + ":" + serverPort);
				attr.setName(attrName);
				attr.setObjectType(p.getObjectType());
				attr.setToStringValue(p.getToStringValue());
				attr.setObjectGraphSizeInBytes(p.getObjectGraphSizeInBytes());
				attr.setObjectSerializedSizeInBytes(p.getObjectSerializedSizeInBytes());
				
				totalObjectGraphSizeInBytes += p.getObjectGraphSizeInBytes();
				totalObjectSerializedSizeInBytes += p.getObjectSerializedSizeInBytes();
				attributes.add(attr);
			}
		}
		
		this.creationTime = new Date(session.getCreationTime());
		this.id = session.getId();
		this.lastAccessedTime = new Date(session.getLastAccessedTime());
		this.maxInactiveIntervalInSeconds = session.getMaxInactiveInterval();
		this.isNew = session.isNew();
		
		Integer totalNumOfActiveSessions = (Integer)session.getServletContext().getAttribute(Constants.CONTEXT_PARAMETER_TOTAL_NUMBER_OF_ACTIVE_SESSIONS);
		if(totalNumOfActiveSessions != null)
			this.totalNumberOfActiveSessions = totalNumOfActiveSessions.intValue();
		this.lastAttributeUpdateTime = (Date)session.getServletContext().getAttribute(Constants.CONTEXT_PARAMETER_LAST_ATTRIBUTE_UPDATE_TIME);
		
		this.serverName = request.getServerName();
		this.serverPort = request.getServerPort();
		this.applicationURL = request.getContextPath();
	}
	public int getTotalNumberOfAttributes() {
		return attributes.size();
	}
	public Date getCreationTime() {
		return creationTime;
	}
	public String getId() {
		return id;
	}
	public Date getLastAccessedTime() {
		return lastAccessedTime;
	}
	public int getMaxInactiveIntervalInSeconds() {
		return maxInactiveIntervalInSeconds;
	}
	public String getServerName() {
		return serverName;
	}
	public int getServerPort() {
		return serverPort;
	}
	public String getApplicationURL() {
		return applicationURL;
	}
	public Collection getAttributes() {
		return attributes;
	}
	public boolean isNew() {
		return isNew;
	}

	public int getTotalObjectGraphSizeInBytes() {
		return totalObjectGraphSizeInBytes;
	}

	public int getTotalObjectSerializedSizeInBytes() {
		return totalObjectSerializedSizeInBytes;
	}

	public Collection getReplicationFailedAttributes() {
		return replicationFailedAttributes;
	}

	public void setReplicationFailedAttributes(
			Collection replicationFailedAttributes) {
		this.replicationFailedAttributes = replicationFailedAttributes;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public int getTotalNumberOfActiveSessions() {
		return totalNumberOfActiveSessions;
	}

	public Date getLastAttributeUpdateTime() {
		return lastAttributeUpdateTime;
	}
}