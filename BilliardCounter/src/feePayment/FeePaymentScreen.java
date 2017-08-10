package feePayment;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import components.ImageButton;
import components.Screen;
import components.Window;
import gameFeeInfoHandling.GameFeeInfoManager;

/**
 * 게임 요금을 계산하는 Screen 클래스
 * @author Kwon
 *
 */
public class FeePaymentScreen extends Screen{
	private final JTextComponent SCREEN_NUMBER_TEXTFIELD = new JTextField();	// 테이블 번호를 보여주는 TextField
	private final JTextComponent USED_TIME_TEXTFIELD = new JTextField();		// 사용 시간을 보여주는 TextField
	private final JTextComponent TOTAL_FEE_TEXTFIELD = new JTextField();		// 현재 요금을 보여주는 TextField
	private final ImageButton GAME_PAYMENT_BUTTON = new ImageButton("src\\resource\\", "PayTableImage.png");	// 게임비를 계산하는 버튼
	
	/**
	 * 컴포넌트들을 초기화해주는 생성자
	 * @param gameFeeInfoManager 컴포넌트들을 초기화 시키기위한 정보를 가지고 있는 객체
	 * @param closedWindow GAME_PAYMENT_BUTTON을 초기화 시키기 위한 정보를 가지고 있는 객체
	 */
	public FeePaymentScreen(GameFeeInfoManager gameFeeInfoManager, Window closedWindow){
		super(gameFeeInfoManager.getScreenNumber());
		
		this.initProperties();
		this.addComponents();
		
		this.addListenerToGamePaymentButton(gameFeeInfoManager, closedWindow);
		this.setUsedTimeTextField(gameFeeInfoManager.getTotalUsedTime());
		this.setTotalFeeTextField(gameFeeInfoManager.getTotalFee());
	}
	
	/**
	 * GAME_PAYMENT_BUTTON에 ActionListener를 add해주는 매서드
	 * @param gameFeeInfoManager 버튼 이벤트 실행 시 실질적인 활동을 해줄 GameFeeInfoManager
	 * @param closedWindow 버튼 이벤트 실행 시 닫힐 window
	 */
	private void addListenerToGamePaymentButton(GameFeeInfoManager gameFeeInfoManager, Window closedWindow){
		GAME_PAYMENT_BUTTON.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameFeeInfoManager.saveCurrentState();
				closedWindow.dispose();
			}
		});
	}
	
	/**
	 * USED_TIME_TEXTFIELD의 text를 설정하는 매서드
	 * @param usedTime text에 작성될 사용시간
	 */
	private void setUsedTimeTextField(int usedTime){
		USED_TIME_TEXTFIELD.setText("총 사용 시간 : " + String.format("%02d",usedTime / 60) + " : " + String.format("%02d",usedTime % 60));
	}
	/**
	 * TOTAL_FEE_TEXTFIELD의 text를 설정하는 매서드
	 * @param totalFee
	 */
	private void setTotalFeeTextField(int totalFee){
		TOTAL_FEE_TEXTFIELD.setText("계산하실 요금 : " + totalFee + "원");
	}
	
	@Override
	protected void initProperties() {
		this.setLayout(null);
	}

	@Override
	protected void initComponentsProperties() {
		int width = this.getWidth() / 3;
		int height = this.getHeight() / 4;
		int x = width * 2;
		int y = height * 2;
		
		Font font = new Font("MD아트체", Font.BOLD, 25);
		
		SCREEN_NUMBER_TEXTFIELD.setBounds(this.getWidth() / 2 - width / 2, 0, width, height);
		SCREEN_NUMBER_TEXTFIELD.setFont(font);
		SCREEN_NUMBER_TEXTFIELD.setText(this.getScreenNumber() + "번 테이블");
		SCREEN_NUMBER_TEXTFIELD.setEditable(false);
		SCREEN_NUMBER_TEXTFIELD.setBorder(null);
		
		USED_TIME_TEXTFIELD.setBounds(0, height * 3 / 2, width * 2, height);		
		USED_TIME_TEXTFIELD.setFont(font);
		USED_TIME_TEXTFIELD.setEditable(false);
		USED_TIME_TEXTFIELD.setBorder(null);
		
		TOTAL_FEE_TEXTFIELD.setBounds(0, height * 5 / 2, width * 2, height);
		TOTAL_FEE_TEXTFIELD.setFont(font);
		TOTAL_FEE_TEXTFIELD.setEditable(false);
		TOTAL_FEE_TEXTFIELD.setBorder(null);
		
		GAME_PAYMENT_BUTTON.setBounds(x, y * 4 / 3, width, height * 4 / 3);
	}

	@Override
	protected void addComponents() {
		this.add(GAME_PAYMENT_BUTTON);
		this.add(SCREEN_NUMBER_TEXTFIELD);
		this.add(TOTAL_FEE_TEXTFIELD);
		this.add(USED_TIME_TEXTFIELD);
	}
	
}
