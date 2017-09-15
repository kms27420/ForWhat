package com.kms.billiardcounter.dao.account;

import java.sql.Connection;
import java.sql.Statement;

import com.kms.billiardcounter.dao.connection.BilliardCounterConnector;

/**
 * 
 * 본 프로그램의 계정을 저장하는 데이터베이스의 ACCOUNT 테이블에 접근하여 내용을 변경할 수 있는 클래스
 * 
 * @author Kwon
 *
 */
public class AccountUpdater {

	private AccountUpdater() {}
	
	private static void createTableIfNotExists( Connection conn, Statement stmt ) throws Exception{
		
		stmt.execute(
				"CREATE TABLE IF NOT EXISTS billiard_counter.ACCOUNT("
				+ "ID VARCHAR(15) NOT NULL,"
				+ "PASSWORD VARCHAR(20) NOT NULL,"
				+ "PRIMARY KEY(ID));"
				);
		
	}
	
	/**
	 * 
	 * 데이터베이스의 ACCOUNT 테이블에 id, password를 저장하는 매서드
	 * 
	 * @param id 입력받은 id
	 * @param password 입력받은 password
	 * @return 작업이 정상적으로 처리되면 true, 그렇지 않으면 false
	 */
	public static final boolean saveAccountToDB( String id, String password ) {
		
		try {
			
			Connection conn = BilliardCounterConnector.getConnection();
			Statement stmt = conn.createStatement();
			String sql = "INSERT INTO billiard_counter.ACCOUNT "
					+ "VALUES('" + id + "', '" + password + "');";
			
			createTableIfNotExists( conn, stmt );
			
			stmt.executeUpdate( sql );
			
			stmt.close();
			conn.close();
			
			return true;
			
		} catch( Exception e ) {
			
			e.printStackTrace();
			
			return false;
			
		}
		
	}
	
	/**
	 * 
	 * 데이터베이스의 ACCOUNT 테이블에 접근하여 password를 변경해주는 매서드
	 * 
	 * @param id 입력받은 id
	 * @param password 변경해줄 password
	 * @return 작업이 정상적으로 처리되면 true, 그렇지 않으면 false
	 */
	public static final boolean updatePassword( String id, String password ) {
		
		try {
			
			Connection conn = BilliardCounterConnector.getConnection();
			Statement stmt = conn.createStatement();
			String sql = "UPDATE billiard_counter.ACCOUNT "
					+ "SET PASSWORD = '" + password + "' "
					+ "WHERE ID = '" + id + "';";
			
			stmt.executeUpdate( sql );
			
			stmt.close();
			conn.close();
			
			return true;
			
		} catch( Exception e ) {
			
			e.printStackTrace();
			
			return false;
			
		}
		
	}
	
}
