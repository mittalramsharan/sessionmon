package sessionmon;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionInfo {
	
	private String serverName = null;
	private int serverPort = 0;
	private String applicationURL = null;
	
	private String id = null;
	private Collection attributes = null;
	private int totalByteSize = 0;
	private Date creationTime = null;
	private Date lastAccessedTime = null;
	private int maxInactiveIntervalInSeconds = 0;
	private boolean isNew = false;
	
	public SessionInfo() {}
	
	public SessionInfo(HttpServletRequest request) throws IOException {
		HttpSession session = request.getSession();
		Enumeration sessionAttributeNames = session.getAttributeNames();
		ByteArrayOutputStream bs = new ByteArrayOutputStream();
		ObjectOutputStream os = new ObjectOutputStream(bs);

		attributes = new ArrayList();
		while(sessionAttributeNames.hasMoreElements()) {
			String attrName = (String)sessionAttributeNames.nextElement();
			Object obj = session.getAttribute(attrName);
			if(obj != null) {
				//for determining total byte size
				os.writeObject(obj);
				//store as SessionAttribute
				SessionAttribute attr = new SessionAttribute();
				attr.setName(attrName);
				attr.setObjectType(obj.getClass().getName());
				attr.setObject(obj);
				attributes.add(attr);
			}
		}
		
		setCreationTime(session.getCreationTime());
		setId(session.getId());
		setLastAccessedTime(session.getLastAccessedTime());
		setMaxInactiveIntervalInSeconds(session.getMaxInactiveInterval());
		setTotalByteSize(bs.size());
		setNew(session.isNew());
		
		setServerName(request.getServerName());
		setServerPort(request.getServerPort());
		setApplicationURL(request.getContextPath());
	}
	
	public int getTotalByteSize() {
		return totalByteSize;
	}
	public void setTotalByteSize(int totalByteSize) {
		this.totalByteSize = totalByteSize;
	}
	public Date getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(long creationTime) {
		this.creationTime = new Date(creationTime);
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getLastAccessedTime() {
		return lastAccessedTime;
	}
	public void setLastAccessedTime(long lastAccessedTime) {
		this.lastAccessedTime = new Date(lastAccessedTime);
	}
	public int getMaxInactiveIntervalInSeconds() {
		return maxInactiveIntervalInSeconds;
	}
	public void setMaxInactiveIntervalInSeconds(int maxInactiveIntervalInSeconds) {
		this.maxInactiveIntervalInSeconds = maxInactiveIntervalInSeconds;
	}
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	public int getServerPort() {
		return serverPort;
	}
	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	public String getApplicationURL() {
		return applicationURL;
	}

	public void setApplicationURL(String applicationURL) {
		this.applicationURL = applicationURL;
	}

	public Collection getAttributes() {
		return attributes;
	}

	public void setAttributes(Collection attributes) {
		this.attributes = attributes;
	}

	public boolean isNew() {
		return isNew;
	}

	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}

}
