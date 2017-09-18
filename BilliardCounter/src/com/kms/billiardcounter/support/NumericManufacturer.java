package com.kms.billiardcounter.support;

/**
 * 
 * 문자열을 숫자로 분류해주는 클래스
 * 
 * @author Kwon
 *
 */
public class NumericManufacturer {

	private NumericManufacturer() {}
	
	/**
	 * 
	 * 입력받은 문자열이 숫자인지 판단해주는 매서드
	 * 
	 * @param searchSentence 입력받은 문자열
	 * @return 입력받은 문자열이 숫자이면 true, 아니면 false
	 */
	private static final boolean isNumeric ( String searchSentence ) {
		
		try{
			
			Integer.parseInt( searchSentence );
			
			return true;
			
		}catch( Exception e ) {
			
			return false;
			
		}
		
	}
	
	/**
	 * 
	 * 입력받은 문자열을 오직 숫자로만 이루어진 문자열로 변경해서 반환해주는 매서드
	 * 
	 * @param searchSentence 입력받은 문자열
	 * @return 숫자로만 이루어진 searchSentence의 substring, 숫자가 없을 시엔 null 반환
	 */
	public static final String getSubstringConsistingOnlyOfNumeric ( String searchSentence ) {
		
		int startPosition = 0;
		int endPosition = 0;
		
		for( ; startPosition < searchSentence.length(); startPosition++ ) {
			
			if( isNumeric( searchSentence.substring( startPosition, startPosition + 1 ) ) ) {
					
				break;
					
			}
				
		}

		for( endPosition = startPosition + 1; endPosition < searchSentence.length(); endPosition++ ) {
			
			if( !isNumeric( searchSentence.substring( endPosition, endPosition + 1 ) ) ) {
					
				break;
					
			}
				
		}
		
		try{
			
			return searchSentence.substring( startPosition, endPosition );
		
		} catch( StringIndexOutOfBoundsException e ) {
			
			return null;
			
		}
		
	}
	
	/**
	 * 
	 * 입력받은 문자열을 숫자로만 이루어진 문자열로 바꿔서 int형으로 반환해주는 매서드
	 * 
	 * @param searchSentence 입력받은 문자열
	 * @return 숫자로만 이루어진 searchSentence의 substring을 int형으로 반환, 숫자가 없을 시엔 0을 반환
	 */
	public static final int getIntConsistingOnlyOfNumeric ( String searchSentence ) {
		
		String substringConsistingOnlyOfNumeric = getSubstringConsistingOnlyOfNumeric ( searchSentence );
		
		if( substringConsistingOnlyOfNumeric == null ) return 0;
		
		return Integer.parseInt( substringConsistingOnlyOfNumeric );
		
	}
	
}
