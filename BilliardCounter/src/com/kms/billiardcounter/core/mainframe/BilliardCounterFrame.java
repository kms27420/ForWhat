package com.kms.billiardcounter.core.mainframe;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.kms.billiardcounter.core.mainframe.components.bottom.BilliardTablesCollectionPanel;
import com.kms.billiardcounter.core.mainframe.components.top.TitleAndMenuPanel;

/**
 * 
 * 본 프로그램의 시작 프레임
 * 
 * @author Kwon
 *
 */

public class BilliardCounterFrame extends JFrame{
	
	public BilliardCounterFrame(){
		
		initThisFrame();
		
		add( createTitleAndMenuPanel(), BorderLayout.NORTH );
		add( createBilliardTablesCollectionScrollPane(), BorderLayout.CENTER );
		
		repaint();
		revalidate();
		
	}
	
	private void initThisFrame() {
		
		Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		setLocation(0, 0);
		setPreferredSize( windowSize );
		setSize( windowSize );
		
		setLayout( new BorderLayout() );
		setResizable( false );
		setDefaultCloseOperation( EXIT_ON_CLOSE );
		
		setVisible( true );
		
	}
	
	private JPanel createTitleAndMenuPanel(){
		
		JPanel titleAndMenuPanel = new TitleAndMenuPanel();
		
		Dimension panelSize = new Dimension( this.getPreferredSize().width, this.getPreferredSize().height / 4 );
		
		titleAndMenuPanel.setPreferredSize( panelSize );
		
		return titleAndMenuPanel;
		
	}
	
	private JScrollPane createBilliardTablesCollectionScrollPane(){
		
		JScrollPane billiardTablesCollectionScrollPane = new JScrollPane( new BilliardTablesCollectionPanel() );
		
		Dimension scrollPaneSize = new Dimension( this.getPreferredSize().width, this.getPreferredSize().height * 3 / 4 );
		
		billiardTablesCollectionScrollPane.setPreferredSize( scrollPaneSize );
		billiardTablesCollectionScrollPane.getVerticalScrollBar().setUnitIncrement( 40 );
		billiardTablesCollectionScrollPane.setHorizontalScrollBarPolicy( JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
		billiardTablesCollectionScrollPane.setOpaque( true );
		
		return billiardTablesCollectionScrollPane;
		
	}
}
