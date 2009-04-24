package sessionmon.report;

import java.util.Date;

import sessionmon.Constants;

/**
 * Defines common interface to different report types. These report types may represent XML and HTML.
 * @author ntatsumi
 *
 */
public abstract class Report {
	
	public abstract String generate(Object info);
	
	public abstract String getMIMEType();
	
	protected String format(Date date) {
		return Constants.DATE_FORMAT_DATE_TIME.format(date);
	}
}
