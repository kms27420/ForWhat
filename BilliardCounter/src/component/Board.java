package component;

import javax.swing.JPanel;

/**
 * 여러 Screen들이 그려지는 곳, 여러 Screen들을 한 눈에 모아 볼 수 있는 곳이다.
 * @author Kwon
 *
 */
public abstract class Board extends JPanel{
	protected BoardAligner boardAligner;		// Board 클래스의 컴포넌트들을 Board위에 정렬해주는 클래스의 인스턴스 변수
	
	/**
	 * boardAligner의 초기화를 해주는 매서드
	 * @param boardAligner, boardAligner에 대입할 값
	 */
	protected void initBoardAligner(BoardAligner boardAligner){
		this.boardAligner = boardAligner;
	}
	/**
	 * 현 Board에 컴포넌트들을 추가하는 매서드
	 */
	protected void addComponents(){
		boardAligner.addComponentsToBoard();
	}
	
	/**
	 * 현재 Board의 기본 설정(layout, background 등)의 초기화를 해주는 매서드
	 */
	protected abstract void initThisBoard();
}
