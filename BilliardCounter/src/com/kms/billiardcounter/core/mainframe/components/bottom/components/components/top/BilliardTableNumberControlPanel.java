package com.kms.billiardcounter.core.mainframe.components.bottom.components.components.top;

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

import com.kms.billiardcounter.core.contentspaneupdater.ContentsPaneUpdater;
import com.kms.billiardcounter.core.string.NumericManufacturer;
import com.kms.billiardcounter.dao.nonpaidgames.NonPaidGamesLoader;
import com.kms.billiardcounter.dao.tableposition.TablePositionUpdater;
import com.kms.billiardcounter.font.FontProvider;

public class BilliardTableNumberControlPanel extends JPanel {

	public BilliardTableNumberControlPanel( int tableNumber, int row, int col, ContentsPaneUpdater contentsPaneUpdater ) {
		
		initThisPanel();
		
		if( tableNumber == 0 ){
			
			add( createGameTableNumberCreatePanel( tableNumber, row, col, contentsPaneUpdater ) );
			
		} else{
			
			add( createGameTableNumberDisplayAndDeleteLabel( tableNumber, row, col, contentsPaneUpdater ) );
			
		}
		
		repaint();
		revalidate();
		
	}
	
	private void initThisPanel(){
		
		setBorder( new BevelBorder(BevelBorder.RAISED) );
		setLayout( new GridLayout(1, 1) );
		
	}
	
	private JLabel createGameTableNumberDisplayAndDeleteLabel( int tableNumber, int row, int col, ContentsPaneUpdater contentsPaneUpdater ) {
		
		JLabel gameTableNumberLabel = new JLabel();			
		
		gameTableNumberLabel.setFont( FontProvider.getDefaultFont() );
		gameTableNumberLabel.setHorizontalAlignment( JLabel.CENTER );
		gameTableNumberLabel.setText( tableNumber + "번 테이블" );
		gameTableNumberLabel.setOpaque( true );
		gameTableNumberLabel.addMouseListener( new MouseListener() {
			
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
				
				if( NonPaidGamesLoader.getNonPaidGameFeeInfoList( tableNumber ).size() > 0 )	return;
				
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

						if( TablePositionUpdater.deleteTablePosition( tableNumber ) ) {
							
							BilliardTableNumberControlPanel.this.remove( gameTableNumberLabel );
							BilliardTableNumberControlPanel.this.add( createGameTableNumberCreatePanel( tableNumber, row, col, contentsPaneUpdater ) );
							
							tablePositionDeleteFrame.dispose();
							
							contentsPaneUpdater.update();
							
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
		
		return gameTableNumberLabel;
		
	}
	
	private JPanel createGameTableNumberCreatePanel( int tableNumber, int row, int col, ContentsPaneUpdater contentsPaneUpdater ){
		
		JPanel gameTableNumberCreatePanel = new JPanel();
		
		JTextField gameTableNumberInputTextField = new JTextField();
		JButton gameTableNumberCreateButton = new JButton("생성");
		
		gameTableNumberInputTextField.setPreferredSize( new Dimension(200, 50) );
		gameTableNumberInputTextField.setFont( FontProvider.getDefaultFont() );
		gameTableNumberInputTextField.setHorizontalAlignment( JTextField.CENTER );
		
		gameTableNumberCreateButton.setPreferredSize( new Dimension(100, 50) );
		gameTableNumberCreateButton.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				int integerOfInputText = NumericManufacturer.getIntConsistingOnlyOfNumeric( gameTableNumberInputTextField.getText() );
				
				if( TablePositionUpdater.saveTablePosition( integerOfInputText, row, col ) ){
				
					BilliardTableNumberControlPanel.this.remove( gameTableNumberCreatePanel );
					BilliardTableNumberControlPanel.this.add( createGameTableNumberDisplayAndDeleteLabel( integerOfInputText, row, col, contentsPaneUpdater ) );
				
					contentsPaneUpdater.update();
				
				}
				
			}
			
		} );
		
		gameTableNumberCreatePanel.setLayout( new BorderLayout() );
		gameTableNumberCreatePanel.add( gameTableNumberInputTextField, BorderLayout.CENTER );
		gameTableNumberCreatePanel.add( gameTableNumberCreateButton, BorderLayout.EAST );
		
		return gameTableNumberCreatePanel;
		
	}
	
}
