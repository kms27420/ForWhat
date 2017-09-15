package com.kms.billiardcounter.core.ancillaryframe.admin_approval;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import com.kms.billiardcounter.dao.account.AccountCheck;
import com.kms.billiardcounter.font.FontProvider;

/**
 * 
 * 계정 주인임을 확인한 후 새로운 창을 열어주는 frame 클래스
 * 
 * @author Kwon
 *
 */
public class AdminDoorLockFrame extends JFrame{

	public AdminDoorLockFrame( FrameOpener frameOpener ) {
		
		initThisFrame();
		
		add( createAdminDoorLockPanel( frameOpener ) );
		
		repaint();
		revalidate();
		
		setVisible( true );
		
	}
	
	private void initThisFrame() {
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = new Dimension( 400, 200 );
		
		setLayout( new GridLayout( 1, 1 ) );
		setLocation( screenSize.width / 2 - frameSize.width / 2, screenSize.height / 2 - frameSize.height / 2);
		setSize( frameSize );
		
		setTitle( "관리자 확인" );
		
		setDefaultCloseOperation( DISPOSE_ON_CLOSE );
		
	}
	
	private JPanel createAdminDoorLockPanel( FrameOpener frameOpener ) {
		
		JPanel adminDoorLockPanel = new JPanel();
		
		JPanel topPanel = new JPanel();
		
		JLabel passwordLabel = new JLabel( "비밀번호" );
		JPasswordField passwordInputField = new JPasswordField();
		
		passwordLabel.setPreferredSize( new Dimension( 100, 40 ) );
		passwordLabel.setFont( FontProvider.getDefaultFont() );
		passwordLabel.setHorizontalAlignment( JLabel.CENTER );
		
		passwordInputField.setPreferredSize( new Dimension( 200, 40 ) );
		
		topPanel.add( passwordLabel );
		topPanel.add( passwordInputField );
		
		JPanel bottomPanel = new JPanel();
		
		JButton confirmButton = new JButton( "확인" );
	
		confirmButton.setPreferredSize( new Dimension( 150, 40 ) );
		confirmButton.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				if( AccountCheck.isItCorrectPassword( String.copyValueOf( passwordInputField.getPassword() ) ) ) {
				
					AdminDoorLockFrame.this.dispose();
				
					frameOpener.openFrame();
				
				} else {
					
					Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
					Dimension frameSize = new Dimension( 300, 150 );
					
					JFrame alertFrame = new JFrame( "접근 실패" );
					
					JLabel alertLabel = new JLabel( "비밀번호를 다시 확인하십시오." );
					
					alertLabel.setHorizontalAlignment( JLabel.CENTER );
					alertLabel.setFont( FontProvider.getDefaultFont() );
					
					alertFrame.setLayout( new GridLayout( 1, 1 ) );
					alertFrame.setLocation( screenSize.width / 2 - frameSize.width / 2, screenSize.height / 2 - frameSize.height / 2 );
					alertFrame.setSize( frameSize );
					alertFrame.setResizable( false );
					
					alertFrame.setDefaultCloseOperation( DISPOSE_ON_CLOSE );
					
					alertFrame.add( alertLabel );
					
					alertFrame.setVisible( true );
					
				}
				
			}
			
		} );
		
		bottomPanel.setPreferredSize( new Dimension( 0, 50 ) );
		
		bottomPanel.add( confirmButton );
		
		adminDoorLockPanel.setLayout( new BorderLayout() );
		adminDoorLockPanel.setBorder( BorderFactory.createEmptyBorder(20 , 0 , 10 , 0) );

		adminDoorLockPanel.add( topPanel, BorderLayout.CENTER );
		adminDoorLockPanel.add( bottomPanel, BorderLayout.SOUTH );
		
		return adminDoorLockPanel;
		
	}
	
}
