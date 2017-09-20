package com.kms.billiardcounter.core.mainframe;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * 
 * 본 프로그램의 시작 프레임
 * 
 * @author Kwon
 *
 */

public class MainFrame extends JFrame{
	
	public MainFrame(){
		
		initThisFrame();
		
		add( createOptionPanel(), BorderLayout.NORTH );
		add( createGameMonitorPanel(), BorderLayout.CENTER );
	
		setVisible( true );
		
	}
	
	private void initThisFrame() {
		
		Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		setLocation(0, 0);
		setPreferredSize( windowSize );
		setSize( windowSize );
		
		setLayout( new BorderLayout() );
		setResizable( false );
		setDefaultCloseOperation( EXIT_ON_CLOSE );
		
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
		gameMonitorPanel.setOpaque( true );
		
		return gameMonitorPanel;
		
	}
}
