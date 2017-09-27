package com.kms.billiardcounter.frame;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import com.kms.billiardcounter.database.account.AccountModifier;
import com.kms.billiardcounter.font.FontProvider;
import com.kms.billiardcounter.size.DeviceSize;
import com.kms.billiardcounter.size.FrameSize;

/**
 * 
 * 비밀번호를 변경하는 frame 클래스
 * 
 * @author Kwon
 *
 */
public class PasswordChangeFrame extends JFrame {

	private static final PasswordChangeFrame INSTANCE = new PasswordChangeFrame();
	
	private PasswordChangeFrame() {
		
		setPreferredSize( FrameSize.getPasswordChangeFrameSize() );
		pack();
		setLocation( ( DeviceSize.getScreenSize().width - FrameSize.getPasswordChangeFrameSize().width ) / 2, 
				( DeviceSize.getScreenSize().height - FrameSize.getPasswordChangeFrameSize().height ) / 2 );
		setResizable( false );
		
		setTitle( "비밀번호 변경" );
		setDefaultCloseOperation( DISPOSE_ON_CLOSE );
		
		getContentPane().setLayout( new GridLayout( 1, 1 ) );
		getContentPane().add( createPasswordChangePanel() );
		
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
				
				int passwordMinLength = AccountModifier.PASSWORD_MIN_LENGTH;
				int passwordMaxLength = AccountModifier.PASSWORD_MAX_LENGTH;
				
				if( inputedPassword.length() >= passwordMinLength && inputedPassword.length() <= passwordMaxLength 
					&& inputedPassword.equals( reInputedPassword ) ) {
				
					if( AccountModifier.changePassword( String.copyValueOf( passwordInputField.getPassword() ) ) ) {
						
						PasswordChangeFrame.this.dispose();
						
					}
				
				} else {
					
					String alertSentence;
					
					if( inputedPassword.length() < passwordMinLength || inputedPassword.length() > passwordMaxLength ) {
						
						alertSentence = "비밀번호를 " + passwordMinLength + "~" + passwordMaxLength + " 자리로 설정해주십시오.";
						
					} else	alertSentence = "입력한 두 비밀번호가 일치하도록 입력해주세요.";
					
					AlertFrame.showOnScreen( "비밀번호 재확인 요망", alertSentence, null );
					
				}
				
				passwordInputField.setText( "" );
				passwordReInputField.setText( "" );
				
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
	
	public static void showOnScreen() {
		
		INSTANCE.setVisible( true );
		
	}
	
}
