package sessionmon.report;

import sessionmon.CommandEnum;

public class ReportFactory {
	public static final String REPORT_TYPE_XML = "xml";
	public static final String REPORT_TYPE_JSON = "json";
	public static final String REPORT_TYPE_HTML = "html";
	
	public static Report create(CommandEnum command, String reportType) {
		if(command.equals(CommandEnum.DUMP)) {
			if(reportType.equals(REPORT_TYPE_XML)) {
				return new SessionDumpReportXML();
			} else if(reportType.equals(REPORT_TYPE_JSON)) {
				return new SessionDumpReportJSON();
			} else if(reportType.equals(REPORT_TYPE_HTML)) {
				return new SessionDumpReportHTML();
			}
		} else if(command.equals(CommandEnum.TEST_REPLICATION)) {
			if(reportType.equals(REPORT_TYPE_HTML)) {
				return new TestReplicationReportHTML();
			}
		}
		return null;
	}
}
