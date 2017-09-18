package com.kms.billiardcounter.core.event;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JLabel;

import com.kms.billiardcounter.database.connection.BilliardCounterConnector;
import com.kms.billiardcounter.database.game_list.GameListModifier;
import com.kms.billiardcounter.database.game_viewer.GameViewerLoader;
import com.kms.billiardcounter.database.game_viewer.GameViewerModifier;
import com.kms.billiardcounter.font.FontProvider;

/**
 * 
 * 사용자가 당구대를 옮길때 그 당구대의 정보를 다른 당구대로 옮겨주는 클래스
 * 
 * @author Kwon
 *
 */
public class GameTableReplace {

	public interface OnReplaceTableListener {
		
		public void onReplaceTable();
		
	}
	
	private static OnReplaceTableListener onReplaceTableListener = null;
	
	private static boolean isEnabled = false;
	
	private static int activatedTableNumber = 0;
	
	private GameTableReplace() {}
	
	/**
	 * 
	 * paneUpdaterAfterReplacingTable를 설정해주는 매서드
	 * 
	 * @param paneUpdaterAfterReplacingTable 설정해줄 paneUpdaterAfterReplacingTable
	 */
	public static final void setOnReplaceTableListener( OnReplaceTableListener onReplaceTableListener ) {
		
		GameTableReplace.onReplaceTableListener = onReplaceTableListener;
		
	}
	
	/**
	 * 
	 * 당구대의 정보를 입력받은 번호의 당구대로 옮겨주는 매서드
	 * 
	 * @param tableNumber 당구대 정보를 옮길 최종 위치
	 */
	public static final void replaceCurrentGameTablePosition( int tableNumber ) {
		
		if( GameListModifier.replaceNonPaidGamesTableNumber( activatedTableNumber, tableNumber ) ) {
		
			if( GameViewerModifier.endUseThisGameViewer( activatedTableNumber ) ) {
				
				if( GameViewerModifier.useThisGameViewer( tableNumber ) ) {
					
					isEnabled = false;
					activatedTableNumber = 0;
				
					onReplaceTableListener.onReplaceTable();
					
				}
				
			}
			
		} 
		
	}
	
	/**
	 * 
	 * 당구대의 정보를 옮겨주기 위해 본 클래스를 활성화 시켜주는 매서드
	 * 
	 * @param tableNumber 정보를 옮길 당구대의 번호
	 */
	public static final void activateReplacementWork( int tableNumber ) {
		
		if( GameViewerLoader.getIsThereUnusedGameViewer() ) {
			
			activatedTableNumber = tableNumber;
			isEnabled = true;
		
			onReplaceTableListener.onReplaceTable();
		
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
	 * 당구대를 옮겨주기 위한 작업을 비활성화 시켜주는 매서드
	 * 
	 */
	public static final void disactivateReplacementWork() {
		
		activatedTableNumber = 0;
		isEnabled = false;
	
		onReplaceTableListener.onReplaceTable();
		
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
	
	/**
	 * 
	 * 본 클래스를 활성화 시켜준 테이블의 번호를 반환하는 매서드
	 * 
	 * @return replaceFromThisTableNumber
	 */
	public static final int getActivatedTableNumber() {
		
		return activatedTableNumber;
		
	}
	
}
