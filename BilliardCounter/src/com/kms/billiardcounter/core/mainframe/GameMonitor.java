package com.kms.billiardcounter.core.mainframe;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

import com.kms.billiardcounter.core.ancillaryframe.PaymentFrame;
import com.kms.billiardcounter.database.game_list.GameListLoader;
import com.kms.billiardcounter.database.game_monitor.GameMonitorLoader;
import com.kms.billiardcounter.database.game_monitor.GameMonitorModifier;
import com.kms.billiardcounter.font.FontProvider;
import com.kms.billiardcounter.support.NumericManufacturer;
import com.kms.billiardcounter.support.TotalFeeInfo;
import com.kms.billiardcounter.support.gamefeeinfo.GameFeeInfoConvertor;

/**
 * 
 * 각 당구대의 상황을 보여주는 패널
 * 
 * @author Kwon
 *
 */

public class GameMonitor extends JPanel{
	
	private int tableNumber = 0;
	
	private BaseMonitor baseMonitor;
	private PlayingMonitor playingMonitor;
	
	/**
	 * 
	 * GameMonitor을 초기화해주는 생성자
	 * 
	 * @param row 초기화 해줄 row 위치
	 * @param col 초기화 해줄 col 위치
	 */
	public GameMonitor( int row, int col ){
		
		initThisPanel();
		
		tableNumber = GameMonitorLoader.getTableNumber( row, col );
		
		add( createTableNumberPanel( row, col ), BorderLayout.NORTH );
		add( baseMonitor = new BaseMonitor(), BorderLayout.CENTER );
		
		playingMonitor = new PlayingMonitor( tableNumber, new PlayingMonitor.OnGameEndListener() {
			
			public void onGameEnd() {
				
				GameMonitor.this.remove( GameMonitor.this.playingMonitor );
				
				baseMonitor.refresh();
				
				GameMonitor.this.add( baseMonitor );
				
				GameMonitor.this.repaint();
				GameMonitor.this.revalidate();	
				
			}
			
		} );
		
	}
	
	private void initThisPanel(){
		
		setBorder( new BevelBorder(BevelBorder.RAISED) );
		setLayout( new BorderLayout() );
		
	}
	
	private JPanel createTableNumberPanel( int row, int col ){
		
		JPanel tableNumberPanel = new JPanel();
		
		JLabel tableNumberLabel = new JLabel();		
		
		JTextField tableNumberInputField = new JTextField();
		JButton gameMonitorCreateButton = new JButton("생성");
		
		MouseListener deleteListener = new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				
				e.getComponent().setBackground( null );
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				
				e.getComponent().setBackground( Color.LIGHT_GRAY );
				
			}
			
