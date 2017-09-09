package com.kms.billiardcounter.dao.tableposition;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.kms.billiardcounter.dao.connection.BilliardCounterConnector;

/**
 * 
 * 당구 테이블의 인터페이스 위치 정보를 load하는 클래스
 * 
 * @author Kwon
 *
 */

public class TablePositionLoader {
	
	private TablePositionLoader(){}
	
	/**
	 * 위치 정보를 통해 당구 테이블의 번호를 반환하는 매서드
	 * @param tableRow 원하는 당구 테이블의 row
	 * @param tableCol 원하는 당구 테이블의 col
	 * @return tableNumber
	 */
	public static final int getTableNumberByRowAndCol( int tableRow, int tableCol ){
		
		int tableNumber = 0;
		
		try{
			
			String sql = "SELECT TABLE_NUMBER "
					+ "FROM BILLIARD_COUNTER.CREATED_TABLE "
					+ "WHERE TABLE_ROW = " + tableRow + " AND TABLE_COL = " + tableCol + ";";
			
			Connection conn = BilliardCounterConnector.getConnection();
			Statement stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery( sql );
			
			while(rs.next()){
				
				tableNumber = rs.getInt("TABLE_NUMBER");
				
			}
			
			rs.close();
			stmt.close();
			conn.close();
			
		}catch(Exception e){
		
			//e.printStackTrace();
		
		}
		
		return tableNumber;
	
	}
}
