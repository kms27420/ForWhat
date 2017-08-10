package components;

import java.awt.Graphics;

import javax.swing.JFrame;

/**
 * 프로그램의 내용을 보여주는 창
 * @author Kwon
 *
 */
public abstract class Window extends JFrame{
	private boolean isItFirstPaint = true;		// paint가 처음되었을 때를 판단하는 boolean 변수
	
	/**
	 * 본 Window의 기본 설정(layout, background 등)을 초기화시켜주는 매서드
	 */
	protected abstract void initProperties();
	/**
	 * 본 Window에 Component들을 추가시켜주는 매서드
	 */
	protected abstract void addComponents();
	/**
	 * 본 Window가 paint되는 시점에 component들의 기본 설정(사이즈 등)을 초기화 시켜주는 매서드
	 */
	protected abstract void initComponentsProperties();
	
	@Override
	public void paint(Graphics g){
		super.paint(g);
		
		if(isItFirstPaint){
			isItFirstPaint = false;
			this.initComponentsProperties();
		}
	}
}
