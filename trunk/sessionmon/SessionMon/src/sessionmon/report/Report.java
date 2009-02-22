package sessionmon.report;

import java.text.SimpleDateFormat;
import java.util.Date;

import sessionmon.SessionInfo;

/**
 * Defines common interface to different report types. These report types may represent XML and HTML.
 * @author ntatsumi
 *
 */
public abstract class Report {
	protected static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss:S");
	
	public abstract String generate(SessionInfo info);
	
	public abstract String getMIMEType();
	
	protected String format(Date date) {
		return DATE_FORMAT.format(date);
	}
}
