package com.kms.billiardcounter.dao.tableposition;

import java.sql.Connection;
import java.sql.Statement;

import com.kms.billiardcounter.dao.connection.BilliardCounterConnector;

/**
 * 
 * 당구 테이블의 인터페이스 위치 정보를 DB에 저장해주는 클래스
 * 
 * @author Kwon
 *
 */
public class TablePositionUpdater {
	
	private TablePositionUpdater(){}
	
	private static final void createTableIfNotExists( Connection conn, Statement stmt ) throws Exception{
		
		stmt.execute(
				"CREATE TABLE IF NOT EXISTS billiard_counter.CREATED_TABLE("
				+ "TABLE_NUMBER INT(11) PRIMARY KEY NOT NULL,"
				+ "TABLE_ROW INT(11) NOT NULL,"
				+ "TABLE_COL INT(11) NOT NULL);"
				);
		
	}
	
	/**
	 * 
	 * 당구 테이블들의 위치를 데이터베이스에 저장해주는 매서드
	 * 
	 * @param tableNumber 당구대 번호
	 * @param row 당구대의 row위치
	 * @param col 당구대의 col위치
	 * @return 저장 작업이 정상적으로 수행되면 true, 그렇지 않으면 false
	 */
	public static final boolean saveTablePosition( int tableNumber, int row, int col ){
		
		try{
			
			Connection conn = BilliardCounterConnector.getConnection();
			Statement stmt = conn.createStatement();
			String sql;
			
			createTableIfNotExists( conn, stmt );
			
			sql = "INSERT INTO billiard_counter.CREATED_TABLE VALUES(" + tableNumber + ", " + row + ", " + col + ");";
			stmt.executeUpdate(sql);
			
			stmt.close();
			conn.close();
			
			return true;
			
		}catch(Exception e){
			
			//e.printStackTrace();
			
			return false;
			
		}
		
	}
	
	/**
	 * 
	 * 당구 테이블의 위치를 데이터베이스에서 삭제하는 매서드
	 * 
	 * @param tableNumber 삭제하고자하는 당구대의 번호
	 * @return 삭제 작업이 정상적으로 처리되면 true, 그렇지 않으면 false
	 */
	public static final boolean deleteTablePosition( int tableNumber ) {
		
		try {
			
			Connection conn = BilliardCounterConnector.getConnection();
			Statement stmt = conn.createStatement();
			String sql = "DELETE FROM billiard_counter.CREATED_TABLE "
						+ "WHERE TABLE_NUMBER = " + tableNumber + ";";
			
			stmt.executeUpdate(sql);
			
			stmt.close();
			conn.close();
			
			return true;
			
		} catch( Exception e ) {
			
			e.printStackTrace();
			
			return false;
			
		}
		
	}
	
}
