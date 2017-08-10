package components;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * 
 * 개발자가 원하는 path의 이미지를 ImageIcon값으로 반환해주는 역할을 하는 매서드를  통해 반환해준다.
 * 
 * @author Kwon
 */
public class ImageIconFactory{
	private ImageIconFactory(){}
	/**
	 * 현 프로젝트 기본 path에 있는 이미지로 ImageIcon을 만들어주는 함수.
	 * @param String imageName : 불러올 이미지의 확장자를 포함한 이름, 형태 : "image.png"
	 * @return ImageIcon
	 */
	public static ImageIcon makeImageIcon(String imageName){
		try{
			File f = new File(imageName);
			BufferedImage img = ImageIO.read(f);
			return new ImageIcon(img);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 현 프로젝트의 기본 path로부터 추가적인 path를 찾아가서 해당 이미지로 ImageIcon을 만들어주는 함수.
	 * @param String additionalPath : 기본 path로부터 추가적인 path, 형태 : "src\\file\\file2\\" 
	 * @param String imageName : 불러올 이미지의 확장자를 포함한 이름, 형태 : "image1.png"
	 * @return ImageIcon
	 */
	public static ImageIcon makeImageIcon(String additionalPath, String imageName){
		String path = additionalPath + imageName;
		return makeImageIcon(path);
	}
}
