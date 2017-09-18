package com.kms.billiardcounter.support;

public class BaseFeeInfo {

	private int baseFeePerMinute, baseFeeTime, feeIncreaseTime;
	private boolean isValid;
	
	public BaseFeeInfo() {
		
		isValid = false;
		
	}
	
	public BaseFeeInfo( int baseFeePerMinute, int baseFeeTime, int feeIncreaseTime ) {
		
		if( baseFeePerMinute > 0 && baseFeeTime > 0 && feeIncreaseTime > 1 ) {
			
			this.baseFeePerMinute = baseFeePerMinute;
			this.baseFeeTime = baseFeeTime;
			this.feeIncreaseTime = feeIncreaseTime;
			
			isValid = true;
			
		}
		
	}
	
	/**
	 * 
	 * baseFeeInfo가 활성화 되었는지를 확인해주는 매서드
	 * 
	 * @return 활성화 되었으면 true, 아니면 false
	 */
	public boolean getIsValid() {
		
		return isValid;
		
	}
	
	/**
	 * 
	 * 기본 요금 정보 중 baseFeePerMinute를 반환하는 매서드
	 * 
	 * @return baseFeePerMinute
	 */
	public int getBaseFeePerMinute() {
		
		return baseFeePerMinute;
		
	}
	
	/**
	 * 
	 * 기본 요금 정보 중 baseFeeTime을 반환하는 매서드
	 * 
	 * @return baseFeeTime
	 */
	public int getBaseFeeTime() {
		
		return baseFeeTime;
		
	}
	
	/**
	 * 
	 * 기본 요금 정보 중 feeIncreaseTime을 반환하는 매서드
	 * 
	 * @return feeIncreaseTime
	 */
	public int getFeeIncreaseTime() {
		
		return feeIncreaseTime;
		
	}
	
}
