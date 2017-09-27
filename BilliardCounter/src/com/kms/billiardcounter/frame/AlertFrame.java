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

import com.kms.billiardcounter.font.FontProvider;
import com.kms.billiardcounter.size.DeviceSize;
import com.kms.billiardcounter.size.FrameSize;

public class AlertFrame extends JFrame {

	private Dimension contentPaneSize;
	private AlertPanel alertPanel;
	
	private static final AlertFrame INSTANCE = new AlertFrame();
	
	private AlertFrame() {
		
		setPreferredSize( FrameSize.getAlertFrameSize() );
		pack();
		setLocation( ( DeviceSize.getScreenSize().width - FrameSize.getAlertFrameSize().width ) / 2, 
				( DeviceSize.getScreenSize().height - FrameSize.getAlertFrameSize().height ) / 2 );
		setResizable( false );
		
		setDefaultCloseOperation( DISPOSE_ON_CLOSE );
		
		contentPaneSize = getContentPane().getSize();
		alertPanel = new AlertPanel();
		
		getContentPane().setLayout( new GridLayout( 1, 1 ) );
		getContentPane().add( alertPanel );
		
	}
	
	public static void showOnScreen( String alertTitle, String alertSentence, ActionListener onClickConfirmButtonListener ) {
		
		INSTANCE.setTitle( alertTitle );
		INSTANCE.alertPanel.setAlertLabel( alertSentence );
		INSTANCE.alertPanel.addOnClickConfirmButtonListener( onClickConfirmButtonListener );
		
		INSTANCE.setVisible( true );
		
	}
	
	private class AlertPanel extends JPanel {
		
		private JPanel topPanel = new JPanel();
		private JLabel alertLabel = new JLabel();
		
		private JPanel bottomPanel = new JPanel();
		private JButton confirmButton = new JButton( "확인" );
		private JButton cancelButton = new JButton( "취소" );
		
		private AlertPanel() {
			
			initComponents();
			
			setLayout( new BorderLayout() );
			add( topPanel, BorderLayout.CENTER );
			add( bottomPanel, BorderLayout.SOUTH );
			
		}
		
		private void initComponents() {
			
			alertLabel.setFont( FontProvider.getDefaultFont() );
			alertLabel.setHorizontalAlignment( JLabel.CENTER );
			
			topPanel.setPreferredSize( new Dimension( 0, contentPaneSize.height * 2 / 3 ) );
			topPanel.setLayout( new GridLayout( 1, 1 ) );
			topPanel.add( alertLabel );
			
			ActionListener onDisposeAlertFrameListener = new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					AlertFrame.this.dispose();
					
				}
			};
			
			confirmButton.setPreferredSize( new Dimension( contentPaneSize.width / 4, 0 ) );
			confirmButton.setFont( FontProvider.getDefaultFont() );
			confirmButton.addActionListener( onDisposeAlertFrameListener );
			
			cancelButton.setPreferredSize( new Dimension( contentPaneSize.width / 4, 0 ) );
			cancelButton.setFont( FontProvider.getDefaultFont() );
			cancelButton.addActionListener( onDisposeAlertFrameListener );
			
			bottomPanel.setPreferredSize( new Dimension( 0, contentPaneSize.height / 3 ) );
			bottomPanel.setBorder( BorderFactory.createEmptyBorder
					( contentPaneSize.height / 18, contentPaneSize.width / 4,
							contentPaneSize.height / 18, contentPaneSize.width / 4 ) );
			bottomPanel.setLayout( new BorderLayout() );
			bottomPanel.add( confirmButton, BorderLayout.WEST );
			bottomPanel.add( cancelButton, BorderLayout.EAST );
			
		}
		
		private void setAlertLabel( String alertSentence ) {
			
			alertLabel.setText( alertSentence );
			
		}
		
		private void addOnClickConfirmButtonListener( ActionListener onClickConfirmButtonListener ) {
			
			ActionListener[] confirmButtonListeners = confirmButton.getActionListeners();
			
			for ( int index = confirmButtonListeners.length - 2; index >= 0; index-- ) {
				
				confirmButton.removeActionListener( confirmButtonListeners[index] );
				
			}
			
			if( onClickConfirmButtonListener == null )	return;
			
			confirmButton.addActionListener( onClickConfirmButtonListener );
			
		}
		
	}
	
}
