package components;

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
	 * default 생성자, 실행하는 내용은 없다
	 */
	public Screen(){}
	/**
	 * screenNumber을 초기화하는 생성자
	 * @param screenNumber 스크린 고유 번호
	 */
	public Screen(int screenNumber){
		this.screenNumber = screenNumber;
	}
	/**
	 * 현재 screen의 번호를 받아오는 매서드
	 * @return 현재 screen의 screenNumber
	 */
	public int getScreenNumber(){
		return screenNumber;
	}
	/**
	 * 현 Screen의 특성을 초기화해주는 매서드
	 */
	protected abstract void initProperties();
	/**
	 * 현 Screen에 포함될 컴포넌트들의 특성을 본 Screen이 paint되는 시점에서 초기화해주는 매서드
	 */
	protected abstract void initComponentsProperties();
	/**
	 * 현 Screen에 포함될 컴포넌트들을 추가해주는 매서드
	 */
	protected abstract void addComponents();
	
	@Override
	public void paint(Graphics g){
		super.paint(g);
		if(isItFirstPaint){
			isItFirstPaint = false;
			this.initComponentsProperties();
		}
	}
}
