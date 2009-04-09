http://code.google.com/p/sessionmon/

What is SessionMon? 

SessionMon is a quick and easy way to monitor and test Servlet sessions at any point in your application's workflow. To get started, all you need to do is download sessionmon.jar file and deploy SessionMonServlet. Sweet!
Requirements

    * Java Runtime Environment version 1.4 and above
    * Java Servlet Specification 2.3 and above 

Features

    * User friendly graphical interface
    * Servlet session dump in HTML, XML and JSON format
          o Session ID
          o Number of other active sessions
          o Total number of session attributes
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
          o Session replication test in your clustered environment
          o Session invalidation test 
    * Monitor (will be available in version 2.0)
          o E-Mail notification
          o Monitor setup wizard 

Getting Started

   1. Download and save sessionmon.jar into your lib directory
   2. Edit your web.xml to deploy SessionMonServlet like this:

      <servlet>
              <servlet-name>SessionMonServlet</servlet-name>
              <servlet-class>sessionmon.SessionMonServlet</servlet-class>
              <init-param>
                      <param-name>enabled</param-name>
                      <param-value>true</param-value>
              </init-param>
              <init-param>
                      <param-name>server_node_addresses</param-name>
                      <param-value>http://localhost:8080,http://localhost:8081</param-value>
              </init-param>
              <init-param>
                      <param-name>override_path</param-name><!-- optional -->
                      <param-value>/foo/blah</param-value>
              </init-param>
      </servlet>

      <servlet-mapping>
              <servlet-name>SessionMonServlet</servlet-name>
              <url-pattern>/sessionmon/*</url-pattern>
      </servlet-mapping>

   3. Access SessionMonServlet from a web browser 