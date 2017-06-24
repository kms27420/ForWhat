package tableStatusScreenControl;

import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * 
 * TableStatusScreen이 그려질 Board, 현재 존재하는 모든 당구대의 상황을 볼 수 있는 Board
 * 
 * @author Kwon
 *
 */
public class TableStatusBoard extends JPanel{
	private final TableStatusScreenCreateButton TABLE_STATUS_SCREEN_CREATE_BUTTON = new TableStatusScreenCreateButton(this);	// TableStatusScreen 생성 버튼
	private final TableStatusScreenAligner TABLE_STATUS_SCREEN_ALIGNER = new TableStatusScreenAligner(this);	// TableStatusScreen 정렬자
	
	private static int createButtonWidth, createButtonHeight;
	private boolean isInitedAlready;
	
	public TableStatusBoard(){
		this.initTableStatusBoard();
		this.addComponents();
	}
	private void initTableStatusBoard() {
		this.setLayout(null);
		TableStatusScreenList.initTableStatusScreenList();				// TableStatusScreenList를 초기화 해줌
	}
	private void addComponents(){
		this.add(TABLE_STATUS_SCREEN_CREATE_BUTTON);
	}
	private void arrangeScreenCreateButton(){
		int x = TableStatusScreenList.getNextCol() * createButtonWidth;
		int y = TableStatusScreenList.getNextRow() * createButtonHeight;
		TABLE_STATUS_SCREEN_CREATE_BUTTON.setBounds(x, y, createButtonWidth, createButtonHeight);
	}
	private void initButtonSize(){
		createButtonWidth = this.getWidth() / TableStatusScreenList.getMaxCol();
		createButtonHeight = this.getHeight() / 2;
	}
	/**
	 * TableStatusScreen을 TableStatusBoard에 그리는 작업을 하는 매서드
	 */
	public void paintTableStatusScreen(){
		if(TableStatusScreenList.createTableStatusScreen()){	// TableStatusScreen이 성공적으로 증설 되었다면
			TABLE_STATUS_SCREEN_ALIGNER.arrangeNewScreen();		// 증설된 Screen을 정렬해줌
			this.arrangeScreenCreateButton();
			this.repaint();
			this.revalidate();
		}else	System.out.println("더 이상 당구대를 증설할 수 없습니다.");
	}
	@Override
	public void paint(Graphics g){
		super.paint(g);
		
		if(!isInitedAlready){
			isInitedAlready = true;
			this.initButtonSize();
			
			TABLE_STATUS_SCREEN_ALIGNER.arrangeLoadedScreens();
			this.arrangeScreenCreateButton();
			
			this.repaint();
			this.revalidate();
			
		}
	}
}
