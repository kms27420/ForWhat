package com.kms.billiardcounter.database.account;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.kms.billiardcounter.database.connection.BilliardCounterConnector;

/**
 * 
 * 본 프로그램의 계정(데이터베이스의 ACCOUNT 테이블에 접근하는)을 판별하는 클래스
 * 
 * @author Kwon
 *
 */
public class AccountCheck {

	private AccountCheck() {}
	
	/**
	 * 
	 * 입력 받은 password가 본 프로그램의 계정의 password와 일치하는지 판단해주는 매서드
	 * 
	 * @param password 입력받은 password
	 * @return 일치하면 true, 그렇지 않으면 false
	 */
	public static final boolean isItCorrectPassword( String password ) {
		
		boolean returnValue = false;
		
		try {
			
			Connection conn = BilliardCounterConnector.getConnection();
			Statement stmt = conn.createStatement();
			String sql = "SELECT * "
					+ "FROM billiard_counter.ACCOUNT "
					+ "WHERE PASSWORD = '" + password + "';";
			
			ResultSet rs = stmt.executeQuery( sql );
			
			if( rs.next() )		returnValue = true;
			
			rs.close();
			stmt.close();
			conn.close();
			
		} catch( Exception e ) {
			
			e.printStackTrace();
			
			return false;
			
		}
		
		return returnValue;
		
	}
	
	/**
	 * 
	 * 입력받은 id, password가 본 프로그램의 계정 정보와 일치하는지 판단하는 매서드
	 * 
	 * @param id 입력받은 id
	 * @param password 입력받은 password
	 * @return 일치하면 true, 그렇지 않으면 false
	 */
	public static final boolean isItCorrectAccount( String id, String password ) {
		
		boolean returnValue = false;
		
		try {
			
			Connection conn = BilliardCounterConnector.getConnection();
			Statement stmt = conn.createStatement();
			String sql = "SELECT * "
					+ "FROM billiard_counter.ACCOUNT "
					+ "WHERE ID = '" + id + "' AND PASSWORD = '" + password + "';";
			
			ResultSet rs = stmt.executeQuery( sql );
			
			if( rs.next() )		returnValue = true;
			
			rs.close();
			stmt.close();
			conn.close();
			
		} catch( Exception e ) {
			
			e.printStackTrace();
			
			return false;
			
		}
		
		return returnValue;
		
	}
	
	/**
	 * 
	 * 본 프로그램의 계정이 생성되어있는지 판단하는 매서드
	 * 
	 * @return 계정이 존재하면 true, 그렇지 않으면 false
	 */
	public static final boolean isThereAccountInDB() {
		
		boolean returnValue = false;
		
		try {
			
			Connection conn = BilliardCounterConnector.getConnection();
			Statement stmt = conn.createStatement();
			String sql = "SELECT * "
					+ "FROM information_schema.tables  "
					+ "WHERE table_name = 'ACCOUNT';";
			
			ResultSet rs = stmt.executeQuery( sql );
			
			if( rs.next() )	returnValue =  true;
			
			rs.close();
			stmt.close();
			conn.close();
			
		} catch( Exception e ) {
			
			e.printStackTrace();
			
			return false;
			
		}
		
		return returnValue;
		
	}
	
}
