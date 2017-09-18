package com.kms.billiardcounter.core.mainframe;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JPanel;

import com.kms.billiardcounter.core.event.GameTableReplace;
import com.kms.billiardcounter.database.game_viewer.GameViewerLoader;

/**
 * 
 * 각 당구대 상황을 보여주는 panel들을 모아놓은 panel
 * 
 * @author Kwon
 *
 */

public class GameViewerPanel extends JPanel{
	
	private class Matrix {
		
		private static final int MAX_ROW = 4;
		private static final int MAX_COL = 4;
		
	}

	private ArrayList<JPanel> gameViewerList;
	
	public GameViewerPanel() {
		
		initThisPanel();
		
		addComponents( gameViewerList = createGameViewerList() );
		
	}
	
	private void initThisPanel() {
		
		setPreferredSize( new Dimension( 0, 385 * Matrix.MAX_ROW ) );
		setLayout( new GridLayout( Matrix.MAX_ROW, Matrix.MAX_COL ) );
		
		GameTableReplace.setOnReplaceTableListener( new GameTableReplace.OnReplaceTableListener() {
			
			@Override
			public void onReplaceTable() {
				
				for( int index = 0; index < gameViewerList.size(); index++ ){
					
					if( GameViewerLoader.getTableNumber( index / Matrix.MAX_COL, index % Matrix.MAX_COL ) != 0 ) {
					
						( (GameViewer)gameViewerList.get( index ) ).refreshGameListControlPanel( index / Matrix.MAX_COL, index % Matrix.MAX_COL );
					
					}
					
				}
				
			}
			
		} );
		
	}
	
	private void addComponents( ArrayList<JPanel> billiardTablePanelsList ){
		
		for(int index = 0; index < billiardTablePanelsList.size(); index++){
			
			add( billiardTablePanelsList.get(index) );
			
		}
		
	}
	
	private ArrayList<JPanel> createGameViewerList(){
		
		ArrayList<JPanel> gameViewerList = new ArrayList<JPanel>();
		
		for(int row = 0; row < Matrix.MAX_ROW; row++){
			
			for(int col = 0; col < Matrix.MAX_COL; col++){
				
				gameViewerList.add( new GameViewer(row, col) );
				
			}
			
		}
		
		return gameViewerList;
		
	}
	
}
