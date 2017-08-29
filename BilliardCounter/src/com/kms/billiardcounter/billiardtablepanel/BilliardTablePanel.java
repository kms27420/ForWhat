package com.kms.billiardcounter.billiardtablepanel;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import com.kms.billiardcounter.contentspaneupdater.ContentsPaneUpdater;
import com.kms.billiardcounter.databasecontrol.TablePositionLoader;

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
	
	public BilliardTablePanel( int row, int col ){
		
		initThisPanel();
		
		add( createBilliardTableNumberControlPanel( row, col ), BorderLayout.NORTH );
		add( gameListControlPanel = createGameListControlPanel( row, col ), BorderLayout.CENTER );
		
		repaint();
		revalidate();
		
	}

	private void initThisPanel(){
		
		setBorder( new BevelBorder(BevelBorder.RAISED) );
		setLayout( new BorderLayout() );
		
	}
	
	private JPanel createBilliardTableNumberControlPanel( int row, int col ){
		
		int tableNumber = TablePositionLoader.getTableNum(row, col);
		
		ContentsPaneUpdater createTableUpdater = new ContentsPaneUpdater(){
			
			public void update(){
				
				BilliardTablePanel.this.remove( gameListControlPanel );
				
				BilliardTablePanel.this.add( gameListControlPanel = createGameListControlPanel( row, col ) );
				
				BilliardTablePanel.this.repaint();
				BilliardTablePanel.this.revalidate();
				
			}
			
		};
		
		JPanel billiardTableNumberControlPanel = new BilliardTableNumberControlPanel( tableNumber, row, col, createTableUpdater	);
		
		billiardTableNumberControlPanel.setPreferredSize( new Dimension(0, 60) );
		
		return billiardTableNumberControlPanel;
		
	}
	
	private JPanel createGameListControlPanel ( int row, int col ){
		
		int tableNumber = TablePositionLoader.getTableNum(row, col);
		
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
		
		int tableNumber = TablePositionLoader.getTableNum(row, col);
		
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
