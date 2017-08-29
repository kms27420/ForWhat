package com.kms.billiardcounter.billiardtablepanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

import com.kms.billiardcounter.contentspaneupdater.ContentsPaneUpdater;
import com.kms.billiardcounter.databasecontrol.TablePositionSaver;
import com.kms.billiardcounter.fontprovider.FontProvider;

public class BilliardTableNumberControlPanel extends JPanel {

	public BilliardTableNumberControlPanel( int tableNumber, int row, int col, ContentsPaneUpdater contentsPaneUpdater ) {
		
		initThisPanel();
		
		add( createThisContentsPanel( tableNumber, row, col, contentsPaneUpdater ) );
		
		repaint();
		revalidate();
		
	}
	
	private void initThisPanel(){
		
		setBorder( new BevelBorder(BevelBorder.RAISED) );
		setLayout( new GridLayout(1, 1) );
		
	}
	
	private JPanel createThisContentsPanel( int tableNumber, int row, int col, ContentsPaneUpdater contentsPaneUpdater ){
		
		JPanel thisContentsPanel;
		
		if( tableNumber == 0 ){
			
			thisContentsPanel = createGameTableNumberCreatePanel( tableNumber, row, col, contentsPaneUpdater );
			
		}
		else{
			
			thisContentsPanel = createGameTableNumberViewerPanel( tableNumber );
			
		}
		
		return thisContentsPanel;
		
	}
	
	private JPanel createGameTableNumberViewerPanel( int tableNumber ) {
		
		JPanel gameTableNumberViewerPanel = new JPanel();
		
		JLabel gameTableNumberLabel = new JLabel();			
		
		gameTableNumberLabel.setFont( FontProvider.getDefaultFont() );
		gameTableNumberLabel.setHorizontalAlignment( JLabel.CENTER );
		gameTableNumberLabel.setText( tableNumber + "번 테이블" );
		
		gameTableNumberViewerPanel.setLayout( new GridLayout(1, 1) );
		gameTableNumberViewerPanel.add( gameTableNumberLabel );
		
		return gameTableNumberViewerPanel;
		
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
				
				int integerOfInputText = Integer.parseInt( gameTableNumberInputTextField.getText() );
				
				if( TablePositionSaver.saveTablePosition( integerOfInputText, row, col ) ){
				
					BilliardTableNumberControlPanel.this.remove( gameTableNumberCreatePanel );
					BilliardTableNumberControlPanel.this.add( createGameTableNumberViewerPanel( integerOfInputText ) );
				
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
