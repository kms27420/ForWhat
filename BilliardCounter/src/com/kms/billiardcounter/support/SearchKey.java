package com.kms.billiardcounter.support;

public class SearchKey {

	private String date, tableNumber;
	private boolean isValid;
	
	public SearchKey( String searchSentence ) {
		
		init( searchSentence );
		
	}
	
	public String getDate() {
		
		return date;
		
	}
	
	public String getTableNumber() {
		
		return tableNumber;
		
	}
	
	public boolean getIsValid() {
		
		return isValid;
		
	}
	
	private void init( String searchSentence ) {
		
		date = NumericManufacturer.getSubstringConsistingOnlyOfNumeric( searchSentence );
		
		searchSentence = searchSentence.substring( date.length() );
		
		tableNumber= NumericManufacturer.getSubstringConsistingOnlyOfNumeric( searchSentence );
		
		setIsValid();
	
	}
	
	private void setIsValid() {
		
		if( date.length() != 6 ) {
			
			isValid = false;
			
			return;
			
		} else if( Integer.parseInt( date.substring( 2, 4 ) ) > 12 || Integer.parseInt( date.substring( 2, 4 ) ) < 1 ) {
			
			isValid = false;
			
			return;
			
		} else if( Integer.parseInt( date.substring( 4, 6 ) ) > 31 || Integer.parseInt( date.substring( 4, 6 ) ) < 1 ) {
			
			isValid = false;
			
			return;
			
		} else {
			
			isValid = true;
			
		}
		
	}
	
}
