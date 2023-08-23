package gen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONArray;
import org.json.JSONObject;

import net.sf.uadetector.OperatingSystem;
import net.sf.uadetector.ReadableUserAgent;
import net.sf.uadetector.UserAgentStringParser;
import net.sf.uadetector.service.UADetectorServiceFactory;

public class DataCollector {
	Parameter objpara = new Parameter();
	String response = objpara.response;
	JSONObject obj = new JSONObject();
	JSONObject obj1 = new JSONObject();

	public ResultSet getCat() {
		ResultSet rs = null;
		try {
			String getQuery = "select * from tbl_cat where status = '2' and name= 'Hollywood'";
			PreparedStatement ps = Loader.contentConn.prepareStatement(getQuery);
			// System.out.println(getQuery);
			rs = ps.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	public static Connection checkConn(Connection conn) throws SQLException {

		if (conn == null) {
			// new DbConnection();
			System.out.println("Inside COnnection " + Loader.NdotMTNConn);

			conn = DbConnection.getMTNDatabse();
		}
		return conn;
	}

	public ResultSet getAllCat(final String age) {
		ResultSet rs = null;
		try {
			String getQuery = null;

			if (age.equalsIgnoreCase("1")) {
				getQuery = "select * from tbl_cat where age not in('" + age + "') and  status = '1' or status='2'";
			} else {
				getQuery = "select * from tbl_cat where  status = '1' or status='2'";
			}

			PreparedStatement ps = Loader.contentConn.prepareStatement(getQuery);
			System.out.println(getQuery);
			rs = ps.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	public ResultSet getSubCat(String id) {
		ResultSet rs = null;
		try {
			String getQuery = "select * from tbl_sub_cat where parent_cat_id = '" + id + "' and status = '1'";
			PreparedStatement ps = Loader.contentConn.prepareStatement(getQuery);
//			System.out.println(getQuery);
			rs = ps.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	public ResultSet getMainCat(final String age) {
		ResultSet rs = null;
		String getQuery = null;
		try {

			if (age.equalsIgnoreCase("1")) {
				getQuery = "select * from tbl_sub_cat where parent_cat_id not in (select category_name from tbl_cat where age='"
						+ age + "') and status = '1' or status='2' ";
			} else {
				getQuery = "select * from tbl_sub_cat where status = '1' or status='2' ";

			}

			PreparedStatement ps = Loader.contentConn.prepareStatement(getQuery);
			// System.out.println(getQuery);
			rs = ps.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	public ResultSet getWatching(String portal) {
		ResultSet rs = null;
		try {
			String getQuery = "SELECT \r\n" + "    c.sub_cat_id, \r\n" + "    m.duration,\r\n" + "    m.videoid,\r\n"
					+ "    m.percentage, \r\n" +

					"    c.vurl, \r\n" + "    c.imgurl,\r\n" + "    s.sub_cat_name,\r\n" + "m.modifieddatetime\r\n"
					+ "FROM\r\n" + "    tbl_time_logging m \r\n"
					+ "INNER JOIN tbl_videos c INNER JOIN tbl_sub_cat s  \r\n"
					+ "	ON c.videoid = m.videoid and m.portal='" + portal
					+ "'  AND s.sub_cat_id=c.sub_cat_id  GROUP BY c.sub_cat_id order by m.modifieddatetime desc limit 5 ";
			PreparedStatement ps = Loader.contentConn.prepareStatement(getQuery);
			// System.out.println(getQuery);
			rs = ps.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	public ResultSet getwhishlist(String portal) {
		ResultSet rs = null;
		try {
			String getQuery = "SELECT    \r\n" + " m.videoid,\r\n" + "    c.sub_cat_id, \r\n" + "    c.vurl, \r\n"
					+ "    c.name, \r\n" + "    s.sub_cat_name,\r\n" + "    c.imgurl\r\n" + "FROM\r\n"
					+ "    tbl_wishlist m \r\n" + "INNER JOIN tbl_videos c INNER JOIN tbl_sub_cat s  \r\n"
					+ "	ON c.videoid = m.videoid and m.portal='" + portal
					+ "'  AND s.sub_cat_id=c.sub_cat_id GROUP BY c.sub_cat_id order by m.datetime desc limit 5 ";
			PreparedStatement ps = Loader.contentConn.prepareStatement(getQuery);
			// System.out.println(getQuery);
			rs = ps.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	public ResultSet getVideos(String id, String genre) {
		ResultSet rs = null;
		try {
			String getQuery = "select * from tbl_videos where sub_cat_id ='" + id
					+ "' and status = '0' and (startdate >= curdate() or startdate is null or genres like '" + genre
					+ "%')";
			PreparedStatement ps = Loader.contentConn.prepareStatement(getQuery);
			// System.out.println(getQuery);
			rs = ps.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	public ResultSet getVideos(String id) {
		ResultSet rs = null;
		try {
			String getQuery = "select * from tbl_videos where sub_cat_id ='" + id
					+ "' and status = '0' and (startdate >= curdate() or startdate is null )";
			PreparedStatement ps = Loader.contentConn.prepareStatement(getQuery);
			// System.out.println(getQuery);
			rs = ps.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	public ResultSet getTop(String portal) {
		ResultSet rs = null;
		try {
			String getQuery = "\r\n" + "SELECT COUNT(1),\r\n" + "t1.videoid,\r\n" + "c.name,\r\n" + "c.sub_cat_id, \r\n"
					+ "c.vurl, \r\n" + "c.imgurl \r\n"
					+ "FROM tbl_video_logging t1 INNER JOIN tbl_videos c WHERE t1.VIEW='1' AND t1.videoid=c.videoid AND t1.TYPE='"
					+ portal + "'  GROUP BY 2 ORDER BY COUNT(1) DESC\r\n" + "limit 10";
			PreparedStatement ps = Loader.contentConn.prepareStatement(getQuery);
			// System.out.println(getQuery);
			rs = ps.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	public ResultSet getRCat() {
		ResultSet rs = null;
		try {
			String getQuery = "SELECT category,DATETIME FROM tbl_videos ORDER BY DATETIME DESC";
			PreparedStatement ps = Loader.contentConn.prepareStatement(getQuery);
			// System.out.println(getQuery);
			rs = ps.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	public ResultSet getRVideo(String id) {
		ResultSet rs = null;
		try {
			String getQuery = "SELECT * FROM tbl_videos WHERE category ='" + id
					+ "' AND STATUS='0' ORDER BY DATETIME DESC LIMIT 2";
			PreparedStatement ps = Loader.contentConn.prepareStatement(getQuery);
			// System.out.println(getQuery);
			rs = ps.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	public ResultSet getLatestVideos() {
		ResultSet rs = null;
		try {
			String getQuery = "select * from tbl_videos  where status ='0' and (startdate >= curdate() or startdate is null) and category in (select category_name from tbl_cat where status='2' or status='1')  order by datetime desc limit 5";
			PreparedStatement ps = Loader.contentConn.prepareStatement(getQuery);
			// System.out.println(getQuery);
			rs = ps.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	public ResultSet getDataBySubID(String id) {
		ResultSet rs = null;
		try {
			String getQuery = "select * from tbl_videos where sub_cat_id ='" + id
					+ "' and status = '0' and (startdate >= curdate() or startdate is null)";
			PreparedStatement ps = Loader.contentConn.prepareStatement(getQuery);
			// System.out.println(getQuery);
			rs = ps.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	public ResultSet getTeaser() {
		ResultSet rs = null;
		try {
			String getQuery = "SELECT t1.* FROM tbl_teaser t1 WHERE video_cat IN (SELECT category_name FROM tbl_cat WHERE STATUS ='1' OR STATUS='2') AND STATUS = '1'  ORDER BY t1.order ASC";
			PreparedStatement ps = Loader.contentConn.prepareStatement(getQuery);
			// System.out.println(getQuery);
			rs = ps.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	public ResultSet getTeaserDetails(String id) {
		ResultSet rs = null;
		try {
			String getQuery = "SELECT * FROM tbl_teaser where teaser_id='" + id + "'";
			PreparedStatement ps = Loader.contentConn.prepareStatement(getQuery);

			rs = ps.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	public ResultSet getTeaserByID(String id) {
		ResultSet rs = null;
		try {
			String getQuery = "select t1.* from tbl_teaser t1 where video_cat ='" + id
					+ "' and status = '1'  order by t1.order asc";
			PreparedStatement ps = Loader.contentConn.prepareStatement(getQuery);
			rs = ps.executeQuery();
			if (rs.next()) {
				rs.beforeFirst();
				return rs;
			} else {
				return getTeaser();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	public ResultSet getvideoDetailsbyId(String id) {
		ResultSet rs = null;
		try {
			String getQuery = "select * from tbl_videos where videoid='" + id + "'";
			PreparedStatement ps = Loader.contentConn.prepareStatement(getQuery);
			// System.out.println(getQuery);
			rs = ps.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	public ResultSet getvideoDetailsbyId(String id, String ani, String portal) {
		ResultSet rs = null;
		try {
			String getQuery = "select t1.*,(select count(1) from tbl_video_logging where view='1' and videoid='" + id
					+ "') as views,(select count(1) from tbl_wishlist where  videoid='" + id + "' and portal='" + portal
					+ "' and ani = '" + ani
					+ "') as whishlist,(select count(1) as count from tbl_like where videoid = '" + id
					+ "' and portal='" + portal
					+ "' and status ='1') as likes,(select count(1) as count from tbl_like where videoid = '" + id
					+ "' and  portal='" + portal + "' and status ='1' and userid='" + ani
					+ "' ) as userlike from tbl_videos as t1 where videoid='" + id + "'";
			PreparedStatement ps = Loader.contentConn.prepareStatement(getQuery);
			// System.out.println(getQuery);
			rs = ps.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	public ResultSet getGenre() {
		ResultSet rs = null;
		try {
			String getQuery = "select * from tbl_genre";
			PreparedStatement ps = Loader.contentConn.prepareStatement(getQuery);
			// System.out.println(getQuery);
			rs = ps.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	public String addLogging(Connection con, Parameter objparameter) {
		JSONObject main = new JSONObject();
		try {

			String addCat = "insert into `tbl_video_logging`(`videoid`,`ani`,`type`,`channel`,`portal`) values (?,?,?,?,?)";

			PreparedStatement pstmt = con.prepareStatement(addCat);

			pstmt.setString(1, objparameter.getVideoid());
			pstmt.setString(2, objparameter.getAni());
			pstmt.setString(3, objparameter.getCategory());
			pstmt.setString(4, objparameter.getChannel());
			pstmt.setString(5, objparameter.getPortal());
			pstmt.executeUpdate();
			pstmt.close();

			obj.put("status", "1");
			obj.put("data", main);
			obj.put("message", "success");
			response = obj.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;

	}

	public int insertLogs(String clickid, Connection conn) {
		int resp = 0;
		try {
			String instQry = "insert into tbl_conv_logs(clickid,createddatetime,modifieddatetime,provider,service,mode,pubid) values (?,now(),now(),'TC','videos','vendor','')";
			PreparedStatement statement = conn.prepareStatement(instQry, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, clickid);
			statement.executeUpdate();
			try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					resp = (int) generatedKeys.getLong(1);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}

	public String getStatus(final String ani, String servicename) {
		String State = "";
		try {

			final Statement stmt = Loader.NdotMTNConn.createStatement();
			final String chkqry = "select * from tbl_subscription where ani='" + ani + "' and service_type='"
					+ servicename + "'  ";
			System.out.println(chkqry);
			final ResultSet rs = stmt.executeQuery(chkqry);
			if (rs.next()) {
				State = this.getUserState(ani, servicename);
			} else {
				State = "2";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return State;
	}

	public String getUserState(final String ani, final String service) {
		String State = "0";
		try {
			Statement stmt = null;
			stmt = Loader.NdotMTNConn.createStatement();
			int cnt = 0;
			final String subQry = "select count(1) as cnt from tbl_subscription where ani='" + ani
					+ "' and service_type='" + service + "' " + "and date(next_billed_date)>= Date(subdate(now(),1)) or date(sub_date_time)=date(now())";
			System.out.println("subQry::::" + subQry);
			final ResultSet rssub = stmt.executeQuery(subQry);
			if (rssub.next()) {
				cnt = rssub.getInt(1);
				System.out.println("cnt~~" + cnt);
			}
			if (cnt > 0) {
				State = "1";
			} else {
				State = "0";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return State;
	}

	public Parameter checkAgeLog(final String ani) {
		Parameter param = new Parameter();

		final String query = "select count(1),status from tbl_video_usr where ani='" + ani + "'";

		System.out.println(query);
		try {
			PreparedStatement ps = Loader.contentConn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {

				param.setState(rs.getString(1));
				param.setStatus(rs.getString(2));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return param;
	}

	public void addPlayLogging(final Connection con, final Parameter objparameter) {
		String query = "";
		try {
			query = "select * from tbl_play_logs where videoid='" + objparameter.getVideoid() + "'and ani='"
					+ objparameter.getAni() + "'and date(datetime)= Date(now())";
			Statement stmt = con.createStatement();
			ResultSet set = stmt.executeQuery(query);
			if (set.next()) {
				query = "UPDATE tbl_play_logs SET duration=?,percentage=? WHERE ani=? AND DATE(DATETIME)=DATE(NOW()) AND videoid=?";

				final PreparedStatement pstmt = con.prepareStatement(query);
				pstmt.setString(1, objparameter.getDuration());
				pstmt.setString(2, objparameter.getPercentage());
				pstmt.setString(4, objparameter.getVideoid());
				pstmt.setString(3, objparameter.getAni());
				pstmt.executeUpdate();
				pstmt.close();
			} else {
				query = "insert into tbl_play_logs(videoid,ani,duration,percentage,datetime) values(?,?,?,?,now())";

				final PreparedStatement pstmt = con.prepareStatement(query);
				pstmt.setString(1, objparameter.getVideoid());
				pstmt.setString(2, objparameter.getAni());
				pstmt.setString(3, objparameter.getDuration());
				pstmt.setString(4, objparameter.getPercentage());
				pstmt.execute();
				pstmt.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public String getUserDetail(Parameter objparameter, Connection conn) {
		ResultSet rs = null;
		String resp = objparameter.response;
		try {
			String query = "select * from tbl_video_usr where ani='" + objparameter.getAni() + "'";
			rs = getResultSet(conn, query);
			JSONObject json = createJson(rs, 2);
			resp = json.toString();
			System.out.println(resp);
			return resp;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}

	public JSONObject createJson(ResultSet res, int col) {
		JSONArray jarray = new JSONArray();
		JSONObject json1 = new JSONObject();

		try {
			ResultSetMetaData rsmd = res.getMetaData();
			if (res.next()) {
				res.beforeFirst();
				while (res.next()) {
					json1 = new JSONObject();
					for (int i = 1; i <= col; i++) {
						json1.put("" + rsmd.getColumnName(i) + "", res.getString(rsmd.getColumnName(i)));
					}
					jarray.put(json1);
				}
			}
			System.out.println("This is array " + jarray);
			obj.put("list", jarray);
			obj1.put("status", "1");
			obj1.put("data", obj);
			obj1.put("message", "success");
			return obj1;
		} catch (Exception e) {
			e.printStackTrace();
			return obj1;
		}
	}

	public String getServiceData(Connection conn, String field, String data, String checkData) {
		try {
			String query = "select " + field + " from tbl_service_master where " + checkData + "='" + data + "'";
			ResultSet res = getResultSet(conn, query);
			if (res.next())
				return res.getString("" + field + "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public ResultSet getResultSet(Connection conn, String query) {
		ResultSet res = null;
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			System.out.println("query ---- " + query);
			res = ps.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	public Parameter UpdateQuery(Connection conn, String query) {
		Parameter objparameter = new Parameter();
		try {
			System.out.println(query);
			PreparedStatement ps = conn.prepareStatement(query);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			return objparameter;
		}
		return objparameter;
	}

	public String addTimeeLogging(final Connection con, final Parameter objparameter) {
		try {
			String addCat = "";
			final String type = "hollywood";
			final int getValue = this.getTimeLogging(con, objparameter, type);
			if (getValue == 0) {
				addCat = "insert into `tbl_time_logging`(`videoid`,`ani`,`duration`,`portal`) values (?,?,?,'" + type
						+ "')";
			} else {
				addCat = "update  `tbl_time_logging` set `videoid`=?,`ani`=?,`duration`=? where _id='" + getValue
						+ "' and portal='" + type + "'";
			}
			final PreparedStatement pstmt = con.prepareStatement(addCat);
			pstmt.setString(1, objparameter.getVideoid());
			pstmt.setString(2, objparameter.getAni());
			pstmt.setString(3, objparameter.getDuration());
			pstmt.executeUpdate();
			pstmt.close();
			this.obj.put("status", "1");
			this.obj.put("data", "{}");
			this.obj.put("message", "success");
			this.response = this.obj.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.response;
	}

	public int getTimeLogging(final Connection con, final Parameter objparameter, final String portal) {
		final JSONObject main = new JSONObject();
		int _id = 0;
		try {
			final String addCat = "select _id from tbl_time_logging where videoid=? and ani=? and portal ='" + portal
					+ "' ";
			final PreparedStatement pstmt = con.prepareStatement(addCat);
			pstmt.setString(1, objparameter.getVideoid());
			pstmt.setString(2, objparameter.getAni());
			final ResultSet res = pstmt.executeQuery();
			if (res.next()) {
				_id = res.getInt("_id");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return _id;
	}

	public String getTimeDurationLogging(final Connection con, final Parameter objparameter, final String portal) {
		final JSONObject main = new JSONObject();
		String duration = "0";
		try {
			final String addCat = "select duration from tbl_time_logging where videoid=? and ani=? and portal ='"
					+ portal + "' ";
			final PreparedStatement pstmt = con.prepareStatement(addCat);
			pstmt.setString(1, objparameter.getVideoid());
			pstmt.setString(2, objparameter.getAni());
			final ResultSet res = pstmt.executeQuery();
			if (res.next()) {
				duration = res.getString("duration");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return duration;
	}

	public void userLoginLogs(String msidn, String videoId) {
		String query = "";
		try {
			if (videoId != null)
				query = "insert into userloginlogs(msidn,portal,log_type,video_id) values('" + msidn
						+ "','NDOTOSTREAM-STS','videoPlay','" + videoId + "')";
			else
				query = "insert into userloginlogs(msidn,portal,log_type) values('" + msidn
						+ "','NDOTOSTREAM-STS','Login')";
			Statement prep = Loader.contentConn.createStatement();
			prep.execute(query);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getDuration(final Connection con, final Parameter objparameter) {
		final JSONObject main = new JSONObject();
		try {
			final String getDuration = this.getTimeDurationLogging(con, objparameter, "hollywood");
			main.put("duration", getDuration);
			this.obj.put("status", "1");
			this.obj.put("data", main);
			this.obj.put("message", "success");
			this.response = this.obj.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.response;
	}

	public String addUserDetail(Parameter param, Connection contentConn) {

		String status = "1";
		System.out.println(param.getAni() + param.getResult());
		if (param.getResult().equalsIgnoreCase("yes")) {
			status = "0";

		}
		String query1 = "select count(1) as cnt from tbl_video_usr where ani='" + param.getAni() + "' ";

		String query = "insert into tbl_video_usr (ani,ageType,status) values('" + param.getAni() + "','"
				+ param.getResult() + "'," + status + ")";

		PreparedStatement ps, ps1;
		try {
			ps = contentConn.prepareStatement(query);
			ps1 = contentConn.prepareStatement(query1);
			ResultSet rs = ps1.executeQuery();
			int count = 0;
			if (rs.next()) {
				count = rs.getInt("cnt");
			}

			if (count == 0) {
				ps.executeUpdate();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return status;
	}

	public static long insertUserSocialMod(String mod, String provider, String pubid, String clickid) {

		if (provider.length() == 0) {
			provider = "social";
		}

		try {

			String query = "insert into tbl_conv_logs(clickid,createddatetime,modifieddatetime,provider,service,mode,pubid) values (?,now(),now(),?,'videos',?,?)";

			PreparedStatement ps = Loader.NdotMTNConn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, clickid);
			ps.setString(2, provider);
			ps.setString(3, mod);
			ps.setString(4, pubid);
			System.out.println("---------" + query);
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				return rs.getLong(1);
			}

			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}

	public static String getAgeStatusfromSubcat(final String catid) {
		String age = null;
		try {
			String query = "select age from tbl_sub_cat where sub_cat_id='" + catid + "'";
			PreparedStatement ps = Loader.contentConn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				age = rs.getString(1);
			}
		} catch (Exception e) {

		}
		return age;
	}

	public String addShareLog(Parameter param, Connection contentConn) {

		String insertquery = "insert into sharelog(ani,mode,portal) values('" + param.getAni() + "','" + param.getMod()
				+ "','" + param.getPortal() + "')";
		String result = insertquery;
		try {
			Statement st = contentConn.createStatement();
			st.execute(insertquery);
			result = "success";

		} catch (SQLException e) {
			e.printStackTrace();
			e.getMessage();
		}
		return result;
	}

	public String addComment(Parameter param, Connection contentConn) {
		JSONObject jo = new JSONObject();
		String status = "0";
		try {

			String query = "insert into tbl_comment (name,comment,videoid,datetime,portal) values(?,?,?,now(),'mtn')";
			System.out.println(query);
			PreparedStatement ps = Loader.contentConn.prepareStatement(query);
			ps.setString(1, param.getAni());
			ps.setString(2, param.getMessage());
			ps.setString(3, param.getVideoid());

			int i = ps.executeUpdate();
			System.out.println("status  " + i);
			jo.put("status", i);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return jo.toString();
	}

	public String getComment(Parameter param, Connection contentConn) {
		JSONObject jo = new JSONObject();
		try {

			JSONArray ja = new JSONArray();
			String query = "select comment from tbl_comment where videoid='" + param.getVideoid()
					+ "' and portal='mtn'";
			PreparedStatement ps = Loader.contentConn.prepareStatement(query);
			System.out.println(query);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {

				ja.put(rs.getString(1));

			}
			jo.put("comment", ja);

		} catch (Exception e) {
			e.getStackTrace();
		}

		return jo.toString();
	}

	public String addTrackLogging(Connection Conn, Parameter param) {

		JSONObject jo = new JSONObject();
		try {

			String query = "insert into tbl_user_track (ani,portal) values('" + param.getAni() + "','"
					+ param.getPortal() + "')";
			PreparedStatement ps = Conn.prepareStatement(query);
			System.out.println(query);

			int status = ps.executeUpdate();

			jo.put("status", status);

		} catch (Exception e) {

			e.printStackTrace();
		}

		return jo.toString();
	}

	public static void insertUserDevice(String agent, String ani) {

		UserAgentStringParser parser = UADetectorServiceFactory.getResourceModuleParser();

		ReadableUserAgent ra = parser.parse(agent);
		OperatingSystem os = ra.getOperatingSystem();
		String device = os.getFamilyName().toUpperCase();
//		System.out.println("$ Device Information :::::: "+device);

		try {

			String query = "insert into tbl_user_device (ani,device_type) values('" + ani + "','" + device + "')";
			PreparedStatement ps = Loader.contentConn.prepareStatement(query);
			ps.executeUpdate();

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public static void insertUserDataUsage(Scrapmodel param) {

		try {

			String query = "insert into tbl_user_datausage (ani,pageurl,size) values('" + param.getAni() + "','"
					+ param.getPageurl() + "','" + param.getSize() + "')";
			System.out.println(query);
			PreparedStatement ps = Loader.contentConn.prepareStatement(query);
			ps.executeUpdate();

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public static void insertUserDataUsage1(Scrapmodel param) {

		try {

			String query = "insert into tbl_user_datausage (ani,pageurl,size) values('" + param.getAni1() + "','"
					+ param.getPageurl1() + "','" + param.getSize1() + "')";
			System.out.println(query);
			PreparedStatement ps = Loader.contentConn.prepareStatement(query);
			ps.executeUpdate();

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public String addActivity(Connection conn, Parameter param) {

		JSONObject jo = new JSONObject();
		try {

			String query = "insert into tbl_user_activity (ani,portal,time,status) values('" + param.getAni() + "','"
					+ param.getPortal() + "','" + param.getTime() + "','" + param.getStatus() + "')";
			PreparedStatement ps = conn.prepareStatement(query);
			System.out.println("User Activity ===> " + query);

			int status = ps.executeUpdate();

			jo.put("status", status);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return jo.toString();
	}

}
