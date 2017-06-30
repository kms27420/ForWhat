package component;

import java.awt.Graphics;

import javax.swing.JFrame;

/**
 * Screen, Board를 보여주는 창
 * @author Kwon
 *
 */
public abstract class Window extends JFrame{
	private boolean isInitedAlready;		// paint가 처음 될때 initComponent를 해주었는가를 판단하는 boolean변수
	
	/**
	 * 본 Window의 기본 설정(layout, background 등)을 초기화시켜주는 매서드
	 */
	protected abstract void initThisWindow();
	/**
	 * 본 Window에 Component들을 추가시켜주는 매서드
	 */
	protected abstract void addComponents();
	/**
	 * 본 Window가 paint되는 시점에 component들의 기본 설정(사이즈 등)을 초기화 시켜주는 매서드
	 */
	protected abstract void initComponents();
	
	@Override
	public void paint(Graphics g){
		super.paint(g);
		
		if(!isInitedAlready){
			isInitedAlready = true;
			this.initComponents();
		}
	}
}
