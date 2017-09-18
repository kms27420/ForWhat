package com.kms.billiardcounter.core.mainframe;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;

import com.kms.billiardcounter.core.ancillaryframe.PaymentFrame;
import com.kms.billiardcounter.core.event.GameTableReplace;
import com.kms.billiardcounter.database.game_list.GameListLoader;
import com.kms.billiardcounter.database.game_viewer.GameViewerLoader;
import com.kms.billiardcounter.database.game_viewer.GameViewerModifier;
import com.kms.billiardcounter.font.FontProvider;
import com.kms.billiardcounter.support.TotalFeeInfo;
import com.kms.billiardcounter.support.gamefeeinfo.GameFeeInfoConvertor;

/**
 * 
 * 계산이 안된 게임 목록을 보여주고 처리할 수 있는 인터페이스를 보여주는 panel
 * 
 * @author Kwon
 *
 */

public class GameListControlPanel extends JPanel {
	
	public interface OnGameStartListener {
		
		public void onGameStart();
		
	}
	
	public GameListControlPanel( int tableNumber, OnGameStartListener onGameStartListener ){
		
		initThisPanel();
		
		add( createGameListScrollPane( tableNumber ), BorderLayout.CENTER );
		add( createTableGameControlPanel( tableNumber, onGameStartListener ), BorderLayout.SOUTH );
		
	}
	
	private void initThisPanel(){
		
		setLayout( new BorderLayout() );
		
	}
	
