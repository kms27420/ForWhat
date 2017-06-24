package gamePayment;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import tableStatusScreenControl.TableStatusScreenList;

/**
 * 
 * 게임비를 정산할 수 있는 window
 * @author Kwon
 *
 */
public class GamePaymentWindow extends JFrame{
	private static final JButton TEST_BUTTON = new JButton("TestButton");
	private final int[] SCREEN_NUMBER = new int[2];
	
	public GamePaymentWindow(int row, int col){
		this.initGamePaymentScreen(row, col);
	}
	private void initGamePaymentScreen(int row, int col){
		SCREEN_NUMBER[0] = row;
		SCREEN_NUMBER[1] = col;
		
		TEST_BUTTON.setBounds(100, 100, 100, 100);
		TEST_BUTTON.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				TableStatusScreenList.getTableStatusScreen(SCREEN_NUMBER[0], SCREEN_NUMBER[1]).getTableStatusScreenContents().updatePayedScreenContents();
				GamePaymentWindow.this.dispose();
			}
		});
		
		this.setBounds(300, 200, 500, 500);
		this.setLayout(null);
		this.add(TEST_BUTTON);
		this.setVisible(true);
	}
}
