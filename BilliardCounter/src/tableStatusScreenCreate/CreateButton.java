package tableStatusScreenCreate;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import component.Board;
import component.ImageButton;

/**
 * TableStatusScreen을 생성하는 기능을 갖고 있는 버튼
 * @author Kwon
 *
 */
public class CreateButton extends ImageButton{
	private final Board SCREEN_CREATOR;					// SCREEN을 생성할 수 있는 Board
	private final int SCREEN_ROW;						// 생성할 SCREEN의 row
	private final int SCREEN_COL;						// 생성할 SCREEN의 col
	private final JTextField SCREEN_NUMBER_INPUT_FIELD;	// 생성할 SCREEN의 screenNumber의 값을 가지고 있는 TextField
	private final SettingWindow WILL_BE_CLOSED_WINDOW;	// SCREEN 생성이 완료되면 닫아줘야할 창
	
	/**
	 * 본 클래스가 갖춰야할 정보들을 모두 초기화 해주는 생성자
	 * @param screenInfoStore	생성될 SCREEN의 row,col,creator 정보를 갖고 있는 SettingWindowOpenScreen 객체
	 * @param willBeClosedWindow	SCREEN 생성이 완료되면 닫아줘야할 창, 본 버튼을 가지고 있는 screen의 window
	 * @param screenNumberInputField	SCREEN에 부여할 screenNumber정보를 가지고 있는 TextField
	 */
	public CreateButton(SettingWindowOpenScreen screenInfoStore, SettingWindow willBeClosedWindow, JTextField screenNumberInputField){
		super("src\\resource\\", "CreateImage.png");
		
		SCREEN_CREATOR = screenInfoStore.getScreenCreator();
		SCREEN_ROW = screenInfoStore.getScreenRow();
		SCREEN_COL = screenInfoStore.getScreenCol();
		SCREEN_NUMBER_INPUT_FIELD = screenNumberInputField;
		WILL_BE_CLOSED_WINDOW = willBeClosedWindow;
		
		this.initThisButton(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int screenNumber = Integer.parseInt(SCREEN_NUMBER_INPUT_FIELD.getText());
				
				if(SCREEN_CREATOR.createScreen(SCREEN_ROW, SCREEN_COL, screenNumber)){
					WILL_BE_CLOSED_WINDOW.dispose();
				}else{
					System.out.println("이미 사용중인 번호이거나 테이블을 정상적으로 생성할 수 없습니다.");
				}
			}
		});
	}
	
}
