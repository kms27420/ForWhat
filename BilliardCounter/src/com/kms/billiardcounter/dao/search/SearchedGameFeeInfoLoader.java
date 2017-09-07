package com.kms.billiardcounter.dao.search;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.kms.billiardcounter.dao.connection.BilliardCounterConnector;
import com.kms.billiardcounter.support.GameFeeInfo;

/**
 * 
 * 검색한 game의 정보를 받아오는 클래스
 * 
 * @author Kwon
 *
 */
public class SearchedGameFeeInfoLoader {
	
	private SearchedGameFeeInfoLoader() {}
	
	/**
	 * 
	 * date와 tableNumber로 검색한 게임들의 정보를 ArrayList<GameFeeInfo>형태로 반환해주는 매서드
	 * 
	 * @param date 검색하고자 하는 날짜
	 * @param tableNumber 검색하고자 하는 테이블 번호
	 * @return ArrayList<GameFeeInfo>
	 */
	public static final ArrayList<GameFeeInfo> getSearchedByDateAndTableNumberGameFeeInfoList ( String date, String tableNumber ) {
	
		ArrayList<GameFeeInfo> gameFeeInfoList = new ArrayList<GameFeeInfo>();
		
		if( date == null || tableNumber == null)	return gameFeeInfoList;
		
		try{
			
			Connection conn = BilliardCounterConnector.getConnection();
			Statement stmt = conn.createStatement();
			
			String sql = "SELECT * "
					+ "FROM billiard_counter.GAME_LIST "
					+ "WHERE DATE = " + date + " AND TABLE_NUMBER = " + tableNumber + ";";
			
			ResultSet rs = stmt.executeQuery( sql );
			
			while( rs.next() ){
				
				GameFeeInfo gameFeeInfo = new GameFeeInfo();
				
				gameFeeInfo.setDate( rs.getString( "DATE" ) );
				gameFeeInfo.setStartTime( rs.getString( "START_TIME" ) );
				gameFeeInfo.setEndTime( rs.getString( "END_TIME" ) );
				gameFeeInfo.setGameNumber( rs.getInt( "GAME_NUMBER" ) );
				gameFeeInfo.setTableNumber( rs.getInt( "TABLE_NUMBER" ) );
				gameFeeInfo.setUsedTime( rs.getInt( "USED_TIME" ) );
				gameFeeInfo.setFee( rs.getInt( "FEE" ) );
				gameFeeInfo.setIsPaid( rs.getBoolean( "IS_PAID" ) );
				
				gameFeeInfoList.add( gameFeeInfo );
				
			}
			
			rs.close();
			stmt.close();
			conn.close();
			
		}catch(Exception e){
			
			// e.printStackTrace();
			
		}
		
		return gameFeeInfoList;
		
	}
	
}
