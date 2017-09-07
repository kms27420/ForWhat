package com.kms.billiardcounter.core.event;

import com.kms.billiardcounter.core.contentspaneupdater.ContentsPaneUpdater;
import com.kms.billiardcounter.dao.gamelist.GameListTableUpdater;

/**
 * 
 * 사용자가 당구대를 옮길때 그 당구대의 정보를 다른 당구대로 옮겨주는 클래스
 * 
 * @author Kwon
 *
 */
public class GameTableReplace {

	private static ContentsPaneUpdater contentsPaneUpdater;
	
	private static boolean isEnabled = false;
	private static int replaceFromThisTableNumber;
	private static int replaceToThisTableNumber;
	
	private GameTableReplace() {}
	
	/**
	 * 
	 * contentsPaneUpdater를 설정해주는 매서드
	 * 
	 * @param contentsPaneUpdater 설정해줄 contentsPaneUpdater
	 */
	public static final void setContentsPaneUpdater( ContentsPaneUpdater contentsPaneUpdater ) {
		
		GameTableReplace.contentsPaneUpdater = contentsPaneUpdater;
		
	}
	
	/**
	 * 
	 * 당구대의 정보를 입력받은 번호의 당구대로 옮겨주는 매서드
	 * 
	 * @param tableNumber 당구대 정보를 옮길 최종 위치
	 */
	public static final void replaceCurrentGameTablePosition( int tableNumber ) {
		
		replaceToThisTableNumber = tableNumber;
		
		if( GameListTableUpdater.updateNonPaidGamesTableNumber( replaceFromThisTableNumber, replaceToThisTableNumber ) ) {
		
			isEnabled = false;
			replaceFromThisTableNumber = 0;
			replaceToThisTableNumber = 0;
		
			contentsPaneUpdater.update();
		
		}
		
	}
	
	/**
	 * 
	 * 당구대의 정보를 옮겨주기 위해 본 클래스를 활성화 시켜주는 매서드
	 * 
	 * @param tableNumber 정보를 옮길 당구대의 번호
	 */
	public static final void activateReplacementWork( int tableNumber ) {
		
		replaceFromThisTableNumber = tableNumber;
		isEnabled = true;
		
		contentsPaneUpdater.update();
		
	}
	
	/**
	 * 
	 * 본 클래스가 활성화 되었는지를 확인하는 변수 isEnabled의 값을 받아오는 매서드
	 * 
	 * @return isEnabled
	 */
	public static final boolean getIsEnabled() {
		
		return isEnabled;
		
	}
	
}
