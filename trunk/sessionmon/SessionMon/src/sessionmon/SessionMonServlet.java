package sessionmon;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import sessionmon.report.Report;
import sessionmon.report.ReportFactory;
import sessionmon.test.Tester;

public class SessionMonServlet extends HttpServlet {
	private static final Logger LOGGER = Logger.getLogger(SessionMonServlet.class);
	
	/**
	 * Constructor of the object.
	 */
	public SessionMonServlet() {
		super();
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Configuration config = (Configuration)request.getSession().getServletContext().getAttribute(Constants.CONTEXT_PARAMETER_CONFIGURATION);
		//send not found error if sessionmon servlet is not enabled
		if(!config.isEnabled()) {
			LOGGER.warn("[sessionmon]SessionMon is disabled. Modify the web descriptor to change this setting.");
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		
		PrintWriter out = response.getWriter();
		
		//resolve command
		String commandParam = request.getParameter(Constants.REQUEST_PARAMETER_COMMAND);
		CommandEnum command = null;
		try {
			command = CommandEnum.valueOf(commandParam);
		} catch(IllegalArgumentException e) {
			LOGGER.warn("[sessionmon]Received unkown command.");
		}
		
		//controller
		if(command != null) {
			if(command.equals(CommandEnum.INVALIDATE_SESSION)) {
				request.getSession().invalidate();
			} else {
				Report report = ReportFactory.create(command, request.getParameter(Constants.REQUEST_PARAMETER_TYPE));
				if(report == null) {
					LOGGER.error("[sessionmon]ReportFactory could not understand your request");
					out.print("Error, ReportFactory could not understand your request.");
				} else {
					response.setContentType(report.getMIMEType());
					if(command.equals(CommandEnum.DUMP)) {
						SessionInfo sessionInfo = new SessionInfo(request);
						out.print(report.generate(sessionInfo));
					} else if(command.equals(CommandEnum.TEST_REPLICATION)) {
						Tester sessionTest = new Tester(request);
						out.print(report.generate(sessionTest));
					}
				}
			}
		} else {
			response.setContentType("text/html");
			out.print(PageRequestProcessor.process(request, response));
		}
		
		out.flush();
		out.close();
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		ServletConfig sc = getServletConfig();
		Configuration configuration = null;
		
		String servers = sc.getInitParameter("server_node_addresses");
		if(servers != null) {
			configuration = new Configuration(servers);
		} else {
			LOGGER.warn("[sessionmon]server_node_addresses parameter is not specified");
			configuration = new Configuration();
		}
		
		String enabled = sc.getInitParameter("enabled");
		if(enabled != null && enabled.equalsIgnoreCase("true")) {
			configuration.setEnabled(true);
		}
		
		String overridePath = sc.getInitParameter("override_path");
		if(overridePath != null) {
			configuration.setOverridePath(overridePath);
		}
		
		sc.getServletContext().setAttribute(Constants.CONTEXT_PARAMETER_CONFIGURATION, configuration);
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy();
	}
}