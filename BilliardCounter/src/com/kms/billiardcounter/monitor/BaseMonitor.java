package com.kms.billiardcounter.monitor;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;

import com.kms.billiardcounter.database.game_list.GameListLoader;
import com.kms.billiardcounter.database.game_list.GameListModifier;
import com.kms.billiardcounter.database.game_monitor.GameMonitorLoader;
import com.kms.billiardcounter.database.game_monitor.GameMonitorModifier;
import com.kms.billiardcounter.font.FontProvider;
import com.kms.billiardcounter.frame.PaymentFrame;
import com.kms.billiardcounter.support.GameFeeInfoConvertor;
import com.kms.billiardcounter.support.TotalFeeInfo;

public class BaseMonitor extends JPanel {
	
	public interface OnStartGameListener {
		
		public void onStartGame();
		
	}
	
	private final int row, col;
	
	private JPanel gameListPanel;
	private JButton replaceButton;
	private JLabel guidanceLabel;
	
	private JLabel totalUsedTimeLabel;	
	private JLabel totalFeeLabel;		
			
	private JButton payButton;
	private JButton startButton;
	
	public BaseMonitor( int row, int col, OnStartGameListener onStartGameListener ) {
		
		this.row = row;
		this.col = col;
		
		initComponents( onStartGameListener );
		
		setLayout( new BorderLayout() );
		add( createGameListPanelViewer(), BorderLayout.CENTER );
		add( createGameControler(), BorderLayout.SOUTH );
		
		addPropertyChangeListener( new PropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				
				if( evt.getPropertyName() == "ancestor" && evt.getNewValue() != null && evt.getOldValue() == null ) {
					
					refresh();
					
				}
				
			}
			
		} );
		
	}
	
	private void initComponents( OnStartGameListener onStartGameListener ) {
		
		gameListPanel = new JPanel();
		gameListPanel.setBorder( new BevelBorder( BevelBorder.RAISED ) );
		
		replaceButton = new JButton();
		replaceButton.setFont( FontProvider.getDefaultFont() );
		replaceButton.addActionListener( BaseMonitorReplace.INSTANCE );
		
		guidanceLabel = new JLabel();
		guidanceLabel.setHorizontalAlignment( JLabel.CENTER );
		guidanceLabel.setFont( FontProvider.getDefaultFont() );
		
		totalUsedTimeLabel = new JLabel();	
		totalUsedTimeLabel.setHorizontalAlignment( JLabel.CENTER );
		totalUsedTimeLabel.setFont( FontProvider.getDefaultFont() );
		
		totalFeeLabel = new JLabel();		
		totalFeeLabel.setHorizontalAlignment( JLabel.CENTER );
		totalFeeLabel.setFont( FontProvider.getDefaultFont() );
		
		payButton = new JButton( "계산" );
		payButton.setFont( FontProvider.getDefaultFont() );
		payButton.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				new PaymentFrame( GameMonitorLoader.getTableNumber( row, col ), new PaymentFrame.OnPaymentCompleteListener() {
					
					@Override
					public void onPaymentComplete() {
						
						if( GameMonitorModifier.endUseThisGameMonitor( GameMonitorLoader.getTableNumber( row, col ) ) ) {
							
							refresh();
							
						}
						
					}
					
				} );
				
			}
			
		} );
		
		startButton = new JButton( "시작" );
		startButton.setFont( FontProvider.getDefaultFont() );
		startButton.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				if( !GameMonitorLoader.getIsTableInUse( GameMonitorLoader.getTableNumber( row, col ) ) ) {
					
					if( !GameMonitorModifier.useThisGameMonitor( GameMonitorLoader.getTableNumber( row, col ) ) ) {	
						
						return;
				
					}
				
				} 
				
				
				onStartGameListener.onStartGame();
				
			}
			
		} );
		
	}
	
	private void refresh() {
		
		int tableNumber = GameMonitorLoader.getTableNumber( row, col );
		
		gameListPanel.removeAll();
		
		if( tableNumber == 0 ) {
			
			guidanceLabel.setText( "테이블 없음" );
			
			gameListPanel.setPreferredSize( new Dimension( 0, 50 ) );
			gameListPanel.setLayout( new GridLayout( 1, 1 ) );
			gameListPanel.add( guidanceLabel );
			
			totalUsedTimeLabel.setText( "게임 없음" );
			totalFeeLabel.setText( "" );
			
			payButton.setEnabled( false );
			startButton.setEnabled( false );
			
			BaseMonitorReplace.removeRefreshMonitor( this );
			
			gameListPanel.repaint();
			gameListPanel.revalidate();
			
			return;
			
		}
		
		int gameLabelHeight = 50;
		
		ArrayList<JLabel> gameLabelList = GameFeeInfoConvertor.convertToLabelList( GameListLoader.getNonPaidGameFeeInfoList( tableNumber ) );
		
		gameListPanel.setPreferredSize( new Dimension( 0, gameLabelHeight * ( gameLabelList.size() + 1 ) ) );
		gameListPanel.setLayout( new GridLayout( gameLabelList.size() + 1, 1 ) );
		
		startButton.setEnabled( true );
		
		BaseMonitorReplace.addRefreshMonitor( this );
		
		if( gameLabelList.size() == 0 ) {
			
			if( BaseMonitorReplace.isReplacementActivated() ) {
				
				replaceButton.setText( "이곳으로 옯기기" );
				
				gameListPanel.add( replaceButton );
				
			} else {
				
				guidanceLabel.setText( "게임 없음" );
				
				gameListPanel.add( guidanceLabel );
			
			}
			
			totalUsedTimeLabel.setText( "게임 없음" );
			totalFeeLabel.setText( "" );
			
			payButton.setEnabled( false );
			
		} else {
			
			for( int index = 0; index < gameLabelList.size(); index++ ) {
				
				gameListPanel.add( gameLabelList.get( index ) );
				
			}
			
			if( BaseMonitorReplace.isReplacementActivated() ) {
				
				replaceButton.setText( "자리 옯기기 취소" );
				
			} else {
				
				replaceButton.setText( "자리 옮기기" );
			
			}
			
			gameListPanel.add( replaceButton );
			
			TotalFeeInfo totalFeeInfo = GameListLoader.getNonPaidGamesTotalFeeInfo( tableNumber );
			
			if( totalFeeInfo.getIsValid() ) {
			
				totalUsedTimeLabel.setText( "총 사용 시간  " +  totalFeeInfo.getUsedTime() );
				totalFeeLabel.setText( "총 요금 : " + totalFeeInfo.getFee() );
				
			}
			
			payButton.setEnabled( true );
			
		}
		
		gameListPanel.repaint();
		gameListPanel.revalidate();
		
	}
	
	private JScrollPane createGameListPanelViewer() {
		
		JScrollPane gameListPanelViewer = new JScrollPane( gameListPanel );
		
		gameListPanelViewer.setPreferredSize( new Dimension( 0, 200 ) );
		
		return gameListPanelViewer;
		
	}
	
	private JPanel createGameControler() {
		
		JPanel gameControler = new JPanel();
		
		JPanel totalFeeInfoViewer = new JPanel();
		JPanel buttonViewer = new JPanel();
		
		totalFeeInfoViewer.setPreferredSize( new Dimension(200, 100) );
		totalFeeInfoViewer.setLayout( new GridLayout(2, 1) );
		totalFeeInfoViewer.add( totalUsedTimeLabel );
		totalFeeInfoViewer.add( totalFeeLabel );
		
		buttonViewer.setPreferredSize( new Dimension( 200, 100 ) );
		buttonViewer.setLayout( new GridLayout( 1, 2 ) );
		buttonViewer.add( payButton );
		buttonViewer.add( startButton );
		
		gameControler.setBorder( new BevelBorder( BevelBorder.RAISED ) );
		gameControler.setLayout( new BorderLayout() );
		gameControler.add( totalFeeInfoViewer, BorderLayout.CENTER );
		gameControler.add( buttonViewer, BorderLayout.EAST );
		
		return gameControler;
		
	}
	
	private static class BaseMonitorReplace implements ActionListener {
		
		private static ArrayList<BaseMonitor> refreshMonitor = new ArrayList<BaseMonitor>();
		private static int activatedTableNumber;
		
		private static final BaseMonitorReplace INSTANCE = new BaseMonitorReplace();
		
		private BaseMonitorReplace(){}
		
		private static void addRefreshMonitor( BaseMonitor baseMonitor ) {
			
			if( !BaseMonitorReplace.refreshMonitor.contains( baseMonitor) )	{
			
				BaseMonitorReplace.refreshMonitor.add( baseMonitor );
			
			}
			
		}
		
		private static void removeRefreshMonitor( BaseMonitor baseMonitor ) {
			
			BaseMonitorReplace.refreshMonitor.remove( baseMonitor );
			
		}
		
		private static boolean isReplacementActivated() {
			
			return activatedTableNumber > 0 ? true : false; 
			
		}
		
		@Override
		public void actionPerformed( ActionEvent e ) {
			
			int tableNumber = 0;
			
			for( Container baseMonitor = ( (JButton)e.getSource() ).getParent(); ; baseMonitor = baseMonitor.getParent() ) {
				
				if( baseMonitor instanceof BaseMonitor ) {
					
					tableNumber = GameMonitorLoader.getTableNumber( ( (BaseMonitor)baseMonitor ).row, ( (BaseMonitor)baseMonitor ).col );
					
					break;
					
				}
				
			}
			
			if( isReplacementActivated() ) {
				
				if( !GameListLoader.getIsThereNonPaidGames( tableNumber ) ) {
				
					if( !GameListModifier.replaceGameMonitorData( activatedTableNumber, tableNumber ) ) {
					
						return;
					
					}
				
				}
				
				activatedTableNumber = 0;
				
			} else {
				
				if( !GameMonitorLoader.getIsThereUnusedGameMonitor() ) {
					
					System.out.println( "모든 당구대가 사용 중입니다." );
					
					return;
					
				}
				
				activatedTableNumber = tableNumber;
				
			}
			
			for( int index = 0; index < refreshMonitor.size(); index++ ) {
				
				refreshMonitor.get( index ).refresh();
			
			}
			
		}
		
	}
	
}