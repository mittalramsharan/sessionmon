### How to deploy SessionMon in your web application ###
  1. Download and save [sessionmon.jar](http://code.google.com/p/sessionmon/downloads/list) into your lib directory along with the dependency JARs that are bundled in the ZIP file.
  1. Edit your web.xml to deploy SessionMon Servlet and listeners like this:
```
<listener>
	<listener-class>sessionmon.SessionListener</listener-class>
</listener>
<listener>
	<listener-class>sessionmon.SessionAttributeListener</listener-class>
</listener>
<servlet>
        <servlet-name>SessionMonServlet</servlet-name>
        <servlet-class>sessionmon.SessionMonServlet</servlet-class>
        <init-param>
                <param-name>enabled</param-name>
                <param-value>true</param-value>
        </init-param>
	<!-- needed only if you're running a clustered environment -->
        <init-param>
        	<param-name>server_node_addresses</param-name>
        	<param-value>http://localhost:8080,http://localhost:8081</param-value>
        </init-param>
        <!-- optional configuration-->
        <!--init-param>
        	<param-name>override_path</param-name>
        	<param-value>/foo/blah</param-value>
        </init-param-->
</servlet>

<servlet-mapping>
	<servlet-name>SessionMonServlet</servlet-name>
	<url-pattern>/sessionmon/*</url-pattern>
</servlet-mapping>
```
  1. Navigate to a page in your application workflow where you want to monitor session in your favorite web browser.
  1. Access SessionMonServlet from the same browser used in previous step.
    1. GUI: `http://your-host/your-application/sessionmon`
    1. JSON Dump: `http://your-host/your-application/sessionmon?command=dump&type=json`
    1. XML Dump: `http://your-host/your-application/sessionmon?command=dump&type=xml`