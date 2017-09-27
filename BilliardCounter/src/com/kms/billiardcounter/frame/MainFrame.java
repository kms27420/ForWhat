package com.kms.billiardcounter.frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.kms.billiardcounter.panel.GameMonitorPanel;
import com.kms.billiardcounter.panel.OptionPanel;
import com.kms.billiardcounter.size.DeviceSize;
import com.kms.billiardcounter.size.FrameSize;

/**
 * 
 * 본 프로그램의 시작 프레임
 * 
 * @author Kwon
 *
 */

public class MainFrame extends JFrame{
	
	private static final MainFrame INSTANCE = new MainFrame();
	
	private MainFrame(){
		
		setPreferredSize( FrameSize.getMainFrameSize() );
		pack();
		setLocation( ( DeviceSize.getWindowSize().width - FrameSize.getMainFrameSize().width ) / 2, 
				( DeviceSize.getWindowSize().height - FrameSize.getMainFrameSize().height ) / 2 );
		setResizable( false );
		
		setTitle( "당구장 프로그램" );
		setDefaultCloseOperation( EXIT_ON_CLOSE );
		
		getContentPane().setLayout( new BorderLayout() );
		getContentPane().add( createOptionPanel(), BorderLayout.NORTH );
		getContentPane().add( createGameMonitorPanel(), BorderLayout.CENTER );
		
	}
	
	private JPanel createOptionPanel(){
		
		JPanel optionPanel = new OptionPanel();
		
		Dimension panelSize = new Dimension( this.getPreferredSize().width, this.getPreferredSize().height / 4 );
		
		optionPanel.setPreferredSize( panelSize );
		
		return optionPanel;
		
	}
	
	private JScrollPane createGameMonitorPanel(){
		
		JScrollPane gameMonitorPanel = new JScrollPane( new GameMonitorPanel() );
		
		Dimension scrollPaneSize = new Dimension( this.getPreferredSize().width, this.getPreferredSize().height * 3 / 4 );
		
		gameMonitorPanel.setPreferredSize( scrollPaneSize );
		gameMonitorPanel.getVerticalScrollBar().setUnitIncrement( 40 );
		gameMonitorPanel.setHorizontalScrollBarPolicy( JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
		
		return gameMonitorPanel;
		
	}
	
	public static void showOnScreen() {
		
		INSTANCE.setVisible( true );
		
	}
	
}
