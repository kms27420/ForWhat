package com.kms.billiardcounter.core.ancillaryframe;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.kms.billiardcounter.core.contentspaneupdater.ContentsPaneUpdater;
import com.kms.billiardcounter.core.mainframe.BilliardCounterFrame;
import com.kms.billiardcounter.core.string.NumericManufacturer;
import com.kms.billiardcounter.dao.basefee.BaseFeeLoader;
import com.kms.billiardcounter.dao.basefee.BaseFeeTableUpdater;
import com.kms.billiardcounter.font.FontProvider;

/**
 * 
 * 기본 요금을 설정하는 프레임
 * 
 * @author Kwon
 *
 */
public class BaseFeeSettingFrame extends JFrame{

	public BaseFeeSettingFrame() {
		
		initThisFrame();
		
		add( createBaseFeeSettingPanel() );
		
		repaint();
		revalidate();
		
		setVisible( true );
		
	}
	
	private void initThisFrame() {
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = new Dimension( 500, 400 );
		
		setLocation( screenSize.width / 2 - frameSize.width / 2, screenSize.height / 2 - frameSize.height / 2 );
		setSize( frameSize );
		
		setLayout( new GridLayout( 1, 1 ) );
		
		setDefaultCloseOperation( DISPOSE_ON_CLOSE );
		
		if( !BaseFeeLoader.isBaseFeeInited() ) {
			
			setDefaultCloseOperation( EXIT_ON_CLOSE );
			
		}
		
	}
	
	private JPanel createBaseFeeSettingPanel() {
		
		JPanel baseFeeSettingPanel = new JPanel();
		
		JPanel topPanel = new JPanel();
		
		JLabel baseFeePerMinuteLabel = new JLabel( "1분당 요금" );
		JTextField baseFeePerMinuteTextField = new JTextField();
		
		JLabel baseFeeTimeLabel = new JLabel( "기본 요금 시간" );
		JTextField baseFeeTimeTextField = new JTextField();
		
		JLabel feeIncreaseTimeLabel = new JLabel( "요금 인상 시간" );
		JTextField feeIncreaseTimeTextField = new JTextField();
		
		JPanel bottomPanel = new JPanel();
		
		JButton saveContentsButton = new JButton( "완료" );
		
		baseFeePerMinuteLabel.setFont( FontProvider.getDefaultFont() );
		baseFeePerMinuteLabel.setHorizontalAlignment( JLabel.CENTER );
		
		baseFeeTimeLabel.setFont( FontProvider.getDefaultFont() );
		baseFeeTimeLabel.setHorizontalAlignment( JLabel.CENTER );
		
		feeIncreaseTimeLabel.setFont( FontProvider.getDefaultFont() );
		feeIncreaseTimeLabel.setHorizontalAlignment( JLabel.CENTER );
		
		topPanel.setPreferredSize( new Dimension( 0, 300 ) );
		topPanel.setLayout( new GridLayout( 3, 2 ) );
		
		topPanel.add( baseFeePerMinuteLabel );
		topPanel.add( baseFeePerMinuteTextField );
		topPanel.add( baseFeeTimeLabel );
		topPanel.add( baseFeeTimeTextField );
		topPanel.add( feeIncreaseTimeLabel );
		topPanel.add( feeIncreaseTimeTextField );
		
		saveContentsButton.setFont( FontProvider.getDefaultFont() );
		saveContentsButton.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				int baseFeePerMinute = NumericManufacturer.getIntConsistingOnlyOfNumeric( baseFeePerMinuteTextField.getText() );
				int baseFeeTime = NumericManufacturer.getIntConsistingOnlyOfNumeric( baseFeeTimeTextField.getText() );
				int feeIncreaseTime = NumericManufacturer.getIntConsistingOnlyOfNumeric( feeIncreaseTimeTextField.getText() );
				
				boolean isBaseFeeInited = BaseFeeLoader.isBaseFeeInited();
				
				if( BaseFeeTableUpdater.updateBaseFeeInfoToDB( baseFeePerMinute, baseFeeTime, feeIncreaseTime ) ) {
				
					BaseFeeSettingFrame.this.dispose();
					
					if( !isBaseFeeInited )	new BilliardCounterFrame();
					
				}
				
			}
			
		} );
		
		bottomPanel.setPreferredSize( new Dimension( 0, 100 ) );
		bottomPanel.setLayout( new GridLayout( 1, 1) );
		
		bottomPanel.add( saveContentsButton );
		
		baseFeeSettingPanel.setLayout( new BorderLayout() );
		
		baseFeeSettingPanel.add( topPanel, BorderLayout.CENTER );
		baseFeeSettingPanel.add( bottomPanel, BorderLayout.SOUTH );
		
		return baseFeeSettingPanel;
		
	}
	
}
