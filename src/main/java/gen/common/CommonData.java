package gen.common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.json.JSONArray;
import org.json.JSONObject;

import gen.Parameter;

public class CommonData {
	Parameter objpara = new Parameter();
	String response = objpara.response;
	JSONObject obj = new JSONObject();

	public String addTimeeLogging(Connection con, Parameter objparameter) {
		try {

			String addCat = "";
			int getValue = getTimeLogging(con, objparameter, objparameter.getChannel());
			if (getValue == 0) {
				addCat = "insert into `tbl_time_logging`(`videoid`,`ani`,`duration`,`portal`,`percentage`,`catid`) values (?,?,?,'"
						+ objparameter.getChannel() + "',?,?)";

			} else {
				addCat = "update  `tbl_time_logging` set `videoid`=?,`ani`=?,`duration`=?,`percentage`=?,`catid`=?,modifieddatetime=now() where _id='"
						+ getValue + "' and portal='" + objparameter.getChannel() + "'";
			}
			userdatausageloging(con, objparameter);

			PreparedStatement pstmt = con.prepareStatement(addCat);
			System.out.println(addCat);
			pstmt.setString(1, objparameter.getVideoid());
			pstmt.setString(2, objparameter.getAni());
			pstmt.setString(3, objparameter.getDuration());
			pstmt.setString(4, objparameter.getPercentage());
			pstmt.setString(5, objparameter.getCategory());
			pstmt.executeUpdate();
			pstmt.close();

			obj.put("status", "1");
			obj.put("data", "{}");
			obj.put("message", "success");
			response = obj.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;

	}

	public void userdatausageloging(Connection con, Parameter objparameter) {

		try {
			int per = (int) Math.ceil(Double.parseDouble(objparameter.getPercentage()));

			if (per == 0) {
				return;
			}
			String query = "select videosize from tbl_videos where videoid='" + objparameter.getVideoid() + "'";
			System.out.println(query);
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(query);
			if (rs.next()) {
				int size = rs.getInt(1);
				int dd = (int) (per * size) / 100;

				query = "select count(*) as count from tbl_user_datausage where ani='" + objparameter.getAni()
						+ "' and pageurl='" + objparameter.getVideoid()
						+ "' and datetime<=DATE_ADD(now(), INTERVAL 5 MINUTE)";
				System.out.println(query);
				Statement stmt = con.createStatement();
				ResultSet rst = stmt.executeQuery(query);
				int count = 0;
				if (rst.next()) {
					count = rst.getInt("count");
				}

				if (count != 0) {
					query = "update tbl_user_datausage set size='" + dd + "' where pageurl='"
							+ objparameter.getVideoid() + "' and ani='" + objparameter.getAni() + "'";

				} else {

					query = "insert into tbl_user_datausage (ani,pageurl,size) values('" + objparameter.getAni() + "','"
							+ objparameter.getVideoid() + "','" + dd + "')";

				}
				System.out.println(query);
				Statement st = con.createStatement();
				st.executeUpdate(query);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public String addRecentSearch(Connection con, Parameter objparameter) {
		try {

			String addCat = "insert into `tbl_recent_search`(`videoid`,`ani`,`name`,`portal`,`sub_cat_id`,`datetime`) values (?,?,?,?,?,now())";

			PreparedStatement pstmt = con.prepareStatement(addCat);
			System.out.println(addCat);
			pstmt.setString(1, objparameter.getVideoid());
			pstmt.setString(2, objparameter.getAni());
			pstmt.setString(3, objparameter.getName());
			pstmt.setString(4, objparameter.getChannel());
			pstmt.setString(5, objparameter.getCategory());
			pstmt.executeUpdate();
			pstmt.close();

			obj.put("status", "1");
			obj.put("data", "{}");
			obj.put("message", "success");
			response = obj.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;

	}

	public String searchVideo(Connection con, Parameter objparameter) {
		try {
			JSONArray jarray = new JSONArray();
			JSONObject obj1 = new JSONObject();
			String addCat = "SELECT * FROM tbl_videos WHERE NAME LIKE ('%" + objparameter.getData()
					+ "%') AND   STATUS ='0' AND (startdate >= CURDATE() OR startdate IS NULL) AND category IN (SELECT category_name FROM tbl_cat WHERE STATUS='2' OR STATUS='1')  LIMIT 10\r\n"
					+ "";
			PreparedStatement pstmt = con.prepareStatement(addCat);
			ResultSet res = pstmt.executeQuery();

			while (res.next()) {
				JSONObject obj2 = new JSONObject();
				obj2.put("vurl", res.getString("vurl"));
				obj2.put("imgurl", res.getString("imgurl"));
				obj2.put("sub_cat_id", res.getString("sub_cat_id"));
				obj2.put("name", res.getString("name"));
				obj2.put("videoid", res.getString("videoid"));
				jarray.put(obj2);
			}
			obj1.put("list", jarray);
			obj.put("status", "1");
			obj.put("data", obj1);
			obj.put("message", "success");
			response = obj.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;

	}

	public int getTimeLogging(Connection con, Parameter objparameter, String portal) {
		JSONObject main = new JSONObject();
		int _id = 0;
		try {

			String addCat = "select _id from tbl_time_logging where videoid=? and ani=? and portal ='" + portal + "' ";

			PreparedStatement pstmt = con.prepareStatement(addCat);

			pstmt.setString(1, objparameter.getVideoid());
			pstmt.setString(2, objparameter.getAni());

			ResultSet res = pstmt.executeQuery();
			if (res.next()) {
				_id = res.getInt("_id");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return _id;

	}

	public String getTimeDurationLogging(Connection con, Parameter objparameter, String portal) {
		JSONObject main = new JSONObject();
		String duration = "0";
		try {

			String addCat = "select duration from tbl_time_logging where videoid=? and ani=? and portal ='" + portal
					+ "' ";

			PreparedStatement pstmt = con.prepareStatement(addCat);
			System.out.println(addCat);

			pstmt.setString(1, objparameter.getVideoid());
			pstmt.setString(2, objparameter.getAni());
			ResultSet res = pstmt.executeQuery();
			if (res.next()) {
				duration = res.getString("duration");

			}
			System.out.println("Final" + duration);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return duration;

	}

	public String getDuration(Connection con, Parameter objparameter) {
		JSONObject main = new JSONObject();
		try {

			String getDuration = getTimeDurationLogging(con, objparameter, objparameter.getChannel());
			main.put("duration", getDuration);

			obj.put("status", "1");
			obj.put("data", main);
			obj.put("message", "success");
			response = obj.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;

	}

	public String addWhishlist(Connection con, Parameter objparameter) {
		try {

			String addCat = "";
			addCat = "insert into `tbl_wishlist`(`videoid`,`ani`,`portal`) values (?,?,'" + objparameter.getChannel()
					+ "')";

			PreparedStatement pstmt = con.prepareStatement(addCat);

			pstmt.setString(1, objparameter.getVideoid());
			pstmt.setString(2, objparameter.getAni());
			pstmt.executeUpdate();
			pstmt.close();

			obj.put("status", "1");
			obj.put("data", "{}");
			obj.put("message", "success");
			response = obj.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;

	}

	public String addViews(Connection con, Parameter objparameter) {
		JSONObject main = new JSONObject();
		try {

			String findq = "select * from tbl_video_logging where videoid='" + objparameter.getVideoid() + "' and ani='"
					+ objparameter.getAni() + "' and view='1'";
			PreparedStatement ps = con.prepareStatement(findq);
			ResultSet res = ps.executeQuery();
			if (!res.next()) {
				String addCat = "insert into `tbl_video_logging`(`videoid`,`ani`,`type`,`view`) values (?,?,?,?)";

				PreparedStatement pstmt = con.prepareStatement(addCat);

				pstmt.setString(1, objparameter.getVideoid());
				pstmt.setString(2, objparameter.getAni());
				pstmt.setString(3, objparameter.getChannel());
				pstmt.setString(4, "1");
				pstmt.executeUpdate();
				pstmt.close();
			}

			obj.put("status", "1");
			obj.put("data", main);
			obj.put("message", "success");
			response = obj.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;

	}

	public String UpdateLike(Connection conn, Parameter objparameter) {
		try {
			ResultSet checkEntry = checkEntry(conn, objparameter);
			if (checkEntry.next())
				if (checkEntry.getString("count").equalsIgnoreCase("1")) {
					ResultSet getStatus = getLikeStatus(conn, objparameter);
					if (getStatus.next()) {
						String upqry = "update tbl_like set status = '0' where videoid = '" + objparameter.getVideoid()
								+ "' and portal='" + objparameter.getChannel() + "' and userid='"
								+ objparameter.getAni() + "' ";
						System.out.println(upqry);
						PreparedStatement ps = conn.prepareStatement(upqry);
						ps.executeUpdate();
						ps.close();
					} else {
						String upqry = "update tbl_like set status = '1' where videoid = '" + objparameter.getVideoid()
								+ "' and portal='" + objparameter.getChannel() + "' and userid='"
								+ objparameter.getAni() + "' ";
						PreparedStatement ps = conn.prepareStatement(upqry);
						System.out.println(upqry);
						ps.executeUpdate();
						ps.close();
					}
				} else {
					String upqry = "insert into tbl_like (name,userid,videoid,category,tbl_like.like,datetime,status,portal) values('"
							+ objparameter.getAni() + "','" + objparameter.getAni() + "','" + objparameter.getVideoid()
							+ "','" + objparameter.getCategory() + "','1',now(),'1','" + objparameter.getChannel()
							+ "')";
					PreparedStatement ps = conn.prepareStatement(upqry);
					System.out.println(upqry);
					ps.executeUpdate();
					ps.close();
				}
			ResultSet getCount = getTotalLikes(conn, objparameter);
			if (getCount.next()) {
				this.obj.put("count", getCount.getString("count"));
			} else {
				this.obj.put("count", "0");
			}
			ResultSet getStatus = getLikeStatus(conn, objparameter);
			if (getStatus.next()) {
				this.obj.put("liked", "1");
			} else {
				this.obj.put("liked", "0");
			}
			this.obj.put("status", "1");
		} catch (Exception e) {
			this.obj.put("status", "0");
			e.printStackTrace();
		}
		return this.obj.toString();
	}

	public ResultSet getTotalLikes(Connection con, Parameter objparameter) {
		ResultSet rs = null;
		try {
			String getQuery = "select count(1) as count from tbl_like where videoid = '" + objparameter.getVideoid()
					+ "' and portal='" + objparameter.getChannel() + "' and status ='1'";
			PreparedStatement ps = con.prepareStatement(getQuery);
			System.out.println(getQuery);
			rs = ps.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	public ResultSet checkEntry(Connection con, Parameter objparameter) {
		ResultSet rs = null;
		try {
			String getQuery = "select count(1) as count from tbl_like where userid = '" + objparameter.getAni()
					+ "' and videoid = '" + objparameter.getVideoid() + "' and portal='" + objparameter.getChannel()
					+ "'";
			PreparedStatement ps = con.prepareStatement(getQuery);
			System.out.println(getQuery);
			rs = ps.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	public ResultSet getLikeStatus(Connection conn, Parameter objparameter) {
		ResultSet resp = null;
		try {
			String getQuery = "select * from tbl_like where videoid = '" + objparameter.getVideoid() + "'and userid='"
					+ objparameter.getAni() + "' and status ='1' and portal='" + objparameter.getChannel() + "'";
			PreparedStatement ps = conn.prepareStatement(getQuery);
			System.out.println(getQuery);
			resp = ps.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
			return resp;
		}
		return resp;
	}

}
