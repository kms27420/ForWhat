package gameSetting;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import tableStatusScreenControl.TableStatusScreenList;
import tableStatusScreenControl.TableStatusScreenStorer;

/**
 * 
 * 게임 시작 시 처리해야할 작업들을 실행해주는 버튼
 * @author Kwon
 *
 */
public class GameStartButton extends JButton{
	private final int[] BUTTON_NUMBER = new int[2];
	private JFrame contentFrame;
	/**
	 * 입력받은 parameter로 GameStartButton을 초기화해준다
	 * @param row, 시작할 스크린의 row번호
	 * @param col, 시작할 스크린의 col번호
	 * @param contentFrame, 버튼 클릭시에 종료하고자 하는 Frame
	 */
	public GameStartButton(int row, int col, JFrame contentFrame){
		this.initGameStartButton(row, col, contentFrame);
	}
	private void initGameStartButton(int row, int col, JFrame contentFrame){
		BUTTON_NUMBER[0] = row;
		BUTTON_NUMBER[1] = col;
		this.contentFrame = contentFrame;
		
		this.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				GameStartButton.this.contentFrame.dispose();
				TableStatusScreenStorer.storeTableStatusScreens();
				TableStatusScreenList.getTableStatusScreen(BUTTON_NUMBER[0], BUTTON_NUMBER[1]).getTableStatusScreenContents().updateStartedScreenContents();
			}
		});
	}
}
