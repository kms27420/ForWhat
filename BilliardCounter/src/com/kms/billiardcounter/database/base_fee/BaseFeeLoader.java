package com.kms.billiardcounter.database.base_fee;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.kms.billiardcounter.database.connection.BilliardCounterConnector;
import com.kms.billiardcounter.support.BaseFeeInfo;

/**
 * 
 * 기본 요금 정보를 불러오는 클래스
 * 
 * @author Kwon
 *
 */
public class BaseFeeLoader {
	
	private BaseFeeLoader(){}
	
	public static final BaseFeeInfo loadBaseFeeInfo() {
		
		try{
			
			BaseFeeInfo baseFeeInfo = null;
			
			Connection conn = BilliardCounterConnector.getConnection();
			Statement stmt = conn.createStatement();
			String sql = "SELECT * "
					+ "FROM billiard_counter.BASE_FEE;";
			
			ResultSet rs = stmt.executeQuery( sql );
			
			if( rs.next() ) {
				
				baseFeeInfo = new BaseFeeInfo( rs.getInt( "BASE_FEE_PER_MINUTE" ), rs.getInt( "BASE_FEE_TIME" ), rs.getInt( "FEE_INCREASE_TIME" ) );
				
			} else {
				
				baseFeeInfo = new BaseFeeInfo();
				
			}
			
			rs.close();
			stmt.close();
			conn.close();
			
			return baseFeeInfo;
			
		} catch( Exception e ) {
			
			//e.printStackTrace();
			
			return new BaseFeeInfo();
			
		}
		
	}
	
}
