package com.kms.billiardcounter.core.mainframe;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import com.kms.billiardcounter.database.game_viewer.GameViewerLoader;

/**
 * 
 * 각 당구대의 상황을 보여주는 panel
 * 
 * @author Kwon
 *
 */

public class GameViewer extends JPanel{
	
	private JPanel gameListControlPanel = null;				// 현 당구대의 게임 리스트를 다룰 수 있는 인터페이스를 보여주는 panel
	private JPanel playingScreenPanel = null;					// 현 당구대가 게임이 진행 중일때를 보여주는 panel
	
	/**
	 * 
	 * 본 panel을 초기화해주는 생성자
	 * 
	 * @param row 초기화 해줄 row 위치
	 * @param col 초기화 해줄 col 위치
	 */
	public GameViewer( int row, int col ){
		
		initThisPanel();
		
		add( createBilliardTableNumberControlPanel( row, col ), BorderLayout.NORTH );
		add( gameListControlPanel = createGameListControlPanel( row, col ), BorderLayout.CENTER );
		
	}
	
	/**
	 * 
	 * 본 panel의 gameListControlPanel를 새로고침해주는 매서드
	 * 
	 * @param row 본 panel의 row 위치
	 * @param col 본 panel의 col 위치
	 */
	public void refreshGameListControlPanel( int row, int col ) {
		
		if( gameListControlPanel != null ) {
			
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
		
		int tableNumber = GameViewerLoader.getTableNumber(row, col);
		
		JPanel billiardTableNumberControlPanel = new BilliardTableNumberControlPanel( tableNumber, row, col, new BilliardTableNumberControlPanel.OnChangeNumberOfBilliardTableListener() {
			
			@Override
			public void onChangeNumberOfBilliardTable() {
				
				refreshGameListControlPanel( row, col );
				
			}
			
		} );
		
		billiardTableNumberControlPanel.setPreferredSize( new Dimension(0, 60) );
		
		return billiardTableNumberControlPanel;
		
	}
	
	private JPanel createGameListControlPanel ( int row, int col ){
		
		int tableNumber = GameViewerLoader.getTableNumber(row, col);
		
		JPanel gameListControlPanel = new GameListControlPanel( tableNumber, new GameListControlPanel.OnGameStartListener() {
			
			@Override
			public void onGameStart() {
				
				GameViewer.this.remove( GameViewer.this.gameListControlPanel );
				GameViewer.this.add( playingScreenPanel = createPlayingScreenPanel( row, col ) );
				
				GameViewer.this.gameListControlPanel = null;
				
				GameViewer.this.repaint();
				GameViewer.this.revalidate();
				
			}
			
		});
		
		return gameListControlPanel;
		
	}
	
	private JPanel createPlayingScreenPanel( int row, int col ){
		
		int tableNumber = GameViewerLoader.getTableNumber(row, col);
		
		JPanel playingScreenPanel = new PlayingScreenPanel( tableNumber, new PlayingScreenPanel.OnGameEndListener() {
			
			@Override
			public void onGameEnd() {
				
				GameViewer.this.remove( GameViewer.this.playingScreenPanel );
				GameViewer.this.add( gameListControlPanel = createGameListControlPanel( row, col ) );
				
				GameViewer.this.playingScreenPanel = null; 
				
				GameViewer.this.repaint();
				GameViewer.this.revalidate();
				
			}
			
		} );
		
		return playingScreenPanel;
		
	}

}
