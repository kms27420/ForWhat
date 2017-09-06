package com.kms.billiardcounter.dao.basefee;

import java.sql.Connection;
import java.sql.Statement;

import com.kms.billiardcounter.dao.connection.BilliardCounterConnector;

/**
 * 
 * 기본 요금 정보에 관한 것을 update하거나 save하는 클래스
 * 
 * @author Kwon
 *
 */
public class BaseFeeTableUpdater {

	private static void createTableIfNotExists( Connection conn, Statement stmt ) throws Exception{
		
		stmt.execute(
				"CREATE TABLE IF NOT EXISTS billiard_counter.BASE_FEE("
				+ "BASE_FEE_PER_MINUTE INT(4) NOT NULL,"
				+ "BASE_FEE_TIME INT(3) NOT NULL,"
				+ "FEE_INCREASE_TIME INT(3) NOT NULL,"
				+ "PRIMARY KEY(BASE_FEE_PER_MINUTE, BASE_FEE_TIME, FEE_INCREASE_TIME));"
				);
		
	}
	
	/**
	 * 
	 * 기본 요금 정보를 DB에 update해주는 매서드
	 * 
	 * @param baseFeePerMinute base_fee_per_minute에 대입할 값
	 * @param baseFeeTime base_fee_time에 대입할 값
	 * @param feeIncreaseTime fee_increase_time에 대입할 값
	 * @return update가 제대로 실행되면 true, 그렇지 않으면 false
	 */
	public static final boolean updateBaseFeeInfoToDB( int baseFeePerMinute, int baseFeeTime, int feeIncreaseTime ) {
		
		try {
			
			if( baseFeePerMinute <= 0 || baseFeeTime <= 0 || feeIncreaseTime <= 1 ) {
				
				return false;
				
			}
			
			Connection conn = BilliardCounterConnector.getConnection();
			Statement stmt = conn.createStatement();
			String sql;
			
			createTableIfNotExists( conn, stmt );
			
			if( BaseFeeLoader.isBaseFeeInited() ) {
				
				sql = "DELETE FROM billiard_counter.BASE_FEE;";
				
				stmt.executeUpdate( sql );
				
			}
			
			sql = "INSERT INTO billiard_counter.BASE_FEE "
					+ "VALUES(" + baseFeePerMinute + ", " + baseFeeTime + ", " + feeIncreaseTime + ");";
			
			stmt.executeUpdate( sql );
			
			BaseFeeLoader.reloadBaseFeeInfo();
			
			stmt.close();
			conn.close();
			
			return true;
			
		} catch( Exception e ) {
			
			// e.printStackTrace();
			
			return false;
			
		}
		
	}
	
}
