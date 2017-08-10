package tableBoardComponents;

import components.Board;
import components.BoardAligner;
import components.Screen;
import dataBaseControl.TablePositionLoader;
import dataBaseControl.TablePositionSaver;

/**
 * 
 * TableStatusScreen이 그려질 Board, 현재 존재하는 모든 당구대의 상황을 볼 수 있는 Board
 * 
 * @author Kwon
 *
 */
public class TableBoard extends Board{
	/**
	 * MAX_ROW, MAX_COL등을 초기화해주고 SCREEN, boardAligner를 초기화 후  컴포넌트들을 추가해주는 생성자
	 */
	public TableBoard(){
		this.initProperties();
		
		this.initBoardAlignerInstance();
		this.initScreensInstance();
		
		this.addComponents();
	}
	
	@Override
	protected void initProperties() {
		this.setLayout(null);
	}
	@Override
	protected void initBoardAlignerInstance() {
		this.boardAligner = new TableBoardAligner();
	}
	@Override
	protected void initScreensInstance(){
		TablePositionLoader loader = new TablePositionLoader();
		int tmpTableNum;
		int maxRow = ((TableBoardAligner)boardAligner).MAX_ROW;
		int maxCol = ((TableBoardAligner)boardAligner).MAX_COL;
		
		screen = new Screen[maxRow * maxCol];
		isNumberUsedAlready = new boolean[maxRow * maxCol + 1];
		
		for(int i = 0; i < maxRow; i++){
			for(int j = 0; j < maxCol; j++){
				if((tmpTableNum = loader.getTableNum(i, j)) == 0){
					screen[i * maxCol + j] = new CreationScreen(this, i * maxRow + j);
				}else{
					screen[i * maxCol + j] = new TableScreen(tmpTableNum);
					isNumberUsedAlready[tmpTableNum] = true;
				}
			}
		}
	}
	@Override
	protected void addComponents() {
		int maxRow = ((TableBoardAligner)boardAligner).MAX_ROW;
		int maxCol = ((TableBoardAligner)boardAligner).MAX_COL;
		
		for(int i = 0; i < maxRow; i++){
			for(int j = 0; j < maxCol; j++){
				if(screen[i * maxCol + j] != null)
					this.add(screen[i * maxCol + j]);
			}
		}
	}
	@Override
	public boolean createScreen(Screen screen, int screenNumber) {
		if(isNumberUsedAlready[screenNumber])	return false;
		
		isNumberUsedAlready[screenNumber] = true;
		
		this.remove(screen);
		
		int x = screen.getX();
		int y = screen.getY();
		int width = screen.getWidth();
		int height = screen.getHeight();
		int maxRow = ((TableBoardAligner)boardAligner).MAX_ROW;
		int maxCol = ((TableBoardAligner)boardAligner).MAX_COL;
		
		new TablePositionSaver(screenNumber, screen.getScreenNumber() / maxRow, screen.getScreenNumber() % maxCol);
		
		screen = new TableScreen(screenNumber);
		screen.setBounds(x, y, width, height);
		this.add(screen);
		
		this.repaint();
		this.revalidate();
		
		return true;
	}
	@Override
	protected boolean deleteScreen(Screen screen) {
		return false;
	}
}
