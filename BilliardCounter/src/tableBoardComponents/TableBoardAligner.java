package tableBoardComponents;

import java.awt.Dimension;

import components.Board;
import components.BoardAligner;

/**
 * TableStatusBoard가 갖고 있는 SCREEN들의 정렬을 하도록 BoardAligner를 구현한 클래스
 * @author Kwon
 *
 */
public class TableBoardAligner extends BoardAligner{
	public final int MAX_ROW = 4;
	public final int MAX_COL = 4;
	
	@Override
	protected void initBoardSize(Board board) {
		this.boardWidth = board.getWidth();
		this.boardHeight = board.getHeight();
	}

	@Override
	protected void initScreenSize(Board board) {
		this.screenWidth = board.getWidth() / MAX_COL;
		this.screenHeight = board.getHeight() / 2;
	}

	@Override
	protected void align(Board board) {
		board.setPreferredSize(new Dimension(this.screenWidth, this.screenHeight * MAX_ROW));	// board의 사이즈를 스크린을 모두 추가했을 때의 사이즈로 변경해줌
		
		for(int i = 0; i < MAX_ROW; i++){
			for(int j = 0; j < MAX_COL; j++){
				if(board.getScreen(i * MAX_COL + j) != null)
					board.getScreen(i * MAX_COL + j).setBounds(j * this.screenWidth, i * this.screenHeight, this.screenWidth, this.screenHeight);
			}
		}
	}

}
