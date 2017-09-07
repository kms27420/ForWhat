package com.kms.billiardcounter.dao.gamelist;

import java.sql.Connection;
import java.sql.Statement;

import com.kms.billiardcounter.dao.connection.BilliardCounterConnector;
import com.kms.billiardcounter.support.GameFeeInfo;

/**
 * 
 * GAME_LIST 테이블의 내용을 추가하거나 업데이트해주는 클래스
 * 
 * @author Kwon
 *
 */
public class GameListTableUpdater {
	
	private GameListTableUpdater(){}
	
	private static void createTableIfNotExists( Connection conn, Statement stmt ) throws Exception{
		
		stmt.execute(
				"CREATE TABLE IF NOT EXISTS billiard_counter.GAME_LIST("
				+ "DATE VARCHAR(6) NOT NULL,"
				+ "START_TIME VARCHAR(8) NOT NULL,"
				+ "END_TIME VARCHAR(8) NOT NULL,"
				+ "GAME_NUMBER INT(11) NOT NULL,"
				+ "TABLE_NUMBER INT(11) NOT NULL,"
				+ "USED_TIME INT(11) NOT NULL,"
				+ "FEE INT(11) NOT NULL,"
				+ "IS_PAID BOOLEAN NOT NULL,"
				+ "PRIMARY KEY(DATE, START_TIME, GAME_NUMBER, TABLE_NUMBER));"
				);
		
	}
	
	/**
	 * 
	 * GameFeeInfo를 본 프로그램의 DB에 저장하는 매서드
	 * 
	 * @param gameFeeInfo 저장하고자 하는 GameFeeInfo
	 * @return 저장 작업이 제대로 실행되면 true, 그렇지 않으면 false를 return
	 */
	public static final boolean saveGameFeeInfoToDB( GameFeeInfo gameFeeInfo ){
		
		try{
			
			Connection conn = BilliardCounterConnector.getConnection();
			Statement stmt = conn.createStatement();
			String sql;
			
			createTableIfNotExists( conn, stmt );
			
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
				
			sql = "INSERT INTO billiard_counter.GAME_LIST "
					+ "VALUES('" + date + "', '" + startTime + "', '" + endTime + "', " 
			+ gameNumber + ", " + tableNumber + ", " + usedTime + ", " + fee + ", " + isPaid + ");";
				
			stmt.executeUpdate(sql);
			
			stmt.close();
			conn.close();
			
			return true;
			
		}catch(Exception e){
			
			//e.printStackTrace();
		
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
	public static final boolean updateIsPaidToTrue( int tableNumber ){
		
		try{
			
			Connection conn = BilliardCounterConnector.getConnection();
			Statement stmt = conn.createStatement();
			String sql = "UPDATE billiard_counter.GAME_LIST "
					+ "SET IS_PAID = TRUE "
					+ "WHERE TABLE_NUMBER = " + tableNumber + ";";
			
			stmt.executeUpdate( sql );
			
			stmt.close();
			conn.close();
			
			return true;
			
		}catch(Exception e){
			
			//e.printStackTrace();
			
			return false;
			
		}
		
	}
	
	/**
	 * 
	 * 계산을 아직 하지 않은 게임의 정보 중 table_number를 변경해주는 매서드
	 * 
	 * @param beforeTableNumber 변경하고자 하는 table의 번호
	 * @param afterTableNumber 이 번호로 table_number을 변경함
	 * @return update가 제대로 실행되면 true, 그렇지 않으면 false
	 */
	public static final boolean updateNonPaidGamesTableNumber( int beforeTableNumber, int afterTableNumber ) {
		
		try {
			
			Connection conn = BilliardCounterConnector.getConnection();
			Statement stmt = conn.createStatement();
			String sql = "UPDATE billiard_counter.GAME_LIST "
					+ "SET TABLE_NUMBER = " + afterTableNumber
					+ " WHERE TABLE_NUMBER = " + beforeTableNumber + " AND IS_PAID = FALSE;";
			
			stmt.executeUpdate( sql );
			
			stmt.close();
			conn.close();
			
			return true;
			
		} catch( Exception e ) {
			
			e.printStackTrace();
			
			return false;
			
		}
		
	}
	
}
