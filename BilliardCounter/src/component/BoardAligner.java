package component;

/**
 * Board에 Screen들과 추가될 컴포넌트들을 정렬해주기 위한 클래스
 * @author Kwon
 *
 */
public abstract class BoardAligner {
	protected Board board;								// screen들과 추가 컴포넌트들이 align될 장소
	protected ScreenList screenList;					// board에 align될 screen들의 list
	protected int screenWidth, screenHeight;			// 정렬하기 위한 screen의 사이즈
	/**
	 * board가 처음 paint될 시점에 screen들을 정렬해줄 때 사용하는 식별자
	 */
	public static final int ALIGN_INIT = 0;
	/**
	 * board에 새로운 screen이 추가되었을 때 정렬하기 위한 식별자
	 */
	public static final int ALIGN_CREATED_SCREEN = 1;
	/**
	 * board의 screen이 삭제될 때 정렬하기 위한 식별자
	 */
	public static final int ALIGN_DELETED_SCREEN = 2;
	
	/**
	 * board, screenList를 초기화해주는 생성자
	 * @param board  board에 대입할 값
	 * @param screenList  screenList에 대입할 값
	 */
	public BoardAligner(Board board, ScreenList screenList){
		this.initBoard(board);
		this.initScreenList(screenList);
	}
	/**
	 * board를 초기화해주는 매서드
	 * @param board  board에 들어갈 값
	 */
	private void initBoard(Board board){
		this.board = board;
	}
	/**
	 * screenList를 초기화해주는 매서드
	 * @param screenList  screenList에 들어갈 값
	 */
	private void initScreenList(ScreenList screenList){
		this.screenList = screenList;
	}
	
	/**
	 * 현 BoardAligner의 기본 설정(screenSize 등)을 초기화해준다.
	 */
	protected abstract void initThisBoardAligner();
	/**
	 * board에 components를 추가해주는 매서드
	 */
	protected abstract void addComponentsToBoard();
	/**
	 * alignOption에 따라서 board를 align해주는 매서드
	 * @param alignOption  ALIGN_INIT, ALIGN_CREATED_SCREEN, ALIGN_DELETED_SCREEN
	 */
	protected abstract void alignBoard(int alignOption);
}
