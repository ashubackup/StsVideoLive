package gen;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;



/**
 * Servlet implementation class BillingStatus
 */
@WebServlet("/Billing")
public class BillingStatus extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private String CallbackPath;
	    
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
    	doPost(request, response);
    }
    
    public String getDateFormat(final String date) {
        final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final String dateInString = date;
        final String[] arr = dateInString.split("\\+");
        final String[] newarr = arr[0].split("T");
        System.out.println(newarr[0]);
        final String data = String.valueOf(newarr[0]) + " " + newarr[1];
        System.out.println(data);
        return data;
    }
    
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/xml");
        final PrintWriter out = response.getWriter();
        final StringBuffer jb = new StringBuffer();
        String line = null;
        String resp = "{\"status\":\"0\",\"data\":{},\"message\":\"Failed\"}";
        try {
          
            final BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null) {
                jb.append(line);
            }
            final JSONObject jsonObj = new JSONObject(jb.toString());
			  String result_name = (String)jsonObj.get("result_name");
			  final String insidObj = jsonObj.get("subscription").toString();
			  final JSONObject jsonObjnew = new JSONObject(insidObj);
			  String ani = (String)jsonObjnew.get("user_msisdn");
			  
			  final String countyCode = "27";
	            final int len = countyCode.length();
	            if (ani.substring(0, len).equals(countyCode)) {
	                ani = ani.substring(len);
	            }
	            String last_billed_at = (String)jsonObjnew.get("last_billed_at");
	            String next_billing_at = (String)jsonObjnew.get("next_billing_at");
	            final String channel_name = (String)jsonObjnew.get("channel_name");
	            final String status_name = (String)jsonObjnew.get("status_name");
	            final String svc_name = (String)jsonObjnew.get("svc_name");
	            String subscription_at = (String)jsonObjnew.get("subscription_started_at");
	            int amount = jsonObjnew.getInt("billing_rate");
	            int campaign_id = jsonObjnew.getInt("campaign_id");
	            String amt = Integer.toString(amount);
	            String camp_id = Integer.toString(campaign_id);
	            
	            next_billing_at = this.getDateFormat(next_billing_at);
	            last_billed_at = this.getDateFormat(last_billed_at);
	            subscription_at = this.getDateFormat(subscription_at);
	            
	            String instQry = "insert into tbl_dlr (ani,channel_name,status_name,svc_name,amount,campaign_id,next_billed_date,last_billed_date,type,sub_date_time,result_name) values ('<ani>','<channel_name>','<status_name>','<svc_name>','<amount>','<campaign_id>','<next_billed_date>','<last_billed_date>','<type>','<sub_date_time>','<result_name>')";
	            instQry = instQry.replace("<ani>", ani).replace("<last_billed_date>", last_billed_at).replace("<next_billed_date>", next_billing_at)
	            		.replace("<channel_name>", channel_name).replace("<status_name>", status_name).replace("<svc_name>", svc_name).replace("<amount>", amt)
	            		.replace("<campaign_id>", camp_id).replace("<type>", "billing").replace("<sub_date_time>", subscription_at)
	            		.replace("<result_name>", result_name);
	            System.out.println(instQry);
	            PreparedStatement stmtup =  Loader.NdotMTNConn.prepareStatement(instQry);
		    	stmtup.executeUpdate(instQry);
			   resp = "{\"status\":\"1\",\"data\":{},\"message\":\"Success\"}";
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        response.setStatus(204);
        out.println(resp);
    }
    
   
}