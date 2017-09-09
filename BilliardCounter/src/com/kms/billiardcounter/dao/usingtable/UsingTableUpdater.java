package com.kms.billiardcounter.dao.usingtable;

import java.sql.Connection;
import java.sql.Statement;

import com.kms.billiardcounter.dao.connection.BilliardCounterConnector;

/**
 * 
 * 고객이 현재 사용중인 당구대를 데이터베이스에 저장 또는 업데이트하는 클래스
 * 
 * @author Kwon
 *
 */
public class UsingTableUpdater {

	private UsingTableUpdater() {}
	
	private static final void createTableIfNotExists( Connection conn, Statement stmt ) throws Exception{
		
		stmt.execute(
				"CREATE TABLE IF NOT EXISTS billiard_counter.USING_TABLE("
				+ "TABLE_NUMBER INT(11) PRIMARY KEY NOT NULL);"
				);
		
	}
	
	/**
	 * 
	 * 현재 사용중인 테이블을 데이터베이스에 저장하는 매서드
	 * 
	 * @param tableNumber 사용하는 테이블
	 * @return 작업이 정상적으로 종료되면 true, 그렇지 않으면 false
	 */
	public static final boolean saveUsingTable( int tableNumber ) {
		
		try {
			
			Connection conn = BilliardCounterConnector.getConnection();
			Statement stmt = conn.createStatement();
			String sql = "INSERT INTO billiard_counter.USING_TABLE "
					+ "VALUES(" + tableNumber + ");";
			
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
	 * 고객이 계산을 마치고 사용을 종료한 테이블을 데이터베이스에서 삭제하는 매서드
	 * 
	 * @param tableNumber 종료한 테이블 번호
	 * @return 작업이 정상적으로 종료되면 true, 그렇지 않으면 false
	 */
	public static final boolean deleteUsingTable( int tableNumber ) {
		
		try {
			
			Connection conn = BilliardCounterConnector.getConnection();
			Statement stmt = conn.createStatement();
			String sql = "DELETE FROM billiard_counter.USING_TABLE "
					+ "WHERE TABLE_NUMBER = " + tableNumber + ";";
			
			stmt.executeUpdate( sql );
			
			stmt.close();
			conn.close();
			
			return true;
			
		} catch( Exception e ) {
			
			//e.printStackTrace();
			
			return false;
			
		}
		
	}
	
}
