package com.kms.billiardcounter.databasecontrol;

import java.sql.Connection;
import java.sql.Statement;

import com.kms.billiardcounter.programdatacollection.GameFeeInfo;

/**
 * 
 * GAME_LIST 테이블의 내용을 추가하거나 업데이트해주는 클래스
 * 
 * @author Kwon
 *
 */
public class GameListUpdater {
	
	private GameListUpdater(){}
	
	private static void createTableIfNotExists( Connection conn, Statement stmt ) throws Exception{
		
		stmt.execute(
				"CREATE TABLE IF NOT EXISTS billiard_counter.GAME_LIST("
				+ "DATE VARCHAR(6) NOT NULL,"
				+ "START_TIME VARCHAR(8) NOT NULL,"
				+ "END_TIME VARCHAR(8) NOT NULL,"
				+ "GAME_NUM INT(11) NOT NULL,"
				+ "TABLE_NUM INT(11) NOT NULL,"
				+ "USED_TIME INT(11) NOT NULL,"
				+ "FEE INT(11) NOT NULL,"
				+ "IS_PAID BOOLEAN NOT NULL,"
				+ "PRIMARY KEY(DATE, START_TIME, GAME_NUM, TABLE_NUM));"
				);
		
	}
	
	/**
	 * 
	 * GameFeeInfo를 본 프로그램의 DB에 저장하는 매서드
	 * 
	 * @param gameFeeInfo 저장하고자 하는 GameFeeInfo
	 * @return 저장 작업이 제대로 실행되면 true, 그렇지 않으면 false를 return
	 */
	public static boolean saveGameFeeInfoToDB( GameFeeInfo gameFeeInfo ){
		
		try{
			
			Connection conn = BilliardCounterConnector.getConnection();
			Statement stmt = conn.createStatement();
			String sql;
			
			createTableIfNotExists( conn, stmt );
			
			String date, startTime, endTime;
			int gameNum, tableNum, usedTime, fee;
			boolean isPaid;
			
			date = gameFeeInfo.getDate();
			startTime = gameFeeInfo.getStartTime();
			endTime = gameFeeInfo.getEndTime();
			gameNum = gameFeeInfo.getGameNum();
			tableNum = gameFeeInfo.getTableNum();
			usedTime = gameFeeInfo.getUsedTime();
			fee = gameFeeInfo.getFee();
			isPaid = gameFeeInfo.getIsPaid();
				
			sql = "INSERT INTO billiard_counter.GAME_LIST "
					+ "VALUES('" + date + "', '" + startTime + "', '" + endTime + "', " 
			+ gameNum + ", " + tableNum + ", " + usedTime + ", " + fee + ", " + isPaid + ");";
				
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
	 * @return
	 */
	public static boolean updateIsPaidToTrue( int tableNumber ){
		
		try{
			
			Connection conn = BilliardCounterConnector.getConnection();
			Statement stmt = conn.createStatement();
			String sql = "UPDATE billiard_counter.GAME_LIST "
					+ "SET IS_PAID = TRUE "
					+ "WHERE TABLE_NUM = " + tableNumber + ";";
			
			stmt.executeUpdate( sql );
			
			stmt.close();
			conn.close();
			
			return true;
			
		}catch(Exception e){
			
			//e.printStackTrace();
			
			return false;
			
		}
		
	}
	
}
