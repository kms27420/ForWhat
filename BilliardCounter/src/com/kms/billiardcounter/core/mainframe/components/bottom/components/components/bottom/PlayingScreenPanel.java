package com.kms.billiardcounter.core.mainframe.components.bottom.components.components.bottom;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import com.kms.billiardcounter.core.contentspaneupdater.ContentsPaneUpdater;
import com.kms.billiardcounter.dao.basefee.BaseFeeLoader;
import com.kms.billiardcounter.dao.gamelist.GameListTableUpdater;
import com.kms.billiardcounter.dao.nonpaidgames.NonPaidGamesLoader;
import com.kms.billiardcounter.font.FontProvider;
import com.kms.billiardcounter.support.GameFeeInfo;

/**
 * 
 * 사용 중인 당구대의 사용 시간과 요금을 보여주는 panel
 * 
 * @author Kwon
 *
 */

public class PlayingScreenPanel extends JPanel {
	
	public PlayingScreenPanel( int tableNumber, ContentsPaneUpdater contentsPaneUpdater ){
		
		initThisPanel();
		
		add( createFeeInfoControlPanel( tableNumber, contentsPaneUpdater ) );
		
		repaint();
		revalidate();
		
	}
	
	private void initThisPanel(){
		
		setLayout( new GridLayout(1, 1) );
		
	}
	
	private class FeeCalculator extends Thread{
		
		private final int BASE_FEE_PER_MINUTE = BaseFeeLoader.getBaseFeePerMinute();
		private final int BASE_FEE_TIME = BaseFeeLoader.getBaseFeeTime();
		private final int FEE_INCREASE_TIME = BaseFeeLoader.getFeeIncreaseTime();
		
		private String date;
		private String startTime;
		
		private int usedTime = 0;
		private int fee = BASE_FEE_PER_MINUTE * BASE_FEE_TIME;
		
		private ContentsPaneUpdater contentsPaneUpdater = null;
		
		private void setContentsPaneUpdater( ContentsPaneUpdater contentsPaneUpdater ){
			
			this.contentsPaneUpdater = contentsPaneUpdater;
			
		}
		
		public void run(){
			
			SimpleDateFormat sdf = new SimpleDateFormat( "yyMMdd HH:mm:ss" );
			
			date = sdf.format( new Date() ).substring( 0, 6 );
			startTime = sdf.format( new Date() ).substring( 7 );
			
			sdf = new SimpleDateFormat("HH:mm:ss");
			
			while(true){
				
				try{
				
					Thread.sleep( 100 );
				
					usedTime++;
					
					if( usedTime > BASE_FEE_TIME && usedTime % FEE_INCREASE_TIME == 1 ){
					
						fee += BASE_FEE_PER_MINUTE * FEE_INCREASE_TIME;
						
					}
					
					contentsPaneUpdater.update();
					
				}catch( InterruptedException e ) { 
				
					//e.printStackTrace();
					
					break;
					
				}
				
			}
			
		}
		
	}
	
	private JPanel createFeeInfoControlPanel( int tableNumber, ContentsPaneUpdater contentsPaneUpdater ){
		
		JPanel feeInfoControlPanel = new JPanel();
		
		JLabel usedTimeLabel = new JLabel( "사용 시간  00:00" );
		JLabel feeLabel = new JLabel();
		
		JButton gameEndButton = new JButton( "게임 종료" );
		
		FeeCalculator calculator = new FeeCalculator();
		
		calculator.setContentsPaneUpdater( new ContentsPaneUpdater(){
			
			public void update(){
				
				usedTimeLabel.setText( "사용 시간  " + String.format( "%02d", calculator.usedTime / 60 ) + ":" + String.format( "%02d", calculator.usedTime % 60 ) );
				feeLabel.setText( "요금 : " + calculator.fee + "원" );
				
			}
			
		} );
		
		usedTimeLabel.setFont( FontProvider.getDefaultFont() );
		usedTimeLabel.setHorizontalAlignment( JLabel.CENTER );
		
		feeLabel.setText( "요금 : " + calculator.BASE_FEE_PER_MINUTE * calculator.BASE_FEE_TIME + "원" );
		feeLabel.setFont( FontProvider.getDefaultFont() );
		feeLabel.setHorizontalAlignment( JLabel.CENTER );
		
		gameEndButton.setFont( FontProvider.getDefaultFont() );
		gameEndButton.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				GameFeeInfo gameFeeInfo = new GameFeeInfo();
				
				calculator.interrupt();
				
				gameFeeInfo.setDate( calculator.date );
				gameFeeInfo.setStartTime( calculator.startTime );
				gameFeeInfo.setEndTime( new SimpleDateFormat( "HH:mm:ss" ).format( new Date() ) );
				gameFeeInfo.setGameNumber( NonPaidGamesLoader.getNonPaidGameFeeInfoList( tableNumber ).size() + 1 );
				gameFeeInfo.setTableNumber( tableNumber );
				gameFeeInfo.setUsedTime( calculator.usedTime );
				gameFeeInfo.setFee( calculator.fee );
				gameFeeInfo.setIsPaid( false );
				
				if( GameListTableUpdater.saveGameFeeInfoToDB( gameFeeInfo ) ){
				
					contentsPaneUpdater.update();
				
				}
				
			}
			
		} );
		
		feeInfoControlPanel.setBorder( new BevelBorder( BevelBorder.RAISED ) );
		feeInfoControlPanel.setLayout( new GridLayout(3, 1) );
		
		feeInfoControlPanel.add( usedTimeLabel );
		feeInfoControlPanel.add( feeLabel );
		feeInfoControlPanel.add( gameEndButton );
		
		calculator.start();
		
		return feeInfoControlPanel;
		
	}
	
}
