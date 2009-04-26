http://code.google.com/p/sessionmon/

What is SessionMon?

SessionMon is a utility that lets you analyze, test and monitor Servlet sessions at any point in your application's workflow. With this tool, fixing your HTTP session related bugs, performance lag, memory over usage, and replication issue are much less painful.

Using SessionMon is quick and easy. To get started, all you need to do is download sessionmon.zip file and deploy SessionMon Servlet in your application. Sweet!
Requirements

    * Java Runtime Environment version 1.4 and above
    * Java Servlet Specification 2.3 and above 

Features

    * User friendly graphical interface
    * Servlet session dump in HTML, XML and JSON format
          o Session ID
          o Total number of active sessions (new in version 1.0)
          o Total number of session attributes
          o Latest attribute update time (new in version 1.0)
          o List of attributes in session
                + Attribute name
                + Attribute object type
                + Attribute toString value
                + Approximate size (in bytes) of attribute's entire data graph rooted at the object instance
                + Size (in bytes) of attribute in serialized form 
          o Total approximate size (in bytes) of all session attributes' entire data graph rooted at the object instance
          o Total size (in bytes) of all session attributes in serialized form
          o Session creation time
          o Session last accessed time
          o Maximum inactive interval in seconds
          o Is new session 
    * Session management tester
          o Session replication test in your clustered environment: Analyze replication synchronization and delay.
          o Session invalidation test 
    * Coming soon to Version 2.0
          o Monitoring of session data and replication with E-Mail notification 

Getting Started

   1. Download and save sessionmon.jar into your lib directory along with the dependency JARs that are bundled in the ZIP file.
   2. Edit your web.xml to deploy SessionMon Servlet and listeners like this:

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

   3. Access SessionMonServlet from a web browser 