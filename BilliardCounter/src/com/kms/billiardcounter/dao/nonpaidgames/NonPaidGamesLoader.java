package com.kms.billiardcounter.dao.nonpaidgames;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JLabel;

import com.kms.billiardcounter.dao.connection.BilliardCounterConnector;
import com.kms.billiardcounter.font.FontProvider;
import com.kms.billiardcounter.support.GameFeeInfo;

/**
 * 
 * 계산되지 않은 게임의 정보를 필요한 형태로 가져오는 클래스
 * 
 * @author Kwon
 *
 */

public class NonPaidGamesLoader {

	private NonPaidGamesLoader(){}
	
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
	 * tableNumber에 해당하며 아직 계산되지 않은 게임 리스트의 game_number, used_time, fee를 출력하는 label의 리스트를 반환해주는 매서드
	 * 
	 * @param tableNumber 찾고자 하는 당구대의 번호
	 * @return ArrayList<JLabel> nonPaidGameFeeInfoLabelList, 게임 리스트의 정보를 출력하는 label의 리스트
	 */
	public static final ArrayList<JLabel> getNonPaidGameFeeInfoLabelList( int tableNumber ) {
		
		ArrayList<JLabel> nonPaidGameFeeInfoLabelList = new ArrayList<JLabel>();
		ArrayList<GameFeeInfo> nonPaidGameFeeInfoList = getNonPaidGameFeeInfoList( tableNumber );
		
		String usedTime;
		int gameNumber;
		
		for( int index = 0; index < nonPaidGameFeeInfoList.size(); index++ ) {
			
			usedTime = String.format( "%02d", nonPaidGameFeeInfoList.get(index).getUsedTime() / 60) + ":" + String.format( "%02d", nonPaidGameFeeInfoList.get(index).getUsedTime() % 60 );
			gameNumber = nonPaidGameFeeInfoList.get(index).getGameNumber();
			
			JLabel nonPaidGameFeeInfoLabel = new JLabel( gameNumber + " 게임 - 사용 시간 " + usedTime + ", 요금 " + nonPaidGameFeeInfoList.get(index).getFee() + "원");
			
			nonPaidGameFeeInfoLabel.setFont( FontProvider.getDefaultFont() );
			nonPaidGameFeeInfoLabel.setHorizontalAlignment( JLabel.CENTER );
			
			nonPaidGameFeeInfoLabelList.add( nonPaidGameFeeInfoLabel );
			
		}
		
		return nonPaidGameFeeInfoLabelList;
		
	}
	
	/**
	 * 
	 * tableNumber에 해당하는 게임 중 계산되지 않은 게임들의 총 사용시간과 총 요금을 String[]형으로 반환해주는 매서드
	 * 
	 * @param tableNumber 검색하고자하는 table의 번호
	 * @return totalUsedTimeAndFee[0] 총 사용 시간, totalUsedTimeAndFee[1] 총 요금
	 */
	public static final String[] getTotalUsedTimeAndFeeOfNonPaidGames( int tableNumber ){
		
		String[] totalUsedTimeAndFee = null;
		
		try{
			
			Connection conn = BilliardCounterConnector.getConnection();
			Statement stmt = conn.createStatement();
			
			String sql = "SELECT * "
					+ "FROM billiard_counter.GAME_LIST "
					+ "WHERE TABLE_NUMBER = " + tableNumber + " AND IS_PAID = FALSE;";
			
			ResultSet rs = stmt.executeQuery( sql );
			
			if( !rs.next() )	return null;
			
			sql = "SELECT SUM(USED_TIME) AS TOTAL_USED_TIME, SUM(FEE) AS TOTAL_FEE "
					+ "FROM billiard_counter.GAME_LIST "
					+ "WHERE TABLE_NUMBER = " + tableNumber + " AND IS_PAID = FALSE;";
			
			rs = stmt.executeQuery(sql);
			
			while( rs.next() ) {
				
				totalUsedTimeAndFee = new String[2];
				
				String totalUsedTime = String.format( "%02d", rs.getInt( "TOTAL_USED_TIME" ) / 60 ) + ":" + String.format( "%02d", rs.getInt( "TOTAL_USED_TIME" ) % 60 );
				String totalFee = Integer.toString( rs.getInt( "TOTAL_FEE" ) );
				
				totalUsedTimeAndFee[0] = totalUsedTime;
				totalUsedTimeAndFee[1] = totalFee;
				
			}
			
			rs.close();
			stmt.close();
			conn.close();
			
		}catch(Exception e){
			
			// e.printStackTrace();
			
		}
		
		return totalUsedTimeAndFee;
		
	}
	
}
