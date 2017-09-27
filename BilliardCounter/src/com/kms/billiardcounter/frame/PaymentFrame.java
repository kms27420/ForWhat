package com.kms.billiardcounter.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.kms.billiardcounter.database.game_list.GameListModifier;
import com.kms.billiardcounter.database.game_list.GameListLoader;
import com.kms.billiardcounter.font.FontProvider;
import com.kms.billiardcounter.size.DeviceSize;
import com.kms.billiardcounter.size.FrameSize;
import com.kms.billiardcounter.support.GameFeeInfo;
import com.kms.billiardcounter.support.GameFeeInfoConvertor;
import com.kms.billiardcounter.support.NumericManufacturer;

/**
 * 
 * 요금 계산을 처리하는 frame 클래스
 * 
 * @author Kwon
 *
 */
public class PaymentFrame extends JFrame {

	public interface OnPaymentCompleteListener {
		
		public void onPaymentComplete();
		
	}
	
	private interface OnOptionChangeListener {
		
		public void onOptionChange();
		
	}
	
	private interface OnSelectedGamePayListener {
		
		public void onSelectedGamePay();
		
	}
	
	private interface OnSelectedGameAddListener {
		
		public void onSelectedGameAdd();
		
	}
	
	private OnOptionChangeListener onOptionChangeListener = null;
	private OnSelectedGamePayListener onSelectedGamePayListener = null;
	private OnSelectedGameAddListener onSelectedGameAddListener = null;
	
	private final String SELECT_ALL = "SELECT_ALL";
	private final String SELECT_PARTIAL = "SELECT_PARTIAL";
	
	private JLabel selectedOptionLabel;
	private ArrayList<GameFeeInfo> selectedGameFeeInfoList = new ArrayList<GameFeeInfo>();
	
	public PaymentFrame( int tableNumber, OnPaymentCompleteListener onPaymentCompleteListener ) {
		
		setPreferredSize( FrameSize.getPaymentFrameSize() );
		pack();
		setLocation( ( DeviceSize.getScreenSize().width - FrameSize.getPaymentFrameSize().width ) / 2, 
				( DeviceSize.getScreenSize().height - FrameSize.getPaymentFrameSize().height ) / 2 );
		setResizable( false );
		
		setTitle( tableNumber + "번 테이블 계산" );		
		setDefaultCloseOperation( DO_NOTHING_ON_CLOSE );
		
		getContentPane().setLayout( new BorderLayout() );
		getContentPane().add( createSelectOptionPanel(), BorderLayout.NORTH );
		getContentPane().add( createNonPaidGameFeeInfoListPanel( tableNumber, onPaymentCompleteListener ), BorderLayout.CENTER );
		getContentPane().add( createPaymentControlPanel(), BorderLayout.SOUTH );
		
		setVisible( true );
		
	}
	
	private JPanel createSelectOptionPanel() {
		
		JPanel selectOptionPanel = new JPanel();
		
		JLabel selectAllGameLabel = new JLabel( "전체 선택" );
		JLabel selectPartialGameLabel = new JLabel( "부분 선택" );
		
		selectedOptionLabel = selectAllGameLabel;
		
		MouseListener labelListMouseListener = new MouseListener() {
			
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

				if( selectedOptionLabel.equals( e.getComponent() ) ) {
					
					e.getComponent().setBackground( Color.LIGHT_GRAY.brighter() );
					
				} else {
					
					e.getComponent().setBackground( Color.LIGHT_GRAY.darker() );
					
				}
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				
				if( !selectedOptionLabel.equals( e.getComponent() ) ) {
					
					e.getComponent().setBackground( Color.LIGHT_GRAY );
				
				}
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {

				if( !selectedOptionLabel.equals( e.getComponent() ) ) {
					
					selectedOptionLabel.setBackground( Color.LIGHT_GRAY.darker() );
					e.getComponent().setBackground( Color.LIGHT_GRAY.brighter() );
					
					selectedOptionLabel = (JLabel)e.getComponent();
					
					onOptionChangeListener.onOptionChange();
					
				} 
				
			}
			
		};
		
		selectAllGameLabel.setName( SELECT_ALL );
		selectAllGameLabel.setOpaque( true );
		selectAllGameLabel.setBackground( Color.LIGHT_GRAY.brighter() );
		selectAllGameLabel.setFont( FontProvider.getDefaultFont() );
		selectAllGameLabel.setHorizontalAlignment( JLabel.CENTER );
		selectAllGameLabel.addMouseListener( labelListMouseListener );
		
		selectPartialGameLabel.setName( SELECT_PARTIAL );
		selectPartialGameLabel.setOpaque( true );
		selectPartialGameLabel.setBackground( Color.LIGHT_GRAY.darker() );
		selectPartialGameLabel.setFont( FontProvider.getDefaultFont() );
		selectPartialGameLabel.setHorizontalAlignment( JLabel.CENTER );
		selectPartialGameLabel.addMouseListener( labelListMouseListener );
		
		selectOptionPanel.setPreferredSize( new Dimension( 0, 40 ) );
		selectOptionPanel.setLayout( new GridLayout( 1, 2 ) );
		selectOptionPanel.add( selectAllGameLabel );
		selectOptionPanel.add( selectPartialGameLabel );
		
		return selectOptionPanel;
		
	}
	
