package billiardCounterAppearance;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;

import javax.swing.JFrame;

import component.Window;

/**
 * 
 * 본 프로그램 "BilliardCounter" 그 자체의 window이다. 
 * 프로그램 명을 나타내주는 이미지 배너(BilliardCounterTitleImageBanner)와 
 * 프로그램 내용(BilliardCounterResultScreen)을 보여주는 클래스이다.
 * 
 * @author Kwon
 *
 */
public class BilliardCounterWindow extends Window{
	private final BilliardCounterNameBanner TITLE_IMAGE_BANNER = new BilliardCounterNameBanner();	// 프로그램 명을 담고 있는 ImageBanner
	private final BilliardCounterResultScreen RESULT_SCREEN = new BilliardCounterResultScreen();				// 프로그램 내용을 담고 있는 Screen
		
	public BilliardCounterWindow(){
		this.initThisWindow();
	}
	
	@Override
	protected void initThisWindow() {
		Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		this.setBounds(0, 0, windowSize.width, windowSize.height - 50);
		this.setLayout(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.addComponents();
		this.setVisible(true);
	}
	@Override
	protected void addComponents() {
		this.add(TITLE_IMAGE_BANNER);
		this.add(RESULT_SCREEN);
	}
	@Override
	protected void initComponents() {
		int titleX, titleY, resultX, resultY;
		int titleWidth, titleHeight, resultWidth, resultHeight;
		
		titleX = titleY = resultX = 0;
		titleWidth = resultWidth = this.getWidth();
		titleHeight = resultY = this.getHeight() / 5;
		resultHeight = this.getHeight() * 4 / 5;
		
		TITLE_IMAGE_BANNER.setBounds(titleX, titleY, titleWidth, titleHeight);
		RESULT_SCREEN.setSize(new Dimension(resultWidth, resultHeight));
		RESULT_SCREEN.setLocation(resultX, resultY);
	}
}
