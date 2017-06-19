import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.*;
import javax.swing.*;

public class PlusButton extends JButton implements ActionListener{
	PlusButton(Icon icon){
		super(icon);
		int[] tmp = this.getXYnSize();
		this.setBounds(tmp[0], tmp[1], tmp[2], tmp[3]);
		this.setBorderPainted(false);
		this.setContentAreaFilled(false);
		this.setFocusPainted(false);
		this.addActionListener(this);
	}
	private int[] getXYnSize(){
		int[] ret = new int[4];
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int paneHeight = screenSize.height - screenSize.height / 5;
		
		if(Counter.getTableCount() % 3 == 0){
			ret[0] = screenSize.width / 25;
		}
		else if(Counter.getTableCount() % 3 == 1){
			ret[0] = screenSize.width * 9 / 25;
		}
		else{
			ret[0] = screenSize.width * 17 / 25;
		}
		
		ret[1] = paneHeight / 25 +  (Counter.getTableCount() / 3) * (paneHeight * 12 / 25);
		
		ret[2] = screenSize.width * 7 / 25;
		ret[3] = paneHeight * 11 / 25;
		
		return ret;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.println("Å×½ºÆ®");
		Counter.addTableCount();
		int[] tmp = this.getXYnSize();
		this.setBounds(tmp[0], tmp[1], tmp[2], tmp[3]);
	}
}
