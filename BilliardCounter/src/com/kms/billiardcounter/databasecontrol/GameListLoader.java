package com.kms.billiardcounter.databasecontrol;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.kms.billiardcounter.controllabledata.GameFeeInfo;

/**
 * 
 * GAME_LIST 테이블에 저장되어있는 내용을 로드시켜주는 클래스
 * 
 * @author Kwon
 *
 */

public class GameListLoader {

	private GameListLoader(){}
	
	/**
	 * 
	 * tableNumber에 해당하며 아직 계산되지 않은 GameList들을 GameFeeInfo 리스트로 반환하는 매서드
	 * 
	 * @param tableNumber 찾고자하는 GameList의 당구대 번호
	 * @return ArrayList<GameFeeInfo> gameList
	 */
	public static ArrayList<GameFeeInfo> getNonPaidGameList( int tableNumber ){
		
		ArrayList<GameFeeInfo> gameList = new ArrayList<GameFeeInfo>();
		
		try{
			
			String sql = "SELECT * FROM billiard_counter.GAME_LIST WHERE TABLE_NUM = " + tableNumber + " AND IS_PAID = FALSE;";
			Connection conn = BilliardCounterConnector.getConnection();
			Statement stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery(sql);
			
			while( rs.next() ){
				
				GameFeeInfo tmpInfo = new GameFeeInfo();
				
				tmpInfo.setDate( rs.getString( "DATE" ) );
				tmpInfo.setStartTime( rs.getString( "START_TIME" ) );
				tmpInfo.setEndTime( rs.getString( "END_TIME" ) );
				tmpInfo.setGameNum( rs.getInt( "GAME_NUM" ) );
				tmpInfo.setTableNum( rs.getInt( "TABLE_NUM" ) );
				tmpInfo.setUsedTime( rs.getInt( "USED_TIME" ) );
				tmpInfo.setFee( rs.getInt( "FEE" ) );
				tmpInfo.setIsPaid( rs.getBoolean( "IS_PAID" ) );
				
				gameList.add( tmpInfo );
				
			}	
			
		}catch(Exception e){ 
			
			//e.printStackTrace(); 
			
		}
		
		return gameList;
		
	}
	
	/**
	 * 
	 * tableNumber에 해당하는 아직 계산되지 않은 게임 리스트들의 총 사용 시간과 총 요금을 문자열 리스트로 반환해주는 매서드
	 * 
	 * @param tableNumber 찾고자 하는 요금 정보의 당구대 번호
	 * @return ArrayList<String> totalFeeInfoList
	 */
	public static ArrayList<String> getTotalFeeInfosStringList( int tableNumber ){
		
		ArrayList<String> totalFeeInfoList = new ArrayList<String>();
		
		try{
			
			Connection conn = BilliardCounterConnector.getConnection();
			Statement stmt = conn.createStatement();
			
			String sql = "SELECT * "
					+ "FROM billiard_counter.GAME_LIST "
					+ "WHERE TABLE_NUM = " + tableNumber + " AND IS_PAID = FALSE;";
			
			ResultSet rs = stmt.executeQuery( sql );
			
			if( !rs.next() )	throw new Exception();
			
			sql = "SELECT SUM(USED_TIME) AS TOTAL_USED_TIME, SUM(FEE) AS TOTAL_FEE "
					+ "FROM billiard_counter.GAME_LIST "
					+ "WHERE TABLE_NUM = " + tableNumber + " AND IS_PAID = FALSE;";
			
			rs = stmt.executeQuery(sql);
			
			while( rs.next() ){
				
				String totalUsedTime = String.format( "%02d", rs.getInt( "TOTAL_USED_TIME" ) / 60 ) + ":" + String.format( "%02d", rs.getInt( "TOTAL_USED_TIME" ) % 60 );
				String totalFee = Integer.toString( rs.getInt( "TOTAL_FEE" ) );
				
				totalFeeInfoList.add( totalUsedTime );
				totalFeeInfoList.add( totalFee );
				
			}
			
			rs.close();
			stmt.close();
			conn.close();
			
		}catch(Exception e){
			
			// e.printStackTrace();
			
		}
		
		return totalFeeInfoList;
		
	}
	
}
