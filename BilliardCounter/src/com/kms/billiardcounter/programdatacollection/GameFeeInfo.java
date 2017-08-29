package com.kms.billiardcounter.programdatacollection;

/**
 * 
 * 게임 사용 정보를 저장하는 데이터 클래스
 * 
 * @author Kwon
 *
 */

public class GameFeeInfo {
	
	private String date, startTime, endTime;
	private int gameNum, tableNum, usedTime, fee;
	private boolean isPaid;
	
	public String getDate(){
		
		return date;
		
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
	
	public boolean getIsPaid(){
		
		return isPaid;
		
	}
	
	public void setDate( String date ){
		
		this.date = date;
		
	}
	
	public void setGameNum( int gameNum ){
		
		this.gameNum = gameNum;
		
	}
	
	public void setTableNum( int tableNum ){
		
		this.tableNum = tableNum;
		
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
