import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class ExternalFrame extends JFrame{
	private ExternalTitlePanel titlePanel;
	private ExternalBodyPanel bodyPanel;
	private ExternalBodyScrollPane bodyScroll;
	
	ExternalFrame(){
		super("당구장 카운터 프로그램");
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		titlePanel = new ExternalTitlePanel(screenSize.width, screenSize.height);
		bodyPanel = new ExternalBodyPanel(screenSize.width, screenSize.height);
		bodyScroll = new ExternalBodyScrollPane(bodyPanel, screenSize.width, screenSize.height);
		
		this.setLayout(null);
		this.setSize(screenSize.width, screenSize.height);
		this.setLocation(0, 0);
		this.setResizable(false);

		this.add(titlePanel);
		this.add(bodyScroll);
		
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

class ExternalTitlePanel extends JPanel{
	ExternalTitlePanel(int screenWidth, int screenHeight){
		super();
		
		URL imgURL = this.getClass().getResource("BilliardTitle.png");
		JLabel titleLabel = new JLabel(new ImageIcon(imgURL));
		
		titleLabel.setBounds(0, 0, screenWidth / 3, screenHeight / 5);
		
		this.setBounds(0, 0, screenWidth, screenHeight / 5);
		this.setBackground(Color.yellow);
		this.add(titleLabel);
	}
}