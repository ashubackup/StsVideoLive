package gen;

import java.lang.reflect.Field;
import java.util.Iterator;

import org.json.*;

import javolution.util.FastMap;


public class Parameter {
	public String response =" {\"status\":\"0\",\"data\":{},\"message\":\"Error\"}";

	private String videoid,ani,action,category,id,duration,percentage,channel,data,name,result,status,state,mod,portal,message,pageurl,html,line,size,page,time;
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}



	private JSONArray jsonarray;
	private String imgurl;
	public JSONArray getJsonarray() {
		return jsonarray;
	}

	public void setJsonarray(JSONArray jsonarray) {
		this.jsonarray = jsonarray;
	}

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}


	
	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}
	public String getSize() {
		return size;
	}

	

	public void setSize(String size) {
		this.size = size;
	}

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public String getPageurl() {
		return pageurl;
	}

	public void setPageurl(String pageurl) {
		this.pageurl = pageurl;
	}

	

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getState() {
		return state;
	}

	public String getMod() {
		return mod;
	}

	public void setMod(String mod) {
		this.mod = mod;
	}

	public String getPortal() {
		return portal;
	}

	public void setPortal(String portal) {
		this.portal = portal;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getPercentage() {
		return percentage;
	}

	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getCategory() {
		return category;
	}

	
	public void setCategory(String category) {
		this.category = category;
	}

	public String getVideoid() {
		return videoid;
	}

	public void setVideoid(String videoid) {
		this.videoid = videoid;
	}

	public String getAni() {
		return ani;
	}

	public void setAni(String ani) {
		this.ani = ani;
	}
	
	
	
	public void setField(String quaryString, Parameter ObjParam) throws Exception
	{
		FastMap params = new FastMap<String,String>();
		JSONObject jObject = new JSONObject(quaryString);
		Iterator<?> keys = jObject.keys();
		while(keys.hasNext() ) {
		    String key = (String)keys.next();
		    params.put(key, (2 == 2) ? jObject.get(key).toString().trim() : "0");
		}
		Iterator iterator = params.keySet().iterator();
		Class<?> clazz = ObjParam.getClass();

		while (iterator.hasNext()) 
		{
			try 
			{
				String key = (String) iterator.next();
				Field field = clazz.getDeclaredField(key);
				Class type = field.getType();
				String Fieldtype = type.getCanonicalName();
				field.setAccessible(true);
				if ("int".equalsIgnoreCase(Fieldtype)|| " java.lang.Integer".equalsIgnoreCase(Fieldtype))
					field.set(ObjParam, new Integer((String) params.get(key)));
				else if ("long".equalsIgnoreCase(Fieldtype)|| "java.lang.Long".equalsIgnoreCase(Fieldtype))
					field.set(ObjParam, new Long((String) params.get(key)));
				else if ("double".equalsIgnoreCase(Fieldtype) || " java.lang.Double".equalsIgnoreCase(Fieldtype))
					field.set(ObjParam, new Integer((String) params.get(key)));
				else if ("java.lang.String".equalsIgnoreCase(Fieldtype))
					field.set(ObjParam, (String) params.get(key));
				@SuppressWarnings("unused")
				Object value = field.get(ObjParam);
			} 
			catch (Exception e) 
			{
               e.printStackTrace();
			}
		}
		
		try 
		{} 
		catch (Exception e) 
		{
	        e.printStackTrace();
		}
		finally
		{
			params=null;
			iterator=null;
		}
		return;
	}



	
}
