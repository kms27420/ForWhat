package com.kms.billiardcounter.panel;

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

import com.kms.billiardcounter.font.FontProvider;
import com.kms.billiardcounter.frame.AdminDoorLockFrame;
import com.kms.billiardcounter.frame.BaseFeeSettingFrame;
import com.kms.billiardcounter.frame.DayTotalSalesFrame;
import com.kms.billiardcounter.frame.FrameOpener;
import com.kms.billiardcounter.frame.PasswordChangeFrame;
import com.kms.billiardcounter.frame.SearchResultFrame;
import com.kms.billiardcounter.size.FrameSize;

/**
 * 
 * 본 프로그램의 제목과 메뉴 기능들을 보여주는 panel
 * 
 * @author Kwon
 *
 */

public class OptionPanel extends JPanel{
	
	public OptionPanel(){
		
		setLayout( new BorderLayout() );
		
		add( createMenuPanel(), BorderLayout.WEST );
		add( createTitleLabel(), BorderLayout.CENTER );
		add( createGameSearchPanel(), BorderLayout.EAST );
		
	}
	
	private JLabel createTitleLabel(){
		
		JLabel titleLabel = new JLabel( "당구장 카운터" );
		
		titleLabel.setFont( FontProvider.getDefaultFont( 40 ) );
		titleLabel.setHorizontalAlignment( JLabel.CENTER );
		
		return titleLabel;
	
	}
	
	private JPanel createGameSearchPanel(){
		
		JPanel gameSearchPanel = new JPanel();
		
		JPanel componentsPanel = new JPanel();
		
		JTextField searchSentenceField = new JTextField();
		
		JButton searchButton = new JButton( "검색하기" );
		
		searchSentenceField.setPreferredSize( new Dimension( FrameSize.getMainFrameSize().width * 2 / 15, 50 ) );
		searchSentenceField.setFont( FontProvider.getDefaultFont() );
		
		searchButton.setPreferredSize( new Dimension( FrameSize.getMainFrameSize().width / 15, 50 ) );
		searchButton.setFont( FontProvider.getDefaultFont() );
		searchButton.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				SearchResultFrame.showOnScreen( searchSentenceField.getText() );
			
			}
			
		} );
		
		componentsPanel.setPreferredSize( new Dimension( 0, 50 ) );
		componentsPanel.setLayout( new BorderLayout() );
		componentsPanel.add( searchSentenceField, BorderLayout.CENTER );
		componentsPanel.add( searchButton, BorderLayout.EAST );
		
		gameSearchPanel.setPreferredSize( new Dimension( FrameSize.getMainFrameSize().width / 5, 0 ) );
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

				AdminDoorLockFrame.showOnScreen( new FrameOpener() {
					
					@Override
					public void openFrame() {

						PasswordChangeFrame.showOnScreen();
						
					}
					
				} );
				
			}
			
		} );
		
		baseFeeSettingButton.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				AdminDoorLockFrame.showOnScreen( new FrameOpener() {
					
					public void openFrame() {
						
						BaseFeeSettingFrame.showOnScreen();
						
					}
					
				} );
				
			}
			
		} );
		
		dayTotalSalesOpenButton.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				DayTotalSalesFrame.showOnScreen();
				
			}
			
		} );
		
		menuPanel.setPreferredSize( new Dimension( FrameSize.getMainFrameSize().width / 5, 0) );
		menuPanel.setLayout( new GridLayout( 3, 1 ) );
		
		menuPanel.add( passwordChangeButton );
		menuPanel.add( baseFeeSettingButton );
		menuPanel.add( dayTotalSalesOpenButton );
		
		return menuPanel;
		
	}
}
