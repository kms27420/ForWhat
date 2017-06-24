package tableStatusScreenControl;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.border.BevelBorder;


/**
 * 
 * 당구대 상황을 나타내주는 화면 클래스
 * 당구대가 사용 중이면 사용중인 화면을 보여주고 그렇지 않다면 대기 화면을 보여준다.
 * @author Kwon
 *
 */
public class TableStatusScreen extends JPanel{
	private final TableStatusScreenContents TABLE_STATUS_SCREEN_CONTENTS = new TableStatusScreenContents();
	
	private boolean isInitedAlready;
	
	public TableStatusScreen(){
		this.initTableStatusScreen();
		this.addComponents();
	}
	private void initTableStatusScreen(){
		this.setLayout(null);
		this.setBackground(new Color(0x00DD00));
		this.setBorder(new BevelBorder(BevelBorder.RAISED));
	}
	private void addComponents(){
		this.add(TABLE_STATUS_SCREEN_CONTENTS.getUsedTimeTextField());
		this.add(TABLE_STATUS_SCREEN_CONTENTS.getTotalFeeTextField());
		this.add(TABLE_STATUS_SCREEN_CONTENTS.getGameSettingScreenOpenButton());
	}
	private void initComponents(int width, int height){
		TABLE_STATUS_SCREEN_CONTENTS.initComponents(width, height);
	}
	/**
	 * TableStatusScreen 인스턴스의 TABLE_STATUS_SCREEN_CONTENTS를 받아오는 매서드
	 * @return TABLE_STATUS_SCREEN_CONTENTS
	 */
	public TableStatusScreenContents getTableStatusScreenContents(){
		return TABLE_STATUS_SCREEN_CONTENTS;
	}
	
	@Override
	public void paint(Graphics g){
		super.paint(g);
		if(!isInitedAlready){
			isInitedAlready = true;
			this.initComponents(this.getWidth(), this.getHeight());
			this.repaint();
			this.revalidate();
		}
	}
	
}