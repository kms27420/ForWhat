package gameSetting;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import component.ImageIconFactory;
import gamePayment.GamePaymentWindow;
import tableStatusScreenControl.TableStatusScreenList;

/**
 * 
 * GameSettingWindow을 open해주는 버튼
 * 
 * @author Kwon
 *
 */
public class GameSettingWindowOpenButton extends JButton{
	private static final ImageIcon IMAGE_ICON = ImageIconFactory.makeImageIcon("src\\resource\\", "UseTableImage.png");
	private final int[] BUTTON_NUMBER = new int[2];		// 버튼의 이벤트 처리를 위한 버튼의 번호를 변수로 선언
	
	public GameSettingWindowOpenButton(){
		super(IMAGE_ICON);
		
		this.initGameSettingWindowOpenButton();
	}
	private void initGameSettingWindowOpenButton(){
		BUTTON_NUMBER[0] = TableStatusScreenList.getCurrentRow();		// 버튼의 row 번호를 [0]에 저장
		BUTTON_NUMBER[1] = TableStatusScreenList.getCurrentCol();		// 버튼의 col 번호를 [1]에 저장
		
		this.setBorderPainted(false);
		this.setFocusPainted(false);
		this.setContentAreaFilled(false);
		this.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(TableStatusScreenList.getTableStatusScreen(BUTTON_NUMBER[0], BUTTON_NUMBER[1]).getTableStatusScreenContents().getIsBeingPlayed())
					new GamePaymentWindow(BUTTON_NUMBER[0], BUTTON_NUMBER[1]);
				else
					new GameSettingWindow(BUTTON_NUMBER[0], BUTTON_NUMBER[1]);
			}
		});
	}
}
