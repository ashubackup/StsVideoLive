package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import gen.DataCollector;
import gen.Loader;
import gen.Parameter;

/**
 * Servlet implementation class redirectServlet
 */
@WebServlet("/redirect")
public class redirectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Parameter param = new Parameter();
	private DataCollector dc = new DataCollector();
	String url = "";

	public redirectServlet() {

		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		System.out.println("redirectServlet.java");
		String s_id = "1";
		String msisdn = request.getParameter("msisdn");
		//String msisdn="9805702978";
		System.out.println("MSISDN--- " + msisdn);

		System.out.println("Before COnnection " + Loader.NdotMTNConn);
		try {
			Loader.NdotMTNConn = dc.checkConn(Loader.NdotMTNConn);
		} catch (SQLException e) {
			System.out.println(e.getStackTrace());
		}
		System.out.println("After COnnection " + Loader.NdotMTNConn);

		if (msisdn == null) {
			url = dc.getServiceData(Loader.NdotMTNConn, "guid_url", s_id, "id");
			response.sendRedirect("" + url + "");
		} else {
			String svc_id = request.getParameter("svc_id");
			String type = request.getParameter("type");
			// For local 
//			String svc_id = "Ndoto Stream";
//			String type = "Ndoto Stream";
			msisdn = checkAni(msisdn);

			System.out.print("type ------ " + type);

			if (type == null || type.equalsIgnoreCase("onnet")) {
				setAccessHeader(response);
				String status = dc.getStatus(msisdn, dc.getServiceData(Loader.NdotMTNConn, "svc_name", svc_id, "id"));
				System.out.println("Status +" + status);
				if (status.equalsIgnoreCase("1")) {
					HttpSession session = request.getSession();
					session.setAttribute("user", msisdn);
					dc.userLoginLogs(msisdn, null);
					param = dc.checkAgeLog(msisdn);

					if (param.getState().equalsIgnoreCase("1")) {
						response.sendRedirect("./index.jsp");
						session.setAttribute("ageid", param.getStatus());
						System.out.println("enter in login.....");

					} else {
						session.setAttribute("ageid", "2");
						response.sendRedirect("./index.jsp");

					}
				} else if (status.equalsIgnoreCase("0")) {
					RequestDispatcher dispatcher = request
							.getRequestDispatcher("/info.jsp?msisdn=" + msisdn + "&svc_id=" + svc_id + "");
					dispatcher.forward(request, response);

				} else {

					url = dc.getServiceData(Loader.NdotMTNConn, "guid_url", s_id, "id");
					response.sendRedirect("" + url + "");
					return;
				}
			} else if (type.equalsIgnoreCase("offnet")) {
				String ref = request.getParameter("ref");
				String output = "";
				String flag = "";
				String URL = dc.getServiceData(Loader.NdotMTNConn, "sms_url", s_id, "id") + "/27" + msisdn + "/?ref="
						+ ref;
				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpPost postRequest = new HttpPost(URL);
				System.out.println(postRequest);
				HttpResponse resp = httpClient.execute(postRequest);
				BufferedReader br = new BufferedReader(new InputStreamReader((resp.getEntity().getContent())));

				System.out.println("Output from Server ....");
				while ((output = br.readLine()) != null) {
					System.out.println("output::" + output);
					flag = output;
				}
				if (flag.equalsIgnoreCase("TRUE")) {
					response.sendRedirect("thanks?flag=TRUE&ref=" + ref + "&ani=" + msisdn);
				} else {
					response.sendRedirect("thanks?flag=FALSE&ref=" + ref + "&ani=" + msisdn);
				}

			}
		}
	}

	public String checkAni(String ani) {
		String countyCode = "27";
		if (ani.startsWith("0"))
			ani = ani.substring(1);
		if (ani.startsWith("+"))
			ani = ani.substring(1);
		int len = countyCode.length();
		if (ani.substring(0, len).equals(countyCode))
			ani = ani.substring(len);
		if (ani.contains(" "))
			ani = ani.replace(" ", "");
		return ani;
	}

	@Override
	protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		setAccessHeader(resp);
		resp.setStatus(HttpServletResponse.SC_OK);
	}

	private void setAccessHeader(HttpServletResponse resp) {
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setHeader("Access-Control-Allow-Method", "POST");
		resp.setHeader("Access-Control-Allow-Headers", "*");
		resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
	}

}
