package gameFeeInfoHandling;

import components.Screen;
import playingGameControl.GameFeeInfo;

/**
 * 
 * @author Kwon
 *
 */
public abstract class GameFeeInfoManager extends Screen{
	public final int MAX_GAME_NUMBER = 20;
	
	protected final GameFeeInfo[] GAME_FEE_INFO = new GameFeeInfo[MAX_GAME_NUMBER];
	
	protected int gameCount = 0;
	protected int totalUsedTime;
	protected int totalFee;
	
	public GameFeeInfoManager(int screenNumber){
		super(screenNumber);
	}
	
	public int getGameCount(){
		return gameCount;
	}
	public int getTotalUsedTime(){
		return totalUsedTime;
	}
	public int getTotalFee(){
		return totalFee;
	}
	public GameFeeInfo[] getGameFeeInfo(){
		return GAME_FEE_INFO;
	}
	
	public abstract void addGameFeeInfo(GameFeeInfo gameFeeInfo);
	public abstract void saveCurrentState();
}
