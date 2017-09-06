package com.kms.billiardcounter.dao.basefee;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.kms.billiardcounter.dao.connection.BilliardCounterConnector;

/**
 * 
 * 기본 요금 정보를 불러오는 클래스
 * 
 * @author Kwon
 *
 */
public class BaseFeeLoader {
	
	private static int[] baseFeeInfo = getBaseFeeInfo();
	
	private BaseFeeLoader(){}
	
	private static final int[] getBaseFeeInfo() {
		
		int[] baseFeeInfo = new int[3];
		
		try{
			
			Connection conn = BilliardCounterConnector.getConnection();
			Statement stmt = conn.createStatement();
			String sql = "SELECT * "
					+ "FROM billiard_counter.BASE_FEE;";
			
			ResultSet rs = stmt.executeQuery( sql );
			
			while( rs.next() ) {
				
				baseFeeInfo[0] = rs.getInt( "BASE_FEE_PER_MINUTE" );
				baseFeeInfo[1] = rs.getInt( "BASE_FEE_TIME" );
				baseFeeInfo[2] = rs.getInt( "FEE_INCREASE_TIME" );
				
				if( baseFeeInfo[0] <= 0 || baseFeeInfo[1] <= 0 || baseFeeInfo[2] <= 1 ) {
					
					baseFeeInfo = null;
					
				}
				
			}
			
			rs.close();
			stmt.close();
			conn.close();
			
		} catch( Exception e ) {
			
			//e.printStackTrace();
			
			return null;
			
		}
		
		return baseFeeInfo;
		
	}
	
	/**
	 * 
	 * 본 클래스의 baseFeeInfo를 reload해주는 매서드
	 * 
	 */
	public static final void reloadBaseFeeInfo() {
		
		baseFeeInfo = getBaseFeeInfo();
		
	}

	/**
	 * 
	 * 본 클래스가 초기화 되었는지를 확인해주는 매서드
	 * 
	 * @return 초기화 되었으면 true, 아니면 false
	 */
	public static final boolean isBaseFeeInited() {
		
		return baseFeeInfo == null ? false : true;
		
	}
	
	/**
	 * 
	 * 기본 요금 정보 중 baseFeePerMinute를 반환하는 매서드
	 * 
	 * @return baseFeePerMinute
	 */
	public static final int getBaseFeePerMinute() {
		
		return baseFeeInfo[0];
		
	}
	
	/**
	 * 
	 * 기본 요금 정보 중 baseFeeTime을 반환하는 매서드
	 * 
	 * @return baseFeeTime
	 */
	public static final int getBaseFeeTime() {
		
		return baseFeeInfo[1];
		
	}
	
	/**
	 * 
	 * 기본 요금 정보 중 feeIncreaseTime을 반환하는 매서드
	 * 
	 * @return feeIncreaseTime
	 */
	public static final int getFeeIncreaseTime() {
		
		return baseFeeInfo[2];
		
	}
	
}
