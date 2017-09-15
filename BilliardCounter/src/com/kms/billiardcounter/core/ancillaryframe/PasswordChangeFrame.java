package com.kms.billiardcounter.core.ancillaryframe;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import com.kms.billiardcounter.dao.account.AccountUpdater;
import com.kms.billiardcounter.font.FontProvider;

/**
 * 
 * 비밀번호를 변경하는 frame 클래스
 * 
 * @author Kwon
 *
 */
public class PasswordChangeFrame extends JFrame {

	public PasswordChangeFrame() {
		
		initThisFrame();
		
		add( createPasswordChangePanel() );
		
		repaint();
		revalidate();
		
		setVisible( true );
		
	}
	
	private void initThisFrame() {
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = new Dimension( 400, 300 );
		
		setTitle( "비밀번호 변경" );
		
		setLayout( new GridLayout( 1, 1 ) );
		setLocation( screenSize.width / 2 - frameSize.width / 2, screenSize.height / 2 - frameSize.height / 2);
		setSize( frameSize );
		
		setDefaultCloseOperation( DISPOSE_ON_CLOSE );
		
	}
	
	private JPanel createPasswordChangePanel() {
		
		JPanel passwordChangePanel = new JPanel();
		
		JLabel passwordLabel = new JLabel( "비밀번호 입력" );
		JLabel passwordReInputLabel = new JLabel( "비밀번호 재입력" );
		
		JPasswordField passwordInputField = new JPasswordField();
		JPasswordField passwordReInputField = new JPasswordField();
		
		JButton confirmButton = new JButton( "확인" );
		
		passwordLabel.setHorizontalAlignment( JLabel.CENTER );
		passwordLabel.setFont( FontProvider.getDefaultFont() );
		
		passwordReInputLabel.setHorizontalAlignment( JLabel.CENTER );
		passwordReInputLabel.setFont( FontProvider.getDefaultFont() );
		
		confirmButton.setFont( FontProvider.getDefaultFont() );
		confirmButton.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				String inputedPassword = String.copyValueOf( passwordInputField.getPassword() );
				String reInputedPassword = String.copyValueOf( passwordReInputField.getPassword() );
				
				if( inputedPassword.equals( reInputedPassword ) ) {
				
					if( AccountUpdater.changePassword( String.copyValueOf( passwordInputField.getPassword() ) ) ) {
						
						PasswordChangeFrame.this.dispose();
						
					}
				
				} else {
					
					Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
					Dimension frameSize = new Dimension( 400, 200 );
					
					JFrame alertFrame = new JFrame( "비밀번호 재확인 요망" );
					
					JLabel alertLabel = new JLabel( "입력한 두 비밀번호가 일치하도록 입력해주세요." );
					
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
		
		passwordChangePanel.setLayout( new GridLayout( 3, 2 ) );
		
		passwordChangePanel.add( passwordLabel );
		passwordChangePanel.add( passwordInputField );
		passwordChangePanel.add( passwordReInputLabel );
		passwordChangePanel.add( passwordReInputField );
		passwordChangePanel.add( new JPanel() );
		passwordChangePanel.add( confirmButton );
		
		return passwordChangePanel;
		
	}
	
}
