package com.kms.billiardcounter.core.ancillaryframe;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.kms.billiardcounter.dao.daysales.DaySalesLoader;
import com.kms.billiardcounter.font.FontProvider;

/**
 * 
 * 금일 매출을 확인하는 프레임
 * 
 * @author Kwon
 *
 */
public class DayTotalSalesFrame extends JFrame {

	public DayTotalSalesFrame() {
		
		initThisFrame();
		
		add( createDayTotalSalesPanel() ); 
		
	}
	
	private void initThisFrame() {
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = new Dimension( 500, 400 );
		
		setLocation( screenSize.width / 2 - frameSize.width / 2, screenSize.height / 2 - frameSize.height / 2 );
		setSize( frameSize );
		
		setLayout( new GridLayout( 1, 1 ) );
		
		setDefaultCloseOperation( DISPOSE_ON_CLOSE );
		
		setVisible( true );
		
	}
	
	private JPanel createDayTotalSalesPanel() {
		
		JPanel daySalesPanel = new JPanel();
		
		JLabel daySalesLabel = new JLabel();
		
		SimpleDateFormat sdf = new SimpleDateFormat( "yyMMdd" );
		
		String date = sdf.format( new Date() );
		
		daySalesLabel.setText( "오늘 총 매출 : " + String.format( "%,10d" , DaySalesLoader.getDaySales( date ) ) + " 원"  );
		daySalesLabel.setFont( FontProvider.getDefaultFont() );
		daySalesLabel.setHorizontalAlignment( JLabel.CENTER );
		
		daySalesPanel.setLayout( new GridLayout( 1, 1 ) );
		daySalesPanel.add( daySalesLabel );
		
		return daySalesPanel;
		
	}
	
}
