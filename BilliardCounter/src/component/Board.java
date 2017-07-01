package component;

import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * 여러 Screen들이 그려지는 곳, 여러 Screen들을 한 눈에 모아 볼 수 있는 곳이다.
 * @author Kwon
 *
 */
public abstract class Board extends JPanel{
	protected ScreenList screenList;
	protected BoardAligner boardAligner;
	private boolean isInitedAlready;
	
	protected void initScreenList(ScreenList screenList){
		this.screenList = screenList;
	}
	protected void initBoardAligner(BoardAligner boardAligner){
		this.boardAligner = boardAligner;
	}
	protected void addComponents(){
		for(int i = 0; i < screenList.getMaxRow(); i++){
			for(int j = 0; j < screenList.getMaxCol(); j++){
				this.add(screenList.getScreen(i, j));
			}
		}
	}
	private void showScreens(){
		boardAligner.followAlignProcess(this, screenList);
	}
	
	@Override
	public void paint(Graphics g){
		super.paint(g);
		
		if(!isInitedAlready){
			isInitedAlready = true;
			
			this.showScreens();
		}
	}
}
