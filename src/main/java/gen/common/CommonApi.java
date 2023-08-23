package gen.common;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import gen.DataCollector;
import gen.Loader;
import gen.Parameter;

/**
 * Servlet implementation class CommonApi
 */
@WebServlet("/CommonApi")
public class CommonApi extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommonApi() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String result= "{\"status\":\"0\",\"error\":\"action not defined\"}";
		PrintWriter out = response.getWriter();
		Parameter objParameter = new Parameter();
		DataCollector coll = new DataCollector();
		objParameter.setAction(request.getParameter("action"));
		System.out.println(request.getParameter("action"));
		CommonData cp = new CommonData();
		HttpSession session=request.getSession();
		try {

			
			 if(objParameter.getAction().equalsIgnoreCase("12")) {  				//Add time duration logging
				objParameter.setAni(request.getParameter("ani"));

				objParameter.setVideoid(request.getParameter("videoid"));
				objParameter.setDuration(request.getParameter("duration"));
				objParameter.setChannel(request.getParameter("channel"));
				objParameter.setPercentage(request.getParameter("percentage"));
				objParameter.setCategory(request.getParameter("catid"));
				result = cp.addTimeeLogging(Loader.contentConn, objParameter);
			}else if(objParameter.getAction().equalsIgnoreCase("13")) {  				//get duration
				objParameter.setAni(request.getParameter("ani"));

				objParameter.setVideoid(request.getParameter("videoid"));
				objParameter.setChannel(request.getParameter("channel"));
				result = cp.getDuration(Loader.contentConn, objParameter);
			}else if(objParameter.getAction().equalsIgnoreCase("14")) {  				//get duration
				objParameter.setAni(request.getParameter("ani"));

				objParameter.setVideoid(request.getParameter("videoid"));
				objParameter.setChannel(request.getParameter("type"));
				result = cp.addWhishlist(Loader.contentConn, objParameter);
			}else if(objParameter.getAction().equalsIgnoreCase("15")) {  				//get duration
				objParameter.setVideoid(request.getParameter("videoid"));
				objParameter.setAni(request.getParameter("ani"));
				objParameter.setChannel(request.getParameter("type"));
				result = cp.addViews(Loader.contentConn, objParameter);
			}else if (objParameter.getAction().equalsIgnoreCase("16")) {
				objParameter.setVideoid(request.getParameter("videoid"));
				objParameter.setAni(request.getParameter("ani"));
				objParameter.setCategory(request.getParameter("category"));
				objParameter.setChannel(request.getParameter("type"));
				result = cp.UpdateLike(Loader.contentConn, objParameter);
			} else if (objParameter.getAction().equalsIgnoreCase("17")) {
				objParameter.setData(request.getParameter("data"));
				result = cp.searchVideo(Loader.contentConn, objParameter);
			}else if (objParameter.getAction().equalsIgnoreCase("18")) {
				objParameter.setAni(request.getParameter("ani"));
				objParameter.setName(request.getParameter("name"));
				objParameter.setCategory(request.getParameter("category"));
				objParameter.setVideoid(request.getParameter("videoid"));
				objParameter.setChannel(request.getParameter("type"));
				result = cp.addRecentSearch(Loader.contentConn, objParameter);
			}
			else if(objParameter.getAction().equalsIgnoreCase("20"))
			{
				objParameter.setAni(request.getParameter("ani"));

				objParameter.setVideoid(request.getParameter("videoid"));
				objParameter.setDuration(request.getParameter("duration"));
			//	objParameter.setChannel(request.getParameter("channel"));
				objParameter.setPercentage(request.getParameter("percentage"));
				objParameter.setCategory(request.getParameter("catid"));
				coll.addPlayLogging(Loader.contentConn, objParameter);
			}else if(objParameter.getAction().equalsIgnoreCase("21"))
			{
				objParameter.setAni(request.getParameter("ani"));
				result = coll.getUserDetail(objParameter,Loader.contentConn);
			}
			else if(objParameter.getAction().equalsIgnoreCase("22"))
			{
				objParameter.setAni(request.getParameter("ani"));
				objParameter.setResult(request.getParameter("result"));
				result = coll.addUserDetail(objParameter,Loader.contentConn);
			    session.setAttribute("ageid", result);
				result=new JSONObject().put("age", result).toString();
				
			}
			else if(objParameter.getAction().equalsIgnoreCase("23"))
			{
				objParameter.setPortal(request.getParameter("portal"));
				objParameter.setAni(request.getParameter("ani"));
				objParameter.setMod(request.getParameter("mod"));
				
				result = coll.addShareLog(objParameter, Loader.contentConn);
				System.out.println(result);
				
			}
			 
			else if(objParameter.getAction().equalsIgnoreCase("24"))
			{
				objParameter.setMessage(request.getParameter("msg"));
				objParameter.setAni(request.getParameter("ani"));
				objParameter.setVideoid(request.getParameter("vid"));				
				result = coll.addComment(objParameter, Loader.contentConn);
				System.out.println(result);
				
			}
			else if(objParameter.getAction().equalsIgnoreCase("25"))
			{
				objParameter.setVideoid(request.getParameter("vid"));
			    result = coll.getComment(objParameter, Loader.contentConn);
				System.out.println(result);
				
			}
			 
		} catch (Exception e) {
			e.printStackTrace();
		}
		out.print(result);

	}

}
