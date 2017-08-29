package com.kms.billiardcounter.billiardtablepanel;

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

import com.kms.billiardcounter.contentspaneupdater.ContentsPaneUpdater;
import com.kms.billiardcounter.databasecontrol.GameListLoader;
import com.kms.billiardcounter.databasecontrol.GameListUpdater;
import com.kms.billiardcounter.fontprovider.FontProvider;
import com.kms.billiardcounter.programdatacollection.GameFeeInfo;

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
		
		ArrayList<GameFeeInfo> gameList = GameListLoader.getNonPaidGameList( tableNumber );
		ArrayList<JLabel> gameLabelList = new ArrayList<JLabel>();
		
		Dimension gameLabelSize = new Dimension( 400, 50 );
		
		String usedTime;
		int gameNumber;
		
		for( int index = 0; index < gameList.size(); index++ ){
			
			usedTime = String.format( "%02d", gameList.get(index).getUsedTime() / 60) + ":" + String.format( "%02d", gameList.get(index).getUsedTime() % 60 );
			gameNumber = gameList.get(index).getGameNum();
			
			JLabel gameLabel = new JLabel( gameNumber + " 게임 - 사용 시간 " + usedTime + ", 요금 " + gameList.get(index).getFee() + "원");
			
			gameLabel.setFont( FontProvider.getDefaultFont() );
			gameLabel.setHorizontalAlignment( JLabel.CENTER );
			
			gameLabelList.add( gameLabel );
			
		}
		
		if( gameLabelList.size() == 0 ){
		
			JLabel gameLabel = new JLabel( "게임 없음" );
			
			gameLabel.setFont( FontProvider.getDefaultFont() );
			gameLabel.setHorizontalAlignment( JLabel.CENTER );
			
			gameLabelList.add( gameLabel );
		
		}
		
		gameListPanel.setPreferredSize( new Dimension( 0, gameLabelSize.height * gameList.size() ) );
		gameListPanel.setBorder( new BevelBorder( BevelBorder.RAISED ) );
		gameListPanel.setLayout( new GridLayout( gameList.size(), 1 ) );
		
		for( int index = 0; index < gameLabelList.size(); index++ ){
			
			gameListPanel.add( gameLabelList.get(index) );
		
		}
		
		gameListScrollPane.setViewportView( gameListPanel );
		gameListScrollPane.setPreferredSize( new Dimension( 0, 200 ));
		
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
		
		ArrayList<String> totalFeeInfosStringList = GameListLoader.getTotalFeeInfosStringList( tableNumber );
		
		if( totalFeeInfosStringList.size() != 0 ){
			
			totalUsedTimeLabel.setText( "총 사용 시간  " + totalFeeInfosStringList.get(0) );
			totalFeeLabel.setText( "총 요금 : " + totalFeeInfosStringList.get(1) + "원" );
			
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
					
					GameListUpdater.updateIsPaidToTrue( tableNumber );
					
					GameListControlPanel.this.removeAll();
					
					GameListControlPanel.this.add( createTableGameControlPanel( tableNumber, contentsPaneUpdater ), BorderLayout.SOUTH );
					GameListControlPanel.this.add( createGameListScrollPane( tableNumber ), BorderLayout.CENTER );
				
					GameListControlPanel.this.repaint();
					GameListControlPanel.this.revalidate();
					
				}
				
			});
			
			startButton.addActionListener( new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {

					contentsPaneUpdater.update();
					
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
