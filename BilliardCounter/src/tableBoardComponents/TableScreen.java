package tableBoardComponents;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.text.JTextComponent;

import components.Screen;
import gameFeeInfoHandling.GameFeeCenterScreen;
import playingGameControl.PlayingScreen;


/**
 * 
 * 당구대 상황을 나타내주는 화면 클래스
 * 당구대가 사용 중이면 사용중인 화면을 보여주고 그렇지 않다면 대기 화면을 보여준다.
 * @author Kwon
 *
 */
public class TableScreen extends Screen implements Runnable{
	private final Thread THIS_SCREEN_THREAD = new Thread(this);
	
	private final JTextComponent SCREEN_NUMBER_TEXTFIELD = new JTextField();	// 테이블 번호를 보여주는 TextField
	private final GameFeeCenterScreen GAME_FEE_CENTER_SCREEN;
	
	private PlayingScreen playingScreen;
	
	private boolean isBeingPlayed;
	
	public TableScreen(int screenNumber){
		super(screenNumber);
		
		GAME_FEE_CENTER_SCREEN = new GameFeeCenterScreen(screenNumber);
		
		this.initProperties();
		this.addComponents();
	}
	
	public boolean getIsBeingPlayed(){
		return isBeingPlayed;
	}
	private void createPlayingScreen(){
		int width = this.getWidth();
		int height = this.getHeight() * 3 / 4;
		int x = 0;
		int y = height / 3;
		
		playingScreen = new PlayingScreen(this.getScreenNumber(), GAME_FEE_CENTER_SCREEN);
		playingScreen.setBounds(x, y, width, height);
		this.add(playingScreen);
		
		this.repaint();
		this.revalidate();
	}
	private void deletePlayingScreen(){
		this.remove(playingScreen);
		playingScreen = null;
	}
	
	private void createPaymentListScreen(){
		this.add(GAME_FEE_CENTER_SCREEN);
		
		this.repaint();
		this.revalidate();
	}
	private void deletePaymentListScreen(){
		this.remove(GAME_FEE_CENTER_SCREEN);
	}
	
	
	@Override
	protected void initProperties() {
		this.setLayout(null);
		this.setBackground(new Color(0x00DD00));
		this.setBorder(new BevelBorder(BevelBorder.RAISED));
		
		THIS_SCREEN_THREAD.start();
	}
	@Override
	protected void initComponentsProperties() {
		int width = this.getWidth() / 3;
		int height = this.getHeight() / 4;
		int x = width * 2;
		int y = height * 2;
		
		Font font = new Font("MD아트체", Font.BOLD, 25);
		Color color = new Color(0x00DD00);
		
		SCREEN_NUMBER_TEXTFIELD.setFont(font);
		SCREEN_NUMBER_TEXTFIELD.setText(this.getScreenNumber() + "번 테이블");
		SCREEN_NUMBER_TEXTFIELD.setEditable(false);
		SCREEN_NUMBER_TEXTFIELD.setBackground(color);
		SCREEN_NUMBER_TEXTFIELD.setBorder(null);
		
		SCREEN_NUMBER_TEXTFIELD.setBounds(this.getWidth() / 2 - width / 2, 0, width, height);
		
		width = this.getWidth();
		height = this.getHeight() * 3 / 4;
		x = 0;
		y = height / 3;
		
		GAME_FEE_CENTER_SCREEN.setBounds(x, y, width, height);
	}
	@Override
	protected void addComponents() {
		this.add(SCREEN_NUMBER_TEXTFIELD);
		this.add(GAME_FEE_CENTER_SCREEN);
	}

	@Override
	public void run() {
		while(true){
			try{
				if(isBeingPlayed){
					isBeingPlayed = false;
					synchronized (playingScreen) {
						playingScreen.wait();
					}
					this.deletePlayingScreen();
					this.createPaymentListScreen();
					
					System.out.println("게임 종료");
				}else{
					isBeingPlayed = true;
					synchronized (GAME_FEE_CENTER_SCREEN) {
						GAME_FEE_CENTER_SCREEN.wait();
					}
					this.deletePaymentListScreen();
					this.createPlayingScreen();
					
					System.out.println("게임 시작");
				}
			}catch(InterruptedException e){e.printStackTrace();}
		}
	}
	
}