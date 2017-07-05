package tableStatusScreenCreate;

import java.awt.Dimension;
import java.awt.Toolkit;

import component.Window;

/**
 * TableStatusScreen을 만들기 위해 Setting을 해주는 창
 * @author Kwon
 *
 */
public class SettingWindow extends Window{
	private final SettingScreen SETTING_SCREEN;		// TableStatusScreen생성 전에 setting하는 본 작업이 진행되는 Screen
	
	/**
	 * 본 인스턴스 초기화와 동시에 SETTING_SCREEN에게 정보 전달을 목적으로 하는 생성자
	 * @param screenInfoStore
	 */
	public SettingWindow(SettingWindowOpenScreen screenInfoStore){
		SETTING_SCREEN = new SettingScreen(screenInfoStore, this);
		this.initThisWindow();
		this.addComponents();
	}

	@Override
	protected void initThisWindow() {
		Dimension monitorSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		int width = (int)monitorSize.getWidth() / 3;
		int height = (int)monitorSize.getHeight() / 3;
		int x = (int)monitorSize.getWidth() / 2 - width / 2;
		int y = (int)monitorSize.getHeight() / 2 - height / 2;
		
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
	protected void initComponents() {
		SETTING_SCREEN.setBounds(0, 0, this.getWidth(), this.getHeight());
	}
}
