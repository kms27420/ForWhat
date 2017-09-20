package com.kms.billiardcounter.core.mainframe;

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
import com.kms.billiardcounter.support.NumericManufacturer;

public class TableNumberPanel extends JPanel {

	public interface OnChangeTableNumberListener {
		
		public void onChangeTableNumber();
		
	}
	
	private OnChangeTableNumberListener onChangeTableNumberListener;
	
	public TableNumberPanel( int row, int col, OnChangeTableNumberListener onChangeTableNumberListener ) {
		
		initThisPanel();
		
		this.onChangeTableNumberListener = onChangeTableNumberListener;
		
		int tableNumber = GameMonitorLoader.getTableNumber( row, col );
		
		if( tableNumber == 0 ){
			
			add( createGameMonitorCreatePanel( row, col ) );
			
		} else{
			
			add( createTableNumberLabel( tableNumber, row, col ) );
			
		}
		
	}
	
	private void initThisPanel(){
		
		setBorder( new BevelBorder(BevelBorder.RAISED) );
		setLayout( new GridLayout(1, 1) );
		
	}
	
	private JLabel createTableNumberLabel( int tableNumber, int row, int col ) {
		
		JLabel tableNumberLabel = new JLabel();			
		
		tableNumberLabel.setFont( FontProvider.getDefaultFont() );
		tableNumberLabel.setHorizontalAlignment( JLabel.CENTER );
		tableNumberLabel.setText( tableNumber + "번 테이블" );
		tableNumberLabel.setOpaque( true );
		tableNumberLabel.addMouseListener( new MouseListener() {
			
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
			public void mouseClicked(MouseEvent e) {
				
				if( GameListLoader.getNonPaidGameFeeInfoList( tableNumber ).size() > 0 )	return;
				
				Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
				Dimension frameSize = new Dimension( 400, 200 );
				
				JFrame tablePositionDeleteFrame = new JFrame( "당구대 빼기" );
				
				JPanel topPanel = new JPanel();
				JLabel guidanceLabel = new JLabel( "정말 " + tableNumber + "번 당구대를 제거하시겠습니까?" );
				
				JPanel bottomPanel = new JPanel();
				JButton confirmButton = new JButton( "제거" );
				JButton cancelButton = new JButton( "취소" );
				
				guidanceLabel.setPreferredSize( new Dimension( 300, 50 ) );
				guidanceLabel.setHorizontalTextPosition( JLabel.CENTER );
				guidanceLabel.setFont( FontProvider.getDefaultFont() );
				
				topPanel.setBorder( BorderFactory.createEmptyBorder( 20, 0, 0, 0 ) );
				topPanel.add( guidanceLabel );
				
				confirmButton.setPreferredSize( new Dimension( 80, 50 ) );
				confirmButton.addActionListener( new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {

						if( GameMonitorModifier.deleteGameViewer( tableNumber ) ) {
							
							TableNumberPanel.this.remove( tableNumberLabel );
							TableNumberPanel.this.add( createGameMonitorCreatePanel( row, col ) );
							
							tablePositionDeleteFrame.dispose();
							
							onChangeTableNumberListener.onChangeTableNumber();
							
						}
						
					}
					
				} );
				
				cancelButton.setPreferredSize( new Dimension( 80, 50 ) );
				cancelButton.addActionListener( new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						
						tablePositionDeleteFrame.dispose();
						
					}
					
				} );
				
				bottomPanel.setPreferredSize( new Dimension( 0, 60 ) );
				bottomPanel.setBorder( BorderFactory.createEmptyBorder( 0, 0, 5, 0 ) );
				bottomPanel.add( confirmButton );
				bottomPanel.add( cancelButton );
				
				tablePositionDeleteFrame.setLocation( screenSize.width / 2 - frameSize.width / 2, screenSize.height / 2 - frameSize.height / 2 );
				tablePositionDeleteFrame.setSize( frameSize );
				tablePositionDeleteFrame.setLayout( new BorderLayout() );
				tablePositionDeleteFrame.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
				
				tablePositionDeleteFrame.add( topPanel, BorderLayout.CENTER );
				tablePositionDeleteFrame.add( bottomPanel, BorderLayout.SOUTH );
				
				tablePositionDeleteFrame.setVisible( true );
				
			}
			
		} );
		
		return tableNumberLabel;
		
	}
	
	private JPanel createGameMonitorCreatePanel( int row, int col ){
		
		JPanel gameMonitorCreatePanel = new JPanel();
		
		JTextField tableNumberInputField = new JTextField();
		JButton gameMonitorCreateButton = new JButton("생성");
		
		tableNumberInputField.setPreferredSize( new Dimension(200, 50) );
		tableNumberInputField.setFont( FontProvider.getDefaultFont() );
		tableNumberInputField.setHorizontalAlignment( JTextField.CENTER );
		
		gameMonitorCreateButton.setPreferredSize( new Dimension(100, 50) );
		gameMonitorCreateButton.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				int integerOfInputText = NumericManufacturer.getIntConsistingOnlyOfNumeric( tableNumberInputField.getText() );
				
				if( GameMonitorModifier.saveNewGameViewer( integerOfInputText, row, col ) ){
				
					TableNumberPanel.this.remove( gameMonitorCreatePanel );
					TableNumberPanel.this.add( createTableNumberLabel( integerOfInputText, row, col ) );
				
					onChangeTableNumberListener.onChangeTableNumber();
				
				}
				
			}
			
		} );
		
		gameMonitorCreatePanel.setLayout( new BorderLayout() );
		gameMonitorCreatePanel.add( tableNumberInputField, BorderLayout.CENTER );
		gameMonitorCreatePanel.add( gameMonitorCreateButton, BorderLayout.EAST );
		
		return gameMonitorCreatePanel;
		
	}
	
}
