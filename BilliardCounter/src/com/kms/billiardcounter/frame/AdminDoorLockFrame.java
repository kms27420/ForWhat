package com.kms.billiardcounter.frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import com.kms.billiardcounter.database.account.AccountCheck;
import com.kms.billiardcounter.font.FontProvider;
import com.kms.billiardcounter.size.DeviceSize;
import com.kms.billiardcounter.size.FrameSize;

/**
 * 
 * 계정 주인임을 확인한 후 새로운 창을 열어주는 frame 클래스
 * 
 * @author Kwon
 *
 */
public class AdminDoorLockFrame extends JFrame {
	
	private FrameOpener frameOpener;
	
	private static final AdminDoorLockFrame INSTANCE = new AdminDoorLockFrame();
	
	private AdminDoorLockFrame() {
		
		setPreferredSize( FrameSize.getAdminDoorLockFrameSize() );
		pack();
		setLocation( ( DeviceSize.getScreenSize().width - FrameSize.getAdminDoorLockFrameSize().width ) / 2, 
				( DeviceSize.getScreenSize().height - FrameSize.getAdminDoorLockFrameSize().height ) / 2 );
		setResizable( false );
		
		setTitle( "관리자 확인" );
		setDefaultCloseOperation( DISPOSE_ON_CLOSE );
		
		getContentPane().setLayout( new GridLayout( 1, 1 ) );
		getContentPane().add( createAdminDoorLockPanel() );
		
	}
	
	private JPanel createAdminDoorLockPanel() {
		
		JPanel adminDoorLockPanel = new JPanel();
		
		JPanel topPanel = new JPanel();
		
		JLabel passwordLabel = new JLabel( "비밀번호" );
		JPasswordField passwordInputField = new JPasswordField();
		
		passwordLabel.setPreferredSize( new Dimension( getContentPane().getSize().width * 4 / 15, 0 ) );
		passwordLabel.setFont( FontProvider.getDefaultFont() );
		passwordLabel.setHorizontalAlignment( JLabel.CENTER );
		
		passwordInputField.setPreferredSize( new Dimension( getContentPane().getSize().width * 8 / 15, 0 ) );
		
		topPanel.setPreferredSize( new Dimension( 0, getContentPane().getSize().height / 5 ) );
		topPanel.setBorder( BorderFactory.createEmptyBorder
				( 0, getContentPane().getSize().width / 10, 0, getContentPane().getSize().width / 10 ) );
		topPanel.setLayout( new BorderLayout() );
		topPanel.add( passwordLabel, BorderLayout.WEST );
		topPanel.add( passwordInputField, BorderLayout.EAST );
		
		JPanel bottomPanel = new JPanel();
		
		JButton confirmButton = new JButton( "확인" );
		
		confirmButton.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				if( AccountCheck.isItCorrectPassword( String.copyValueOf( passwordInputField.getPassword() ) ) ) {
				
					AdminDoorLockFrame.this.dispose();
				
					frameOpener.openFrame();
				
				} else {
					
					AlertFrame.showOnScreen( "접근 실패", "비밀번호를 다시 확인하십시오.", null );
					
				}
				
				passwordInputField.setText( "" );
				
			}
			
		} );
		
		bottomPanel.setPreferredSize( new Dimension( 0, getContentPane().getSize().height / 4 ) );
		bottomPanel.setBorder( BorderFactory.createEmptyBorder
				( 0, getContentPane().getSize().width / 4, 0, getContentPane().getSize().width / 4 ) );
		bottomPanel.setLayout( new BorderLayout() );
		bottomPanel.add( confirmButton, BorderLayout.CENTER );
		
		adminDoorLockPanel.setLayout( new BorderLayout() );
		adminDoorLockPanel.setBorder( BorderFactory.createEmptyBorder
				( getContentPane().getSize().height / 5 , 0 , getContentPane().getSize().height / 10 , 0) );
		adminDoorLockPanel.add( topPanel, BorderLayout.NORTH );
		adminDoorLockPanel.add( bottomPanel, BorderLayout.SOUTH );
		
		return adminDoorLockPanel;
		
	}

	public static void showOnScreen( FrameOpener frameOpener ) {
		
		INSTANCE.frameOpener = frameOpener;
		
		INSTANCE.setVisible( true );
		
	}
	
}
