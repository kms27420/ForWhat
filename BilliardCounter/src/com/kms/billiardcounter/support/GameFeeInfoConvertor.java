package com.kms.billiardcounter.support;

import java.util.ArrayList;

import javax.swing.JLabel;

import com.kms.billiardcounter.font.FontProvider;

/**
 * 
 * GameFeeInfo 클래스를 여러가지 형태로 변환해주는 클래스
 * 
 * @author Kwon
 *
 */
public class GameFeeInfoConvertor {

	private GameFeeInfoConvertor() {}
	
	/**
	 * 
	 * ArrayList<GameFeeInfo>를 ArrayList<JLabel>형태로 변환해주는 매서드
	 * return되는 ArrayList<JLabel>에 add되는 JLabel들은 본 프로그램에서 사용되는 형태( n게임 - 사용 시간 00:00 , 요금 0000 원 )로 변환됨
	 * 
	 * @param gameFeeInfoList 변환하고자하는 ArrayList<GameFeeInfo>
	 * @return 변환 완료된 ArrayList<JLabel>
	 */
	public static final ArrayList<JLabel> convertToLabelList( ArrayList<GameFeeInfo> gameFeeInfoList ) {
		
		ArrayList<JLabel> labelList = new ArrayList<JLabel>();
		
		for( int index = 0; index < gameFeeInfoList.size(); index++ ) {
			
			labelList.add( convertToLabel( gameFeeInfoList.get( index ) ) );
			
		}
		
		return labelList;
		
	}
	
	/**
	 * 
	 * GameFeeInfo를 JLabel 형태로 변환시켜주는 매서드
	 * 
	 * @param gameFeeInfo 변환하고자하는 GameFeeInfo
	 * @return 변환완료된 JLabel
	 */
	public static final JLabel convertToLabel( GameFeeInfo gameFeeInfo ) {
		
		JLabel gameFeeInfoLabel = new JLabel();
		
		String usedTime = String.format( "%02d", gameFeeInfo.getUsedTime() / 60) + ":" + String.format( "%02d", gameFeeInfo.getUsedTime() % 60 );
		int gameNumber = gameFeeInfo.getGameNumber();
		
		gameFeeInfoLabel.setHorizontalAlignment( JLabel.CENTER );
		gameFeeInfoLabel.setFont( FontProvider.getDefaultFont() );
		gameFeeInfoLabel.setText( gameNumber + " 게임 - 사용 시간 " + usedTime + ", 요금 " + gameFeeInfo.getFee() + "원" );
		
		return gameFeeInfoLabel;
		
	}
	
}
