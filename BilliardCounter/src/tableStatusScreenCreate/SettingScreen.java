package tableStatusScreenCreate;

import java.awt.Font;

import javax.swing.JTextField;

import component.Screen;

/**
 * TableStatusScreen을 생성하기 전에 Setting 작업을 진행하는 Screen
 * @author Kwon
 *
 */
public class SettingScreen extends Screen{
	private final JTextField INFO_SIGNBOARD = new JTextField();				// ScreenNumber 입력란에 대한 설명을 담고 있는 TextField
	private final JTextField SCREEN_NUMBER_INPUT_FIELD = new JTextField();	// ScreenNumber를 입력받는 TextField
	private final CreateButton CREATE_BUTTON;	// TableStatusScreen을 생성하는 버튼
	
	/**
	 * CREATE_BUTTON에게 TableStatusScreen 생성에 필요한 정보들을 넘겨주면서 CREATE_BUTTON을 초기화 시켜주는 생성자
	 * @param screenInfoStore	생성할 TableStatusScreen의 row,col,Creator 정보를 담고 있는 SettingWindowOpenScreen
	 * @param willBeClosedWindow	TableStatusScreen이 생성될 때 닫아줘야할 Window
	 */
	public SettingScreen(SettingWindowOpenScreen screenInfoStore, SettingWindow willBeClosedWindow){
		CREATE_BUTTON = new CreateButton(screenInfoStore, willBeClosedWindow, SCREEN_NUMBER_INPUT_FIELD);
		
		this.initThisScreen();
		this.addComponents();
	}
	
	@Override
	protected void initThisScreen() {
		this.setLayout(null);
	}
	@Override
	protected void initComponents() {
		int width = this.getWidth() * 3 / 4;
		int height = this.getHeight() / 5;
		int x = this.getWidth() / 2 - width / 2;
		int y = height / 2;
		
		INFO_SIGNBOARD.setBounds(x, y, width, height);
		INFO_SIGNBOARD.setFont(new Font("MD아트체", Font.BOLD, 28));
		INFO_SIGNBOARD.setText("테이블의 번호를 입력해주세요.");
		INFO_SIGNBOARD.setEditable(false);
		INFO_SIGNBOARD.setBorder(null);
		
		width /= 2;
		y += height * 2;
		SCREEN_NUMBER_INPUT_FIELD.setBounds(x, y, width, height);
		SCREEN_NUMBER_INPUT_FIELD.setFont(new Font("MD아트체", Font.BOLD, 28));
		SCREEN_NUMBER_INPUT_FIELD.setHorizontalAlignment(JTextField.RIGHT);
		
		x += width * 3 / 2;
		width = width * 2 / 3;
		
		CREATE_BUTTON.setBounds(x, y, width, height);
	}
	@Override
	protected void addComponents() {
		this.add(INFO_SIGNBOARD);
		this.add(SCREEN_NUMBER_INPUT_FIELD);
		this.add(CREATE_BUTTON);
	}
	@Override
	protected void updateComponents() {
		
	}
}
