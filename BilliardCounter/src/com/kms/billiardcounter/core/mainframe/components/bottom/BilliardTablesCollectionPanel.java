package com.kms.billiardcounter.core.mainframe.components.bottom;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JPanel;

import com.kms.billiardcounter.core.contentspaneupdater.ContentsPaneUpdater;
import com.kms.billiardcounter.core.event.GameTableReplace;
import com.kms.billiardcounter.core.mainframe.components.bottom.components.BilliardTablePanel;
import com.kms.billiardcounter.dao.tableposition.TablePositionLoader;

/**
 * 
 * 각 당구대 상황을 보여주는 panel들을 모아놓은 panel
 * 
 * @author Kwon
 *
 */

public class BilliardTablesCollectionPanel extends JPanel{
	
	private class Matrix {
		
		private static final int MAX_ROW = 4;
		private static final int MAX_COL = 4;
		
	}

	private ArrayList<JPanel> billiardTablePanelsList;
	
	public BilliardTablesCollectionPanel() {
		
		initThisPanel();
		
		addComponents( billiardTablePanelsList = createBilliardTablePanelsList() );
		
		repaint();
		revalidate();
		
	}
	
	private void initThisPanel() {
		
		setPreferredSize( new Dimension( 0, 385 * Matrix.MAX_ROW ) );
		setLayout( new GridLayout( Matrix.MAX_ROW, Matrix.MAX_COL ) );
		
		GameTableReplace.setPaneUpdaterAfterReplacingTable( new ContentsPaneUpdater() {
			
			@Override
			public void update() {
				
				BilliardTablesCollectionPanel.this.removeAll();
				
				for( int index = 0; index < billiardTablePanelsList.size(); index++ ){
					
					if( TablePositionLoader.getTableNumberByRowAndCol( index / Matrix.MAX_COL, index % Matrix.MAX_COL ) != 0 ) {
					
						( (BilliardTablePanel)billiardTablePanelsList.get( index ) ).refreshGameListControlPanel( index / Matrix.MAX_COL, index % Matrix.MAX_COL );
					
					}
					
				}
				
				BilliardTablesCollectionPanel.this.addComponents( billiardTablePanelsList );
				
				repaint();
				revalidate();
				
			}
			
		} );
		
	}
	
	private void addComponents( ArrayList<JPanel> billiardTablePanelsList ){
		
		for(int index = 0; index < billiardTablePanelsList.size(); index++){
			
			add( billiardTablePanelsList.get(index) );
			
		}
		
	}
	
	private ArrayList<JPanel> createBilliardTablePanelsList(){
		
		ArrayList<JPanel> billiardTablePanelsList = new ArrayList<JPanel>();
		
		for(int row = 0; row < Matrix.MAX_ROW; row++){
			
			for(int col = 0; col < Matrix.MAX_COL; col++){
				
				billiardTablePanelsList.add( new BilliardTablePanel(row, col) );
				
			}
			
		}
		
		return billiardTablePanelsList;
		
	}
	
}
