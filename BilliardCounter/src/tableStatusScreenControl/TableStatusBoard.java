package tableStatusScreenControl;

import component.Board;
import component.BoardAligner;
import tableStatusScreenCreate.SettingWindowOpenScreen;

/**
 * 
 * TableStatusScreen이 그려질 Board, 현재 존재하는 모든 당구대의 상황을 볼 수 있는 Board
 * 
 * @author Kwon
 *
 */
public class TableStatusBoard extends Board{
	public TableStatusBoard(){
		super(4,4);
		this.initThisBoard();
		this.initScreens();
		this.initBoardAligner(new TableStatusBoardAligner());
		this.addComponents();
	}

	@Override
	protected void initThisBoard() {
		this.setLayout(null);
	}
	@Override
	protected void initScreens(){
		for(int i = 0; i < this.getMaxRow(); i++){
			for(int j = 0; j < this.getMaxCol(); j++){
				SCREEN[i][j] = new SettingWindowOpenScreen(i, j, this);
			}
		}
	}
	@Override
	protected void initBoardAligner(BoardAligner boardAligner) {
		this.boardAligner = boardAligner;
	}
	@Override
	protected void addComponents() {
		for(int i = 0; i < this.getMaxRow(); i++){
			for(int j = 0; j < this.getMaxCol(); j++){
				if(SCREEN[i][j] != null)
					this.add(SCREEN[i][j]);
			}
		}
	}
	
	@Override
	public boolean createScreen(int row, int col, int screenNumber) {
		if(IS_NUMBER_USED_ALREADY[screenNumber])	return false;
		
		IS_NUMBER_USED_ALREADY[screenNumber] = true;
		
		this.remove(SCREEN[row][col]);
		
		int x = SCREEN[row][col].getX();
		int y = SCREEN[row][col].getY();
		int width = SCREEN[row][col].getWidth();
		int height = SCREEN[row][col].getHeight();
		
		SCREEN[row][col] = new TableStatusScreen(row, col, screenNumber);
		SCREEN[row][col].setBounds(x, y, width, height);
		this.add(SCREEN[row][col]);
		
		this.repaint();
		this.revalidate();
		
		return true;
	}

	@Override
	protected boolean deleteScreen(int row, int col) {
		return false;
	}
}
