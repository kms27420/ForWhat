package com.kms.billiardcounter.core.event;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JLabel;

import com.kms.billiardcounter.core.contentspaneupdater.ContentsPaneUpdater;
import com.kms.billiardcounter.dao.connection.BilliardCounterConnector;
import com.kms.billiardcounter.dao.gamelist.GameListTableUpdater;
import com.kms.billiardcounter.font.FontProvider;

/**
 * 
 * 사용자가 당구대를 옮길때 그 당구대의 정보를 다른 당구대로 옮겨주는 클래스
 * 
 * @author Kwon
 *
 */
public class GameTableReplace {

	private static ContentsPaneUpdater contentsPaneUpdater;
	
	private static boolean isEnabled = false;
	private static int replaceFromThisTableNumber = 0;
	private static int replaceToThisTableNumber = 0;
	
	private GameTableReplace() {}
	
	private static final boolean isThereReplacableTable() {
		
		try {
			
			Connection conn = BilliardCounterConnector.getConnection();
			Statement stmt = conn.createStatement();
			String sql = "SELECT COUNT( TABLE_NUMBER ) AS COUNT_OF_CREATED_TABLE "
					+ "FROM billiard_counter.CREATED_TABLE_INFO;";
			
			ResultSet rs = stmt.executeQuery( sql );
			
			int countOfCreatedTable = 0;
			int countOfNonPaidTable = 0;
			
			while( rs.next() ) {
				
				countOfCreatedTable = rs.getInt( "COUNT_OF_CREATED_TABLE" );
				
			}
			
			sql = "SELECT COUNT( DISTINCT TABLE_NUMBER ) AS COUNT_OF_NON_PAID_TABLE "
					+ "FROM billiard_counter.GAME_LIST "
					+ "WHERE IS_PAID = FALSE;";
			
			rs = stmt.executeQuery( sql );
			
			while( rs.next() ) {
				
				countOfNonPaidTable = rs.getInt( "COUNT_OF_NON_PAID_TABLE" );
				
			}
			
			rs.close();
			stmt.close();
			conn.close();
			
			if( countOfCreatedTable == countOfNonPaidTable )	return false;
			
			return true;
			
		} catch( Exception e ) {
			
			e.printStackTrace();
			
			return false;
			
		}
		
	}
	
	/**
	 * 
	 * contentsPaneUpdater를 설정해주는 매서드
	 * 
	 * @param contentsPaneUpdater 설정해줄 contentsPaneUpdater
	 */
	public static final void setContentsPaneUpdater( ContentsPaneUpdater contentsPaneUpdater ) {
		
		GameTableReplace.contentsPaneUpdater = contentsPaneUpdater;
		
	}
	
	/**
	 * 
	 * 당구대의 정보를 입력받은 번호의 당구대로 옮겨주는 매서드
	 * 
	 * @param tableNumber 당구대 정보를 옮길 최종 위치
	 */
	public static final void replaceCurrentGameTablePosition( int tableNumber ) {
		
		replaceToThisTableNumber = tableNumber;
		
		if( GameListTableUpdater.updateNonPaidGamesTableNumber( replaceFromThisTableNumber, replaceToThisTableNumber ) ) {
		
			isEnabled = false;
			replaceFromThisTableNumber = 0;
			replaceToThisTableNumber = 0;
		
			contentsPaneUpdater.update();
		
		} 
		
	}
	
	/**
	 * 
	 * 당구대의 정보를 옮겨주기 위해 본 클래스를 활성화 시켜주는 매서드
	 * 
	 * @param tableNumber 정보를 옮길 당구대의 번호
	 */
	public static final void activateReplacementWork( int tableNumber ) {
		
		if( isThereReplacableTable() ) {
			
			replaceFromThisTableNumber = tableNumber;
			isEnabled = true;
		
			contentsPaneUpdater.update();
		
		} else {
			
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			Dimension frameSize = new Dimension( 400, 200 );
			
			JFrame alertFrame = new JFrame( "자리 이동 실패" );
			
			JLabel alertLabel = new JLabel( "모든 당구대가 사용중입니다." );
			
			alertLabel.setHorizontalAlignment( JLabel.CENTER );
			alertLabel.setFont( FontProvider.getDefaultFont() );
			
			alertFrame.setLayout( new GridLayout( 1, 1 ) );
			alertFrame.setLocation( screenSize.width / 2 - frameSize.width / 2, screenSize.height / 2 - frameSize.height / 2 );
			alertFrame.setSize( frameSize );
			alertFrame.setResizable( false );
			
			alertFrame.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
			
			alertFrame.add( alertLabel );
			
			alertFrame.setVisible( true );
			
		}
		
	}
	
	/**
	 * 
	 * 본 클래스가 활성화 되었는지를 확인하는 변수 isEnabled의 값을 받아오는 매서드
	 * 
	 * @return isEnabled
	 */
	public static final boolean getIsEnabled() {
		
		return isEnabled;
		
	}
	
}
