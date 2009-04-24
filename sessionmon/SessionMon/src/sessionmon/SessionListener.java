package sessionmon;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener {
	
    public void sessionCreated(HttpSessionEvent sessionEvent) {
    	ServletContext sc = sessionEvent.getSession().getServletContext();
    	Integer numOfActiveSessions = (Integer)sc.getAttribute(Constants.CONTEXT_PARAMETER_TOTAL_NUMBER_OF_ACTIVE_SESSIONS);
    	if(numOfActiveSessions == null) {
    		sc.setAttribute(Constants.CONTEXT_PARAMETER_TOTAL_NUMBER_OF_ACTIVE_SESSIONS, new Integer(1));
    	} else {
    		int temp = numOfActiveSessions.intValue();
    		temp++;
    		sc.setAttribute(Constants.CONTEXT_PARAMETER_TOTAL_NUMBER_OF_ACTIVE_SESSIONS, new Integer(temp));
    	}	
    }

    public void sessionDestroyed(HttpSessionEvent sessionEvent) {
    	ServletContext sc = sessionEvent.getSession().getServletContext();
    	Integer numOfActiveSessions = (Integer)sc.getAttribute(Constants.CONTEXT_PARAMETER_TOTAL_NUMBER_OF_ACTIVE_SESSIONS);
    	if(numOfActiveSessions != null) {
    		int temp = numOfActiveSessions.intValue();
    		temp--;
    		sc.setAttribute(Constants.CONTEXT_PARAMETER_TOTAL_NUMBER_OF_ACTIVE_SESSIONS, new Integer(temp));
    	}
    }
}
