package com.kms.billiardcounter.database.game_list;

import com.kms.billiardcounter.database.connection.DatabaseConnector;
import com.kms.billiardcounter.support.GameFeeInfo;

/**
 * 
 * GAME_LIST 테이블의 내용을 추가하거나 업데이트해주는 클래스
 * 
 * @author Kwon
 *
 */
public class GameListModifier {
	
	private GameListModifier(){}
	
	/**
	 * 
	 * GameFeeInfo를 본 프로그램의 DB에 저장하는 매서드
	 * 
	 * @param gameFeeInfo 저장하고자 하는 GameFeeInfo
	 * @return 저장 작업이 제대로 실행되면 true, 그렇지 않으면 false를 return
	 */
	public static final boolean saveGameFeeInfoToDB( GameFeeInfo gameFeeInfo ){
		
		try{
			
			String date, startTime, endTime;
			int gameNumber, tableNumber, usedTime, fee;
			boolean isPaid;
			
			date = gameFeeInfo.getDate();
			startTime = gameFeeInfo.getStartTime();
			endTime = gameFeeInfo.getEndTime();
			gameNumber = gameFeeInfo.getGameNumber();
			tableNumber = gameFeeInfo.getTableNumber();
			usedTime = gameFeeInfo.getUsedTime();
			fee = gameFeeInfo.getFee();
			isPaid = gameFeeInfo.getIsPaid();
				
			String sql = "INSERT INTO billiard_counter.GAME_LIST "
					+ "VALUES('" + date + "', '" + startTime + "', '" + endTime + "', " 
			+ gameNumber + ", " + tableNumber + ", " + usedTime + ", " + fee + ", " + isPaid + ");";
				
			DatabaseConnector.getStatement().executeUpdate(sql);
			
			return true;
			
		}catch(Exception e){
			
			e.printStackTrace();
		
			return false;
			
		}
	
	}
	
	/**
	 * 
	 * 게임비 계산 여부를 확인하는 IS_PAID를 true로 변경해주는 매서드
	 * 
	 * @param tableNumber 변경하고자하는(계산이 완료된) 당구대의 번호
	 * @return update가 제대로 실행되면 true, 그렇지 않으면 false
	 */
	public static final boolean updateIsPaidToTrue( int tableNumber, int gameNumber ){
		
		try{
			
			String sql = "UPDATE billiard_counter.GAME_LIST "
					+ "SET IS_PAID = TRUE "
					+ "WHERE TABLE_NUMBER = " + tableNumber + " AND GAME_NUMBER = " + gameNumber + ";";
			
			DatabaseConnector.getStatement().executeUpdate( sql );
			
			return true;
			
		}catch(Exception e){
			
			e.printStackTrace();
			
			return false;
			
		}
		
	}
	
	/**
	 * 
	 * 계산을 아직 하지 않은 게임의 정보 중 table_number를 변경해주는 매서드
	 * 
	 * @param activatedTableNumber 변경하고자 하는 table의 번호
	 * @param confirmTableNumber 이 번호로 table_number을 변경함
	 * @return update가 제대로 실행되면 true, 그렇지 않으면 false
	 */
	public static final boolean replaceGameMonitorData( int activatedTableNumber, int confirmTableNumber ) {
		
		try {
			
			String sql = "UPDATE billiard_counter.GAME_LIST "
					+ "SET TABLE_NUMBER = " + confirmTableNumber
					+ " WHERE TABLE_NUMBER = " + activatedTableNumber + " AND IS_PAID = FALSE;";
			
			DatabaseConnector.getStatement().executeUpdate( sql );
			
			return true;
			
		} catch( Exception e ) {
			
			e.printStackTrace();
			
			return false;
			
		}
		
	}
	
	/**
	 * 
	 * 요금을 계산할 때 사용자가 본래의 요금이 아닌 사용자가 지정한 요금으로 변경할 때 이를 데이터베이스에 반영시키는 매서드
	 * 
	 * @param gameFeeInfo 변경할 게임의 정보
	 * @param changedFee 변경할 요금
	 * @return update 작업이 정상적으로 처리되면 true, 그렇지 않으면 false
	 */
	public static final boolean reflectTheChangedFee( GameFeeInfo gameFeeInfo, int changedFee ) {
		
		try {
			
			String sql = "UPDATE billiard_counter.GAME_LIST "
					+ "SET FEE = " + changedFee
					+ " WHERE DATE = '" + gameFeeInfo.getDate() + "' AND START_TIME = '" + gameFeeInfo.getStartTime() 
					+ "' AND TABLE_NUMBER = " + gameFeeInfo.getTableNumber() + " AND GAME_NUMBER = " + gameFeeInfo.getGameNumber() + ";";
			
			DatabaseConnector.getStatement().executeUpdate( sql );
			
			return true;
			
		} catch( Exception e ) {
			
			e.printStackTrace();
			
			return false;
			
		}
		
	}
	
}
