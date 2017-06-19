import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class CounterFrame extends JFrame{
	private class StartButton extends JButton implements ActionListener{
		StartButton(){
			this.addActionListener(this);
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
	CounterFrame(CounterButton counterButton){
		super(counterButton.getTableNum() + "");
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int frameWidth = screenSize.width * 2 / 3;
		int frameHeight = screenSize.height * 2 / 3;
		int x = screenSize.width / 2 - frameWidth / 2;
		int y = screenSize.height / 2 - frameHeight / 2;
		
		this.setBounds(x, y, frameWidth, frameHeight);
		this.setVisible(true);
	}
}
