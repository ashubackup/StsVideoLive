package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gen.DataCollector;

/**
 * Servlet implementation class SocialServlet
 */
@WebServlet("/social")
public class SocialServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
    public SocialServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	// TODO Auto-generated method stub
    	doPost(req, resp);
    }
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	        // /social?mod=facebook.com
	              PrintWriter out=response.getWriter();
	              String mod=request.getParameter("mod");
	              String provider=request.getParameter("provider");
	              String pubid=request.getParameter("pubid");
	              String clickid=request.getParameter("clickid");
	        
	             
	              try {
	            	  
	            	  long cid=DataCollector.insertUserSocialMod(mod,provider,pubid,clickid);
	            	  out.println(cid);
	            	  response.setStatus(200);
	            	  response.sendRedirect("http://doi.mtndep.co.za/service/7911?cid="+cid+"");
	            	  
	              }catch(Exception e)
	              {
	            	  e.printStackTrace();
	            	  response.setStatus(500);
	              }
	        
                           	
	}

}
