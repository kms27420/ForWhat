package components;

import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * 여러 Screen들을 갖고 있는 Screen, Screen의 생성과 삭제가 가능한 곳이다.
 * @author Kwon
 *
 */
public abstract class Board extends JPanel{
	protected BoardAligner boardAligner;	// 현 Board에 Screen들을 정렬시켜줄 인스턴스
	protected Screen[] screen;	// 현 Board에 생성할 Screen들을 담을 변수
	
	protected boolean[] isNumberUsedAlready;	// 현 Board에 생성할 Screen들에 부여될 ScreenNumber가 이미 사용된 것인지 판단하는 boolean변수
	
	private boolean isItFirstPaint = true;		// paint가 처음되었을 때를 판단하는 boolean 변수
	
	/**
	 * 입력받은 index의 Screen을 반환하는 매서드
	 * @param index 원하는 Screen의 index
	 * @return screen[index]
	 */
	public Screen getScreen(int index){
		return screen[index];
	}
	
	/**
	 * 현 Board의 특성을 초기화해주는 매서드
	 */
	protected abstract void initProperties();
	/**
	 * boardAligner의 객체를 초기화해주는 매서드
	 */
	protected abstract void initBoardAlignerInstance();
	/**
	 * 현 Board에 포함되는 Screen들의 객체를 초기화해주는 매서드
	 */
	protected abstract void initScreensInstance();
	/**
	 * component들을 현 Board에 추가시켜주는 매서드
	 */
	protected abstract void addComponents();
	/**
	 * 입력받은 screen의 위치에 새로운 Screen을 생성하는 매서드
	 * @param screen screen의 위치를 객체로 직접 받는 파라미터
	 * @param screenNumber screen 고유 번호
	 * @return 성공적으로 테이블이 생성되면 true, 그렇지 않으면 false
	 */
	public abstract boolean createScreen(Screen screen, int screenNumber);
	/**
	 * 입력받은 screen의 위치에 있는 Screen을 삭제하는 매서드
	 * @param screen screen의 위치를 객체로 직접 받는 파라미터
	 * @return 성공적으로 삭제되면 true, 그렇지 않으면 false
	 */
	protected abstract boolean deleteScreen(Screen screen);
	
	
	@Override
	public void paint(Graphics g){
		super.paint(g);
		
		if(isItFirstPaint){
			isItFirstPaint = false;
			
			boardAligner.followAlignProcess(this);
		}
	}
}
