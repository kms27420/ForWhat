package com.kms.billiardcounter.panel;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JPanel;

import com.kms.billiardcounter.monitor.GameMonitor;

/**
 * 
 * GameMonitor들을 모아놓은 panel
 * 
 * @author Kwon
 *
 */

public class GameMonitorPanel extends JPanel{
	
	private class GameMonitorMatrix {
		
		private static final int MAX_ROW = 4;
		private static final int MAX_COL = 4;
		
	}
	
	public GameMonitorPanel() {
		
		initThisPanel();
		
		addComponents( createGameMonitorList() );
		
	}
	
	private void initThisPanel() {
		
		setPreferredSize( new Dimension( 0, 385 * GameMonitorMatrix.MAX_ROW ) );
		setLayout( new GridLayout( GameMonitorMatrix.MAX_ROW, GameMonitorMatrix.MAX_COL ) );
		
	}
	
	private void addComponents( ArrayList<JPanel> gameMonitorList ){
		
		for(int index = 0; index < gameMonitorList.size(); index++){
			
			add( gameMonitorList.get(index) );
			
		}
		
	}
	
	private ArrayList<JPanel> createGameMonitorList(){
		
		ArrayList<JPanel> gameMonitorList = new ArrayList<JPanel>();
		
		for(int row = 0; row < GameMonitorMatrix.MAX_ROW; row++){
			
			for(int col = 0; col < GameMonitorMatrix.MAX_COL; col++){
				
				gameMonitorList.add( new GameMonitor(row, col) );
				
			}
			
		}
		
		return gameMonitorList;
		
	}
	
}
