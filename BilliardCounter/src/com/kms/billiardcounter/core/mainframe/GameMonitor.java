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
import com.kms.billiardcounter.database.game_list.GameListLoader;
import com.kms.billiardcounter.database.game_list.GameListModifier;
import com.kms.billiardcounter.database.game_monitor.GameMonitorLoader;
import com.kms.billiardcounter.database.game_monitor.GameMonitorModifier;
import com.kms.billiardcounter.font.FontProvider;
import com.kms.billiardcounter.support.TotalFeeInfo;
import com.kms.billiardcounter.support.gamefeeinfo.GameFeeInfoConvertor;

/**
 * 
 * 각 당구대의 상황을 보여주는 패널
 * 
 * @author Kwon
 *
 */

public class GameMonitor extends JPanel{
	
	private JPanel baseMonitor = null;				// 현 당구대의 게임 리스트를 다룰 수 있는 인터페이스를 보여주는 panel
	private JPanel playingMonitor = null;					// 현 당구대가 게임이 진행 중일때를 보여주는 panel
	
	/**
	 * 
	 * 본 panel을 초기화해주는 생성자
	 * 
	 * @param row 초기화 해줄 row 위치
	 * @param col 초기화 해줄 col 위치
	 */
	public GameMonitor( int row, int col ){
		
		initThisPanel();
		
		add( createTableNumberPanel( row, col ), BorderLayout.NORTH );
		add( baseMonitor = createBaseMonitor( GameMonitorLoader.getTableNumber( row, col) ), BorderLayout.CENTER );
		
	}
	
	private void initThisPanel(){
		
		setBorder( new BevelBorder(BevelBorder.RAISED) );
		setLayout( new BorderLayout() );
		
	}
	
	/**
	 * 
	 * 본 panel의 baseMonitor를 새로고침해주는 매서드
	 * 
	 * @param row 본 panel의 row 위치
	 * @param col 본 panel의 col 위치
	 */
	private void refreshBaseMonitor( int tableNumber ) {
		
		if( baseMonitor != null ) {
			
			remove( baseMonitor );
			
			baseMonitor = null;
			
			add( baseMonitor = createBaseMonitor( tableNumber ), BorderLayout.CENTER );
			
			repaint();
			revalidate();
			
		}
		
	}
	
	private JPanel createTableNumberPanel( int row, int col ){
		
		JPanel tableNumberPanel = new TableNumberPanel( row, col, new TableNumberPanel.OnChangeTableNumberListener() {
			
			@Override
			public void onChangeTableNumber() {
				
				refreshBaseMonitor( GameMonitorLoader.getTableNumber( row, col ) );
				
			}
			
		} );
		
		tableNumberPanel.setPreferredSize( new Dimension(0, 60) );
		
		return tableNumberPanel;
		
	}
	
	private JPanel createBaseMonitor ( int tableNumber ){
		
		JPanel baseMonitor = new JPanel();
		
		JScrollPane gameListPanelViewer = new JScrollPane();
		JPanel gameListPanel = new JPanel();						
		ArrayList<JLabel> gameLabelList = GameFeeInfoConvertor.convertToLabelList( GameListLoader.getNonPaidGameFeeInfoList( tableNumber ) );
		
		int gameLabelHeight = 50;
		
		gameListPanel.setPreferredSize( new Dimension( 0, gameLabelHeight * ( gameLabelList.size() + 1 ) ) );
		gameListPanel.setBorder( new BevelBorder( BevelBorder.RAISED ) );
		gameListPanel.setLayout( new GridLayout( gameLabelList.size() + 1, 1 ) );
		
		if( gameLabelList.size() == 0 ){
			
			JLabel gameLabel = new JLabel();
					
			if( tableNumber == 0)	gameLabel.setText( "테이블 없음" );
			else	gameLabel.setText( "게임 없음" );
				
			gameLabel.setFont( FontProvider.getDefaultFont() );
			gameLabel.setHorizontalAlignment( JLabel.CENTER );
			
			gameListPanel.add( gameLabel );
				
		} else {
		
			for( int index = 0; index < gameLabelList.size(); index++ ){
			
				gameListPanel.add( gameLabelList.get(index) );
		
			}
		
		}
		
		gameListPanelViewer.setViewportView( gameListPanel );
		gameListPanelViewer.setPreferredSize( new Dimension( 0, 200 ) );
		
		JPanel gameControler = new JPanel();						
		
		JPanel totalFeeInfoViewer = new JPanel();					
		JLabel totalUsedTimeLabel = new JLabel("게임 없음");			
		JLabel totalFeeLabel = new JLabel();						
		
		JPanel buttonViewer = new JPanel();				
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
		
		totalFeeInfoViewer.setPreferredSize( new Dimension(200, 100) );
		totalFeeInfoViewer.setLayout( new GridLayout(2, 1) );
		totalFeeInfoViewer.add( totalUsedTimeLabel );
		totalFeeInfoViewer.add( totalFeeLabel );
		
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
					
					if( gameLabelList.size() > 0 ) {
						
						new PaymentFrame( tableNumber, new PaymentFrame.OnPaymentCompleteListener() {
							
							@Override
							public void onPaymentComplete() {

								refreshBaseMonitor( tableNumber );
								
							}
							
						} );
						
					}
					
				}
				
			});
			
			startButton.addActionListener( new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {

					if( !GameMonitorLoader.getIsInUse( tableNumber ) ) {
						
						if( !GameMonitorModifier.useThisGameMonitor( tableNumber ) ) {	
							
							return;
					
						}
					
					} 
					
					GameMonitor.this.remove( GameMonitor.this.baseMonitor );
					GameMonitor.this.add( playingMonitor = createPlayingMonitor( tableNumber ) );
					
					GameMonitor.this.baseMonitor = null;
					
					GameMonitor.this.repaint();
					GameMonitor.this.revalidate();
					
				}
				
			} );
			
		}
		
		buttonViewer.setPreferredSize( new Dimension( 200, 100 ) );
		buttonViewer.setLayout( new GridLayout( 1, 2 ) );
		
		buttonViewer.add( payButton );
		buttonViewer.add( startButton );
		
		gameControler.setBorder( new BevelBorder( BevelBorder.RAISED ) );
		gameControler.setLayout( new BorderLayout() );
		
		gameControler.add( totalFeeInfoViewer, BorderLayout.CENTER );
		gameControler.add( buttonViewer, BorderLayout.EAST );
		
		baseMonitor.setLayout( new BorderLayout() );
		baseMonitor.add( gameListPanelViewer, BorderLayout.CENTER );
		baseMonitor.add( gameControler, BorderLayout.SOUTH );
		
		return baseMonitor;
		
	}
	
	private JPanel createPlayingMonitor( int tableNumber ){
		
		JPanel playingScreenPanel = new PlayingScreenPanel( tableNumber, new PlayingScreenPanel.OnGameEndListener() {
			
			@Override
			public void onGameEnd() {
				
				GameMonitor.this.remove( GameMonitor.this.playingMonitor );
				GameMonitor.this.add( baseMonitor = createBaseMonitor( tableNumber ) );
				
				GameMonitor.this.playingMonitor = null; 
				
				GameMonitor.this.repaint();
				GameMonitor.this.revalidate();
				
			}
			
		} );
		
		return playingScreenPanel;
		
	}

}
