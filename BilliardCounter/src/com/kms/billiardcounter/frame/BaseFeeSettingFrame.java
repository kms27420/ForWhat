package com.kms.billiardcounter.frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.kms.billiardcounter.database.base_fee.BaseFeeLoader;
import com.kms.billiardcounter.database.base_fee.BaseFeeModifier;
import com.kms.billiardcounter.font.FontProvider;
import com.kms.billiardcounter.size.DeviceSize;
import com.kms.billiardcounter.size.FrameSize;
import com.kms.billiardcounter.support.BaseFeeInfo;
import com.kms.billiardcounter.support.NumericManufacturer;

/**
 * 
 * 기본 요금을 설정하는 프레임
 * 
 * @author Kwon
 *
 */
public class BaseFeeSettingFrame extends JFrame{

	private static final BaseFeeSettingFrame INSTANCE = new BaseFeeSettingFrame();
	
	private BaseFeeSettingFrame() {
		
		setPreferredSize( FrameSize.getBaseFeeSettingFrameSize() );
		pack();
		setLocation( ( DeviceSize.getScreenSize().width - FrameSize.getBaseFeeSettingFrameSize().width ) / 2, 
				( DeviceSize.getScreenSize().height - FrameSize.getBaseFeeSettingFrameSize().height ) / 2 );
		setResizable( false );
		
		setTitle( "기본 요금 설정" );
		
		getContentPane().setLayout( new GridLayout( 1, 1 ) );
		getContentPane().add( createBaseFeeSettingPanel() );
		
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
		
		JButton confirmButton = new JButton( "완료" );
		
		baseFeePerMinuteLabel.setFont( FontProvider.getDefaultFont() );
		baseFeePerMinuteLabel.setHorizontalAlignment( JLabel.CENTER );
		
		baseFeeTimeLabel.setFont( FontProvider.getDefaultFont() );
		baseFeeTimeLabel.setHorizontalAlignment( JLabel.CENTER );
		
		feeIncreaseTimeLabel.setFont( FontProvider.getDefaultFont() );
		feeIncreaseTimeLabel.setHorizontalAlignment( JLabel.CENTER );
		
		topPanel.setPreferredSize( new Dimension( 0, getContentPane().getSize().height * 3 / 4 ) );
		topPanel.setLayout( new GridLayout( 3, 2 ) );
		
		topPanel.add( baseFeePerMinuteLabel );
		topPanel.add( baseFeePerMinuteTextField );
		topPanel.add( baseFeeTimeLabel );
		topPanel.add( baseFeeTimeTextField );
		topPanel.add( feeIncreaseTimeLabel );
		topPanel.add( feeIncreaseTimeTextField );
		
		confirmButton.setFont( FontProvider.getDefaultFont() );
		confirmButton.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				int baseFeePerMinute = NumericManufacturer.getIntConsistingOnlyOfNumeric( baseFeePerMinuteTextField.getText() );
				int baseFeeTime = NumericManufacturer.getIntConsistingOnlyOfNumeric( baseFeeTimeTextField.getText() );
				int feeIncreaseTime = NumericManufacturer.getIntConsistingOnlyOfNumeric( feeIncreaseTimeTextField.getText() );
				
				if( BaseFeeLoader.loadBaseFeeInfo().getIsValid() ) {
					
					if( BaseFeeModifier.changeBaseFeeInfo( new BaseFeeInfo( baseFeePerMinute, baseFeeTime, feeIncreaseTime ) ) ) {
						
						BaseFeeSettingFrame.this.dispose();
						
					}
					
				} else {
					
					if( BaseFeeModifier.saveBaseFeeInfoToDB( new BaseFeeInfo( baseFeePerMinute, baseFeeTime, feeIncreaseTime ) ) ) {
						
						BaseFeeSettingFrame.this.dispose();
						
						MainFrame.showOnScreen();
						
					}
					
				}
				
				baseFeePerMinuteTextField.setText( "" );
				baseFeeTimeTextField.setText( "" );
				feeIncreaseTimeTextField.setText( "" );
				
			}
			
		} );
		
		bottomPanel.setPreferredSize( new Dimension( 0, getContentPane().getSize().height / 4 ) );
		bottomPanel.setLayout( new GridLayout( 1, 1) );
		
		bottomPanel.add( confirmButton );
		
		baseFeeSettingPanel.setLayout( new BorderLayout() );
		
		baseFeeSettingPanel.add( topPanel, BorderLayout.NORTH );
		baseFeeSettingPanel.add( bottomPanel, BorderLayout.SOUTH );
		
		return baseFeeSettingPanel;
		
	}
	
	public static void showOnScreen() {
		
		if( !BaseFeeLoader.loadBaseFeeInfo().getIsValid() ) {
			
			INSTANCE.setDefaultCloseOperation( EXIT_ON_CLOSE );
			
		} else {
			
			INSTANCE.setDefaultCloseOperation( DISPOSE_ON_CLOSE );
			
		}
		
		INSTANCE.setVisible( true );
		
	}
	
}
