package gameFeeInfoHandling;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import components.ImageButton;
import dataBaseControl.GameListSaver;
import feePayment.FeePaymentWindow;
import playingGameControl.GameFeeInfo;

/**
 * 종료된 게임들의 목록과 현재 계산해야할 요금의 상태를 보여주고 게임 시작과 계산을 선택할 수 있는 Screen
 * @author Kwon
 *
 */
public class GameFeeCenterScreen extends GameFeeInfoManager{
	private final GameListScreen GAME_LIST_SCREEN = new GameListScreen();					// 종료된 게임들의 목록을 가지고 있는 Screen의 객체
	private final JScrollPane GAME_LIST_CONTAIN_PANE = new JScrollPane(GAME_LIST_SCREEN);	// GAME_LIST_SCREEN을 스크롤 판으로 보여주기 위한 객체
	
	private final JTextComponent TOTAL_USED_TIME_TEXTFIELD = new JTextField();				// 총 사용 시간을 보여주는 텍스트 필드 객체
	private final JTextComponent TOTAL_FEE_TEXTFIELD = new JTextField();					// 총 요금을 보여주는 텍스트 필드 객체
	private final ImageButton START_BUTTON = new ImageButton("src\\resource\\", "UseTableImage.png");	// 게임을 시작하는 버튼 객체
	private final ImageButton PAYMENT_BUTTON = new ImageButton("src\\resource\\", "PayTableImage.png");	// 게임 요금 계산 Window를 띄워주는 버튼 객체
	
	/**
	 * 컴포넌트들을 초기화해주는 생성자
	 * @param screenNumber 본 Screen의 screenNumber를 초기화시켜주기위한 파라미터
	 */
	public GameFeeCenterScreen(int screenNumber){
		super(screenNumber);
		
		this.initProperties();
		this.addComponents();
		this.addListenerToStartButton();
		this.addListenerToPaymentButton();
	}
	
	/**
	 * START_BUTTON에 ActionListener를 add해주는 매서드
	 */
	private void addListenerToStartButton(){
		START_BUTTON.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				synchronized (GameFeeCenterScreen.this) {
					GameFeeCenterScreen.this.notify();
				}
			}
		});
	}
	/**
	 * PAYMENT_BUTTON에 ActionListener를 add해주는 매서드
	 */
	private void addListenerToPaymentButton(){
		PAYMENT_BUTTON.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new FeePaymentWindow(GameFeeCenterScreen.this);
			}
		});
	}
	
	@Override
	protected void initProperties() {
		this.setLayout(null);
	}
	@Override
	protected void initComponentsProperties() {
		int width = this.getWidth();
		int height = this.getHeight() / 3;
		
		Font font = new Font("MD아트체", Font.BOLD, 20);
		
		TOTAL_USED_TIME_TEXTFIELD.setFont(font);
		TOTAL_USED_TIME_TEXTFIELD.setText("게임 없음");
		TOTAL_USED_TIME_TEXTFIELD.setEditable(false);
		
		TOTAL_FEE_TEXTFIELD.setFont(font);
		TOTAL_FEE_TEXTFIELD.setEditable(false);
		
		GAME_LIST_CONTAIN_PANE.setBounds(0, 0, width, height * 2);
		TOTAL_USED_TIME_TEXTFIELD.setBounds(0, height * 2, width * 3 / 5, height / 2);
		TOTAL_FEE_TEXTFIELD.setBounds(0, height * 5 / 2, width * 3 / 5, height / 2);
		START_BUTTON.setBounds(width * 3 / 5, height * 2, width / 5, height);
		PAYMENT_BUTTON.setBounds(width * 4 / 5, height * 2, width / 5, height);
		
		PAYMENT_BUTTON.setEnabled(false);
	}
	@Override
	protected void addComponents() {
		this.add(GAME_LIST_CONTAIN_PANE);
		this.add(TOTAL_USED_TIME_TEXTFIELD);
		this.add(TOTAL_FEE_TEXTFIELD);
		this.add(START_BUTTON);
		this.add(PAYMENT_BUTTON);
	}
	
	@Override
	public void addGameFeeInfo(GameFeeInfo gameFeeInfo) {
		if(gameCount == 0)
			PAYMENT_BUTTON.setEnabled(true);
		
		if(gameCount < MAX_GAME_NUMBER){
			gameFeeInfo.setGameNum(gameCount + 1);
			GAME_FEE_INFO[gameCount] = gameFeeInfo;
			
			totalUsedTime += GAME_FEE_INFO[gameCount].getUsedTime();
			totalFee += GAME_FEE_INFO[gameCount].getFee();
			
			gameCount++;
			
			TOTAL_USED_TIME_TEXTFIELD.setText("사용 시간 : " + String.format("%02d", totalUsedTime / 60) + " : " + String.format("%02d", totalUsedTime % 60));
			TOTAL_FEE_TEXTFIELD.setText("요금 : " + totalFee + "원");
			
			GAME_LIST_SCREEN.addGameInfoLabel(this);
		}else{
			System.out.println("쌓아둘 수 있는 게임 횟수를 초과하였습니다.");
		}
	}
	@Override
	public void saveCurrentState(){
		new GameListSaver(this);
		GAME_LIST_SCREEN.removeAllLabel();
		TOTAL_USED_TIME_TEXTFIELD.setText("게임 없음");
		TOTAL_FEE_TEXTFIELD.setText("");
		PAYMENT_BUTTON.setEnabled(false);
		
		gameCount = totalUsedTime = totalFee =  0;
	}
}
