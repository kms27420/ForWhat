import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ExternalBodyPanel extends JPanel{
	private PlusButton plusButton;
	
	private class PlusButton extends JButton implements ActionListener{
		PlusButton(Icon icon){
			super(icon);
			int[] tmp = this.getXYnSize();
			this.setBounds(tmp[0], tmp[1], tmp[2], tmp[3]);
			this.setSize(tmp[2], tmp[3]);
			this.setBorderPainted(false);
			this.setContentAreaFilled(false);
			this.setFocusPainted(false);
			this.addActionListener(this);
		}
		private int[] getXYnSize(){
			int[] ret = new int[4];
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			int paneHeight = screenSize.height - screenSize.height / 5;
			
			if(CounterButton.getTableCount() % 3 == 0){
				ret[0] = screenSize.width / 25;
			}
			else if(CounterButton.getTableCount() % 3 == 1){
				ret[0] = screenSize.width * 9 / 25;
			}
			else{
				ret[0] = screenSize.width * 17 / 25;
			}
			
			ret[1] = paneHeight / 25 +  (CounterButton.getTableCount() / 3) * (paneHeight * 12 / 25);
			
			ret[2] = screenSize.width * 7 / 25;
			ret[3] = paneHeight * 11 / 25;
			
			return ret;
		}
		
		private void addCounter(int x, int y, int width, int height){
			ExternalBodyPanel.this.add(new CounterButton(x, y, width, height));
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(CounterButton.getTableCount() < CounterButton.MAX_TABLE_NUMBER){
				int[] tmp = this.getXYnSize();
				this.addCounter(tmp[0], tmp[1], tmp[2], tmp[3]);
				tmp = this.getXYnSize();
				this.setBounds(tmp[0], tmp[1], tmp[2], tmp[3]);
			}
			else{
				JOptionPane.showMessageDialog(null, "당구대는 23대까지만 증설이 가능합니다.");
			}
		}
	}
	
	ExternalBodyPanel(int screenWidth, int screenHeight){
		super();
		
		URL imgURL = this.getClass().getResource("PlusImage.png");
		plusButton = new PlusButton(new ImageIcon(imgURL));
		Dimension size = new Dimension();
		size.setSize(screenWidth, (screenHeight - screenHeight / 5) * 4);
		
		this.setLayout(null);
		this.setPreferredSize(size);
		this.add(plusButton);
		this.setBackground(Color.green);
	}
	
	public void updatePlusButtonPosition(){
		Component[] c = this.getComponents();
		int i = 0;
		
		while(!(c[i] instanceof PlusButton) && i + 1 < c.length){
			i++;
		}
		
		if(c[i] instanceof PlusButton){
			int[] tmp = ((PlusButton)c[i]).getXYnSize();
			((PlusButton)c[i]).setLocation(tmp[0], tmp[1]);
		}
		else{
			System.out.println("PlusButton Component를 찾을 수가 없습니다.");
		}
	}
}

class ExternalBodyScrollPane extends JScrollPane{
	
	ExternalBodyScrollPane(Component view, int screenWidth, int screenHeight){
		super(view);
		
		this.setBounds(0, screenHeight / 5, screenWidth, screenHeight - screenHeight / 5 - 30);
		this.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);
		this.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
		this.getVerticalScrollBar().setUnitIncrement(40);
	}
}