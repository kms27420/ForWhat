package playingGameControl;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import components.ImageButton;
import components.Screen;
import gameFeeInfoHandling.GameFeeInfoManager;

public class PlayingScreen extends Screen implements Runnable{
	private final JTextComponent USED_TIME_TEXTFIELD = new JTextField();		// 사용 시간을 보여주는 TextField
	private final JTextComponent TOTAL_FEE_TEXTFIELD = new JTextField();		// 현재 요금을 보여주는 TextField
	private final ImageButton EXIT_BUTTON = new ImageButton("src\\resource\\", "ExitImage.png");
	
	private final GameFeeCalculator CALCULATOR = new GameFeeCalculator();
	private final Thread THIS_SCREEN_THREAD = new Thread(this);
	
	private final GameFeeInfo GAME_FEE_INFO = new GameFeeInfo();
	
	private boolean isBeingPlayed = true;
	
	public PlayingScreen(int screenNum, GameFeeInfoManager gameFeeInfoManager){
		super(screenNum);
		this.initProperties();
		this.addComponents();
		this.addListenerToExitButton(gameFeeInfoManager);
	}
	
	public Thread getThisScreenThread(){
		return THIS_SCREEN_THREAD;
	}
	public JTextComponent getUsedTimeTextField(){
		return USED_TIME_TEXTFIELD;
	}
	public JTextComponent getTotalFeeTextField(){
		return TOTAL_FEE_TEXTFIELD;
	}
	private void setUsedTimeTextField(int usedTime){
		USED_TIME_TEXTFIELD.setText("사용 시간 : " + String.format("%02d",usedTime / 60) + " : " + String.format("%02d",usedTime % 60));
	}
	private void setTotalFeeTextField(int totalFee){
		TOTAL_FEE_TEXTFIELD.setText("요금 : " + totalFee + "원");
	}
	
	private void addListenerToExitButton(GameFeeInfoManager gameFeeInfoManager){
		EXIT_BUTTON.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
				Date date = new Date();
				
				String endTime = sdf.format(date);
				
				GAME_FEE_INFO.setEndTime(endTime);
				GAME_FEE_INFO.setUsedTime(CALCULATOR.getUsedTime());
				GAME_FEE_INFO.setTotalFee(CALCULATOR.getTotalFee());
				
				gameFeeInfoManager.addGameFeeInfo(GAME_FEE_INFO);
				
				isBeingPlayed = false;
				synchronized (PlayingScreen.this) {
					PlayingScreen.this.notify();
				}
			}
		});
	}
	
	@Override
	protected void initProperties() {
		this.setLayout(null);
		this.setBackground(new Color(0x00DD00));
		
		THIS_SCREEN_THREAD.start();
	}

	@Override
	protected void initComponentsProperties() {
		int width = this.getWidth() / 3;
		int height = this.getHeight() / 3;
		int x = width * 2;
		int y = height * 2;
		
		Font font = new Font("MD아트체", Font.BOLD, 25);
		Color color = new Color(0x00DD00);
		
		USED_TIME_TEXTFIELD.setBounds(width / 4, 0, width * 2, height);		
		USED_TIME_TEXTFIELD.setFont(font);
		USED_TIME_TEXTFIELD.setEditable(false);
		USED_TIME_TEXTFIELD.setBackground(color);
		USED_TIME_TEXTFIELD.setBorder(null);
		
		TOTAL_FEE_TEXTFIELD.setBounds(width / 4, height, width, height);
		TOTAL_FEE_TEXTFIELD.setFont(font);
		TOTAL_FEE_TEXTFIELD.setEditable(false);
		TOTAL_FEE_TEXTFIELD.setBackground(color);
		TOTAL_FEE_TEXTFIELD.setBorder(null);
		
		EXIT_BUTTON.setBounds(x, y, width, height);
	}

	@Override
	protected void addComponents() {
		this.add(USED_TIME_TEXTFIELD);
		this.add(TOTAL_FEE_TEXTFIELD);
		this.add(EXIT_BUTTON);
	}

	@Override
	public void run() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd HH:mm:ss");
		Date date = new Date();
		
		String dateOfGame = sdf.format(date).substring(0, 6);
		String startTime = sdf.format(date).substring(7, 15);
		
		GAME_FEE_INFO.setDateOfGame(dateOfGame);
		GAME_FEE_INFO.setStartTime(startTime);
		GAME_FEE_INFO.setTableNum(this.getScreenNumber());
		
		while(true){			
			if(!isBeingPlayed){
				CALCULATOR.interrupt();
				break;
			}
			try{
				synchronized (CALCULATOR) {
					setUsedTimeTextField(CALCULATOR.getUsedTime());
					setTotalFeeTextField(CALCULATOR.getTotalFee());
					CALCULATOR.wait();
				}
			}catch(InterruptedException e){e.printStackTrace();}
		}
	}
}
