## What is SessionMon? ##
SessionMon is a utility that lets you analyze, test and monitor Servlet sessions at any point in your application's workflow. With this tool, fixing your HTTP session related bugs, performance lag, memory over usage, and replication issue are much less painful.

Using SessionMon is quick and easy. To get started, all you need to do is download [sessionmon.zip](http://code.google.com/p/sessionmon/downloads/list) file and deploy SessionMon Servlet in your application. Sweet!

### Requirements ###
  * Java Runtime Environment version 1.4 and above
  * Java Servlet Specification 2.3 and above

### Features ###
  * **User friendly graphical interface (Access via API also available)**
  * **Servlet session dump in HTML, XML and JSON format**
    * Session ID
    * Number of other active sessions
    * Number of session attributes
    * Latest session attribute update time
    * List of attributes in session
      * Attribute name
      * Attribute object type
      * Non-serializable object indicator
      * Attribute toString value
      * Approximate size (in bytes) of attribute's entire data graph rooted at the object instance
      * Size (in bytes) of attribute in serialized form
    * Total approximate size (in bytes) of all session attributes' entire data graph rooted at the object instance
    * Total size (in bytes) of all session attributes in serialized form
    * Session creation time
    * Session last accessed time
    * Maximum inactive interval in seconds
    * Is new session
  * **Session management tester**
    * Session replication test in your clustered environment: Analyze replication synchronization and delay.
    * Session invalidation test
  * **Future Enhacements**
    * Monitoring of session data and replication with E-Mail notification
    * Dump and run tests on all active sessions

### Getting Started ###
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
  1. Navigate to a point/page of your application workflow where you want to monitor session in your favorite web browser.
  1. Access SessionMonServlet from the same browser used in previous step.
    1. GUI: `http://your-host/your-application/sessionmon`
    1. JSON Dump: `http://your-host/your-application/sessionmon?command=dump&type=json`
    1. XML Dump: `http://your-host/your-application/sessionmon?command=dump&type=xml`

Please refer to the [Wiki page](UserGuideAPI.md) on how to access SessionMon via API.
### Screenshots ###
_Screenshot #1: Session Dump_
![http://farm3.static.flickr.com/2430/3570584330_af42d1fa79_o.gif](http://farm3.static.flickr.com/2430/3570584330_af42d1fa79_o.gif)

_Screenshot #2: Session Replication Test_
![http://farm4.static.flickr.com/3595/3569771235_1f5911eeea_o.gif](http://farm4.static.flickr.com/3595/3569771235_1f5911eeea_o.gif)