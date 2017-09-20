package com.kms.billiardcounter.core.mainframe;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JPanel;

/**
 * 
 * GameMonitor들을 모아놓은 panel
 * 
 * @author Kwon
 *
 */

public class GameMonitorPanel extends JPanel{
	
	private class Matrix {
		
		private static final int MAX_ROW = 4;
		private static final int MAX_COL = 4;
		
	}
	
	public GameMonitorPanel() {
		
		initThisPanel();
		
		addComponents( createGameMonitorList() );
		
	}
	
	private void initThisPanel() {
		
		setPreferredSize( new Dimension( 0, 385 * Matrix.MAX_ROW ) );
		setLayout( new GridLayout( Matrix.MAX_ROW, Matrix.MAX_COL ) );
		
	}
	
	private void addComponents( ArrayList<JPanel> gameMonitorList ){
		
		for(int index = 0; index < gameMonitorList.size(); index++){
			
			add( gameMonitorList.get(index) );
			
		}
		
	}
	
	private ArrayList<JPanel> createGameMonitorList(){
		
		ArrayList<JPanel> gameMonitorList = new ArrayList<JPanel>();
		
		for(int row = 0; row < Matrix.MAX_ROW; row++){
			
			for(int col = 0; col < Matrix.MAX_COL; col++){
				
				gameMonitorList.add( new GameMonitor(row, col) );
				
			}
			
		}
		
		return gameMonitorList;
		
	}
	
}
