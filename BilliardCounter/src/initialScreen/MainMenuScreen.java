package initialScreen;

import java.awt.Color;

import javax.swing.JLabel;

import components.ImageIconFactory;
import components.Screen;

/**
 * 
 * 프로그램의 제목을 ImageLabel을 통해 보여주는 클래스이다.
 * 
 * @author Kwon
 *
 */
public class MainMenuScreen extends Screen{
	private final JLabel IMAGE_LABEL = new JLabel(ImageIconFactory.makeImageIcon("src\\resource\\","BilliardTitle.png"));	// 프로그램 명 이미지 라벨
	
	public MainMenuScreen(){
		this.initProperties();
		this.addComponents();
	}
	@Override
	protected void initProperties() {
		this.setLayout(null);
		this.setBackground(new Color(0xDDFF00));
	}
	@Override
	protected void initComponentsProperties() {
		int width, height, x, y;
		width = this.getWidth() / 3;
		height = this.getHeight();
		x = this.getWidth() / 2 - width / 2;
		y = this.getHeight() / 2 - height / 2;
		
		IMAGE_LABEL.setBounds(x, y, width, height);
	}
	@Override
	protected void addComponents() {
		this.add(IMAGE_LABEL);
	}
}
