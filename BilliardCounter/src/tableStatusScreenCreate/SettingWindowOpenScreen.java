package tableStatusScreenCreate;

import java.awt.Color;

import javax.swing.border.BevelBorder;

import component.Board;
import component.Screen;

/**
 * SettingWindow를 open하기 위한 Screen 클래스이다. SettingWindow를 열어주는 버튼을 가지고 있다.
 * @author Kwon
 *
 */
public class SettingWindowOpenScreen extends Screen{
	private final SettingWindowOpenButton SETTING_WINDOW_OPEN_BUTTON;		// SettingWindow를 열어주는 Button
	private final int SCREEN_ROW, SCREEN_COL;								// TableStatusScreen을 생성할 때 사용되는 SCREEN의 위치 정보
	private final Board SCREEN_CREATOR;										// TablesStatusSCreen을 생성할 때 사용되는 SCREEN을 만드는 Board
	
	/**
	 * 본 스크린을 객체화 시켜주는 위치에서 TableStatusScreen을 만들 때에 필요한 정보들을 받아와 초기화 시켜주는 생성자
	 * @param row	TableStatusScreen을 만들 row위치
	 * @param col	TableStatusScreen을 만들 col위치
	 * @param screenCreator	TableStatusScreen을 만들 수 있는 Board
	 */
	public SettingWindowOpenScreen(int row, int col, Board screenCreator){
		SCREEN_ROW = row;
		SCREEN_COL = col;
		SCREEN_CREATOR = screenCreator;
		SETTING_WINDOW_OPEN_BUTTON = new SettingWindowOpenButton(this);
		
		this.initThisScreen();
		this.addComponents();
	}
	/**
	 * 만들 TableStatusScreen의 row위치를 반환하는 매서드
	 * @return	SCREEN_ROW
	 */
	public int getScreenRow(){
		return SCREEN_ROW;
	}
	/**
	 * 만들 TableStatusScreen의 col위치를 반환하는 매서드
	 * @return	SCREEN_COL
	 */
	public int getScreenCol(){
		return SCREEN_COL;
	}
	/**
	 * TableStatusScreen을 만들 수 있는 Board를 반환해주는 매서드
	 * @return	SCREEN_CREATOR
	 */
	public Board getScreenCreator(){
		return SCREEN_CREATOR;
	}
	
	@Override
	protected void initThisScreen() {
		this.setLayout(null);
		this.setBackground(new Color(0x00DD00));
		this.setBorder(new BevelBorder(BevelBorder.RAISED));
	}

	@Override
	protected void initComponents() {
		SETTING_WINDOW_OPEN_BUTTON.setBounds(0, 0, this.getWidth(), this.getHeight());
	}

	@Override
	protected void addComponents() {
		this.add(SETTING_WINDOW_OPEN_BUTTON);
	}

	@Override
	protected void updateComponents() {
		
	}
}
