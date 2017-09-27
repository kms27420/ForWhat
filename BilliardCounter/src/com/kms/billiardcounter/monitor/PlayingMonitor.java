package com.kms.billiardcounter.monitor;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import com.kms.billiardcounter.database.base_fee.BaseFeeLoader;
import com.kms.billiardcounter.database.game_list.GameListModifier;
import com.kms.billiardcounter.database.game_monitor.GameMonitorLoader;
import com.kms.billiardcounter.database.game_list.GameListLoader;
import com.kms.billiardcounter.font.FontProvider;
import com.kms.billiardcounter.support.BaseFeeInfo;
import com.kms.billiardcounter.support.GameFeeInfo;

/**
 * 
 * 사용 중인 당구대의 사용 시간과 요금을 보여주는 panel
 * 
 * @author Kwon
 *
 */

public class PlayingMonitor extends JPanel {
	
	public interface OnEndGameListener {
		
		public void onEndGame();
		
	}
	
	private JLabel usedTimeLabel;
	private JLabel feeLabel;
	
	private  FeeCalculator calculator;
	
	public PlayingMonitor( int row, int col, OnEndGameListener onEndGameListener ){
		
		initComponents();
		
		setBorder( new BevelBorder( BevelBorder.RAISED ) );
		setLayout( new GridLayout( 3, 1 ) );
		add( usedTimeLabel );
		add( feeLabel );
		add( createGameEndButton( row, col, onEndGameListener ) );
		
		addPropertyChangeListener( new PropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				
				if( evt.getPropertyName() == "ancestor" && evt.getNewValue() != null && evt.getOldValue() == null ) {
					
					refresh();
					
				}
				
			}
			
		} );
		
	}
	
	private void initComponents(){
		
		usedTimeLabel = new JLabel();
		usedTimeLabel.setFont( FontProvider.getDefaultFont() );
		usedTimeLabel.setHorizontalAlignment( JLabel.CENTER );
		
		feeLabel = new JLabel();
		feeLabel.setFont( FontProvider.getDefaultFont() );
		feeLabel.setHorizontalAlignment( JLabel.CENTER );
		
	}
	
	private JButton createGameEndButton( int row, int col, OnEndGameListener onEndGameListener ) {
		
		JButton gameEndButton = new JButton( "게임 종료" );
		
		gameEndButton.setFont( FontProvider.getDefaultFont() );
		gameEndButton.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				int tableNumber = GameMonitorLoader.getTableNumber( row, col );
				GameFeeInfo gameFeeInfo = new GameFeeInfo();
				
				calculator.interrupt();
				
				gameFeeInfo.setDate( calculator.date );
				gameFeeInfo.setStartTime( calculator.startTime );
				gameFeeInfo.setEndTime( new SimpleDateFormat( "HH:mm:ss" ).format( new Date() ) );
				gameFeeInfo.setGameNumber( GameListLoader.getNonPaidGameFeeInfoList( tableNumber ).size() + 1 );
				gameFeeInfo.setTableNumber( tableNumber );
				gameFeeInfo.setUsedTime( calculator.usedTime );
				gameFeeInfo.setFee( calculator.fee );
				gameFeeInfo.setIsPaid( false );
				
				if( GameListModifier.saveGameFeeInfoToDB( gameFeeInfo ) ){
				
					onEndGameListener.onEndGame();
				
				}
				
			}
			
		} );
		
		return gameEndButton;
		
	}
	
	private void refresh() {
		
		calculator = null;
		calculator = new FeeCalculator();
		
		usedTimeLabel.setText( "사용 시간  00:00" );
		feeLabel.setText( "요금 : " + calculator.BASE_FEE_INFO.getBaseFeePerMinute() * calculator.BASE_FEE_INFO.getBaseFeeTime() + "원" );
		
		calculator.start();
		
	}
	
	private class FeeCalculator extends Thread {
		
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
					
					usedTimeLabel.setText( "사용 시간  " + String.format( "%02d", calculator.usedTime / 60 ) + ":" + String.format( "%02d", calculator.usedTime % 60 ) );
					feeLabel.setText( "요금 : " + calculator.fee + "원" );
					
				}catch( InterruptedException e ) { 
				
					//e.printStackTrace();
					
					break;
					
				}
				
			}
			
		}
		
	}
	
}
