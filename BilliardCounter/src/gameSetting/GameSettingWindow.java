package gameSetting;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

/**
 * 
 * 게임 시작 전 설정 사항들을 세팅하는 화면
 * 
 * @author Kwon
 *
 */
public class GameSettingWindow extends JFrame{
	private GameStartButton gameStartButton;
	
	public GameSettingWindow(int row, int col){
		this.initGameSettingWindow();
		this.addComponents(row, col);
	}
	private void initGameSettingWindow(){
		this.setBounds(500,300,500,500);
		this.setLayout(null);
		this.setVisible(true);
	}
	private void addComponents(int row, int col){
		gameStartButton = new GameStartButton(row, col, this);
		
		gameStartButton.setBounds(150,150,200,200);
		gameStartButton.setText("당구대 사용하기");
		this.add(gameStartButton);
	}
}