	private JScrollPane createGameListScrollPane( int tableNumber ) {
		
		JScrollPane gameListScrollPane = new JScrollPane();
		
		JPanel gameListPanel = new JPanel();						
		
		ArrayList<JLabel> nonPaidGameFeeInfoLabelList = GameFeeInfoConvertor.convertToLabelList( GameListLoader.getNonPaidGameFeeInfoList( tableNumber ) );
		
		Dimension gameLabelSize = new Dimension( 0, 50 );
		
		gameListPanel.setPreferredSize( new Dimension( 0, gameLabelSize.height * ( nonPaidGameFeeInfoLabelList.size() + 1 ) ) );
		gameListPanel.setBorder( new BevelBorder( BevelBorder.RAISED ) );
		gameListPanel.setLayout( new GridLayout( nonPaidGameFeeInfoLabelList.size() + 1, 1 ) );
		
		if( nonPaidGameFeeInfoLabelList.size() == 0 ){
		
			if( GameTableReplace.getIsEnabled() && tableNumber > 0 ) {
				
				JButton changeTablePositionButton = new JButton();
				
				changeTablePositionButton.setText( "이곳으로 옮기기" );
				changeTablePositionButton.setFont( FontProvider.getDefaultFont() );
				changeTablePositionButton.addActionListener( new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {

						GameTableReplace.replaceCurrentGameTablePosition( tableNumber );
						
					}
					
				} );
				
				gameListPanel.add( changeTablePositionButton );
				
			} else if( tableNumber > 0 ) {
			
				JLabel gameLabel = new JLabel( "게임 없음" );
			
				gameLabel.setFont( FontProvider.getDefaultFont() );
				gameLabel.setHorizontalAlignment( JLabel.CENTER );
			
				gameListPanel.add( gameLabel );
			
			} else {
				
				JLabel gameLabel = new JLabel( "테이블 없음" );
				
				gameLabel.setFont( FontProvider.getDefaultFont() );
				gameLabel.setHorizontalAlignment( JLabel.CENTER );
			
				gameListPanel.add( gameLabel );
				
			}
		
		} else {
		
			for( int index = 0; index < nonPaidGameFeeInfoLabelList.size(); index++ ){
			
				gameListPanel.add( nonPaidGameFeeInfoLabelList.get(index) );
		
			}
		
			if( !GameTableReplace.getIsEnabled() ) {
			
				JButton changeTablePositionButton = new JButton();
					
				changeTablePositionButton.setText( "자리 옮기기" );
				changeTablePositionButton.setFont( FontProvider.getDefaultFont() );
				changeTablePositionButton.addActionListener( new ActionListener() {
				
					@Override
					public void actionPerformed(ActionEvent e) {

						GameTableReplace.activateReplacementWork( tableNumber );
						
					}
						
				} );
			
				gameListPanel.add( changeTablePositionButton );
					
			} else {
				
				if( GameTableReplace.getActivatedTableNumber() == tableNumber ){
					
					JButton disactivateReplacementWorkButton = new JButton();
					
					disactivateReplacementWorkButton.setText( "자리 옮기기 취소" );
					disactivateReplacementWorkButton.setFont( FontProvider.getDefaultFont() );
					disactivateReplacementWorkButton.addActionListener( new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {

							GameTableReplace.disactivateReplacementWork();
							
						}
						
					} );
					
					gameListPanel.add( disactivateReplacementWorkButton );
					
				} 
				
			}
		
		}
		
		gameListScrollPane.setViewportView( gameListPanel );
		gameListScrollPane.setPreferredSize( new Dimension( 0, 200 ) );
		
		return gameListScrollPane;
		
	}
	
	private JPanel createTableGameControlPanel( int tableNumber, OnGameStartListener onGameStartListener ) {
		
		JPanel tableGameControlPanel = new JPanel();						
		
		JPanel gameResultInfoPanel = new JPanel();					
		JLabel totalUsedTimeLabel = new JLabel("게임 없음");			
		JLabel totalFeeLabel = new JLabel();						
		
		JPanel gameControlPanel = new JPanel();				
		JButton payButton = new JButton("계산");						
		JButton startButton = new JButton("시작");					
		
		TotalFeeInfo totalFeeInfo = GameListLoader.getNonPaidGamesTotalFeeInfo( tableNumber );
		
		if( totalFeeInfo.getIsValid() ){
			
			totalUsedTimeLabel.setText( "총 사용 시간  " + totalFeeInfo.getUsedTime() );
			totalFeeLabel.setText( "총 요금 : " + totalFeeInfo.getFee() + "원" );
			
		}
		
		totalUsedTimeLabel.setFont( FontProvider.getDefaultFont() );
		totalUsedTimeLabel.setHorizontalAlignment( JLabel.CENTER );
		
		totalFeeLabel.setFont( FontProvider.getDefaultFont() );
		totalFeeLabel.setHorizontalAlignment( JLabel.CENTER );
		
		gameResultInfoPanel.setPreferredSize( new Dimension(200, 100) );
		gameResultInfoPanel.setLayout( new GridLayout(2, 1) );
		gameResultInfoPanel.add( totalUsedTimeLabel );
		gameResultInfoPanel.add( totalFeeLabel );
		
		payButton.setFont( FontProvider.getDefaultFont() );
		startButton.setFont( FontProvider.getDefaultFont() );
		
		if( tableNumber == 0){
			
			payButton.setEnabled( false );
			startButton.setEnabled( false );
			
		}
		else{
			
			payButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					if( GameListLoader.getNonPaidGameFeeInfoList( tableNumber ).size() > 0 ) {
						
						new PaymentFrame( tableNumber, new PaymentFrame.OnPaymentCompleteListener() {
							
							@Override
							public void onPaymentComplete() {

								GameListControlPanel.this.removeAll();
									
								GameListControlPanel.this.add( createTableGameControlPanel( tableNumber, onGameStartListener ), BorderLayout.SOUTH );
								GameListControlPanel.this.add( createGameListScrollPane( tableNumber ), BorderLayout.CENTER );
								
								GameListControlPanel.this.repaint();
								GameListControlPanel.this.revalidate();
								
							}
							
						} );
						
					}
					
				}
				
			});
			
			startButton.addActionListener( new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {

					if( !GameViewerLoader.getIsInUse( tableNumber ) ) {
						
						if( !GameViewerModifier.useThisGameViewer( tableNumber ) ) {	
							
							return;
					
						}
					
					} 
					
					onGameStartListener.onGameStart();
					
				}
				
			} );
			
		}
		
		gameControlPanel.setPreferredSize( new Dimension( 200, 100 ) );
		gameControlPanel.setLayout( new GridLayout( 1, 2 ) );
		
		gameControlPanel.add( payButton );
		gameControlPanel.add( startButton );
		
		tableGameControlPanel.setBorder( new BevelBorder( BevelBorder.RAISED ) );
		tableGameControlPanel.setLayout( new BorderLayout() );
		
		tableGameControlPanel.add( gameResultInfoPanel, BorderLayout.CENTER );
		tableGameControlPanel.add( gameControlPanel, BorderLayout.EAST );
		
		return tableGameControlPanel;
		
	}
	
}