			@Override
			public void mouseClicked(MouseEvent labelClickedEvent) {
				
				if( GameListLoader.getNonPaidGameFeeInfoList( tableNumber ).size() > 0 )	return;
				
				Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
				Dimension frameSize = new Dimension( 400, 200 );
				
				JFrame monitorDeleteFrame = new JFrame( "당구대 빼기" );
				
				JPanel guidanceViewer = new JPanel();
				JLabel guidanceLabel = new JLabel( "정말 " + tableNumber + "번 당구대를 제거하시겠습니까?" );
				
				JPanel buttonViewer = new JPanel();
				JButton confirmButton = new JButton( "제거" );
				JButton cancelButton = new JButton( "취소" );
				
				guidanceLabel.setPreferredSize( new Dimension( 300, 50 ) );
				guidanceLabel.setHorizontalTextPosition( JLabel.CENTER );
				guidanceLabel.setFont( FontProvider.getDefaultFont() );
				
				guidanceViewer.setBorder( BorderFactory.createEmptyBorder( 20, 0, 0, 0 ) );
				guidanceViewer.add( guidanceLabel );
				
				confirmButton.setPreferredSize( new Dimension( 80, 50 ) );
				confirmButton.addActionListener( new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent confirmEvent) {

						if( GameMonitorModifier.deleteGameMonitor( tableNumber ) ) {
							
							tableNumber = 0;
							
							tableNumberPanel.removeAll();
							
							tableNumberPanel.setLayout( new BorderLayout() );
							tableNumberPanel.add( tableNumberInputField, BorderLayout.CENTER );
							tableNumberPanel.add( gameMonitorCreateButton, BorderLayout.EAST );
							
							tableNumberPanel.repaint();
							tableNumberPanel.revalidate();
							
							monitorDeleteFrame.dispose();
							
							baseMonitor.refresh();
							
						}
						
					}
					
				} );
				
				cancelButton.setPreferredSize( new Dimension( 80, 50 ) );
				cancelButton.addActionListener( new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent cancelEvent) {
						
						monitorDeleteFrame.dispose();
						
					}
					
				} );
				
				buttonViewer.setPreferredSize( new Dimension( 0, 60 ) );
				buttonViewer.setBorder( BorderFactory.createEmptyBorder( 0, 0, 5, 0 ) );
				buttonViewer.add( confirmButton );
				buttonViewer.add( cancelButton );
				
				monitorDeleteFrame.setLocation( screenSize.width / 2 - frameSize.width / 2, screenSize.height / 2 - frameSize.height / 2 );
				monitorDeleteFrame.setSize( frameSize );
				monitorDeleteFrame.setLayout( new BorderLayout() );
				monitorDeleteFrame.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
				
				monitorDeleteFrame.add( guidanceViewer, BorderLayout.CENTER );
				monitorDeleteFrame.add( buttonViewer, BorderLayout.SOUTH );
				
				monitorDeleteFrame.setVisible( true );
				
			}
			
		};
		
		ActionListener createListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent createEvent) {
				
				int integerOfInputText = NumericManufacturer.getIntConsistingOnlyOfNumeric( tableNumberInputField.getText() );
				
				if( GameMonitorModifier.saveNewGameMonitor( integerOfInputText, row, col ) ){
				
					tableNumber = integerOfInputText;
					
					tableNumberPanel.removeAll();
					
					tableNumberLabel.setText( tableNumber + "번 테이블" );
					
					tableNumberPanel.setLayout( new GridLayout( 1, 1 ) );
					tableNumberPanel.add( tableNumberLabel );
					
					tableNumberPanel.repaint();
					tableNumberPanel.revalidate();
					
					baseMonitor.refresh();
				
				}
				
			}
			
		};
		
		tableNumberLabel.setFont( FontProvider.getDefaultFont() );
		tableNumberLabel.setHorizontalAlignment( JLabel.CENTER );
		tableNumberLabel.setOpaque( true );
		tableNumberLabel.addMouseListener( deleteListener );
		
		tableNumberInputField.setPreferredSize( new Dimension(200, 50) );
		tableNumberInputField.setFont( FontProvider.getDefaultFont() );
		tableNumberInputField.setHorizontalAlignment( JTextField.CENTER );
		
		gameMonitorCreateButton.setPreferredSize( new Dimension(100, 50) );
		gameMonitorCreateButton.addActionListener( createListener );
		
		if( tableNumber > 0 ) {
			
			tableNumberLabel.setText( tableNumber + "번 테이블" );
			
			tableNumberPanel.setLayout( new GridLayout( 1, 1 ) );
			tableNumberPanel.add( tableNumberLabel );
			
		} else {
			
			tableNumberPanel.setLayout( new BorderLayout() );
			tableNumberPanel.add( tableNumberInputField, BorderLayout.CENTER );
			tableNumberPanel.add( gameMonitorCreateButton, BorderLayout.EAST );
			
		}
		
		tableNumberPanel.setPreferredSize( new Dimension(0, 60) );
		tableNumberPanel.setBorder( new BevelBorder(BevelBorder.RAISED) );
		
		return tableNumberPanel;
		
	}
	
	private class BaseMonitor extends JPanel {
		
		private JPanel gameListPanel = new JPanel();
		private ArrayList<JLabel> gameLabelList;
		private JLabel guidanceLabel = new JLabel();
		
		private JLabel totalUsedTimeLabel = new JLabel();	
		private JLabel totalFeeLabel = new JLabel();			
				
		private JButton payButton = new JButton( "계산" );
		private JButton startButton = new JButton( "시작" );	
		
		private BaseMonitor() {
			
			init();
			
			add( createGameListPanelViewer(), BorderLayout.CENTER );
			add( createGameControler(), BorderLayout.SOUTH );
			
		}
		
		private void init() {
			
			setLayout( new BorderLayout() );
			
			gameListPanel.setBorder( new BevelBorder( BevelBorder.RAISED ) );
			
			guidanceLabel.setHorizontalAlignment( JLabel.CENTER );
			guidanceLabel.setFont( FontProvider.getDefaultFont() );
			
			totalUsedTimeLabel.setHorizontalAlignment( JLabel.CENTER );
			totalUsedTimeLabel.setFont( FontProvider.getDefaultFont() );
			
			totalFeeLabel.setHorizontalAlignment( JLabel.CENTER );
			totalFeeLabel.setFont( FontProvider.getDefaultFont() );
			
			payButton.setFont( FontProvider.getDefaultFont() );
			payButton.addActionListener( new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {

					new PaymentFrame( tableNumber, new PaymentFrame.OnPaymentCompleteListener() {
						
						@Override
						public void onPaymentComplete() {

							refresh();
							
						}
						
					} );
					
				}
				
			} );
			
			startButton.setFont( FontProvider.getDefaultFont() );
			startButton.addActionListener( new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {

					if( !GameMonitorLoader.getIsInUse( tableNumber ) ) {
						
						if( !GameMonitorModifier.useThisGameMonitor( tableNumber ) ) {	
							
							return;
					
						}
					
					} 
					
					GameMonitor.this.remove( GameMonitor.this.baseMonitor );
					GameMonitor.this.add( playingMonitor );
					
					playingMonitor.refresh();
					
					GameMonitor.this.repaint();
					GameMonitor.this.revalidate();
					
				}
				
			} );
			
			refresh();
			
		}
		
		private void refresh() {
			
			if( tableNumber == 0 ) {
				
				guidanceLabel.setText( "테이블 없음" );
				
				gameListPanel.removeAll();
				
				gameListPanel.setPreferredSize( new Dimension( 0, 50 ) );
				gameListPanel.setLayout( new GridLayout( 1, 1 ) );
				gameListPanel.add( guidanceLabel );
				
				totalUsedTimeLabel.setText( "게임 없음" );
				totalFeeLabel.setText( "" );
				
				payButton.setEnabled( false );
				startButton.setEnabled( false );
				
				gameListPanel.repaint();
				gameListPanel.revalidate();
				
				return;
				
			}
			
			int gameLabelHeight = 50;
			
			gameLabelList = GameFeeInfoConvertor.convertToLabelList( GameListLoader.getNonPaidGameFeeInfoList( tableNumber ) );
			
			gameListPanel.removeAll();
			gameListPanel.setPreferredSize( new Dimension( 0, gameLabelHeight * ( gameLabelList.size() + 1 ) ) );
			gameListPanel.setLayout( new GridLayout( gameLabelList.size() + 1, 1 ) );
			
			startButton.setEnabled( true );
			
			if( gameLabelList.size() == 0 ) {
				
				guidanceLabel.setText( "게임 없음" );
				
				gameListPanel.add( guidanceLabel );
				
				totalUsedTimeLabel.setText( "게임 없음" );
				totalFeeLabel.setText( "" );
				
				payButton.setEnabled( false );
				
			} else {
				
				for( int index = 0; index < gameLabelList.size(); index++ ) {
					
					gameListPanel.add( gameLabelList.get( index ) );
					
				}
				
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
		
	}

}
