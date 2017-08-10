package tableScreenCreationPlace;

import java.awt.Dimension;
import java.awt.Toolkit;

import components.Board;
import components.Screen;
import components.Window;

/**
 * TableStatusScreen을 만들기 위해 Setting을 해주는 창
 * @author Kwon
 *
 */
public class PropertySettingWindow extends Window{
	private final PropertySettingScreen SETTING_SCREEN;		// TableStatusScreen생성 전에 setting하는 본 작업이 진행되는 Screen
	
	/**
	 * 본 인스턴스 초기화와 동시에 SETTING_SCREEN에게 정보 전달을 목적으로 하는 생성자
	 * @param screenInfoStore
	 */
	public PropertySettingWindow(Board screenCreator, Screen handledScreen){
		SETTING_SCREEN = new PropertySettingScreen(screenCreator, handledScreen, this);
		this.initProperties();
		this.addComponents();
	}

	@Override
	protected void initProperties() {
		Dimension monitorSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		int width = monitorSize.width / 3;
		int height = monitorSize.height / 3;
		int x = monitorSize.width / 2 - width / 2;
		int y = monitorSize.height / 2 - height / 2;
		
		this.setBounds(x, y, width, height);
		this.setLayout(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}
	@Override
	protected void addComponents() {
		this.add(SETTING_SCREEN);
	}
	@Override
	protected void initComponentsProperties() {
		SETTING_SCREEN.setBounds(0, 0, this.getContentPane().getSize().width, this.getContentPane().getSize().height);
	}
}
