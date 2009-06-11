package sessionmon;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener {
	
    public void sessionCreated(HttpSessionEvent sessionEvent) {
    	ServletContext sc = sessionEvent.getSession().getServletContext();
    	Set activeSessions = (Set)sc.getAttribute(Constants.CONTEXT_PARAMETER_ACTIVE_SESSIONS);
    	if(activeSessions == null) {
    		activeSessions = Collections.synchronizedSet(new HashSet());
    	}
    	activeSessions.add(sessionEvent.getSession().getId());
    	sc.setAttribute(Constants.CONTEXT_PARAMETER_ACTIVE_SESSIONS, activeSessions);
    }

    public void sessionDestroyed(HttpSessionEvent sessionEvent) {
    	ServletContext sc = sessionEvent.getSession().getServletContext();
    	Set activeSessions = (Set)sc.getAttribute(Constants.CONTEXT_PARAMETER_ACTIVE_SESSIONS);
    	if(activeSessions != null) {
    		activeSessions.remove(sessionEvent.getSession().getId());
    		sc.setAttribute(Constants.CONTEXT_PARAMETER_ACTIVE_SESSIONS, activeSessions);
    	}
    }
}