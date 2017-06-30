package billiardCounterAppearance;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JScrollPane;

import tableStatusScreenControl.TableStatusBoard;

/**
 * 
 * 본 프로그램 "BilliardCounter"의 내용(당구장 요금 계산, 정산 프로그램의 기능 등  기능과 이와 관련된 인터페이스)을 담고 있는 클래스이다.
 * 
 * @author Kwon
 *
 */
public class BilliardCounterResultScreen extends JScrollPane{
	private final TableStatusBoard TABLE_STATUS_SCREEN_BORARD = new TableStatusBoard();	// TableStatusScreen을 담고 있는 Board
	private boolean isInitedAlready;
	
	public BilliardCounterResultScreen() {
		this.initThisScreen();
		this.addComponents();
	}
	
	private void initThisScreen(){
		this.getVerticalScrollBar().setUnitIncrement(40);
	}
	private void addComponents(){
		this.setViewportView(TABLE_STATUS_SCREEN_BORARD);
	}
	private void initComponents(){
		TABLE_STATUS_SCREEN_BORARD.setBounds(0, 0, this.getWidth(), this.getHeight());
		TABLE_STATUS_SCREEN_BORARD.setBackground(new Color(0x00AA00));
	}
	
	@Override
	public void paint(Graphics g){
		super.paint(g);
		if(!isInitedAlready){
			isInitedAlready = true;
			this.initComponents();
			this.repaint();
			this.revalidate();
		}
	}
}
