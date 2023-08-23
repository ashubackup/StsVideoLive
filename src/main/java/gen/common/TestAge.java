package gen.common;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gen.DataCollector;
import gen.Loader;
import gen.Parameter;

/**
 * Servlet implementation class TestAge
 */
public class TestAge extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    Parameter objParameter =new Parameter();
    DataCollector coll=new DataCollector();
    public TestAge() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		objParameter.setAni(request.getParameter("ani"));
		objParameter.setResult(request.getParameter("result"));
		String result = coll.addUserDetail(objParameter,Loader.contentConn);
		
		
	}

}
