package playingGameControl;

public class GameFeeInfo {
	private String dateOfGame, startTime, endTime;
	private int gameNum, tableNum, usedTime, fee;
	
	public String getDateOfGame(){
		return dateOfGame;
	}
	public int getGameNum(){
		return gameNum;
	}
	public int getTableNum(){
		return tableNum;
	}
	public String getStartTime(){
		return startTime;
	}
	public String getEndTime(){
		return endTime;
	}
	public int getUsedTime(){
		return usedTime;
	}
	public int getFee(){
		return fee;
	}
	
	public void setDateOfGame(String dateOfGame){
		this.dateOfGame = dateOfGame;
	}
	public void setGameNum(int gameNum){
		this.gameNum = gameNum;
	}
	public void setTableNum(int tableNum){
		this.tableNum = tableNum;
	}
	public void setStartTime(String startTime){
		this.startTime = startTime;
	}
	public void setEndTime(String endTime){
		this.endTime = endTime;
	}
	public void setUsedTime(int usedTime){
		this.usedTime = usedTime;
	}
	public void setTotalFee(int fee){
		this.fee = fee;
	}
}
