package billiardCounterAppearance;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JPanel;

import component.ImageIconFactory;

/**
 * 
 * 프로그램의 제목을 ImageLabel을 통해 보여주는 클래스이다.
 * 
 * @author Kwon
 *
 */
public class BilliardCounterTitleImageBanner extends JPanel{
	private static final JLabel IMAGE_LABEL = new JLabel(ImageIconFactory.makeImageIcon("src\\resource\\","BilliardTitle.png"));	// 프로그램 명 이미지 라벨
	private boolean isInitedAlready;
	
	public BilliardCounterTitleImageBanner(){
		this.initBilliardCounterTitleImageBanner();
		this.addComponents();
	}
	private void initBilliardCounterTitleImageBanner(){
		this.setBackground(new Color(0xDDFF00));
	}
	private void addComponents(){
		this.add(IMAGE_LABEL);
	}
	private void setImageLabel(){
		int width, height, x, y;
		width = this.getWidth() / 3;
		height = this.getHeight();
		x = this.getWidth() / 2 - width / 2;
		y = this.getHeight() / 2 - height / 2;
		
		IMAGE_LABEL.setBounds(x, y, width, height);
	}
	@Override
	public void paint(Graphics g){
		super.paint(g);
		if(!isInitedAlready){
			this.setImageLabel();
			isInitedAlready = true;
		}
	}
}
