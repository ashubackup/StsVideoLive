package gen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mysql.cj.xdevapi.JsonArray;

import gen.common.ScrapingPage;

@WebServlet("/scrapapi")
public class ScrapingServlet extends HttpServlet {
	
	
	
	private static final long serialVersionUID = 1L;
  Scrapmodel param=new Scrapmodel();
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		PrintWriter out=response.getWriter();
		
		String line="";
		StringBuffer jd=new StringBuffer();
		JSONObject js=null ;
		BufferedReader rd=request.getReader();
		while((line=rd.readLine())!=null)
		{
            jd.append(line);
             
		}
		
		
		
		js=new JSONObject(jd.toString());
//		System.out.println(js);
		String action=js.getString("action");
		if(action.equalsIgnoreCase("1"))
		{
			int length=js.getJSONArray("imgurl").length();
			JSONArray jsonArray = js.getJSONArray("imgurl");
			Object ani= js.get("ani");
			String url=js.getString("pageurl");
			String html=js.getString("page");			
			param.setJsonarray1(jsonArray);
			param.setAni1(ani.toString());
			param.setPageurl1(url);
				
			param=ScrapingPage.getSize1(param);
			DataCollector.insertUserDataUsage1(param);
			
			out.print(new JSONObject().put("size", param.getSize1()));
		}
		
		else if(action.equalsIgnoreCase("2"))
		{
		
			int length=js.getJSONArray("imgurl").length();
			JSONArray jsonArray = js.getJSONArray("imgurl");
			Object ani= js.get("ani");
			String url=js.getString("pageurl");
			String html=js.getString("page");
			
			param.setJsonarray(jsonArray);
			param.setAni(ani.toString());
			param.setPageurl(url);
				
			
			param=ScrapingPage.getSize(param);
			DataCollector.insertUserDataUsage(param);
			
			out.print(new JSONObject().put("size", param.getSize()));
		}
	}
	

}
