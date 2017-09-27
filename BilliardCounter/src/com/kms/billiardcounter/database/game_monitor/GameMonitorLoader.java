package com.kms.billiardcounter.database.game_monitor;

import java.sql.ResultSet;

import com.kms.billiardcounter.database.connection.DatabaseConnector;

/**
 * 
 * 데이터베이스 GAME_MONITOR 테이블의 내용을 원하는 형태로 불러오는 클래스
 * 
 * @author Kwon
 *
 */

public class GameMonitorLoader {
	
	private GameMonitorLoader(){}
	
	/**
	 * 위치 정보를 통해 당구 테이블의 번호를 반환하는 매서드
	 * @param tableRow 원하는 당구 테이블의 row
	 * @param tableCol 원하는 당구 테이블의 col
	 * @return tableNumber
	 */
	public static final int getTableNumber( int tableRow, int tableCol ){
		
		int tableNumber = 0;
		
		try{
			
			String sql = "SELECT TABLE_NUMBER "
					+ "FROM BILLIARD_COUNTER.GAME_MONITOR "
					+ "WHERE TABLE_ROW = " + tableRow + " AND TABLE_COL = " + tableCol + ";";
			
			ResultSet rs = DatabaseConnector.getStatement().executeQuery( sql );
			
			if( rs.next() ){
				
				tableNumber = rs.getInt("TABLE_NUMBER");
				
			}
			
			rs.close();
			
		}catch(Exception e){
		
			e.printStackTrace();
		
		}
		
		return tableNumber;
	
	}
	
	/**
	 * 
	 * tableNumber의 당구대 모니터가 사용중인지를 받아오는 매서드
	 * 
	 * @param tableNumber 사용 중인지 알고 싶은 당구대의 번호
	 * @return 사용중이면 true, 그렇지 않으면 false
	 */
	public static final boolean getIsTableInUse( int tableNumber ) {
		
		try {
			
			String sql = "SELECT IS_IN_USE "
					+ "FROM billiard_counter.GAME_MONITOR "
					+ "WHERE TABLE_NUMBER = " + tableNumber + ";";
			
			ResultSet rs = DatabaseConnector.getStatement().executeQuery( sql );
			
			boolean isInUse = false;
			
			if( rs.next() ) {
				
				isInUse =  rs.getBoolean( "IS_IN_USE" );
				
			}
			
			rs.close();
			
			return isInUse;
			
		} catch ( Exception e ) {
			
			e.printStackTrace();
			
			return false;
			
		}
		
	}
	
	/**
	 * 
	 * 현재 사용중이지 않은 당구대 모니터가 있는지 판단해주는 매서드
	 * 
	 * @return 사용중이지 않은 당구대 모니터가 있다면 true, 그렇지 않다면 false
	 */
	public static final boolean getIsThereUnusedGameMonitor() {
		
		try {
			
			String sql = "SELECT COUNT(TABLE_NUMBER) AS UNUSED_GAME_MONITOR_COUNT "
					+ "FROM billiard_counter.GAME_MONITOR "
					+ "WHERE IS_IN_USE = FALSE;";
			
			ResultSet rs = DatabaseConnector.getStatement().executeQuery( sql );
			
			int unusedGameMonitorCount = 0;
			
			if( rs.next() ) {
				
				unusedGameMonitorCount = rs.getInt( "UNUSED_GAME_MONITOR_COUNT" );
				
			}
			
			rs.close();
			
			return unusedGameMonitorCount > 0 ? true : false;
			
		} catch( Exception e ) {
			
			e.printStackTrace();
			
			return false;
			
		}
		
	}
	
}
