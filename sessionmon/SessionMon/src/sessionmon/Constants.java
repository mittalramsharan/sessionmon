package sessionmon;

import java.text.SimpleDateFormat;

public interface Constants {
	public static final SimpleDateFormat DATE_FORMAT_DATE_TIME = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss:S");
	public static final SimpleDateFormat DATE_FORMAT_DAY_OF_WEEK_TIME_YEAR = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyy");
	
	public static final String CONTEXT_PARAMETER_ACTIVE_SESSIONS = "sessionmon.activeSessions";
	public static final String CONTEXT_PARAMETER_LAST_ATTRIBUTE_UPDATE_TIME = "sessionmon.lastAttributeUpdateTime";
	public static final String CONTEXT_PARAMETER_CONFIGURATION = "sessionmon.config";
	
	public static final String REQUEST_PARAMETER_COMMAND = "command";
	public static final String REQUEST_PARAMETER_TYPE = "type";
	
}
