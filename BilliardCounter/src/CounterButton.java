import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class CounterButton extends JButton implements ActionListener{
	private static int tableCount = 0;
	public static final int MAX_TABLE_NUMBER = 23;
	private int tableNum;
	private boolean isBeingPlayed;
	private String buttonName;
	
	CounterButton(int x, int y, int width, int height){
		tableNum = ++tableCount;
		isBeingPlayed = false;
		buttonName = "당구대 사용하기";
		
		this.setBounds(x, y, width, height);
		this.setText(buttonName);
		this.addActionListener(this);
	}
	static public int getTableCount(){
		return tableCount;
	}
	public int getTableNum(){
		return tableNum;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		new CounterFrame(getTableNum());
	}
}
