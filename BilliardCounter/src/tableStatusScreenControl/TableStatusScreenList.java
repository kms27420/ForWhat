package tableStatusScreenControl;

/**
 * 
 * TableStatusScreen의 목록에 대한 정보를 가지고 있는 클래스
 * @author Kwon
 *
 */
public class TableStatusScreenList {
	private static final int MAX_ROW = 4;
	private static final int MAX_COL = 4;
	private static final TableStatusScreen[][] TABLE_STATUS_SCREEN = new TableStatusScreen[MAX_ROW][MAX_COL];
	
	private static int currentRow, currentCol;
	
	private TableStatusScreenList(){}
	
	public static void initTableStatusScreenList() {
		currentCol = -1;
		currentRow = 0;
		TableStatusScreenLoader.loadTableStatusScreens();
	}
	/**
	 * 가장 최근에 생성된 TableStatusScreen의 row를 반환하는 매서드
	 * @return currentRow
	 */
	public static int getCurrentRow(){
		return currentRow;
	}
	/**
	 * 가장 최근에 생성된 TableStatusScreen의 col를 반환하는 매서드
	 * @return currentCol
	 */
	public static int getCurrentCol(){
		return currentCol;
	}
	/**
	 * 다음에 생성될 TableStatusScreen의 row를 반환하는 매서드
	 * @return 다음에 생성될 TableStatusScreen의 row
	 */
	public static int getNextRow(){
		if(currentCol == MAX_COL - 1)
			return currentRow + 1;
		return currentRow;
	}
	/**
	 * 다음에 생성될 TableStatusScreen의 col를 반환하는 매서드
	 * @return 다음에 생성될 TableStatusScreen의 col
	 */
	public static int getNextCol(){
		if(currentCol == MAX_COL - 1)
			return 0;
		return currentCol + 1;
	}
	/**
	 * 생성할 수 있는 TableStatusScreen의 최대 row를 반환하는 매서드
	 * @return MAX_ROW
	 */
	public static int getMaxRow(){
		return MAX_ROW;
	}
	/**
	 * 생성할 수 있는 TableStatusScreen의 최대 col를 반환하는 매서드
	 * @return MAX_COL
	 */
	public static int getMaxCol(){
		return MAX_COL;
	}
	/**
	 * 원하는 (row, col) 좌표의 TableStatusScreen을 반환해주는 매서드
	 * @param row, 원하는 좌표 중 row
	 * @param col, 원하는 좌표 중 col
	 * @return TABLE_STATUS_SCREEN[row][col]
	 */
	public static TableStatusScreen getTableStatusScreen(int row, int col){
		return TABLE_STATUS_SCREEN[row][col];
	}
	/**
	 * TableStatusScreen을 증설하는 매서드
	 * @return TableStatusScreen을 성공적으로 만들었으면 true를 리턴, 그렇지 않다면 false를 리턴
	 */
	public static boolean createTableStatusScreen(){
		if(currentRow == MAX_ROW - 1 && currentCol == MAX_COL - 1)	return false;
		if(currentCol == MAX_COL - 1){
			currentRow++;
			currentCol = 0;
		}else currentCol++;
		
		TABLE_STATUS_SCREEN[currentRow][currentCol] = new TableStatusScreen();
		return true;
	}
}
