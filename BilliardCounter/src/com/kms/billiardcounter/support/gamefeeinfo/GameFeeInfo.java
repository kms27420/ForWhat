package com.kms.billiardcounter.support.gamefeeinfo;

/**
 * 
 * 게임 사용 정보를 저장하는 데이터 클래스
 * 
 * @author Kwon
 *
 */

public class GameFeeInfo {
	
	private String date, startTime, endTime;
	private int gameNumber, tableNumber, usedTime, fee;
	private boolean isPaid;
	
	public String getDate(){
		
		return date;
		
	}
	
	public int getGameNumber(){
		
		return gameNumber;
		
	}
	
	public int getTableNumber(){
		
		return tableNumber;
		
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
	
	public boolean getIsPaid(){
		
		return isPaid;
		
	}
	
	public void setDate( String date ){
		
		this.date = date;
		
	}
	
	public void setGameNumber( int gameNumber ){
		
		this.gameNumber = gameNumber;
		
	}
	
	public void setTableNumber( int tableNumber ){
		
		this.tableNumber = tableNumber;
		
	}
	
	public void setStartTime( String startTime ){
		
		this.startTime = startTime;
		
	}
	
	public void setEndTime( String endTime ){
		
		this.endTime = endTime;
		
	}
	
	public void setUsedTime( int usedTime ){
		
		this.usedTime = usedTime;
		
	}
	
	public void setFee( int fee ){
		
		this.fee = fee;
		
	}
	
	public void setIsPaid( boolean isPaid ){
		
		this.isPaid = isPaid;
		
	}
	
}
