import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

public class CounterButton extends JButton implements ActionListener, Runnable{
	private static int tableCount = 0;							// 현재 당구대가 몇대인지 알 수 있는 변수
	public static final int MAX_TABLE_NUMBER = 23;				// 최대 증설 가능한 당구대 수(상수)
	private int tableNum;										// 몇 번 당구대인가를 구별해주는 변수
	private boolean isBeingPlayed;								// 당구대를 사용중인가 아닌가를 구별해주는 변수
	private String buttonName;									// 버튼에 들어갈 문구
	private long startTime, usedMin;							// 시작 시간, 사용 시간
	private int feePerTenMin, totalFee, defaultFeeMin;			// 10분당 요금, 최종 요금, 기본 요금 시간(분)
	private class CounterFrame extends JFrame{
		private class StartButton extends JButton{
			StartButton(){
				this.setBounds(100, 100, 100, 100);
				this.setText("test");
				this.addActionListener(CounterButton.this);
			}
		}
		CounterFrame(){
			super(tableNum + "");
			
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			int frameWidth = screenSize.width * 2 / 3;
			int frameHeight = screenSize.height * 2 / 3;
			int x = screenSize.width / 2 - frameWidth / 2;
			int y = screenSize.height / 2 - frameHeight / 2;
			
			this.setBounds(x, y, frameWidth, frameHeight);
			this.add(new StartButton());
			this.setVisible(true);
		}
	}
	CounterButton(int x, int y, int width, int height){
		tableNum = ++tableCount;
		isBeingPlayed = false;
		buttonName = "당구대 사용하기";
		feePerTenMin = 1200;
		usedMin = 0;
		defaultFeeMin = 30;
		totalFee = defaultFeeMin * feePerTenMin / 10;
		
		this.setBounds(x, y, width, height);
		this.setText(buttonName);
		this.addActionListener(this);
		
		Thread thread = new Thread((Runnable)this);
		thread.start();
	}
	static public int getTableCount(){
		return tableCount;
	}
	public int getTableNum(){
		return tableNum;
	}
	
	@Override
	public void run(){
		while(true){
			if(isBeingPlayed){
				usedMin = (System.currentTimeMillis() - startTime) / 1000l;
				buttonName = "경과 시간 : " + (usedMin / 60l) + " : " + (usedMin % 60l) + "  요금 : " + totalFee + " 원";
				if((int)usedMin > defaultFeeMin)
					totalFee = (((int)usedMin - defaultFeeMin + 9) / 10) * feePerTenMin + defaultFeeMin * feePerTenMin / 10;
			}
			else{
				buttonName = "당구대 사용하기";
			}
			this.setText(buttonName);
			this.setLocation(this.getX(), this.getY());
		}
	}
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() instanceof CounterFrame.StartButton){
			if(!isBeingPlayed)
				startTime = System.currentTimeMillis();
			
			isBeingPlayed = !isBeingPlayed;
		}
		else
			new CounterFrame();
	}
}
