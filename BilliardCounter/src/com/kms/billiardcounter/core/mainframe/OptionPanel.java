package com.kms.billiardcounter.core.mainframe;

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
import com.kms.billiardcounter.core.ancillaryframe.SearchResultFrame;
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

public class OptionPanel extends JPanel{
	
	public OptionPanel(){
		
		initThisPanel();
		
		add( createMenuPanel(), BorderLayout.WEST );
		add( createTitleLabel(), BorderLayout.CENTER );
		add( createGameSearchPanel(), BorderLayout.EAST );
		
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
		
		JTextField searchSentenceField = new JTextField();
		
		JButton searchButton = new JButton( "검색하기" );
		
		searchSentenceField.setPreferredSize( new Dimension(200, 50) );
		searchSentenceField.setFont( FontProvider.getDefaultFont() );
		
		searchButton.setPreferredSize( new Dimension(100, 50) );
		searchButton.setFont( FontProvider.getDefaultFont() );
		searchButton.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				new SearchResultFrame( searchSentenceField.getText() );
			
			}
			
		} );
		
		componentsPanel.setPreferredSize( new Dimension( 0, 50 ) );
		componentsPanel.setLayout( new BorderLayout() );
		componentsPanel.add( searchSentenceField, BorderLayout.CENTER );
		componentsPanel.add( searchButton, BorderLayout.EAST );
		
		gameSearchPanel.setPreferredSize( new Dimension(300, 50) );
		gameSearchPanel.setLayout( new BorderLayout() );
		
		gameSearchPanel.add( componentsPanel, BorderLayout.SOUTH );
		
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
		
		menuPanel.setPreferredSize( new Dimension(300, 0) );
		menuPanel.setLayout( new GridLayout( 3, 1 ) );
		
		menuPanel.add( passwordChangeButton );
		menuPanel.add( baseFeeSettingButton );
		menuPanel.add( dayTotalSalesOpenButton );
		
		return menuPanel;
		
	}
}
