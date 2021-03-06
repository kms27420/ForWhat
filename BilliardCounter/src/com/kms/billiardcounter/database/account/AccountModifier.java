package com.kms.billiardcounter.database.account;

import com.kms.billiardcounter.database.connection.DatabaseConnector;

/**
 * 
 * 본 프로그램의 계정을 저장하는 데이터베이스의 ACCOUNT 테이블에 접근하여 내용을 변경할 수 있는 클래스
 * 
 * @author Kwon
 *
 */
public class AccountModifier {

	public static final int ID_MIN_LENGTH = 4;
	public static final int ID_MAX_LENGTH = 15;
	
	public static final int PASSWORD_MIN_LENGTH = 6;
	public static final int PASSWORD_MAX_LENGTH = 20;
	
	private AccountModifier() {}
	
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
			
			String sql = "INSERT INTO billiard_counter.ACCOUNT "
					+ "VALUES('" + id + "', '" + password + "');";
			
			DatabaseConnector.getStatement().executeUpdate( sql );
			
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
	 * @param password 변경해줄 password
	 * @return 작업이 정상적으로 처리되면 true, 그렇지 않으면 false
	 */
	public static final boolean changePassword( String password ) {
		
		try {
			
			String sql = "UPDATE billiard_counter.ACCOUNT "
					+ "SET PASSWORD = '" + password + "';";
			
			DatabaseConnector.getStatement().executeUpdate( sql );
			
			return true;
			
		} catch( Exception e ) {
			
			e.printStackTrace();
			
			return false;
			
		}
		
	}
	
}
