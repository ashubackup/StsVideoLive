package gen;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ApiServlet
 */
@WebServlet("/ApiServlet")
public class ApiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */

	public ApiServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String result = "{\"status\":\"0\",\"error\":\"action not defined\"}";
		PrintWriter out = response.getWriter();
		Parameter objParameter = new Parameter();
		DataCollector coll = new DataCollector();
		objParameter.setAction(request.getParameter("action"));
		try {
			if (objParameter.getAction().equalsIgnoreCase("1")) {

				/* result = coll.insertLogs(request.getParameter("cid"), Loader.NdotMTNConn); */

			} else if (objParameter.getAction().equalsIgnoreCase("4")) { // Add time logging
				objParameter.setVideoid(request.getParameter("videoid"));
				objParameter.setAni(request.getParameter("ani"));
				objParameter.setCategory(request.getParameter("type"));
				objParameter.setChannel(request.getParameter("channel"));
				objParameter.setPortal(request.getParameter("portal"));
				result = coll.addLogging(Loader.contentConn, objParameter);
			} else if (objParameter.getAction().equalsIgnoreCase("5")) // Add Track Loging
			{
				objParameter.setAni(request.getParameter("ani"));
				objParameter.setPortal(request.getParameter("portal"));
				result = coll.addTrackLogging(Loader.contentConn, objParameter);

			} else if (objParameter.getAction().equalsIgnoreCase("6")) // Add user InActive
			{
				objParameter.setAni(request.getParameter("ani"));
				objParameter.setStatus(request.getParameter("status"));
				objParameter.setTime(request.getParameter("time"));
				objParameter.setPortal(request.getParameter("portal"));
				result = coll.addActivity(Loader.contentConn, objParameter);

			} else if (objParameter.getAction().equalsIgnoreCase("7")) // Add User Active
			{
				objParameter.setAni(request.getParameter("ani"));
				objParameter.setStatus(request.getParameter("status"));
				result = coll.addTrackLogging(Loader.contentConn, objParameter);

			}
//
//			else if (objParameter.getAction().equalsIgnoreCase("8")) // Expello call
//			{
//
//				result = EmpelloApi.empelloFunc(request.getParameter("ip"), request.getParameter("useragent"),
//						request.getParameter("cid"), request.getParameter("token"), Loader.NdotMTNConn);
//
//				JSONObject obj = new JSONObject();
//				obj.put("ip", request.getParameter("ip"));
//				obj.put("userAgent", request.getParameter("useragent"));
//				obj.put("data", result);
//				System.out.println("Get Data From Rrquest : : :  " + obj.toString());
//				result = obj.toString();
//
//			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		out.print(result);

	}

}
