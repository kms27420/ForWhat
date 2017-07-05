package component;

import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * 여러 컴포넌트를 가지고 있으면서 원하는 결과들을 화면에 나타내주는 클래스
 * @author Kwon
 *
 */
public abstract class Screen extends JPanel{
	private int screenNumber;	// 현재 스크린의 번호, 스크린의 번호가 필요한 경우 사용된다.
	private boolean isItFirstPaint = true;		// paint가 처음되었을 때를 판단하는 boolean 변수
	/**
	 * screenNumber를 설정해주는 매서드
	 * @param screenNumber  screenNumber에 대입될 값
	 */
	protected void setScreenNumber(int screenNumber){
		this.screenNumber = screenNumber;
	}
	/**
	 * 현재 screen의 번호를 받아오는 매서드
	 * @return SCREEN_NUMBER
	 */
	public int getScreenNumber(){
		return screenNumber;
	}
	
	/**
	 * 현 Screen의 기본 설정(layout, background 등)을 초기화해주는 매서드
	 */
	protected abstract void initThisScreen();
	/**
	 * 현 Screen에 포함될 컴포넌트들의 기본 설정(사이즈 등)을 본 Screen이 paint되는 시점에서 초기화해주는 매서드
	 */
	protected abstract void initComponents();
	/**
	 * 현 Screen에 포함될 컴포넌트들을 추가해주는 매서드
	 */
	protected abstract void addComponents();
	/**
	 * 현 Screen에 나타날 컴포넌트들의 상황을 최신화해주는 매서드
	 */
	protected abstract void updateComponents();
	
	@Override
	public void paint(Graphics g){
		super.paint(g);
		if(isItFirstPaint){
			isItFirstPaint = false;
			this.initComponents();
		}
	}
}
