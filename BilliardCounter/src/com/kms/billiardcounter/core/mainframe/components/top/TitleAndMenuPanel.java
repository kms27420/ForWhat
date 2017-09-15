package com.kms.billiardcounter.core.mainframe.components.top;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.kms.billiardcounter.core.ancillaryframe.BaseFeeSettingFrame;
import com.kms.billiardcounter.core.ancillaryframe.DayTotalSalesFrame;
import com.kms.billiardcounter.core.ancillaryframe.PasswordChangeFrame;
import com.kms.billiardcounter.core.ancillaryframe.SearchedGameFeeInfoListFrame;
import com.kms.billiardcounter.core.ancillaryframe.admin_approval.AdminDoorLockFrame;
import com.kms.billiardcounter.core.ancillaryframe.admin_approval.FrameOpener;
import com.kms.billiardcounter.font.FontProvider;

/**
 * 
 * 본 프로그램의 제목과 메뉴 기능들을 보여주는 panel
 * 
 * @author Kwon
 *
 */

public class TitleAndMenuPanel extends JPanel{
	
	public TitleAndMenuPanel(){
		
		initThisPanel();
		
		add( createMenuPanel(), BorderLayout.WEST );
		add( createTitleLabel(), BorderLayout.CENTER );
		add( createGameSearchPanel(), BorderLayout.EAST );
		
		repaint();
		revalidate();
		
	}
	
	private void initThisPanel(){
		
		setLayout( new BorderLayout() );
	
	}
	
	private JLabel createTitleLabel(){
		
		final String TITLE_NAME = "당구장 카운터";
		
		JLabel titleLabel = new JLabel( TITLE_NAME );
		
		titleLabel.setFont( FontProvider.getDefaultFont( 40 ) );
		titleLabel.setHorizontalAlignment( JLabel.CENTER );
		titleLabel.setBackground( Color.GREEN );
		
		return titleLabel;
	
	}
	
	private JPanel createGameSearchPanel(){
		
		JPanel gameSearchPanel = new JPanel();
		
		JPanel componentsPanel = new JPanel();
		
		JTextField searchWordInputTextField = new JTextField();
		
		final String BUTTON_NAME = "검색하기";
		
		JButton searchButton = new JButton( BUTTON_NAME );
		
		searchWordInputTextField.setPreferredSize( new Dimension(200, 50) );
		searchWordInputTextField.setFont( FontProvider.getDefaultFont() );
		
		searchButton.setPreferredSize( new Dimension(100, 50) );
		searchButton.setFont( FontProvider.getDefaultFont() );
		searchButton.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				new SearchedGameFeeInfoListFrame( searchWordInputTextField.getText() );
			
			}
			
		} );
		
		componentsPanel.setLayout( new BorderLayout() );
		componentsPanel.add( searchWordInputTextField, BorderLayout.CENTER );
		componentsPanel.add( searchButton, BorderLayout.EAST );
		
		int dividingLineNumber = 4;
		
		gameSearchPanel.setPreferredSize( new Dimension(300, 50) );
		gameSearchPanel.setLayout( new GridLayout( dividingLineNumber, 1 ) );
		
		for( int panelNum = 0; panelNum < dividingLineNumber - 1; panelNum++ ){
			
			gameSearchPanel.add( new JPanel() );
			
		}
		
		gameSearchPanel.add( componentsPanel );
		
		return gameSearchPanel;
		
	}
	
	private JPanel createMenuPanel(){
		
		JPanel menuPanel = new JPanel();
		
		JButton passwordChangeButton = new JButton( "비밀번호 변경" );
		JButton baseFeeSettingButton = new JButton( "기본 요금 설정" );
		JButton dayTotalSalesOpenButton = new JButton( "금일 매출" );
		
		passwordChangeButton.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				new AdminDoorLockFrame( new FrameOpener() {
					
					@Override
					public void openFrame() {

						new PasswordChangeFrame();
						
					}
					
				} );
				
			}
			
		} );
		
		baseFeeSettingButton.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				new AdminDoorLockFrame( new FrameOpener() {
					
					public void openFrame() {
						
						new BaseFeeSettingFrame();
						
					}
					
				} );
				
			}
			
		} );
		
		dayTotalSalesOpenButton.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				new DayTotalSalesFrame();
				
			}
			
		} );
		
		menuPanel.setPreferredSize( new Dimension(300, 50) );
		menuPanel.setLayout( new GridLayout( 3, 1 ) );
		
		menuPanel.add( passwordChangeButton );
		menuPanel.add( baseFeeSettingButton );
		menuPanel.add( dayTotalSalesOpenButton );
		
		return menuPanel;
		
	}
}
