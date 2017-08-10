package components;

import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * 원하는 경로에서 이미지를 받아와 버튼으로 생성해주는 클래스
 * @author Kwon
 *
 */
public class ImageButton extends JButton{
	private final ImageIcon IMAGE_ICON;				// 이미지를 받아올 Icon
	
	/**
	 * 원하는 경로를 통해 버튼에 이미지를 입힌채 생성해주는 생성자
	 * @param path
	 * @param imageName
	 */
	public ImageButton(String path, String imageName){
		IMAGE_ICON = ImageIconFactory.makeImageIcon(path, imageName);
		this.setIcon(IMAGE_ICON);
		this.initProperties();
	}
	
	/**
	 * 현재 버튼의 특성을 초기화해주는 매서드
	 */
	private void initProperties(){
		//this.setBorderPainted(false);
		this.setFocusPainted(false);
		this.setContentAreaFilled(false);
	}
}