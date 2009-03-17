package sessionmon;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import sessionmon.report.Report;
import sessionmon.report.ReportFactory;

public class SessionMonServlet extends HttpServlet {
	private static final Logger LOGGER = Logger.getLogger(SessionMonServlet.class);
	
	public static final String REQUEST_PARAMETER_COMMAND = "command";
	public static final String REQUEST_PARAMETER_TYPE = "type";
	
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
		PrintWriter out = response.getWriter();
		
		//resolve command
		String commandParam = request.getParameter(REQUEST_PARAMETER_COMMAND);
		CommandEnum command = null;
		try {
			command = CommandEnum.valueOf(commandParam);
		} catch(IllegalArgumentException e) {
		}
		
		//controller
		if(command != null) {
			if(command.equals(CommandEnum.DUMP)) {
				Report report = ReportFactory.create(command, request.getParameter(REQUEST_PARAMETER_TYPE));
				response.setContentType(report.getMIMEType());
				SessionInfo sessionInfo = new SessionInfo(request);
				out.print(report.generate(sessionInfo));
			} else if(command.equals(CommandEnum.TEST)) {
				Report report = ReportFactory.create(command, request.getParameter(REQUEST_PARAMETER_TYPE));
				response.setContentType(report.getMIMEType());
				Test sessionTest = new Test(request);
				out.print(report.generate(sessionTest));
			} else if(command.equals(CommandEnum.CONFIGURE)) {
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
	public void init() throws ServletException {}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy();
	}
}