package com.kms.billiardcounter.database.base_fee;

import java.sql.Connection;
import java.sql.Statement;

import com.kms.billiardcounter.database.connection.BilliardCounterConnector;
import com.kms.billiardcounter.support.BaseFeeInfo;

/**
 * 
 * 기본 요금 정보에 관한 것을 update하거나 save하는 클래스
 * 
 * @author Kwon
 *
 */
public class BaseFeeModifier {

	private BaseFeeModifier() {}
	
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
	public static final boolean saveBaseFeeInfoToDB( BaseFeeInfo baseFeeInfo ) {
		
		try {
			
			if( !baseFeeInfo.getIsValid() ) {
				
				return false;
				
			}
			
			Connection conn = BilliardCounterConnector.getConnection();
			Statement stmt = conn.createStatement();
			String sql = "INSERT INTO billiard_counter.BASE_FEE "
					+ "VALUES(" + baseFeeInfo.getBaseFeePerMinute() + ", " + baseFeeInfo.getBaseFeeTime() + ", " + baseFeeInfo.getFeeIncreaseTime() + ");";
			
			createTableIfNotExists( conn, stmt );
			
			stmt.executeUpdate( sql );
			
			stmt.close();
			conn.close();
			
			return true;
			
		} catch( Exception e ) {
			
			// e.printStackTrace();
			
			return false;
			
		}
		
	}
	
	/**
	 * 
	 * Database내의 기본 요금 정보를 변경하는 매서드
	 * 
	 * @param baseFeeInfo 새로 바꿀 기본 요금 정보
	 * @return update작업이 정상적으로 처리되면 true, 그렇지 않으면 false
	 */
	public static final boolean changeBaseFeeInfo( BaseFeeInfo baseFeeInfo ) {
		
		try {
			
			if( !baseFeeInfo.getIsValid() ) {
				
				return false;
				
			}
			
			Connection conn = BilliardCounterConnector.getConnection();
			Statement stmt = conn.createStatement();
			String sql = "DELETE FROM billiard_counter.BASE_FEE;";
			
			stmt.executeUpdate( sql );
			
			sql = "INSERT INTO billiard_counter.BASE_FEE "
					+ "VALUES(" + baseFeeInfo.getBaseFeePerMinute() + ", " + baseFeeInfo.getBaseFeeTime() + ", " + baseFeeInfo.getFeeIncreaseTime() + ");";
			
			stmt.executeUpdate( sql );
			
			stmt.close();
			conn.close();
			
			return true;
			
		} catch( Exception e ) {
			
			// e.printStackTrace();
			
			return false;
			
		}
		
	}
	
}
