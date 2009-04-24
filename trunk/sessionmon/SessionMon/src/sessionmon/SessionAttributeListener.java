package sessionmon;

import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

public class SessionAttributeListener implements HttpSessionAttributeListener {
	public void attributeAdded(HttpSessionBindingEvent se) {
		update(se);
	}

	public void attributeRemoved(HttpSessionBindingEvent se) {
		update(se);
	}

	public void attributeReplaced(HttpSessionBindingEvent se) {
		update(se);
	}
	
	private void update(HttpSessionBindingEvent se) {
		ServletContext sc = se.getSession().getServletContext();
		Date now = new Date();
		sc.setAttribute(Constants.CONTEXT_PARAMETER_LAST_ATTRIBUTE_UPDATE_TIME, now);
	}
}
