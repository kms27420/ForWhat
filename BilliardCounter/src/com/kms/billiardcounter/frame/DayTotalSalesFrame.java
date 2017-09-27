package com.kms.billiardcounter.frame;

import java.awt.GridLayout;
import java.awt.Point;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.kms.billiardcounter.database.game_list.GameListLoader;
import com.kms.billiardcounter.font.FontProvider;
import com.kms.billiardcounter.size.DeviceSize;
import com.kms.billiardcounter.size.FrameSize;

/**
 * 
 * 금일 매출을 확인하는 프레임
 * 
 * @author Kwon
 *
 */
public class DayTotalSalesFrame extends JFrame {

	private static final DayTotalSalesFrame INSTANCE = new DayTotalSalesFrame();
	
	private DayTotalSalesFrame() {
		
		setPreferredSize( FrameSize.getDayTotalSalesFrameSize() );
		pack();
		setLocation( ( DeviceSize.getScreenSize().width - FrameSize.getDayTotalSalesFrameSize().width ) / 2, 
				( DeviceSize.getScreenSize().height - FrameSize.getDayTotalSalesFrameSize().height ) / 2 );
		setResizable( false );
		
		setTitle( "금일 매출" );
		setDefaultCloseOperation( DISPOSE_ON_CLOSE );
		
		getContentPane().setLayout( new GridLayout( 1, 1 ) );
		
	}
	
	private JPanel createDayTotalSalesPanel() {
		
		JPanel daySalesPanel = new JPanel();
		
		JLabel daySalesLabel = new JLabel();
		
		SimpleDateFormat sdf = new SimpleDateFormat( "yyMMdd" );
		
		String date = sdf.format( new Date() );
		
		daySalesLabel.setText( "오늘 총 매출 : " + String.format( "%,10d" , GameListLoader.getDaySales( date ) ) + " 원"  );
		daySalesLabel.setFont( FontProvider.getDefaultFont() );
		daySalesLabel.setHorizontalAlignment( JLabel.CENTER );
		
		daySalesPanel.setLayout( new GridLayout( 1, 1 ) );
		daySalesPanel.add( daySalesLabel );
		
		return daySalesPanel;
		
	}
	
	public static void showOnScreen() {
		
		INSTANCE.getContentPane().removeAll();
		INSTANCE.getContentPane().add( INSTANCE.createDayTotalSalesPanel() ); 
		
		INSTANCE.setVisible( true );
		
	}
	
}
