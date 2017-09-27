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
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.kms.billiardcounter.database.account.AccountCheck;
import com.kms.billiardcounter.database.account.AccountModifier;
import com.kms.billiardcounter.database.base_fee.BaseFeeLoader;
import com.kms.billiardcounter.font.FontProvider;
import com.kms.billiardcounter.size.DeviceSize;
import com.kms.billiardcounter.size.FrameSize;

public class LoginFrame extends JFrame {

	private JPanel loginPanel;
	private JPanel accountCreatePanel;
	
	private static final LoginFrame INSTANCE = new LoginFrame();
	
	private LoginFrame() {
		
		setPreferredSize( FrameSize.getLoginFrameSize() );
		pack();
		setLocation( ( DeviceSize.getScreenSize().width - FrameSize.getLoginFrameSize().width ) / 2, 
				( DeviceSize.getScreenSize().height - FrameSize.getLoginFrameSize().height ) / 2 );
		setResizable( false );
		
		setDefaultCloseOperation( EXIT_ON_CLOSE );
		
		getContentPane().setLayout( new GridLayout( 1, 1 ) );
		
		loginPanel = createLoginPanel();
		accountCreatePanel = createAccountCreatePanel();
		
	}
	
	private JPanel createLoginPanel() {
		
		JPanel loginPanel = new JPanel();
		
		JPanel topPanel = new JPanel();
		
		JLabel idLabel = new JLabel( "아이디" );
		JLabel passwordLabel = new JLabel( "비밀번호" );
		
		JTextField idInputField = new JTextField();
		JPasswordField passwordInputField = new JPasswordField();
		
		JPanel bottomPanel = new JPanel();
		
		JButton confirmButton = new JButton( "로그인" );
		
		idLabel.setHorizontalAlignment( JLabel.CENTER );
		idLabel.setFont( FontProvider.getDefaultFont() );
		
		passwordLabel.setHorizontalAlignment( JLabel.CENTER );
		passwordLabel.setFont( FontProvider.getDefaultFont() );
		
		topPanel.setPreferredSize( new Dimension( 0, getContentPane().getSize().height * 3 / 9 ) );
		topPanel.setBorder( BorderFactory.createEmptyBorder( 0, getContentPane().getSize().width / 20, 0, getContentPane().getSize().width / 20 ) );
		topPanel.setLayout( new GridLayout( 2, 2 ) );
		topPanel.add( idLabel );
		topPanel.add( idInputField );
		topPanel.add( passwordLabel );
		topPanel.add( passwordInputField );
		
		confirmButton.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				if( AccountCheck.isItCorrectAccount( idInputField.getText(), String.copyValueOf( passwordInputField.getPassword() ) ) ) {
					
					LoginFrame.this.dispose();
					
					if( BaseFeeLoader.loadBaseFeeInfo().getIsValid() ) {
						
						MainFrame.showOnScreen();
					
					} else {
						
						BaseFeeSettingFrame.showOnScreen();
						
					}
					
				} else {
					
					AlertFrame.showOnScreen( "로그인 실패", "아이디 또는 비밀번호를 다시 확인하십시오.", null );
					
				}
				
				idInputField.setText( "" );
				passwordInputField.setText( "" );
				
			}
			
		} );
		
		bottomPanel.setPreferredSize( new Dimension( 0, getContentPane().getSize().height * 2 / 9 ) );
		bottomPanel.setBorder( BorderFactory.createEmptyBorder( 0, getContentPane().getSize().width / 4, 0, getContentPane().getSize().width / 4 ) );
		bottomPanel.setLayout( new GridLayout( 1, 1 ) );
		bottomPanel.add( confirmButton );
		
		loginPanel.setBorder( BorderFactory.createEmptyBorder( getContentPane().getSize().height / 4, 0, getContentPane().getSize().height / 12, 0 ) );
		loginPanel.setLayout( new BorderLayout() );
		loginPanel.add( topPanel, BorderLayout.NORTH );
		loginPanel.add( bottomPanel, BorderLayout.SOUTH );
		
		return loginPanel;
		
	}
	
	private JPanel createAccountCreatePanel() {
		
		JPanel accountCreatePanel = new JPanel();
		
		JLabel idLabel = new JLabel( "아이디" );
		JLabel passwordLabel = new JLabel( "비밀번호" );
		JLabel passwordReInputLabel = new JLabel( "비밀번호 재입력" );
		
		JTextField idInputField = new JTextField();
		JPasswordField passwordInputField = new JPasswordField();
		JPasswordField passwordReInputField = new JPasswordField();
		
		JButton confirmButton = new JButton( "계정 생성" );
		
		idLabel.setHorizontalAlignment( JLabel.CENTER );
		idLabel.setFont( FontProvider.getDefaultFont() );
		
		passwordLabel.setHorizontalAlignment( JLabel.CENTER );
		passwordLabel.setFont( FontProvider.getDefaultFont() );
		
		passwordReInputLabel.setHorizontalAlignment( JLabel.CENTER );
		passwordReInputLabel.setFont( FontProvider.getDefaultFont() );
		
		confirmButton.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String id = idInputField.getText();
				String password = String.copyValueOf( passwordInputField.getPassword() );
				String reInputedPassword = String.copyValueOf( passwordReInputField.getPassword() );
				
				int idMinLength = AccountModifier.ID_MIN_LENGTH;
				int idMaxLength = AccountModifier.ID_MAX_LENGTH;
				
				int passwordMinLength = AccountModifier.PASSWORD_MIN_LENGTH;
				int passwordMaxLength = AccountModifier.PASSWORD_MAX_LENGTH;
				
				if( id.length() >= idMinLength && id.length() <= idMaxLength 
					&& password.length() >= passwordMinLength && password.length() <= passwordMaxLength 
					&& password.equals( reInputedPassword ) ) {
					
					if( AccountModifier.saveAccountToDB( id, password ) ) {
						
						LoginFrame.this.dispose();
						
						LoginFrame.showOnScreen();
						
					}
					
				} else {
					
					String alertSentence;
					
					if( id.length() < idMinLength || id.length() > idMaxLength ) {
						
						alertSentence = "아이디를 " + idMinLength + "~" + idMaxLength + " 자리로 설정해주십시오.";
					
					} else if( password.length() < passwordMinLength || password.length() > passwordMaxLength ) {
						
						alertSentence =  "비밀번호를 " + passwordMinLength + "~" + passwordMaxLength + " 자리로 설정해주십시오.";
					
					} else 	alertSentence = "입력한 두 비밀번호가 일치하도록 입력해주세요.";
					
					AlertFrame.showOnScreen( "재확인 요망", alertSentence, null );
					
				}
				
				idInputField.setText( "" );
				passwordInputField.setText( "" );
				passwordReInputField.setText( "" );
				
			}
			
		} );
		
		accountCreatePanel.setLayout( new GridLayout( 4, 2 ) );
		
		accountCreatePanel.add( idLabel );
		accountCreatePanel.add( idInputField );
		accountCreatePanel.add( passwordLabel );
		accountCreatePanel.add( passwordInputField );
		accountCreatePanel.add( passwordReInputLabel );
		accountCreatePanel.add( passwordReInputField );
		accountCreatePanel.add( new JPanel() );
		accountCreatePanel.add( confirmButton );
		
		return accountCreatePanel;
		
	}
	
	public static void showOnScreen() {
		
		if( AccountCheck.isThereAccountInDB() ) {
			
			INSTANCE.setTitle( "로그인" );
			
			if( SwingUtilities.getWindowAncestor( INSTANCE.loginPanel ) == null ) {
				
				INSTANCE.getContentPane().removeAll();
				INSTANCE.getContentPane().add( INSTANCE.loginPanel );
				
			}
			
		
		} else {
			
			INSTANCE.setTitle( "계정 생성" );
			
			if( SwingUtilities.getWindowAncestor( INSTANCE.accountCreatePanel ) == null ) {
				
				INSTANCE.getContentPane().removeAll();
				INSTANCE.getContentPane().add( INSTANCE.accountCreatePanel );
			
			}
			
		}
		
		INSTANCE.setVisible( true );
		
	}
	
}
