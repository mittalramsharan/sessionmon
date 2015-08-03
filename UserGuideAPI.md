### How to use SessionMon via API ###

Example 1: Generating XML report
```
String resultXML = null;
if(command.equals(CommandEnum.DUMP)) {
	Report report = ReportFactory.create(CommandEnum.DUMP, ReportFactory.REPORT_TYPE_XML);
	SessionInfo sessionInfo = new SessionInfo(request);
	resultXML  = report.generate(sessionInfo);
} else if(command.equals(CommandEnum.TEST_REPLICATION)) {
	Report report = ReportFactory.create(CommandEnum.TEST_REPLICATION, ReportFactory.REPORT_TYPE_XML);
	Test sessionTest = new Test(request);
	resultXML  = report.generate(sessionTest);
}
```