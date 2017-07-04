package tableStatusScreenControl;

import java.awt.Dimension;

import component.Board;
import component.BoardAligner;

public class TableStatusBoardAligner extends BoardAligner{
	@Override
	protected void initBoardSize(Board board) {
		this.boardWidth = board.getWidth();
		this.boardHeight = board.getHeight();
	}

	@Override
	protected void initScreenSize(Board board) {
		this.screenWidth = board.getWidth() / board.getMaxCol();
		this.screenHeight = board.getHeight() / 2;
	}

	@Override
	protected void align(Board board) {
		board.setPreferredSize(new Dimension(this.screenWidth, this.screenHeight * board.getMaxRow()));	// board의 사이즈를 스크린을 모두 추가했을 때의 사이즈로 변경해줌
		
		for(int i = 0; i < board.getMaxRow(); i++){
			for(int j = 0; j < board.getMaxCol(); j++){
				if(board.getScreen(i, j) != null)
					board.getScreen(i, j).setBounds(j * this.screenWidth, i * this.screenHeight, this.screenWidth, this.screenHeight);
			}
		}
	}

}
