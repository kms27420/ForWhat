package com.kms.billiardcounter.core.mainframe.components.bottom.components;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import com.kms.billiardcounter.core.contentspaneupdater.ContentsPaneUpdater;
import com.kms.billiardcounter.core.mainframe.components.bottom.components.components.bottom.GameListControlPanel;
import com.kms.billiardcounter.core.mainframe.components.bottom.components.components.bottom.PlayingScreenPanel;
import com.kms.billiardcounter.core.mainframe.components.bottom.components.components.top.BilliardTableNumberControlPanel;
import com.kms.billiardcounter.dao.tableposition.TablePositionLoader;

/**
 * 
 * 각 당구대의 상황을 보여주는 panel
 * 
 * @author Kwon
 *
 */

public class BilliardTablePanel extends JPanel{
	
	private JPanel gameListControlPanel = null;				// 현 당구대의 게임 리스트를 다룰 수 있는 인터페이스를 보여주는 panel
	private JPanel playingScreenPanel = null;					// 현 당구대가 게임이 진행 중일때를 보여주는 panel
	
	/**
	 * 
	 * 본 panel을 초기화해주는 생성자
	 * 
	 * @param row 초기화 해줄 row 위치
	 * @param col 초기화 해줄 col 위치
	 */
	public BilliardTablePanel( int row, int col ){
		
		initThisPanel();
		
		add( createBilliardTableNumberControlPanel( row, col ), BorderLayout.NORTH );
		add( gameListControlPanel = createGameListControlPanel( row, col ), BorderLayout.CENTER );
		
		repaint();
		revalidate();
		
	}
	
	/**
	 * 
	 * 본 panel의 gameListControlPanel를 새로고침해주는 매서드
	 * 
	 * @param row 본 panel의 row 위치
	 * @param col 본 panel의 col 위치
	 */
	public void refreshGameListControlPanel( int row, int col ) {
		
		if( TablePositionLoader.getTableNumberByRowAndCol( row, col ) != 0 && gameListControlPanel != null ) {
			
			remove( gameListControlPanel );
			
			gameListControlPanel = null;
			
			add( gameListControlPanel = createGameListControlPanel( row, col ), BorderLayout.CENTER );
			
			repaint();
			revalidate();
			
		}
		
	}
	
	private void initThisPanel(){
		
		setBorder( new BevelBorder(BevelBorder.RAISED) );
		setLayout( new BorderLayout() );
		
	}
	
	private JPanel createBilliardTableNumberControlPanel( int row, int col ){
		
		int tableNumber = TablePositionLoader.getTableNumberByRowAndCol(row, col);
		
		ContentsPaneUpdater createTableUpdater = new ContentsPaneUpdater(){
			
			public void update(){
				
				refreshGameListControlPanel( row, col );
				
			}
			
		};
		
		JPanel billiardTableNumberControlPanel = new BilliardTableNumberControlPanel( tableNumber, row, col, createTableUpdater	);
		
		billiardTableNumberControlPanel.setPreferredSize( new Dimension(0, 60) );
		
		return billiardTableNumberControlPanel;
		
	}
	
	private JPanel createGameListControlPanel ( int row, int col ){
		
		int tableNumber = TablePositionLoader.getTableNumberByRowAndCol(row, col);
		
		ContentsPaneUpdater playTableUpdater = new ContentsPaneUpdater(){
			
			public void update(){
								
				BilliardTablePanel.this.remove( gameListControlPanel );
				BilliardTablePanel.this.add( playingScreenPanel = createPlayingScreenPanel( row, col ) );
				
				gameListControlPanel = null;
				
				BilliardTablePanel.this.repaint();
				BilliardTablePanel.this.revalidate();
				
			}
			
		};
		
		JPanel gameListControlPanel = new GameListControlPanel( tableNumber, playTableUpdater );
		
		return gameListControlPanel;
		
	}
	
	private JPanel createPlayingScreenPanel( int row, int col ){
		
		int tableNumber = TablePositionLoader.getTableNumberByRowAndCol(row, col);
		
		ContentsPaneUpdater finishGameUpdater = new ContentsPaneUpdater(){
			
			public void update(){
				
				BilliardTablePanel.this.remove( playingScreenPanel );
				BilliardTablePanel.this.add( gameListControlPanel = createGameListControlPanel( row, col ) );
				
				playingScreenPanel = null; 
				
				BilliardTablePanel.this.repaint();
				BilliardTablePanel.this.revalidate();
				
			}
			
		};
		
		JPanel playingScreenPanel = new PlayingScreenPanel( tableNumber, finishGameUpdater );
		
		return playingScreenPanel;
		
	}

}
