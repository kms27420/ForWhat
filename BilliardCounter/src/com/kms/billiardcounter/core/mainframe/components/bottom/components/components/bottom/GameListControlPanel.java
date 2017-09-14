package com.kms.billiardcounter.core.mainframe.components.bottom.components.components.bottom;

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

import com.kms.billiardcounter.core.contentspaneupdater.ContentsPaneUpdater;
import com.kms.billiardcounter.core.event.GameTableReplace;
import com.kms.billiardcounter.dao.gamelist.GameListTableUpdater;
import com.kms.billiardcounter.dao.nonpaidgames.NonPaidGamesLoader;
import com.kms.billiardcounter.dao.usingtable.UsingTableLoader;
import com.kms.billiardcounter.dao.usingtable.UsingTableUpdater;
import com.kms.billiardcounter.font.FontProvider;
import com.kms.billiardcounter.support.GameFeeInfo;

/**
 * 
 * 계산이 안된 게임 목록을 보여주고 처리할 수 있는 인터페이스를 보여주는 panel
 * 
 * @author Kwon
 *
 */

public class GameListControlPanel extends JPanel {
	
	public GameListControlPanel( int tableNumber, ContentsPaneUpdater contentsPaneUpdater ){
		
		initThisPanel();
		
		add( createGameListScrollPane( tableNumber ), BorderLayout.CENTER );
		add( createTableGameControlPanel( tableNumber, contentsPaneUpdater ), BorderLayout.SOUTH );
		
		repaint();
		revalidate();
		
	}
	
	private void initThisPanel(){
		
		setLayout( new BorderLayout() );
		
	}
	
	private JScrollPane createGameListScrollPane( int tableNumber ) {
		
		JScrollPane gameListScrollPane = new JScrollPane();
		
		JPanel gameListPanel = new JPanel();						
		
		ArrayList<GameFeeInfo> nonPaidGameFeeInfoList = NonPaidGamesLoader.getNonPaidGameFeeInfoList( tableNumber );
		ArrayList<JLabel> nonPaidGameFeeInfoLabelList = new ArrayList<JLabel>();
		
		Dimension gameLabelSize = new Dimension( 0, 50 );
		
		String usedTime;
		int gameNumber;
		
		for( int index = 0; index < nonPaidGameFeeInfoList.size(); index++ ){
			
			usedTime = String.format( "%02d", nonPaidGameFeeInfoList.get(index).getUsedTime() / 60) + ":" + String.format( "%02d", nonPaidGameFeeInfoList.get(index).getUsedTime() % 60 );
			gameNumber = nonPaidGameFeeInfoList.get(index).getGameNumber();
			
			JLabel nonPaidGameFeeInfoLabel = new JLabel( gameNumber + " 게임 - 사용 시간 " + usedTime + ", 요금 " + nonPaidGameFeeInfoList.get(index).getFee() + "원");
			
			nonPaidGameFeeInfoLabel.setFont( FontProvider.getDefaultFont() );
			nonPaidGameFeeInfoLabel.setHorizontalAlignment( JLabel.CENTER );
			
			nonPaidGameFeeInfoLabelList.add( nonPaidGameFeeInfoLabel );
			
		}
		
		gameListPanel.setPreferredSize( new Dimension( 0, gameLabelSize.height * ( nonPaidGameFeeInfoList.size() + 1 ) ) );
		gameListPanel.setBorder( new BevelBorder( BevelBorder.RAISED ) );
		gameListPanel.setLayout( new GridLayout( nonPaidGameFeeInfoList.size() + 1, 1 ) );
		
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
	
	private JPanel createTableGameControlPanel( int tableNumber, ContentsPaneUpdater contentsPaneUpdater ) {
		
		JPanel tableGameControlPanel = new JPanel();						
		
		JPanel gameResultInfoPanel = new JPanel();					
		JLabel totalUsedTimeLabel = new JLabel("게임 없음");			
		JLabel totalFeeLabel = new JLabel();						
		
		JPanel gameControlPanel = new JPanel();				
		JButton payButton = new JButton("계산");						
		JButton startButton = new JButton("시작");					
		
		String[] totalUsedTimeAndFee = NonPaidGamesLoader.getTotalUsedTimeAndFeeOfNonPaidGames( tableNumber );
		
		if( totalUsedTimeAndFee != null ){
			
			totalUsedTimeLabel.setText( "총 사용 시간  " + totalUsedTimeAndFee[0] );
			totalFeeLabel.setText( "총 요금 : " + totalUsedTimeAndFee[1] + "원" );
			
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
					
					if( GameListTableUpdater.updateIsPaidToTrue( tableNumber ) ) {
						
						if( UsingTableUpdater.deleteUsingTable( tableNumber ) ) {
							
							GameListControlPanel.this.removeAll();
							
							GameListControlPanel.this.add( createTableGameControlPanel( tableNumber, contentsPaneUpdater ), BorderLayout.SOUTH );
							GameListControlPanel.this.add( createGameListScrollPane( tableNumber ), BorderLayout.CENTER );
						
							GameListControlPanel.this.repaint();
							GameListControlPanel.this.revalidate();
							
						}
						
					}
					
				}
				
			});
			
			startButton.addActionListener( new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {

					if( UsingTableLoader.isThisTableInUse( tableNumber ) ) {
						
						contentsPaneUpdater.update();
					
					} else {
						
						if( UsingTableUpdater.saveUsingTable( tableNumber ) ) {	
							
							contentsPaneUpdater.update();
					
						}
						
					}
					
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
