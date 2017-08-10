package components;

/**
 * Board클래스가 갖고 있는 SCREEN들을 Board에 정렬시켜주는 역할의 클래스
 * @author Kwon
 *
 */
public abstract class BoardAligner {
	protected int boardWidth, boardHeight;		// 정렬을 위해 필요한 Board의 사이즈
	protected int screenWidth, screenHeight;	// 정렬을 위해 필요한 Screen의 사이즈
	
	/**
	 * 정렬을 위한 절차를 밟는 매서드, 변수 초기화 후 board에 screen들을 정렬해주는 매서드
	 * @param board  정렬이 필요한 Board
	 */
	protected void followAlignProcess(Board board){
		this.initBoardSize(board);
		this.initScreenSize(board);
		this.align(board);
	}
	
	/**
	 * Board의 사이즈를 초기화해주는 매서드
	 * @param board	 정렬이 필요한 Board
	 */
	protected abstract void initBoardSize(Board board);
	/**
	 * Screen의 사이즈를 초기화해주는 매서드
	 * @param screen들이 정렬되어질 board
	 */
	protected abstract void initScreenSize(Board board);
	/**
	 * screen들을 board위에 정렬해주는 매서드
	 * @param board 정렬이 필요한 board
	 */
	protected abstract void align(Board board);
}
