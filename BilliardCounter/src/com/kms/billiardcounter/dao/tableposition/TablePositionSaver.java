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
public class TablePositionSaver {
	
	private TablePositionSaver(){}
	
	private static final void createTableIfNotExists( Connection conn, Statement stmt ) throws Exception{
		
		stmt.execute(
				"CREATE TABLE IF NOT EXISTS billiard_counter.CREATED_TABLE("
				+ "TABLE_NUMBER INT(11) PRIMARY KEY NOT NULL,"
				+ "TABLE_ROW INT(11) NOT NULL,"
				+ "TABLE_COL INT(11) NOT NULL);"
				);
		
	}
	
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
	
}
