package gen;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbConnection {
	public static Connection getDatabse() {
		Connection conn = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();

			conn = DriverManager.getConnection(
					"jdbc:mysql://5.189.169.12:3306/ndoto?autoReconnect=true&useSSL=false&enabledTLSProtocols=TLSv1.2&serverTimezone=UTC",
					"root", "gloadmin123");
			System.out.println("ndoto DB connected");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	public static Connection getMTNDatabse() {
		Connection conn = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();

			conn = DriverManager.getConnection(
					"jdbc:mysql://91.205.172.123:3306/ndotosts?autoReconnect=true&useSSL=false&enabledTLSProtocols=TLSv1.2&serverTimezone=UTC","root", "gloadmin123");

			System.out.println("ndotomtn DB connected");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

}