	private JPanel createNonPaidGameFeeInfoListPanel( int tableNumber, OnPaymentCompleteListener onPaymentCompleteListener ) {
		
		JPanel nonPaidGameFeeInfoListPanel = new JPanel();
		
		ArrayList<GameFeeInfo> nonPaidGameFeeInfoList = GameListLoader.getNonPaidGameFeeInfoList( tableNumber );
		ArrayList<JLabel> nonPaidGameFeeInfoLabelList = GameFeeInfoConvertor.convertToLabelList( nonPaidGameFeeInfoList );
		
		MouseListener labelListMouseListener = new MouseListener() {
			
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

				GameFeeInfo clickedGameFeeInfo = nonPaidGameFeeInfoList.get( nonPaidGameFeeInfoLabelList.indexOf( e.getComponent() ) ) ;
				
				if( selectedGameFeeInfoList.contains( clickedGameFeeInfo ) ) {
					
					e.getComponent().setBackground( Color.LIGHT_GRAY.brighter() );
					
				} else {
					
					e.getComponent().setBackground( Color.LIGHT_GRAY.darker() );
					
				}
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				
				e.getComponent().setBackground( Color.LIGHT_GRAY );
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {

				GameFeeInfo clickedGameFeeInfo = nonPaidGameFeeInfoList.get( nonPaidGameFeeInfoLabelList.indexOf( e.getComponent() ) ) ;
				
				if( selectedGameFeeInfoList.contains( clickedGameFeeInfo ) ) {
					
					selectedGameFeeInfoList.remove( clickedGameFeeInfo );
					
					e.getComponent().setBackground( Color.LIGHT_GRAY.darker() );
					
				} else {
					
					selectedGameFeeInfoList.add( clickedGameFeeInfo );
					
					e.getComponent().setBackground( Color.LIGHT_GRAY.brighter() );
					
				}
				
				onSelectedGameAddListener.onSelectedGameAdd();
				
			}
			
		};
		
		onOptionChangeListener = new OnOptionChangeListener() {
			
			@Override
			public void onOptionChange() {
				
				if( selectedOptionLabel.getName().equals( SELECT_PARTIAL ) ) {
					
					for( int index = 0; index < nonPaidGameFeeInfoLabelList.size(); index++ ) {
						
						nonPaidGameFeeInfoLabelList.get( index ).addMouseListener( labelListMouseListener );
						nonPaidGameFeeInfoLabelList.get( index ).setBackground( Color.LIGHT_GRAY.darker() );
						
					}
					
					selectedGameFeeInfoList.clear();
					
				} else {
					
					selectedGameFeeInfoList.clear();
					
					for( int index = 0; index < nonPaidGameFeeInfoLabelList.size(); index++ ) {
						
						nonPaidGameFeeInfoLabelList.get( index ).removeMouseListener( labelListMouseListener );
						nonPaidGameFeeInfoLabelList.get( index ).setBackground( Color.LIGHT_GRAY.brighter() );
						
						selectedGameFeeInfoList.add( nonPaidGameFeeInfoList.get( index ) );
						
					}
					
				}
				
				onSelectedGameAddListener.onSelectedGameAdd();
				
			}
			
		};
		
		onSelectedGamePayListener = new OnSelectedGamePayListener() {
			
			@Override
			public void onSelectedGamePay() {
				
				if( selectedGameFeeInfoList.size() == nonPaidGameFeeInfoList.size() ) {
					
					PaymentFrame.this.dispose();
					
					onPaymentCompleteListener.onPaymentComplete();
					
					return;
					
				}
				
				for( int index = 0; index < selectedGameFeeInfoList.size(); index++ ) {
					
					nonPaidGameFeeInfoLabelList.remove( nonPaidGameFeeInfoList.indexOf( selectedGameFeeInfoList.get( index ) ) );
					nonPaidGameFeeInfoList.remove( selectedGameFeeInfoList.get( index ) );
					
				}
				
				selectedGameFeeInfoList.clear();
				
				nonPaidGameFeeInfoListPanel.removeAll();
				
				for( int index = 0; index < nonPaidGameFeeInfoLabelList.size(); index++ ) {
					
					nonPaidGameFeeInfoListPanel.add( nonPaidGameFeeInfoLabelList.get( index ) );
					
				}
				
				nonPaidGameFeeInfoListPanel.setLayout( new GridLayout( nonPaidGameFeeInfoLabelList.size(), 1 ) );
				
				nonPaidGameFeeInfoListPanel.repaint();
				nonPaidGameFeeInfoListPanel.revalidate();
				
				onSelectedGameAddListener.onSelectedGameAdd();
				
			}
			
		};
		
		for( int index = 0; index < nonPaidGameFeeInfoLabelList.size(); index++ ) {
			
			JLabel nonPaidGameFeeInfoLabel = nonPaidGameFeeInfoLabelList.get( index );
			
			nonPaidGameFeeInfoLabel.setOpaque( true );
			nonPaidGameFeeInfoLabel.setBackground( Color.LIGHT_GRAY.brighter() );
			
			selectedGameFeeInfoList.add( nonPaidGameFeeInfoList.get( index ) );
			
			nonPaidGameFeeInfoListPanel.add( nonPaidGameFeeInfoLabel );
			
		}
		
		nonPaidGameFeeInfoListPanel.setPreferredSize( new Dimension( 0, 280 ) );
		nonPaidGameFeeInfoListPanel.setLayout( new GridLayout( nonPaidGameFeeInfoLabelList.size(), 1 ) );
		
		return nonPaidGameFeeInfoListPanel;
		
	}
	
