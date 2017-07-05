package tableStatusScreenCreate;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import component.ImageButton;

/**
 *
 * TableStatusScreen을 생성해주는 버튼
 * @author Kwon
 *
 */
public class SettingWindowOpenButton extends ImageButton{
	/**
	 * TableStatusScreen을 그릴 Board를 parameter로 받아들여 초기화 시켜준다
	 * @param tableStatusBoard, TableStatusScreen을 그릴 장소
	 */
	public SettingWindowOpenButton(SettingWindowOpenScreen screenInfoStore){
		super("src\\resource\\", "PlusImage.png");
		this.initThisButton(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new SettingWindow(screenInfoStore);
			}
		});
	}
}