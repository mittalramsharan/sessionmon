package sessionmon;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sessionmon.report.Report;
import sessionmon.report.ReportFactory;

public class SessionMonServlet extends HttpServlet {
	
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
		
		String commandParam = request.getParameter("command");
		CommandEnum command = null;
		
		try {
			command = CommandEnum.valueOf(commandParam);
		} catch(IllegalArgumentException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}
		
		if(command.equals(CommandEnum.REPORT)) {
			SessionInfo sessionInfo = new SessionInfo(request);
			Report report = ReportFactory.create(request.getParameter("type"));
			response.setContentType(report.getMIMEType());
			out.print(report.generate(sessionInfo));
		} else if(command.equals(CommandEnum.ADD_SESSION_PARAMETERS)) {
			SessionTesterTool.addStringAttributes(10, request.getSession());
			out.print("completed");
		} else if(command.equals(CommandEnum.REMOVE_SESSION_PARAMETERS)) {
			SessionTesterTool.removeAttributes(request.getSession());
			out.print("completed");
		} else if(command.equals(CommandEnum.INVALIDATE_SESSION)) {
			request.getSession().invalidate();
			out.print("completed");
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
