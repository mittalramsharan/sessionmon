package sessionmon.report;

public class ReportFactory {
	public static final String REPORT_TYPE_XML = "xml";
	public static final String REPORT_TYPE_JSON = "json";
	
	public static Report create(String reportType) {
		if(reportType.equals(REPORT_TYPE_XML)) {
			return new XMLReport();
		} else if(reportType.equals(REPORT_TYPE_JSON)) {
			return new JSONReport();
		} else {
			return null;
		}
	}
}
