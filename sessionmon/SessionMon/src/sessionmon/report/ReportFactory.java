package sessionmon.report;

import sessionmon.CommandEnum;

public class ReportFactory {
	public static final String REPORT_TYPE_XML = "xml";
	public static final String REPORT_TYPE_JSON = "json";
	
	public static Report create(CommandEnum command, String reportType) {
		if(command.equals(CommandEnum.DUMP)) {
			if(reportType.equals(REPORT_TYPE_XML)) {
				return new SessionDumpReportXML();
			} else if(reportType.equals(REPORT_TYPE_JSON)) {
				return new SessionDumpReportJSON();
			}
		} else if(command.equals(CommandEnum.TEST)) {
			if(reportType.equals(REPORT_TYPE_XML)) {
				return new TestReportXML();
			} else if(reportType.equals(REPORT_TYPE_JSON)) {
				return new TestReportJSON();
			}
		}
		return null;
	}
}
