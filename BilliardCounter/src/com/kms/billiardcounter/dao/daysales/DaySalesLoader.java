package com.kms.billiardcounter.dao.daysales;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.kms.billiardcounter.dao.connection.BilliardCounterConnector;

/**
 * 
 * 해당 날의 매출을 구해주는 클래스
 * 
 * @author Kwon
 *
 */
public class DaySalesLoader {

	private DaySalesLoader() {}
	
	/**
	 * 
	 * 해당 날짜의 매출을 구해주는 매서드
	 * 
	 * @param date 구하고자 하는 날짜
	 * @return date에 해당하는 날짜의 매출액
	 */
	public static final int getDaySales( String date ) {
		
		int daySales = 0;
		
		try {
			
			Connection conn = BilliardCounterConnector.getConnection();
			Statement stmt = conn.createStatement();
			String sql = "SELECT SUM(FEE) AS TOTAL_SALES "
					+ "FROM billiard_counter.GAME_LIST "
					+ "WHERE DATE = '" + date + "' AND IS_PAID = 1;";
			
			ResultSet rs = stmt.executeQuery( sql );
			
			while( rs.next() ){
				
				daySales = rs.getInt( "TOTAL_SALES" );
				
			}
			
			rs.close();
			stmt.close();
			conn.close();
			
		} catch( Exception e ) {
			
			 //e.printStackTrace();
			
		}
		
		return daySales;
		
	}
	
}
