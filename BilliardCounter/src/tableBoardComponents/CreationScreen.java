package tableBoardComponents;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.border.BevelBorder;

import components.Board;
import components.ImageButton;
import components.Screen;
import tableScreenCreationPlace.PropertySettingWindow;

/**
 * SettingWindow를 open하기 위한 Screen 클래스이다. SettingWindow를 열어주는 버튼을 가지고 있다.
 * @author Kwon
 *
 */
public class CreationScreen extends Screen{
	private final ImageButton SETTING_WINDOW_OPEN_BUTTON = new ImageButton("src\\resource\\", "PlusImage.png");		// SettingWindow를 열어주는 Button
	/**
	 * 본 스크린을 객체화 시켜주는 위치에서 TableStatusScreen을 만들 때에 필요한 정보들을 받아와 초기화 시켜주는 생성자
	 * @param row	TableStatusScreen을 만들 row위치
	 * @param col	TableStatusScreen을 만들 col위치
	 * @param screenCreator	TableStatusScreen을 만들 수 있는 Board
	 */
	public CreationScreen(Board screenCreator, int screenNum){
		super(screenNum);
		this.initProperties();
		this.addComponents();
		this.addListenerToSettingWindowOpenButton(screenCreator);
	}
	
	private void addListenerToSettingWindowOpenButton(Board screenCreator){
		SETTING_WINDOW_OPEN_BUTTON.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new PropertySettingWindow(screenCreator, CreationScreen.this);
			}
		});
	}
	
	@Override
	protected void initProperties() {
		this.setLayout(null);
		this.setBackground(new Color(0x00DD00));
		this.setBorder(new BevelBorder(BevelBorder.RAISED));
	}

	@Override
	protected void initComponentsProperties() {
		SETTING_WINDOW_OPEN_BUTTON.setBounds(0, 0, this.getWidth(), this.getHeight());
	}

	@Override
	protected void addComponents() {
		this.add(SETTING_WINDOW_OPEN_BUTTON);
	}
}
