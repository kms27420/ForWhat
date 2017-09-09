package com.kms.billiardcounter.dao.usingtable;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.kms.billiardcounter.dao.connection.BilliardCounterConnector;

/**
 * 
 * 데이터베이스 내의 USING_TABLE에 접근하여 원하는 형태의 정보를 load해주는 클래스
 * 
 * @author Kwon
 *
 */
public class UsingTableLoader {

	private UsingTableLoader() {}
	
	/**
	 * 
	 * 현재 입력받은 파라미터의 테이블에서 고객이 사용중인지를 판단하는 매서드
	 * 
	 * @param tableNumber 확인하고자하는 테이블의 번호
	 * @return 사용중이면 true, 그렇지 않으면 false
	 */
	public static final boolean isThisTableInUse( int tableNumber ) {
		
		try {
			
			Connection conn = BilliardCounterConnector.getConnection();
			Statement stmt = conn.createStatement();
			String sql = "SELECT * FROM billiard_counter.USING_TABLE WHERE TABLE_NUMBER = " + tableNumber + ";";
			
			ResultSet rs = stmt.executeQuery( sql );
			
			boolean returnValue = false;
			
			if( rs.next() )		returnValue = true;

			rs.close();
			stmt.close();
			conn.close();
			
			return returnValue;
			
		} catch( Exception e ) {
			
			//e.printStackTrace();
			
			return false;
			
		}
		
	}
	
	/**
	 * 
	 * 현재 사용중인 테이블의 개수를 반환하는 매서드
	 * 
	 * @return 현재 사용중인 테이블의 개수
	 */
	public static final int getCountOfUsingTable() {
		
		try {
			
			int countOfUsingTable = 0;
			
			Connection conn = BilliardCounterConnector.getConnection();
			Statement stmt = conn.createStatement();
			String sql = "SELECT COUNT(TABLE_NUMBER) AS COUNT_OF_USING_TABLE "
					+ "FROM billiard_counter.USING_TABLE;";
			
			ResultSet rs = stmt.executeQuery( sql );
			
			while( rs.next() ) {
				
				countOfUsingTable = rs.getInt( "COUNT_OF_USING_TABLE" );
				
			}
			
			rs.close();
			stmt.close();
			conn.close();
			
			return countOfUsingTable;
			
		} catch( Exception e ) {
			
			//e.printStackTrace();
			
			return 0;
			
		}
		
	}
	
}
