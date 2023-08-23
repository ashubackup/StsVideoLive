package gen.common;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import gen.Scrapmodel;

public class ScrapingPage {
	
	
//	public static void main(String[] args) throws IOException {
//		
//		String url="https://mtnvideos.ndotomobile.com/redirect?msisdn=277018694842&result=active";
//		url="https://mtnvideos.ndotomobile.com/index.jsp";
//		getSize(url);
//	
//	}
	
	
	public static Scrapmodel getSize(Scrapmodel param) throws IOException
	{
		
		double size=0;
		
		
		System.out.println("Page url :"+param.getPageurl());
		
		for(int i=0;i<param.getJsonarray().length();i++)
		{
			String imgurl=(String) param.getJsonarray().get(i);
			size+=ScrapingPage.getimageSize(imgurl);
			
		}
		
		param.setSize(String.valueOf(size));
		return param;
	}
	    
	
	 public static  double getimageSize(String imgurl)
     {
   	  
		  URL ur;
		  
		  double kb=0;
		  
		try {
			ur = new URL(imgurl);
	      	URLConnection uconn=ur.openConnection();
	      	
	       kb=uconn.getContentLength()/1024;
	      	double mb=kb/1024;
	      	
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		 return kb;
	      }
   	  
	 

		public static Scrapmodel getSize1(Scrapmodel param) throws IOException
		{
		
//			byte [] page=html.getBytes();
			double size=0;
			
			
			System.out.println("Page url :"+param.getPageurl());
			
			for(int i=0;i<param.getJsonarray1().length();i++)
			{
				String imgurl=(String) param.getJsonarray1().get(i);
				size+=ScrapingPage.getimageSize1(imgurl);
				
			}
			
			param.setSize1(String.valueOf(size));
			return param;
		}
		    
		
		 public static  double getimageSize1(String imgurl)
	     {
	   	  
			  URL ur;
			  
			  double kb=0;
			  
			try {
				ur = new URL(imgurl);
		      	URLConnection uconn=ur.openConnection();
		      	
		       kb=uconn.getContentLength()/1024;
		      	double mb=kb/1024;
		      	
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			
			 return kb;
		      }
	   	  
     
	

}
