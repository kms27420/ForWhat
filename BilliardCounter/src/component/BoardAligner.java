package component;

/**
 * Board클래스에 ScreenList에서 가지고 있는 Screen들을 정렬시켜주는 역할의 클래스
 * @author Kwon
 *
 */
public abstract class BoardAligner {
	protected int boardWidth, boardHeight;		// 정렬을 위해 필요한 Board의 사이즈
	protected int screenWidth, screenHeight;	// 정렬을 위해 필요한 Screen의 사이즈
	
	/**
	 * 정렬을 위한 절차를 밟는 매서드, 변수 초기화 후 정렬해주는 매서드
	 * @param board  정렬이 필요한 Board
	 * @param screenList  Board에 정렬해줘야할 Screen들을 가지고 있는 list
	 */
	protected void followAlignProcess(Board board, ScreenList screenList){
		this.initBoardSize(board);
		this.initScreenSize();
		this.align(board, screenList);
	}
	
	/**
	 * Board의 사이즈를 초기화해주는 매서드
	 * @param board	 정렬이 필요한 Board
	 */
	protected abstract void initBoardSize(Board board);
	/**
	 * 초기화 되어있는 Board의 사이즈를 통해 screenSize를 초기화해주는 매서드
	 */
	protected abstract void initScreenSize();
	/**
	 * Board에 Screen들을 정렬해주는 매서드
	 * @param board		정렬이 필요한 Board
	 * @param screenList	Board에 정렬해줘야할 Screen들을 가지고 있는 List
	 */
	protected abstract void align(Board board, ScreenList screenList);
}
