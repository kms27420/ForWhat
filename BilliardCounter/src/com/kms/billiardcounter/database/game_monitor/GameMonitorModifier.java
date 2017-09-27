package com.kms.billiardcounter.database.game_monitor;

import com.kms.billiardcounter.database.connection.DatabaseConnector;

/**
 * 
 * 데이터베이스 GAME_MONITOR 테이블을 내용을 추가 또는 변경해주는 클래스
 * 
 * @author Kwon
 *
 */
public class GameMonitorModifier {
	
	private GameMonitorModifier(){}
	
	/**
	 * 
	 * 새로 생성된 GameMonitor를 저장해주는 매서드
	 * 
	 * @param tableNumber 당구대 번호
	 * @param row 당구대의 row위치
	 * @param col 당구대의 col위치
	 * @return 저장 작업이 정상적으로 수행되면 true, 그렇지 않으면 false
	 */
	public static final boolean saveNewGameMonitor( int tableNumber, int row, int col ){
		
		if( tableNumber <= 0 )	return false;
		
		try{
			
			String sql = "INSERT INTO billiard_counter.GAME_MONITOR VALUES(" + tableNumber + ", " + row + ", " + col + ", FALSE);";
			
			DatabaseConnector.getStatement().executeUpdate(sql);
			
			return true;
			
		}catch(Exception e){
			
			e.printStackTrace();
			
			return false;
			
		}
		
	}
	
	/**
	 * 
	 * 해당 당구대의 GameMonitor를 삭제해주는 매서드
	 * 
	 * @param tableNumber 삭제하고자하는 당구대의 번호
	 * @return 삭제 작업이 정상적으로 처리되면 true, 그렇지 않으면 false
	 */
	public static final boolean deleteGameMonitor( int tableNumber ) {
		
		try {
			
			String sql = "DELETE FROM billiard_counter.GAME_MONITOR "
						+ "WHERE TABLE_NUMBER = " + tableNumber + ";";
			
			DatabaseConnector.getStatement().executeUpdate(sql);
			
			return true;
			
		} catch( Exception e ) {
			
			e.printStackTrace();
			
			return false;
			
		}
		
	}
	
	/**
	 * 
	 * tableNumber의 당구대 모니터를 사용중인 상태로 변경해주는 매서드
	 * 
	 * @param tableNumber 변경하고자하는 당구대 번호
	 * @return 작업이 정상적으로 처리되면 true, 그렇지 않으면 false
	 */
	public static final boolean useThisGameMonitor( int tableNumber ) {
		
		try {
			
			String sql = "UPDATE billiard_counter.GAME_MONITOR "
					+ "SET IS_IN_USE = TRUE "
					+ "WHERE TABLE_NUMBER = " + tableNumber + ";";
			
			DatabaseConnector.getStatement().executeUpdate( sql );
			
			return true;
			
		} catch( Exception e ) {
			
			e.printStackTrace();
			
			return false;
			
		}
		
	}
	
	/**
	 * 
	 * tableNumber의 당구대 모니터를 사용중이지 않은 상태로 변경해주는 매서드
	 * 
	 * @param tableNumber 변경하고자하는 당구대 번호
	 * @return 작업이 정상적으로 처리되면 true, 그렇지 않으면 false
	 */
	public static final boolean endUseThisGameMonitor( int tableNumber ) {
		
		try {
			
			String sql = "UPDATE billiard_counter.GAME_MONITOR "
					+ "SET IS_IN_USE = FALSE "
					+ "WHERE TABLE_NUMBER = " + tableNumber + ";";
			
			DatabaseConnector.getStatement().executeUpdate( sql );
			
			return true;
			
		} catch( Exception e ) {
			
			e.printStackTrace();
			
			return false;
			
		}
		
	}
	
}
