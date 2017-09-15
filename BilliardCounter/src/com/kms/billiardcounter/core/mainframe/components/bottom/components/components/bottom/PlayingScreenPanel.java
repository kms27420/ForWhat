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
import com.kms.billiardcounter.support.gamefeeinfo.GameFeeInfo;

/**
 * 
 * 사용 중인 당구대의 사용 시간과 요금을 보여주는 panel
 * 
 * @author Kwon
 *
 */

public class PlayingScreenPanel extends JPanel {
	
	private final FeeCalculator CALCULATOR = new FeeCalculator();
	
	public PlayingScreenPanel( int tableNumber, ContentsPaneUpdater contentsPaneUpdater ){
		
		initThisPanel();
		
		JLabel[] usedTimeAndFeeLabels = createUsedTimeAndFeeLabels();
		
		add( usedTimeAndFeeLabels[0] );
		add( usedTimeAndFeeLabels[1] );
		add( createGameEndButton( tableNumber, contentsPaneUpdater ) );
		
		repaint();
		revalidate();
		
		CALCULATOR.start();
		
	}
	
	private void initThisPanel(){
		
		setBorder( new BevelBorder( BevelBorder.RAISED ) );
		setLayout( new GridLayout( 3, 1 ) );
		
	}
	
	private class FeeCalculator extends Thread{
		
		private final int BASE_FEE_PER_MINUTE = BaseFeeLoader.getBaseFeePerMinute();
		private final int BASE_FEE_TIME = BaseFeeLoader.getBaseFeeTime();
		private final int FEE_INCREASE_TIME = BaseFeeLoader.getFeeIncreaseTime();
		
		private String date;
		private String startTime;
		
		private int usedTime = 0;
		private int fee = BASE_FEE_PER_MINUTE * BASE_FEE_TIME;
		
		private ContentsPaneUpdater usedTimeAndFeeLabelUpdater = null;
		
		private void setUsedTimeAndFeeLabelUpdater( ContentsPaneUpdater usedTimeAndFeeLabelUpdater ){
			
			this.usedTimeAndFeeLabelUpdater = usedTimeAndFeeLabelUpdater;
			
		}
		
		public void run(){
			
			String dateAndStartTime = new SimpleDateFormat( "yyMMdd HH:mm:ss" ).format( new Date() );
			
			date = dateAndStartTime.substring( 0, 6 );
			startTime = dateAndStartTime.substring( 7 );
			
			while(true){
				
				try{
				
					Thread.sleep( 100 );
				
					usedTime++;
					
					if( usedTime > BASE_FEE_TIME && usedTime % FEE_INCREASE_TIME == 1 ){
					
						fee += BASE_FEE_PER_MINUTE * FEE_INCREASE_TIME;
						
					}
					
					usedTimeAndFeeLabelUpdater.update();
					
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
		
		usedTimeAndFeeLabels[1].setText( "요금 : " + CALCULATOR.BASE_FEE_PER_MINUTE * CALCULATOR.BASE_FEE_TIME + "원" );
		usedTimeAndFeeLabels[1].setFont( FontProvider.getDefaultFont() );
		usedTimeAndFeeLabels[1].setHorizontalAlignment( JLabel.CENTER );

		CALCULATOR.setUsedTimeAndFeeLabelUpdater( new ContentsPaneUpdater(){
			
			public void update(){
				
				usedTimeAndFeeLabels[0].setText( "사용 시간  " + String.format( "%02d", CALCULATOR.usedTime / 60 ) + ":" + String.format( "%02d", CALCULATOR.usedTime % 60 ) );
				usedTimeAndFeeLabels[1].setText( "요금 : " + CALCULATOR.fee + "원" );
				
			}
			
		} );

		return usedTimeAndFeeLabels;
		
	}
	
	private JButton createGameEndButton( int tableNumber, ContentsPaneUpdater contentsPaneUpdater ) {
		
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
				gameFeeInfo.setGameNumber( NonPaidGamesLoader.getNonPaidGameFeeInfoList( tableNumber ).size() + 1 );
				gameFeeInfo.setTableNumber( tableNumber );
				gameFeeInfo.setUsedTime( CALCULATOR.usedTime );
				gameFeeInfo.setFee( CALCULATOR.fee );
				gameFeeInfo.setIsPaid( false );
				
				if( GameListTableUpdater.saveGameFeeInfoToDB( gameFeeInfo ) ){
				
					contentsPaneUpdater.update();
				
				}
				
			}
			
		} );
		
		return gameEndButton;
		
	}
	
}
