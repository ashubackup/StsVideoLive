package gen;

import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

/**
 * Servlet implementation class Loader
 */
@WebServlet("/Loader")
public class Loader extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static Connection contentConn = null;
	public static Connection NdotMTNConn = null;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Loader() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void init() throws ServletException {
		DbConnection db = new DbConnection();
		contentConn = db.getDatabse();
		NdotMTNConn = db.getMTNDatabse();
		System.out.println("Db Connected in init first time");
	}

}
