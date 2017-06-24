package tableStatusScreenControl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import component.ImageIconFactory;

/**
 *
 * TableStatusScreen을 생성해주는 버튼
 * @author Kwon
 *
 */
public class TableStatusScreenCreateButton extends JButton{
	private static final ImageIcon IMAGE_ICON = ImageIconFactory.makeImageIcon("src\\resource\\", "PlusImage.png");	// 버튼에 포함될 이미지의 아이콘 초기화
	private final TableStatusBoard TABLE_STATUS_BOARD;					// TableStatusScreen을 그릴 장소를 받을 변수
	
	/**
	 * TableStatusScreen을 그릴 Board를 parameter로 받아들여 초기화 시켜준다
	 * @param tableStatusBoard, TableStatusScreen을 그릴 장소
	 */
	public TableStatusScreenCreateButton(TableStatusBoard tableStatusBoard){
		super(IMAGE_ICON);
		TABLE_STATUS_BOARD = tableStatusBoard;
		this.initTableStatusScreenCreateButton();
	}
	private void initTableStatusScreenCreateButton(){
		this.setBorderPainted(false);
		this.setFocusPainted(false);
		this.setContentAreaFilled(false);
		this.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				TableStatusScreenCreateButton.this.TABLE_STATUS_BOARD.paintTableStatusScreen();
			}
		});
	}
}