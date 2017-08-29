package com.kms.billiardcounter.billiardcounterframe;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JPanel;

import com.kms.billiardcounter.billiardtablepanel.BilliardTablePanel;

/**
 * 
 * 각 당구대 상황을 보여주는 panel들을 모아놓은 panel
 * 
 * @author Kwon
 *
 */

public class BilliardTablesCollectionPanel extends JPanel{
	
	public BilliardTablesCollectionPanel() {
		
		Aligner aligner = new Aligner();
		
		addComponents( createBilliardTablePanelsList() );
		
		aligner.align();
		
		repaint();
		revalidate();
		
	}
	
	private class Aligner{
		
		public static final int MAX_ROW = 4;
		public static final int MAX_COL = 4;
		
		private void align(){
			
			BilliardTablesCollectionPanel.this.setPreferredSize( new Dimension( 0, 385 * MAX_ROW ) );
			BilliardTablesCollectionPanel.this.setLayout( new GridLayout( MAX_ROW, MAX_COL ) );
			
		}
		
	}
	
	private void addComponents( ArrayList<JPanel> billiardTablePanelsList ){
		
		for(int index = 0; index < billiardTablePanelsList.size(); index++){
			
			add( billiardTablePanelsList.get(index) );
			
		}
		
	}
	
	private ArrayList<JPanel> createBilliardTablePanelsList(){
		
		ArrayList<JPanel> billiardTablePanelsList = new ArrayList<JPanel>();
		
		for(int row = 0; row < Aligner.MAX_ROW; row++){
			
			for(int col = 0; col < Aligner.MAX_COL; col++){
				
				billiardTablePanelsList.add( new BilliardTablePanel(row, col) );
				
			}
			
		}
		
		return billiardTablePanelsList;
		
	}
	
}
