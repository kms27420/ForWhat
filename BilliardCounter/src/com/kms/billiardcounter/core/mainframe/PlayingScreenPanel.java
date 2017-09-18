package com.kms.billiardcounter.core.mainframe;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import com.kms.billiardcounter.database.base_fee.BaseFeeLoader;
import com.kms.billiardcounter.database.game_list.GameListModifier;
import com.kms.billiardcounter.database.game_list.GameListLoader;
import com.kms.billiardcounter.font.FontProvider;
import com.kms.billiardcounter.support.BaseFeeInfo;
import com.kms.billiardcounter.support.gamefeeinfo.GameFeeInfo;

/**
 * 
 * 사용 중인 당구대의 사용 시간과 요금을 보여주는 panel
 * 
 * @author Kwon
 *
 */

public class PlayingScreenPanel extends JPanel {
	
	public interface OnGameEndListener {
		
		public void onGameEnd();
		
	}
	
	private interface OnUsedTimeIncreaseListener {
		
		public void onUsedTimeIncrease();
		
	}
	
	private OnUsedTimeIncreaseListener onUsedTimeIncreaseListener;
	
	private final FeeCalculator CALCULATOR = new FeeCalculator();
	
	public PlayingScreenPanel( int tableNumber, OnGameEndListener onGameEndListener ){
		
		initThisPanel();
		
		JLabel[] usedTimeAndFeeLabels = createUsedTimeAndFeeLabels();
		
		add( usedTimeAndFeeLabels[0] );
		add( usedTimeAndFeeLabels[1] );
		add( createGameEndButton( tableNumber, onGameEndListener ) );
		
		CALCULATOR.start();
		
	}
	
	private void initThisPanel(){
		
		setBorder( new BevelBorder( BevelBorder.RAISED ) );
		setLayout( new GridLayout( 3, 1 ) );
		
	}
	
	private class FeeCalculator extends Thread{
		
		private final BaseFeeInfo BASE_FEE_INFO = BaseFeeLoader.loadBaseFeeInfo();
		
		private String date;
		private String startTime;
		
		private int usedTime = 0;
		private int fee = BASE_FEE_INFO.getBaseFeePerMinute() * BASE_FEE_INFO.getBaseFeeTime();
		
		public void run(){
			
			String dateAndStartTime = new SimpleDateFormat( "yyMMdd HH:mm:ss" ).format( new Date() );
			
			date = dateAndStartTime.substring( 0, 6 );
			startTime = dateAndStartTime.substring( 7 );
			
			while(true){
				
				try{
				
					Thread.sleep( 100 );
				
					usedTime++;
					
					if( usedTime > BASE_FEE_INFO.getBaseFeeTime() && usedTime % BASE_FEE_INFO.getFeeIncreaseTime() == 1 ){
					
						fee += BASE_FEE_INFO.getBaseFeePerMinute() * BASE_FEE_INFO.getFeeIncreaseTime();
						
					}
					
					onUsedTimeIncreaseListener.onUsedTimeIncrease();
					
				}catch( InterruptedException e ) { 
				
					//e.printStackTrace();
					
					break;
					
				}
				
			}
			
		}
		
	}
	
	private JLabel[] createUsedTimeAndFeeLabels() {
		
		JLabel[] usedTimeAndFeeLabels = new JLabel[2];
		
		usedTimeAndFeeLabels[0] = new JLabel( "사용 시간  00:00" );
		
		usedTimeAndFeeLabels[0].setFont( FontProvider.getDefaultFont() );
		usedTimeAndFeeLabels[0].setHorizontalAlignment( JLabel.CENTER );
		
		usedTimeAndFeeLabels[1] = new JLabel();
		
		usedTimeAndFeeLabels[1].setText( "요금 : " + CALCULATOR.BASE_FEE_INFO.getBaseFeePerMinute() * CALCULATOR.BASE_FEE_INFO.getBaseFeeTime() + "원" );
		usedTimeAndFeeLabels[1].setFont( FontProvider.getDefaultFont() );
		usedTimeAndFeeLabels[1].setHorizontalAlignment( JLabel.CENTER );

		onUsedTimeIncreaseListener = new OnUsedTimeIncreaseListener() {
			
			@Override
			public void onUsedTimeIncrease() {

				usedTimeAndFeeLabels[0].setText( "사용 시간  " + String.format( "%02d", CALCULATOR.usedTime / 60 ) + ":" + String.format( "%02d", CALCULATOR.usedTime % 60 ) );
				usedTimeAndFeeLabels[1].setText( "요금 : " + CALCULATOR.fee + "원" );
				
			}
			
		};

		return usedTimeAndFeeLabels;
		
	}
	
	private JButton createGameEndButton( int tableNumber, OnGameEndListener onGameEndListener ) {
		
		JButton gameEndButton = new JButton( "게임 종료" );
		
		gameEndButton.setFont( FontProvider.getDefaultFont() );
		gameEndButton.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				GameFeeInfo gameFeeInfo = new GameFeeInfo();
				
				CALCULATOR.interrupt();
				
				gameFeeInfo.setDate( CALCULATOR.date );
				gameFeeInfo.setStartTime( CALCULATOR.startTime );
				gameFeeInfo.setEndTime( new SimpleDateFormat( "HH:mm:ss" ).format( new Date() ) );
				gameFeeInfo.setGameNumber( GameListLoader.getNonPaidGameFeeInfoList( tableNumber ).size() + 1 );
				gameFeeInfo.setTableNumber( tableNumber );
				gameFeeInfo.setUsedTime( CALCULATOR.usedTime );
				gameFeeInfo.setFee( CALCULATOR.fee );
				gameFeeInfo.setIsPaid( false );
				
				if( GameListModifier.saveGameFeeInfoToDB( gameFeeInfo ) ){
				
					onGameEndListener.onGameEnd();
				
				}
				
			}
			
		} );
		
		return gameEndButton;
		
	}
	
}
