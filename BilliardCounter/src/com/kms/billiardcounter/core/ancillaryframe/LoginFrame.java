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
import javax.swing.JTextField;

import com.kms.billiardcounter.core.mainframe.BilliardCounterFrame;
import com.kms.billiardcounter.dao.account.AccountCheck;
import com.kms.billiardcounter.dao.account.AccountUpdater;
import com.kms.billiardcounter.dao.basefee.BaseFeeLoader;
import com.kms.billiardcounter.font.FontProvider;

public class LoginFrame extends JFrame {

	public LoginFrame() {
		
		initThisFrame();
		
		if( AccountCheck.isThereAccountInDB() ) {
		
			setTitle( "로그인" );
			add( createLoginPanel() );
		
		} else {
			
			setTitle( "계정 생성" );
			add( createAccountCreatePanel() );
			
		}
		
		repaint();
		revalidate();
		
		setVisible( true );
		
	}
	
	private void initThisFrame() {
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = new Dimension( 400, 300 );
		
		setLayout( new GridLayout( 1, 1 ) );
		setLocation( screenSize.width / 2 - frameSize.width / 2, screenSize.height / 2 - frameSize.height / 2);
		setSize( frameSize );
		
		setDefaultCloseOperation( EXIT_ON_CLOSE );
		
	}
	
	private JPanel createLoginPanel() {
		
		JPanel loginPanel = new JPanel();
		
		JLabel idLabel = new JLabel( "아이디" );
		JLabel passwordLabel = new JLabel( "비밀번호" );
		
		JTextField idInputField = new JTextField();
		JPasswordField passwordInputField = new JPasswordField();
		
		JButton confirmButton = new JButton( "로그인" );
		
		idLabel.setHorizontalAlignment( JLabel.CENTER );
		idLabel.setFont( FontProvider.getDefaultFont() );
		
		passwordLabel.setHorizontalAlignment( JLabel.CENTER );
		passwordLabel.setFont( FontProvider.getDefaultFont() );
		
		confirmButton.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				if( AccountCheck.isItCorrectAccount( idInputField.getText(), String.copyValueOf( passwordInputField.getPassword() ) ) ) {
					
					LoginFrame.this.dispose();
					
					new BilliardCounterFrame();
					
					if( !BaseFeeLoader.isBaseFeeInited() ) {
						
						new BaseFeeSettingFrame();
						
					}
					
				} else {
					
					Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
					Dimension frameSize = new Dimension( 400, 200 );
					
					JFrame alertFrame = new JFrame( "로그인 실패" );
					
					JLabel alertLabel = new JLabel( "아이디 또는 비밀번호를 다시 확인하십시오." );
					
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
		
		loginPanel.setLayout( new GridLayout( 3, 2 ) );
		
		loginPanel.add( idLabel );
		loginPanel.add( idInputField );
		loginPanel.add( passwordLabel );
		loginPanel.add( passwordInputField );
		loginPanel.add( new JPanel() );
		loginPanel.add( confirmButton );
		
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
				
				if( password.equals( reInputedPassword ) ) {
					
					if( AccountUpdater.saveAccountToDB( id, password ) ) {
						
						LoginFrame.this.dispose();
						
						new LoginFrame();
						
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
	
}
