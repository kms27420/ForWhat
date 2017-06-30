package component;

public abstract class ScreenList {
	private final int MAX_ROW;			// 현 ScreenList에 생성할 Screen들의 최대 열
	private final int MAX_COL;			// 현 ScreenList에 생성할 Screen들의 최대 행
	private final Screen[][] SCREEN;	// 현 ScreenList에 생성할 Screen들을 담을 변수
	private final boolean[] SCREEN_NUMBER_USING_STATEMENT;			// 해당 index + 1 의 번호가 Screen의 screenNumber에 사용되었는지 판단하는 boolean변수
	
	protected int currentRow = 0, currentCol = -1;		// 가장 최근에 생성된 Screen의 열과 행, 초기값은 (0, -1)
	
	/**
	 * MAX_ROW와 MAX_COL, SCREEN들을 초기화해주는 생성자
	 * @param maxRow
	 * @param maxCol
	 * @param screen
	 */
	public ScreenList(int maxRow, int maxCol, Screen[][] screen){
		MAX_ROW = maxRow;
		MAX_COL = maxCol;
		SCREEN = screen;
		SCREEN_NUMBER_USING_STATEMENT = new boolean[MAX_ROW * MAX_COL];
	}
	
	/**
	 * MAX_ROW값을 반환하는 매서드
	 * @return MAX_ROW
	 */
	public int getMaxRow(){
		return MAX_ROW;
	}
	/**
	 * MAX_COL값을 반환하는 매서드
	 * @return MAX_COL
	 */
	public int getMaxCol(){
		return MAX_COL;
	}
	/**
	 * 가장 최근에 생성된 Screen의 row를 반환하는 매서드
	 * @return currentRow
	 */
	public int getCurrentRow(){
		return currentRow;
	}
	/**
	 * 가장 최근에 생성된 Screen의 col을 반환하는 매서드
	 * @return currentCol
	 */
	public int getCurrentCol(){
		return currentCol;
	}
	/**
	 * 다음에 생성될 Screen의 row를 반환하는 매서드
	 * @return 다음에 생성될 Screen의 row
	 */
	public int getNextRow(){
		if(currentCol == MAX_COL - 1)
			return currentRow + 1;
		else
			return currentRow;
	}
	/**
	 * 다음에 생성될 screen의 col를 반환하는 매서드
	 * @return 다음에 생성될 screen의 col
	 */
	public int getNextCol(){
		if(currentCol == MAX_COL - 1)
			return 0;
		else
			return currentCol + 1;
	}
	/**
	 * 원하는 (row,col)의 SCREEN을 반환하는 매서드
	 * @param row  SCREEN의 열
	 * @param col  SCREEN의 행
	 * @return SCREEN[row][col]
	 */
	public Screen getScreen(int row, int col){
		return SCREEN[row][col];
	}
	/**
	 * 현 ScreenList의 기본 정보를 초기화해주는 매서드
	 */
	protected abstract void initThisScreenList();
	/**
	 * 새로 생성할 SCREEN에 screenNumber를 부여하고 생성해주는 매서드
	 * @param screenNumber  새로운 SCREEN에 부여해줄 screenNumber
	 */
	protected abstract void createScreen(int screenNumber);
	/**
	 * 해당 (row, col)의 SCREEN을 삭제하는 매서드
	 * @param row  삭제하고자 하는 SCREEN의 row
	 * @param col  삭제하고자 하는 SCREEN의 col
	 */
	protected abstract void deleteScreen(int row, int col);
}
