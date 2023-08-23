package gen;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import gen.Loader;

/**
 * Servlet implementation class CheckSub
 */
@WebServlet("/CheckSub")
public class CheckSub extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	public void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
	
    
    public void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        final HttpSession session = request.getSession(true);
        session.setMaxInactiveInterval(600);
        int status = 3;
        final JSONObject obj = new JSONObject();
        response.setContentType("text/html");
        final PrintWriter out = response.getWriter();
        String ani = request.getParameter("ani");
        final String cat = request.getParameter("category");
        final String countyCode = "234";
        System.out.println("Called");
        final int len = countyCode.length();
        if (ani.startsWith("+")) {
            ani = ani.substring(1);
        }
        if (ani.startsWith("0")) {
            ani = ani.substring(1);
        }
        if (ani.startsWith("00234")) {
            ani = ani.substring(5);
        }
        if (ani.substring(0, len).equals(countyCode)) {
            ani = ani.substring(len);
        }
        ani = ani.trim();
        try {
            final Statement stmt1 = Loader.contentConn.createStatement();
            final Statement stmt2 = Loader.contentConn.createStatement();
            final Statement stmt3 = Loader.contentConn.createStatement();
            final String chkqry = "select count(1) from tbl_subscription where ani='" + ani + "' and service_type='" + cat + "' ";
            System.out.println(chkqry);
            final ResultSet rs = stmt1.executeQuery(chkqry);
            rs.next();
            final int count = rs.getInt(1);
            System.out.println("count : " + count);
            if (count > 0) {
                final String qry = "select * from tbl_subscription where ani='" + ani + "' and service_type='" + cat + "' and NEXT_BILLED_DATE is not null ";
                System.out.println(qry);
                final ResultSet rsn = stmt2.executeQuery(qry);
                if (rsn.next()) {
                    final String qry2 = "select count(1) from tbl_subscription where ani='" + ani + "' and service_type='" + cat + "' and date(NEXT_BILLED_DATE) >= date(now())";
                    System.out.println(qry2);
                    final ResultSet rsn2 = stmt2.executeQuery(qry2);
                    if (rsn2.next()) {
                        final int cnt = rsn2.getInt(1);
                        if (cnt > 0) {
                            status = 1;
                        }
                        else {
                            status = 2;
                        }
                    }
                }
                else {
                    status = 3;
                }
            }
            else {
                final String instSub = "insert into tbl_subscription (ani,sub_date_time,unsub_date_time,m_act,lang,service_type,status,charging_date,billing_date,default_amount,RECORDSTATUS,pack_type) values ('" + ani + "',now(),NULL,'WEB','e','" + cat + "',NULL,NULL,NULL,'2','1','Daily')";
                System.out.println(instSub);
                stmt3.executeUpdate(instSub);
                status = 3;
            }
            obj.put("status", status);
            out.print(obj.toString());
        }
        catch (Exception Exception) {
            Exception.printStackTrace();
            out.print("{\"status\":\"0\"}");
        }
    }
}