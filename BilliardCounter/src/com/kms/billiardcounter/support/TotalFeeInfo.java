package com.kms.billiardcounter.support;

public class TotalFeeInfo {

	private String usedTime, fee;
	private boolean isValid;
	
	public TotalFeeInfo() {
		
		isValid = false;
		
	}
	
	public TotalFeeInfo( String usedTime, String fee ) {
		
		this.usedTime = usedTime;
		this.fee = fee;
		
		if( NumericManufacturer.getIntConsistingOnlyOfNumeric( usedTime ) == 0 || NumericManufacturer.getIntConsistingOnlyOfNumeric( fee ) == 0 ) {
			
			isValid = false;
			
		} else {
		
			isValid = true;
		
		}
		
	}
	
	public String getUsedTime() {
		
		return usedTime;
		
	}
	
	public String getFee() {
		
		return fee;
		
	}
	
	public boolean getIsValid() {
		
		return isValid;
		
	}
	
}
