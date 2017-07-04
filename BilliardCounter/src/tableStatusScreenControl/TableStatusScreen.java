package tableStatusScreenControl;

import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.text.JTextComponent;

import component.ImageIconFactory;
import component.Screen;
import gameSetting.GameSettingWindowOpenButton;


/**
 * 
 * 당구대 상황을 나타내주는 화면 클래스
 * 당구대가 사용 중이면 사용중인 화면을 보여주고 그렇지 않다면 대기 화면을 보여준다.
 * @author Kwon
 *
 */
public class TableStatusScreen extends Screen{
	private final JTextComponent SCREEN_NUMBER_TEXTFIELD = new JTextField();	// 테이블 번호를 보여주는 TextField
	private final JTextComponent USED_TIME_TEXTFIELD = new JTextField();		// 사용 시간을 보여주는 TextField
	private final JTextComponent TOTAL_FEE_TEXTFIELD = new JTextField();		// 현재 요금을 보여주는 TextField
	private final GameSettingWindowOpenButton GAME_SETTING_SCREEN_OPEN_BUTTON = new GameSettingWindowOpenButton(this);	// GameSettingWindow을 open해주는 버튼
	private final int SCREEN_ROW, SCREEN_COL;
	
	protected boolean isBeingPlayed;
	
	public TableStatusScreen(int row, int col, int screenNumber){
		this.setScreenNumber(screenNumber);
		SCREEN_ROW = row;
		SCREEN_COL = col;
		
		this.initThisScreen();
		this.addComponents();
	}
	public boolean getIsBeingPlayed(){
		return isBeingPlayed;
	}
	@Override
	protected void initThisScreen() {
		this.setLayout(null);
		this.setBackground(new Color(0x00DD00));
		this.setBorder(new BevelBorder(BevelBorder.RAISED));
	}
	@Override
	protected void initComponents() {
		int width = this.getWidth() / 2;
		int height = this.getHeight() / 4;
		int x = width / 4;
		int y = height;
		
		Font font = new Font("MD아트체", Font.BOLD, 25);
		Color color = new Color(0x00DD00);
		
		SCREEN_NUMBER_TEXTFIELD.setBounds(x, y - height, width, height);
		SCREEN_NUMBER_TEXTFIELD.setFont(font);
		SCREEN_NUMBER_TEXTFIELD.setText(this.getScreenNumber() + "번 테이블");
		SCREEN_NUMBER_TEXTFIELD.setEditable(false);
		SCREEN_NUMBER_TEXTFIELD.setBackground(color);
		SCREEN_NUMBER_TEXTFIELD.setBorder(null);
		
		USED_TIME_TEXTFIELD.setBounds(x, y, width, height);		
		USED_TIME_TEXTFIELD.setFont(font);
		USED_TIME_TEXTFIELD.setEditable(false);
		USED_TIME_TEXTFIELD.setBackground(color);
		USED_TIME_TEXTFIELD.setBorder(null);
		
		TOTAL_FEE_TEXTFIELD.setBounds(x, y + height, width, height);
		TOTAL_FEE_TEXTFIELD.setFont(font);
		TOTAL_FEE_TEXTFIELD.setEditable(false);
		TOTAL_FEE_TEXTFIELD.setBackground(color);
		TOTAL_FEE_TEXTFIELD.setBorder(null);
		
		width = width / 3 * 2;
		height = height / 3 * 4;
		x = width * 2;
		y = height * 2;
		
		GAME_SETTING_SCREEN_OPEN_BUTTON.setBounds(x, y, width, height);
		
	}
	@Override
	protected void addComponents() {
		this.add(SCREEN_NUMBER_TEXTFIELD);
		this.add(USED_TIME_TEXTFIELD);
		this.add(TOTAL_FEE_TEXTFIELD);
		this.add(GAME_SETTING_SCREEN_OPEN_BUTTON);
	}
	@Override
	protected void updateComponents() {
		ImageIcon imageIcon;
		
		if(!isBeingPlayed){
			isBeingPlayed = true;
			imageIcon = ImageIconFactory.makeImageIcon("src\\resource\\", "PayTableImage.png");
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
		}else{
			isBeingPlayed = false;
			imageIcon = ImageIconFactory.makeImageIcon("src\\resource\\", "UseTableImage.png");
			GAME_SETTING_SCREEN_OPEN_BUTTON.setIcon(imageIcon);
			USED_TIME_TEXTFIELD.setText("");
			TOTAL_FEE_TEXTFIELD.setText("");
		}
	}
}