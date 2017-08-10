package gameFeeInfoHandling;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;

import components.Screen;

public class GameListScreen extends Screen{
	private final JLabel[] GAME_INFO_LABEL = new JLabel[20];
	private final JLabel EMPTY_SIGN = new JLabel("비어 있음");
	
	private int labelWidth;
	private int labelHeight;
	
	public GameListScreen(){
		this.initProperties();
		this.addComponents();
	}
	
	protected void addGameInfoLabel(GameFeeInfoManager gameFeeInfoManager){
		int i = gameFeeInfoManager.getGameCount() - 1;
		int usedTime = gameFeeInfoManager.getGameFeeInfo()[i].getUsedTime();
		int fee = gameFeeInfoManager.getGameFeeInfo()[i].getFee();
		
		Font font = new Font("MD아트체", Font.BOLD, 18);
		
		GAME_INFO_LABEL[i] = new JLabel();
		GAME_INFO_LABEL[i].setBounds(0, labelHeight * i, labelWidth, labelHeight);
		GAME_INFO_LABEL[i].setFont(font);
		GAME_INFO_LABEL[i].setText((i + 1) + "게임 | " + "사용 시간 " + String.format("%02d",usedTime / 60) + " : " + String.format("%02d",usedTime % 60) 
										+ ", 요금 " + fee + "원");
		
		if(gameFeeInfoManager.getGameCount() - 1 == 0)
			this.remove(EMPTY_SIGN);
		
		this.add(GAME_INFO_LABEL[gameFeeInfoManager.getGameCount() - 1]);
		
		this.setPreferredSize(new Dimension(0, labelHeight * gameFeeInfoManager.getGameCount()));
	}
	protected void removeAllLabel(){
		this.removeAll();
		this.add(EMPTY_SIGN);
		this.repaint();
		this.revalidate();
	}
	
	@Override
	protected void initProperties() {
		this.setLayout(null);
	}
	@Override
	protected void initComponentsProperties() {
		labelWidth = this.getWidth();
		labelHeight = this.getHeight() / 3;
		
		EMPTY_SIGN.setText("비어 있음");
		EMPTY_SIGN.setBounds(0, 0, this.getWidth(), this.getHeight());
	}
	@Override
	protected void addComponents() {
		this.add(EMPTY_SIGN);
	}
}
