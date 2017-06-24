package tableStatusScreenControl;

import java.awt.Dimension;

import javax.swing.JPanel;

/**
 * 
 * TableStatusBoard위에 그려질 TableStatusScreen들을 정렬해주는 클래스
 * @author Kwon
 *
 */
public class TableStatusScreenAligner {
	private final JPanel PANEL;								// TableStatusScreen이 그려질 JPanel을 담기위한 변수
	private static int maxCol, screenWidth, screenHeight;	// 정렬에 필요한 TableStatusScreen의 크기와 개수 파악을 위한 변수
	
	/**
	 * TableStatusScreen이 그려질 JPanel을 받아와서 그 위에 TableStatusScreen을 정렬해준다.
	 * @param JPanel panel, TableStatusScreen이 그려질 JPanel
	 */
	public TableStatusScreenAligner(JPanel panel){
		PANEL = panel;
		maxCol = TableStatusScreenList.getMaxCol();
	}
	private void initScreenSize(){
		screenWidth = PANEL.getWidth() / TableStatusScreenList.getMaxCol();
		screenHeight = PANEL.getHeight() / 2;
	}
	/**
	 * 저장된 파일에서 불러온 TableStatusScreen들을 정렬해주는 매서드
	 */
	public void arrangeLoadedScreens(){
		int currentRow = TableStatusScreenList.getCurrentRow();
		int currentCol = TableStatusScreenList.getCurrentCol();
		
		this.initScreenSize();
		this.updateBoardSize();
		
		for(int i = 0; i <= currentRow; i++){
			if(i == currentRow){
				for(int j = 0; j <= currentCol; j++){
					TableStatusScreenList.getTableStatusScreen(i, j).setBounds(j * screenWidth, i * screenHeight, screenWidth, screenHeight);
					PANEL.add(TableStatusScreenList.getTableStatusScreen(i, j));
				}
			}
			else{
				for(int j = 0; j < maxCol; j++){
					TableStatusScreenList.getTableStatusScreen(i, j).setBounds(j * screenWidth, i * screenHeight, screenWidth, screenHeight);
					PANEL.add(TableStatusScreenList.getTableStatusScreen(i, j));
				}
			}
		}
	}
	/**
	 * 새로 추가한 TableStatusScreen을 정렬해주는 매서드
	 */
	public void arrangeNewScreen(){
		int currentRow = TableStatusScreenList.getCurrentRow();
		int currentCol = TableStatusScreenList.getCurrentCol();
		
		int x = currentCol * screenWidth;
		int y = currentRow * screenHeight;
		
		this.updateBoardSize();
		
		TableStatusScreenList.getTableStatusScreen(currentRow, currentCol).setBounds(x, y, screenWidth, screenHeight);
		PANEL.add(TableStatusScreenList.getTableStatusScreen(currentRow, currentCol));
	}
	/**
	 * TableStatusScreen이 그려지는 Board의 사이즈를 최신화 시켜주는 매서드
	 */
	private void updateBoardSize(){
		int nextRow = TableStatusScreenList.getNextRow();
		
		PANEL.setPreferredSize(new Dimension(PANEL.getWidth(), (nextRow + 1) * screenHeight));
	}
}
