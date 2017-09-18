package com.kms.billiardcounter.database.game_viewer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.kms.billiardcounter.database.connection.BilliardCounterConnector;

/**
 * 
 * 데이터베이스 GAME_VIEWER 테이블의 내용을 원하는 형태로 불러오는 클래스
 * 
 * @author Kwon
 *
 */

public class GameViewerLoader {
	
	private GameViewerLoader(){}
	
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
					+ "FROM BILLIARD_COUNTER.GAME_VIEWER "
					+ "WHERE TABLE_ROW = " + tableRow + " AND TABLE_COL = " + tableCol + ";";
			
			Connection conn = BilliardCounterConnector.getConnection();
			Statement stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery( sql );
			
			while(rs.next()){
				
				tableNumber = rs.getInt("TABLE_NUMBER");
				
			}
			
			rs.close();
			stmt.close();
			conn.close();
			
		}catch(Exception e){
		
			//e.printStackTrace();
		
		}
		
		return tableNumber;
	
	}
	
	/**
	 * 
	 * tableNumber의 당구대 뷰어가 사용중인지를 받아오는 매서드
	 * 
	 * @param tableNumber 사용 중인지 알고 싶은 당구대의 번호
	 * @return 사용중이면 true, 그렇지 않으면 false
	 */
	public static final boolean getIsInUse( int tableNumber ) {
		
		try {
			
			Connection conn = BilliardCounterConnector.getConnection();
			Statement stmt = conn.createStatement();
			String sql = "SELECT IS_IN_USE "
					+ "FROM billiard_counter.GAME_VIEWER "
					+ "WHERE TABLE_NUMBER = " + tableNumber + ";";
			
			ResultSet rs = stmt.executeQuery( sql );
			
			boolean isInUse = false;
			
			while( rs.next() ) {
				
				isInUse =  rs.getBoolean( "IS_IN_USED" );
				
			}
			
			rs.close();
			stmt.close();
			conn.close();
			
			return isInUse;
			
		} catch ( Exception e ) {
			
			//e.printStackTrace();
			
			return false;
			
		}
		
	}
	
	/**
	 * 
	 * 현재 사용중이지 않은 당구대 뷰어가 있는지 판단해주는 매서드
	 * 
	 * @return 사용중이지 않은 당구대 뷰어가 있다면 true, 그렇지 않다면 false
	 */
	public static final boolean getIsThereUnusedGameViewer() {
		
		try {
			
			Connection conn = BilliardCounterConnector.getConnection();
			Statement stmt = conn.createStatement();
			String sql = "SELECT COUNT(TABLE_NUMBER) AS UNUSED_GAME_VIEWER_COUNT "
					+ "FROM billiard_counter.GAME_VIEWER "
					+ "WHERE IS_IN_USE = FALSE;";
			
			ResultSet rs = stmt.executeQuery( sql );
			
			int unusedGameViewerCount = 0;
			
			while( rs.next() ) {
				
				unusedGameViewerCount = rs.getInt( "UNUSED_GAME_VIEWER_COUNT" );
				
			}
			
			rs.close();
			stmt.close();
			conn.close();
			
			if( unusedGameViewerCount > 0 )	return true;
			
			return false;
			
		} catch( Exception e ) {
			
			//e.printStackTrace();
			
			return false;
			
		}
		
	}
	
}
