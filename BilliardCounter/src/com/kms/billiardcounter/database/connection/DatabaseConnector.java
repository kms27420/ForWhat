package com.kms.billiardcounter.database.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import com.kms.billiardcounter.database.account.AccountModifier;

/**
 * 
 * 본 프로그램의 DB를 생성하고 연결해주는 클래스
 * 
 * @author Kwon
 *
 */

public class DatabaseConnector {
	
	private static final String DB_URL = "jdbc:mysql://localhost/mysql?useSSL=true&verifyServerCertificate=false";		// 연결할 DB의 URL
	private static final String USER = "root";			// 연결할 DB의 USER이름
	private static final String PASSWORD = "1234";		// 연결할 DB의 password
	
	private static Connection conn;
	
	static {
		
		try {
			
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
			Statement stmt = conn.createStatement();
		
			stmt.execute("CREATE SCHEMA IF NOT EXISTS billiard_counter;");
			
			createAccountTable( stmt );
			createBaseFeeTable( stmt );
			createGameListTable( stmt );
			createGameMonitorTable( stmt );
		
		} catch( Exception e ) { e.printStackTrace(); }
		
	}
	
	private DatabaseConnector(){}

	/**
	 * 
	 * DB에 연결한 Connection에서 생성한 Statement를 반환하는 매서드
	 * 
	 * @return stmt
	 */
	public static final Statement getStatement() throws Exception {
		
		return conn.createStatement();
		
	}
	
	private static final void createAccountTable( Statement stmt ) throws Exception {
		
		stmt.execute(
				"CREATE TABLE IF NOT EXISTS billiard_counter.ACCOUNT("
				+ "ID VARCHAR(" + AccountModifier.ID_MAX_LENGTH + ") NOT NULL,"
				+ "PASSWORD VARCHAR(" + AccountModifier.PASSWORD_MAX_LENGTH + ") NOT NULL,"
				+ "PRIMARY KEY(ID));"
				);
		
	}
	
	private static final void createBaseFeeTable( Statement stmt ) throws Exception {
		
		stmt.execute(
				"CREATE TABLE IF NOT EXISTS billiard_counter.BASE_FEE("
				+ "BASE_FEE_PER_MINUTE INT(3) NOT NULL,"
				+ "BASE_FEE_TIME INT(3) NOT NULL,"
				+ "FEE_INCREASE_TIME INT(3) NOT NULL,"
				+ "PRIMARY KEY(BASE_FEE_PER_MINUTE, BASE_FEE_TIME, FEE_INCREASE_TIME));"
				);
		
	}
	
	private static final void createGameListTable( Statement stmt ) throws Exception {
		
		stmt.execute(
				"CREATE TABLE IF NOT EXISTS billiard_counter.GAME_LIST("
				+ "DATE VARCHAR(6) NOT NULL,"
				+ "START_TIME VARCHAR(8) NOT NULL,"
				+ "END_TIME VARCHAR(8) NOT NULL,"
				+ "GAME_NUMBER INT(3) NOT NULL,"
				+ "TABLE_NUMBER INT(3) NOT NULL,"
				+ "USED_TIME INT(4) NOT NULL,"
				+ "FEE INT(10) NOT NULL,"
				+ "IS_PAID BOOLEAN NOT NULL,"
				+ "PRIMARY KEY(DATE, START_TIME, GAME_NUMBER, TABLE_NUMBER));"
				);
		
	}
	
	private static final void createGameMonitorTable( Statement stmt ) throws Exception {
		
		stmt.execute(
				"CREATE TABLE IF NOT EXISTS billiard_counter.GAME_MONITOR("
				+ "TABLE_NUMBER INT(11) PRIMARY KEY NOT NULL,"
				+ "TABLE_ROW INT(11) NOT NULL,"
				+ "TABLE_COL INT(11) NOT NULL,"
				+ "IS_IN_USE BOOLEAN NOT NULL);"
				);
		
	}
	
}
