package com.kms.billiardcounter.databasecontrol;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * 
 * 본 프로그램의 DB에 연결한 connection을 구해주는 클래스
 * 
 * @author Kwon
 *
 */

public class BilliardCounterConnector {
	
	private static final String DB_URL = "jdbc:mysql://localhost/mysql?useSSL=true&verifyServerCertificate=false";		// 연결할 DB의 URL
	private static final String USER = "root";			// 연결할 DB의 USER이름
	private static final String PASSWORD = "1234";		// 연결할 DB의 password
	
	private BilliardCounterConnector(){}

	/**
	 * 
	 * 본 프로그램의 DB에 연결된 Connection을 반환하는 매서드
	 * 
	 * @return 본 프로그램의 DB에 연결된 Connection
	 * @throws Exception
	 */
	public static Connection getConnection() throws Exception{
		
		Connection conn;
		
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
		Statement stmt = conn.createStatement();
		
		stmt.execute("CREATE SCHEMA IF NOT EXISTS billiard_counter;");
		
		stmt.close();
		
		return conn;
		
	}
	
}
