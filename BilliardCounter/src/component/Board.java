package component;

import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * 여러 Screen들이 그려지는 곳, 여러 Screen들을 한 눈에 모아 볼 수 있는 곳이다.
 * @author Kwon
 *
 */
public abstract class Board extends JPanel{
	private final int MAX_ROW;			// 현 Board에 생성할 Screen들의 최대 열
	private final int MAX_COL;			// 현 Board에 생성할 Screen들의 최대 행
	protected final Screen[][] SCREEN;	// 현 Board에 생성할 Screen들을 담을 변수
	protected final boolean[] IS_NUMBER_USED_ALREADY;	// 현 Board에 생성할 Screen들에 부여될 ScreenNumber가 이미 사용된 것인지 판단하는 boolean변수
	
	protected BoardAligner boardAligner;	// 현 Board에 Screen들을 정렬시켜줄 인스턴스
	
	private boolean isItFirstPaint = true;		// paint가 처음되었을 때를 판단하는 boolean 변수
	
	/**
	 * Board의 상수변수들을 초기화해준다.
	 * @param row  MAX_ROW에 대입할 값
	 * @param col  MAX_COL에 대입할 값
	 */
	public Board(int row, int col){
		MAX_ROW = row;
		MAX_COL = col;
		SCREEN = new Screen[row][col];
		IS_NUMBER_USED_ALREADY = new boolean[row * col + 1];
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
	 * 원하는 (row,col)의 SCREEN을 반환하는 매서드
	 * @param row  SCREEN의 열
	 * @param col  SCREEN의 행
	 * @return SCREEN[row][col]
	 */
	public Screen getScreen(int row, int col){
		return SCREEN[row][col];
	}
	/**
	 * 해당 숫자가 이미 사용중인 ScreenNumber인지 확인해주는 매서드
	 * @param number  확인하고자 하는 숫자
	 * @return 이미 사용중인 숫자면 true를 리턴, 사용중이지 않으면 false를 리턴
	 */
	public boolean getIsNumberUsedAlready(int number){
		return IS_NUMBER_USED_ALREADY[number];
	}
	
	
	/**
	 * 현 Board에 포함되어있는 screen들을 보여주는 매서드, paint 시점에 호출되는 매서드이다.
	 */
	private void showScreens(){
		boardAligner.followAlignProcess(this);
	}
	
	@Override
	public void paint(Graphics g){
		super.paint(g);
		
		if(isItFirstPaint){
			isItFirstPaint = false;
			
			this.showScreens();
		}
	}
	
	/**
	 * 현 Board를 초기화해주는 매서드
	 */
	protected abstract void initThisBoard();
	/**
	 * 현 Board에 포함되는 Screen들을 초기화해준다.
	 */
	protected abstract void initScreens();
	/**
	 * boardAligner를 초기화 시켜주는 매서드
	 * @param boardAligner boardAligner에 대입할 값
	 */
	protected abstract void initBoardAligner(BoardAligner boardAligner);
	/**
	 * component들을 현 Board에 추가시켜주는 매서드
	 */
	protected abstract void addComponents();
	/**
	 * (row, col)좌표에 screenNumber의 숫자를 부여한 Screen을 생성한다.
	 * @param row 생성하고자 하는 Screen의 row
	 * @param col 생성하고자 하는 Screen의 col
	 * @param screenNumber 생성하고자 하는 Screen에게 부여되는 번호
	 */
	public abstract boolean createScreen(int row, int col, int screenNumber);
	/**
	 * 해당 (row, col)의 SCREEN을 삭제하는 매서드
	 * @param row  삭제하고자 하는 SCREEN의 row
	 * @param col  삭제하고자 하는 SCREEN의 col
	 */
	protected abstract boolean deleteScreen(int row, int col);
}