	private JPanel createPaymentControlPanel() {
		
		JPanel paymentControlPanel = new JPanel();
		
		JPanel feeInfoPanel = new JPanel();
		
		JLabel feeGuidanceLabel = new JLabel( "내실 요금 : " );
		JTextField feeTextField = new JTextField();
		JLabel feeUnitLabel = new JLabel( " 원" );
		
		JButton payButton = new JButton( "계산" );
		
		onSelectedGameAddListener = new OnSelectedGameAddListener() {

			@Override
			public void onSelectedGameAdd() {

				int fee = 0;
				
				for( int index = 0; index < selectedGameFeeInfoList.size(); index++ ) {
					
					fee += selectedGameFeeInfoList.get( index ).getFee();
					
				}
				
				feeTextField.setText( "" + fee );
				feeTextField.setCaretPosition( feeTextField.getText().length() );
				
			}
			
		};
		
		feeGuidanceLabel.setPreferredSize( new Dimension( 150, 0 ) );
		feeGuidanceLabel.setHorizontalAlignment( JLabel.CENTER );
		feeGuidanceLabel.setFont( FontProvider.getDefaultFont() );
		
		feeTextField.setPreferredSize( new Dimension( 200, 0 ) );
		feeTextField.setFont( FontProvider.getDefaultFont() );
		onSelectedGameAddListener.onSelectedGameAdd();
		
		feeUnitLabel.setPreferredSize( new Dimension( 50, 0 ) );
		feeUnitLabel.setHorizontalTextPosition( JLabel.CENTER );
		feeUnitLabel.setFont( FontProvider.getDefaultFont() );
		
		feeInfoPanel.setLayout( new BorderLayout() );
		feeInfoPanel.add( feeGuidanceLabel, BorderLayout.WEST );
		feeInfoPanel.add( feeTextField, BorderLayout.CENTER );
		feeInfoPanel.add( feeUnitLabel, BorderLayout.EAST );
		
		payButton.setPreferredSize( new Dimension( 200, 0 ) );
		payButton.setFont( FontProvider.getDefaultFont() );
		payButton.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				int tableNumber = 0;
				int fee = 0;
				int changedFee = NumericManufacturer.getIntConsistingOnlyOfNumeric( feeTextField.getText() );
				
				for( int index = 0; index < selectedGameFeeInfoList.size(); index++ ) {
					
					if( index == 0 )	tableNumber = selectedGameFeeInfoList.get( index ).getTableNumber();
					
					fee += selectedGameFeeInfoList.get( index ).getFee();
					
				}
				
				for( int index = 0; index < selectedGameFeeInfoList.size(); index++ ) {
					
					if( fee != changedFee ) {
						
						if( index == 0 ) {
							
							if( !GameListModifier.reflectTheChangedFee( selectedGameFeeInfoList.get( index ), changedFee ) ) {
								
								System.out.println( "오류 발생" );
								
								return;
								
							}
							
						} else {
							
							if( !GameListModifier.reflectTheChangedFee( selectedGameFeeInfoList.get( index ), 0 ) ) {
								
								System.out.println( "오류 발생" );
								
								return;
								
							}
							
						}
						
					}
					
					if( !GameListModifier.updateIsPaidToTrue( tableNumber, selectedGameFeeInfoList.get( index ).getGameNumber() ) ) {
						
						System.out.println( "오류 발생" );
						
						return;
						
					}
					
					
				}
				
				onSelectedGamePayListener.onSelectedGamePay();
				
			}
			
		});
		
		paymentControlPanel.setPreferredSize( new Dimension( 0, 60 ) );
		paymentControlPanel.setLayout( new BorderLayout() );
		
		paymentControlPanel.add( feeInfoPanel, BorderLayout.CENTER );
		paymentControlPanel.add( payButton, BorderLayout.EAST );
		
		return paymentControlPanel;
		
	}
	
}
