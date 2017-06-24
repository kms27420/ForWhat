package tableStatusScreenControl;

import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import component.ImageIconFactory;
import gameSetting.GameSettingWindowOpenButton;

/**
 * TableStatusScreen에 들어가는 내용들을 다룰 수 있는 클래스
 * 컴포넌트들의 초기화와 변경을 가능하게 해준다.
 * @author Kwon
 *
 */
public class TableStatusScreenContents{
	private final JTextComponent USED_TIME_TEXTFIELD = new JTextField();		// 사용 시간을 보여주는 TextField
	private final JTextComponent TOTAL_FEE_TEXTFIELD = new JTextField();		// 현재 요금을 보여주는 TextField
	private final GameSettingWindowOpenButton GAME_SETTING_SCREEN_OPEN_BUTTON = new GameSettingWindowOpenButton();	// GameSettingWindow을 open해주는 버튼
	private boolean isBeingPlayed;		// 현재 객체의 컴포넌트들이 게임 진행중인 상태인지를 반환해주는 변수
	
	/**
	 * 사용 시간을 보여주는 TextField를 반환해주는 매서드
	 * @return JTextComponent
	 */
	public JTextComponent getUsedTimeTextField(){
		return USED_TIME_TEXTFIELD;
	}
	/**
	 * 현재 요금을 보여주는 TextField를 반환해주는 매서드
	 * @return JTextComponent
	 */
	public JTextComponent getTotalFeeTextField(){
		return TOTAL_FEE_TEXTFIELD;
	}
	/**
	 * GameSettingWindow를 열어주는 버튼을 반환해주는 매서드 
	 * @return GameSettingScreenOpenButton
	 */
	public GameSettingWindowOpenButton getGameSettingScreenOpenButton(){
		return GAME_SETTING_SCREEN_OPEN_BUTTON;
	}
	/**
	 * 현재 객체의 컴포넌트들이 게임 진행중인 상태인지를 반환해주는 매서드
	 * @return boolean isBeingPlayed
	 */
	public boolean getIsBeingPlayed(){
		return this.isBeingPlayed;
	}
	/**
	 * 컴포넌트들의 사이즈와 위치 등 기본 설정을 초기화해주는 매서드
	 * @param screenWidth, 본 객체가 포함되어질 screen의 width 값
	 * @param screenHeight, 본 객체가 포함되어질 screen의 height 값
	 */
	public void initComponents(int screenWidth, int screenHeight){
		int width = screenWidth / 2;
		int height = screenHeight / 4;
		int x = width / 4;
		int y = height;
		
		USED_TIME_TEXTFIELD.setBounds(x, y, width, height);		
		TOTAL_FEE_TEXTFIELD.setBounds(x, y + height, width, height);
		
		Font font = new Font("MD아트체", Font.BOLD, 25);
		
		USED_TIME_TEXTFIELD.setEditable(false);
		USED_TIME_TEXTFIELD.setFont(font);
		USED_TIME_TEXTFIELD.setBackground(new Color(0x00DD00));
		
		TOTAL_FEE_TEXTFIELD.setEditable(false);
		TOTAL_FEE_TEXTFIELD.setFont(font);
		TOTAL_FEE_TEXTFIELD.setBackground(new Color(0x00DD00));
		
		width = width / 3 * 2;
		height = height / 3 * 4;
		x = width * 2;
		y = height * 2;
		
		GAME_SETTING_SCREEN_OPEN_BUTTON.setBounds(x, y, width, height);
		
	}
	/**
	 * 게임이 시작되면 본 객체를 최신화 시켜주는 매서드
	 */
	public void updateStartedScreenContents(){
		isBeingPlayed = true;
		ImageIcon imageIcon = ImageIconFactory.makeImageIcon("src\\resource\\", "PayTableImage.png");
		GAME_SETTING_SCREEN_OPEN_BUTTON.setIcon(imageIcon);
		
		Thread thread = new Thread(new Thread(){
			private final String TIME_MESSAGE = "사용 시간 : ";
			private final String FEE_MESSAGE = "요금 : ";
			private final int FEE_PER_MIN = 120;
			private int usedMin;
			@Override
			public void run(){
				while(GAME_SETTING_SCREEN_OPEN_BUTTON.getIcon().equals(imageIcon)){
					try{
						USED_TIME_TEXTFIELD.setText(TIME_MESSAGE + usedMin / 60 + " : " + usedMin % 60);
						TOTAL_FEE_TEXTFIELD.setText(FEE_MESSAGE + usedMin * FEE_PER_MIN);
						sleep(1000);
						usedMin++;
					}catch(InterruptedException e){e.printStackTrace();}
				}
			}
		});
		thread.start();
	}
	/**
	 * 게임비가 지불되면 본 객체를 최신화 시켜주는 매서드
	 */
	public void updatePayedScreenContents(){
		isBeingPlayed = false;
		ImageIcon imageIcon = ImageIconFactory.makeImageIcon("src\\resource\\", "UseTableImage.png");
		GAME_SETTING_SCREEN_OPEN_BUTTON.setIcon(imageIcon);
		USED_TIME_TEXTFIELD.setText("");
		TOTAL_FEE_TEXTFIELD.setText("");
	}
}
