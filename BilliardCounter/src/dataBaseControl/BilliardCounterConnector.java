package dataBaseControl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 본 프로젝트의 데이터베이스에 연결해주는 클래스
 * @author Kwon
 *
 */
public class BilliardCounterConnector {
	private final String DB_URL = "jdbc:mysql://localhost/mysql?useSSL=true&verifyServerCertificate=false";		// 연결할 DB의 URL
	private final String USER = "root";			// 연결할 DB의 USER이름
	private final String PASSWORD = "1234";		// 연결할 DB의 password
	
	private Connection conn = null;				// DB에 연결시켜줄 Connection
	
	/**
	 * 연결된 Connection의 객체를 받아오는 매서드
	 * @return 연결된 Connection
	 */
	public Connection getConnection(){
		return conn;
	}
	
	/**
	 * DB에 연결하는 매서드
	 * @throws Exception
	 */
	protected void connectToDB() throws Exception{
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
		Statement stmt = conn.createStatement();
		
		stmt.execute("CREATE SCHEMA IF NOT EXISTS billiard_counter;");
		
		stmt.close();
		stmt = null;
	}
	/**
	 * 연결된 Connection의 연결을 끊어주는 매서드
	 * @throws SQLException
	 */
	protected void disconnectFromDB() throws SQLException{
		if(conn != null){
			conn.close();
			conn = null;
		}
	}
}
