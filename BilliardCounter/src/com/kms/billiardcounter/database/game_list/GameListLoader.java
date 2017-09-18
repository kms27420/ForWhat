package com.kms.billiardcounter.database.game_list;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.kms.billiardcounter.database.connection.BilliardCounterConnector;
import com.kms.billiardcounter.support.SearchKey;
import com.kms.billiardcounter.support.TotalFeeInfo;
import com.kms.billiardcounter.support.gamefeeinfo.GameFeeInfo;

/**
 * 
 * 데이터베이스 GAME_LIST의 정보를 원하는 형태로 불러오는 클래스
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
	public static final ArrayList<GameFeeInfo> getNonPaidGameFeeInfoList( int tableNumber ){
		
		ArrayList<GameFeeInfo> nonPaidGameFeeInfoList = new ArrayList<GameFeeInfo>();
		
		try{
			
			String sql = "SELECT * "
					+ "FROM billiard_counter.GAME_LIST "
					+ "WHERE TABLE_NUMBER = " + tableNumber + " AND IS_PAID = FALSE;";
			Connection conn = BilliardCounterConnector.getConnection();
			Statement stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery(sql);
			
			while( rs.next() ){
				
				GameFeeInfo tmpInfo = new GameFeeInfo();
				
				tmpInfo.setDate( rs.getString( "DATE" ) );
				tmpInfo.setStartTime( rs.getString( "START_TIME" ) );
				tmpInfo.setEndTime( rs.getString( "END_TIME" ) );
				tmpInfo.setGameNumber( rs.getInt( "GAME_NUMBER" ) );
				tmpInfo.setTableNumber( rs.getInt( "TABLE_NUMBER" ) );
				tmpInfo.setUsedTime( rs.getInt( "USED_TIME" ) );
				tmpInfo.setFee( rs.getInt( "FEE" ) );
				tmpInfo.setIsPaid( rs.getBoolean( "IS_PAID" ) );
				
				nonPaidGameFeeInfoList.add( tmpInfo );
				
			}	
			
			rs.close();
			stmt.close();
			conn.close();
			
		}catch(Exception e){ 
			
			//e.printStackTrace(); 
			
		}
		
		return nonPaidGameFeeInfoList;
		
	}
	
	/**
	 * 
	 * tableNumber에 해당하는 게임 중 계산되지 않은 게임들의 총 사용시간과 총 요금을 TotalFeeInfo형으로 반환해주는 매서드
	 * 
	 * @param tableNumber 검색하고자하는 table의 번호
	 * @return 해당 당구대의 계산되지 않은 게임들의 TotalFeeInfo
	 */
	public static final TotalFeeInfo getNonPaidGamesTotalFeeInfo ( int tableNumber ){
		
		try{
			
			TotalFeeInfo totalFeeInfo;
			
			Connection conn = BilliardCounterConnector.getConnection();
			Statement stmt = conn.createStatement();
			
			String sql = "SELECT * "
					+ "FROM billiard_counter.GAME_LIST "
					+ "WHERE TABLE_NUMBER = " + tableNumber + " AND IS_PAID = FALSE;";
			
			ResultSet rs = stmt.executeQuery( sql );
			
			if( !rs.next() )	return new TotalFeeInfo();
			
			sql = "SELECT SUM(USED_TIME) AS TOTAL_USED_TIME, SUM(FEE) AS TOTAL_FEE "
					+ "FROM billiard_counter.GAME_LIST "
					+ "WHERE TABLE_NUMBER = " + tableNumber + " AND IS_PAID = FALSE;";
			
			rs = stmt.executeQuery(sql);
			
			if( rs.next() ) {
				
				String totalUsedTime = String.format( "%02d", rs.getInt( "TOTAL_USED_TIME" ) / 60 ) + ":" + String.format( "%02d", rs.getInt( "TOTAL_USED_TIME" ) % 60 );
				String totalFee = Integer.toString( rs.getInt( "TOTAL_FEE" ) );
				
				totalFeeInfo = new TotalFeeInfo( totalUsedTime, totalFee );
				
			} else {
				
				totalFeeInfo = new TotalFeeInfo();
				
			}
			
			rs.close();
			stmt.close();
			conn.close();
			
			return totalFeeInfo;
			
		} catch(Exception e){
			
			// e.printStackTrace();
			
			return new TotalFeeInfo();
			
		}
		
	}
	
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
	
	/**
	 * 
	 * 검색된 게임들의 정보들을 ArrayList<GameFeeInfo> 형태로 반환해주는 매서드
	 * 
	 * @param searchKey 검색할 내용
	 * @return 검색된 게임들의 ArrayList<GameFeeInfo> 형태
	 */
	public static final ArrayList<GameFeeInfo> getSearchedGameFeeInfoList ( SearchKey searchKey ) {
	
		ArrayList<GameFeeInfo> gameFeeInfoList = new ArrayList<GameFeeInfo>();
		
		if( !searchKey.getIsValid() )	return gameFeeInfoList;
		
		try{
			
			Connection conn = BilliardCounterConnector.getConnection();
			Statement stmt = conn.createStatement();
			
			String sql = "SELECT * "
					+ "FROM billiard_counter.GAME_LIST "
					+ "WHERE DATE = '" + searchKey.getDate() + "' AND TABLE_NUMBER = " + searchKey.getTableNumber() + ";";
			
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
			
			 e.printStackTrace();
			
		}
		
		return gameFeeInfoList;
		
	}
	
}
