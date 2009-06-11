package sessionmon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener {
	
    public void sessionCreated(HttpSessionEvent sessionEvent) {
    	ServletContext sc = sessionEvent.getSession().getServletContext();
    	List activeSessions = (List)sc.getAttribute(Constants.CONTEXT_PARAMETER_ACTIVE_SESSIONS);
    	if(activeSessions == null) {
    		activeSessions = Collections.synchronizedList(new ArrayList());
    	}
    	activeSessions.add(sessionEvent.getSession().getId());
    	sc.setAttribute(Constants.CONTEXT_PARAMETER_ACTIVE_SESSIONS, activeSessions);
    }

    public void sessionDestroyed(HttpSessionEvent sessionEvent) {
    	ServletContext sc = sessionEvent.getSession().getServletContext();
    	List activeSessions = (List)sc.getAttribute(Constants.CONTEXT_PARAMETER_ACTIVE_SESSIONS);
    	if(activeSessions != null) {
    		activeSessions.remove(sessionEvent.getSession().getId());
    		sc.setAttribute(Constants.CONTEXT_PARAMETER_ACTIVE_SESSIONS, activeSessions);
    	}
    }
}