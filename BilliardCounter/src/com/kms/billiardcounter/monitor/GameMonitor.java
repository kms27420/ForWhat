package com.kms.billiardcounter.monitor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

import com.kms.billiardcounter.database.game_list.GameListLoader;
import com.kms.billiardcounter.database.game_monitor.GameMonitorLoader;
import com.kms.billiardcounter.database.game_monitor.GameMonitorModifier;
import com.kms.billiardcounter.font.FontProvider;
import com.kms.billiardcounter.frame.AlertFrame;
import com.kms.billiardcounter.support.NumericManufacturer;

/**
 * 
 * 각 당구대의 상황을 보여주는 패널
 * 
 * @author Kwon
 *
 */

public class GameMonitor extends JPanel{
	
	private BaseMonitor baseMonitor;
	private PlayingMonitor playingMonitor;
	
	/**
	 * 
	 * GameMonitor을 초기화해주는 생성자
	 * 
	 * @param row 초기화 해줄 row 위치
	 * @param col 초기화 해줄 col 위치
	 */
	public GameMonitor( int row, int col ) {
		
		setBorder( new BevelBorder(BevelBorder.RAISED) );
		setLayout( new BorderLayout() );
		add( createTableNumberPanel( row, col ), BorderLayout.NORTH );
		add( baseMonitor = new BaseMonitor( row, col, new BaseMonitor.OnStartGameListener() {
			
			@Override
			public void onStartGame() {
				
				GameMonitor.this.remove( baseMonitor );
				
				if( playingMonitor == null ) {
					
					playingMonitor = new PlayingMonitor( row, col, new PlayingMonitor.OnEndGameListener() {
						
						public void onEndGame() {
							
							GameMonitor.this.remove( playingMonitor );
							
							GameMonitor.this.add( baseMonitor );
							
							GameMonitor.this.repaint();
							GameMonitor.this.revalidate();	
							
						}
						
					} );
					
				}
				
				GameMonitor.this.add( playingMonitor );
				
				GameMonitor.this.repaint();
				GameMonitor.this.revalidate();
				
			}
			
		} ), BorderLayout.CENTER );
		
	}
	
	private JPanel createTableNumberPanel( int row, int col ){
		
		JPanel tableNumberPanel = new JPanel();
		
		JLabel tableNumberLabel = new JLabel();		
		
		JTextField tableNumberInputField = new JTextField();
		JButton gameMonitorCreateButton = new JButton( "생성" );
		
		MouseListener deleteListener = new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				
				e.getComponent().setBackground( null );
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				
				e.getComponent().setBackground( Color.LIGHT_GRAY );
				
			}
			
			@Override
			public void mouseClicked(MouseEvent labelClickedEvent) {
				
				int tableNumber = GameMonitorLoader.getTableNumber( row, col );
				
				if( GameListLoader.getIsThereNonPaidGames( tableNumber ) )	return;
				
				AlertFrame.showOnScreen( "당구대 빼기", "정말 " + tableNumber + "번 당구대를 제거하시겠습니까?", new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent confirmEvent) {
						
						if( GameMonitorModifier.deleteGameMonitor( tableNumber ) ) {
							
							tableNumberPanel.removeAll();
							
							tableNumberPanel.setLayout( new BorderLayout() );
							tableNumberPanel.add( tableNumberInputField, BorderLayout.CENTER );
							tableNumberPanel.add( gameMonitorCreateButton, BorderLayout.EAST );
							
							tableNumberPanel.repaint();
							tableNumberPanel.revalidate();

							GameMonitor.this.remove( baseMonitor );
							GameMonitor.this.add( baseMonitor );
							
						}
						
					}
					
				} );
				
			}
			
		};
		
		ActionListener createListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent createEvent) {
				
				int integerOfInputText = NumericManufacturer.getIntConsistingOnlyOfNumeric( tableNumberInputField.getText() );
				
				if( GameMonitorModifier.saveNewGameMonitor( integerOfInputText, row, col ) ){
				
					tableNumberPanel.removeAll();
					
					tableNumberLabel.setText( integerOfInputText + "번 테이블" );
					
					tableNumberPanel.setLayout( new GridLayout( 1, 1 ) );
					tableNumberPanel.add( tableNumberLabel );
					
					tableNumberPanel.repaint();
					tableNumberPanel.revalidate();
					
					GameMonitor.this.remove( baseMonitor );
					GameMonitor.this.add( baseMonitor );
				
				}
				
			}
			
		};
		
		tableNumberLabel.setFont( FontProvider.getDefaultFont() );
		tableNumberLabel.setHorizontalAlignment( JLabel.CENTER );
		tableNumberLabel.setOpaque( true );
		tableNumberLabel.addMouseListener( deleteListener );
		
		tableNumberInputField.setPreferredSize( new Dimension(200, 50) );
		tableNumberInputField.setFont( FontProvider.getDefaultFont() );
		tableNumberInputField.setHorizontalAlignment( JTextField.CENTER );
		
		gameMonitorCreateButton.setPreferredSize( new Dimension(100, 50) );
		gameMonitorCreateButton.addActionListener( createListener );
		
		int tableNumber = GameMonitorLoader.getTableNumber( row, col );
		
		if( tableNumber > 0 ) {
			
			tableNumberLabel.setText( tableNumber + "번 테이블" );
			
			tableNumberPanel.setLayout( new GridLayout( 1, 1 ) );
			tableNumberPanel.add( tableNumberLabel );
			
		} else {
			
			tableNumberPanel.setLayout( new BorderLayout() );
			tableNumberPanel.add( tableNumberInputField, BorderLayout.CENTER );
			tableNumberPanel.add( gameMonitorCreateButton, BorderLayout.EAST );
			
		}
		
		tableNumberPanel.setPreferredSize( new Dimension(0, 60) );
		tableNumberPanel.setBorder( new BevelBorder(BevelBorder.RAISED) );
		
		return tableNumberPanel;
		
	}

}
