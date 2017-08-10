package feePayment;

import java.awt.Dimension;
import java.awt.Toolkit;

import components.Window;
import gameFeeInfoHandling.GameFeeInfoManager;

/**
 * 
 * 게임비를 계산하는 Screen을 가지고 있는 Window
 * @author Kwon
 *
 */
public class FeePaymentWindow extends Window{
	private final FeePaymentScreen GAME_PAYMENT_SCREEN;		// 게임비를 계산할 클래스의 객체, 생성자에서 초기화 해준다
	
	/**
	 * 컴포넌트의 객체를 초기화해주는 생성자
	 * @param gameFeeInfoManager
	 */
	public FeePaymentWindow(GameFeeInfoManager gameFeeInfoManager){
		GAME_PAYMENT_SCREEN = new FeePaymentScreen(gameFeeInfoManager, this);
		
		this.initProperties();
		this.addComponents();
	}
	
	@Override
	protected void initProperties() {
		Dimension monitorSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		int width = monitorSize.width / 3;
		int height = monitorSize.height / 3;
		int x = monitorSize.width / 2 - width / 2;
		int y = monitorSize.height / 2 - height / 2;
		
		this.setLayout(null);
		this.setBounds(x, y, width, height);
		this.setVisible(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	@Override
	protected void addComponents() {
		this.add(GAME_PAYMENT_SCREEN);
	}

	@Override
	protected void initComponentsProperties() {
		GAME_PAYMENT_SCREEN.setBounds(0, 0, this.getContentPane().getSize().width, this.getContentPane().getSize().height);
	}
}
