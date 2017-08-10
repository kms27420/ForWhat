package tableScreenCreationPlace;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import components.Board;
import components.ImageButton;
import components.Screen;

/**
 * TableStatusScreen을 생성하기 전에 Setting 작업을 진행하는 Screen
 * @author Kwon
 *
 */
public class PropertySettingScreen extends Screen{
	private final JTextField INFO_SIGN_BOARD = new JTextField();				// ScreenNumber 입력란에 대한 설명을 담고 있는 TextField
	private final JTextField SCREEN_NUMBER_INPUT_FIELD = new JTextField();	// ScreenNumber를 입력받는 TextField
	private final ImageButton CREATE_BUTTON = new ImageButton("src\\resource\\", "CreateImage.png");	// TableStatusScreen을 생성하는 버튼
	
	/**
	 * CREATE_BUTTON에게 TableStatusScreen 생성에 필요한 정보들을 넘겨주면서 CREATE_BUTTON을 초기화 시켜주는 생성자
	 * @param screenInfoStore	생성할 TableStatusScreen의 row,col,Creator 정보를 담고 있는 SettingWindowOpenScreen
	 * @param willBeClosedWindow	TableStatusScreen이 생성될 때 닫아줘야할 Window
	 */
	public PropertySettingScreen(Board screenCreator, Screen handledScreen, PropertySettingWindow willBeClosedWindow){
		this.initProperties();
		this.addComponents();
		this.addListenerToCreateButton(screenCreator, handledScreen, willBeClosedWindow);
	}
	
	private void addListenerToCreateButton(Board screenCreator, Screen handledScreen, PropertySettingWindow willBeClosedWindow){
		CREATE_BUTTON.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int screenNumber = Integer.parseInt(SCREEN_NUMBER_INPUT_FIELD.getText());
				
				if(screenCreator.createScreen(handledScreen, screenNumber)){
					willBeClosedWindow.dispose();
				}else{
					System.out.println("이미 사용중인 번호이거나 테이블을 정상적으로 생성할 수 없습니다.");
				}
			}
		});
	}
	@Override
	protected void initProperties() {
		this.setLayout(null);
	}
	@Override
	protected void initComponentsProperties() {
		int width = this.getWidth() * 3 / 4;
		int height = this.getHeight() / 5;
		int x = this.getWidth() / 2 - width / 2;
		int y = height;
		
		INFO_SIGN_BOARD.setBounds(x, y, width, height);
		INFO_SIGN_BOARD.setFont(new Font("MD아트체", Font.BOLD, 28));
		INFO_SIGN_BOARD.setText("테이블의 번호를 입력해주세요.");
		INFO_SIGN_BOARD.setEditable(false);
		INFO_SIGN_BOARD.setBorder(null);
		
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
		this.add(INFO_SIGN_BOARD);
		this.add(SCREEN_NUMBER_INPUT_FIELD);
		this.add(CREATE_BUTTON);
	}
}
