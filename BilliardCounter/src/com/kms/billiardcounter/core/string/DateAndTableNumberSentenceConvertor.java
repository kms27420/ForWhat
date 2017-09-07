package com.kms.billiardcounter.core.string;

/**
 * 
 * 검색 문장을 날짜와 테이블 번호로 변환해주는 클래스
 * 
 * @author Kwon
 *
 */
public class DateAndTableNumberSentenceConvertor {
	
	private DateAndTableNumberSentenceConvertor() {};
	
	/**
	 * 
	 * 검색 문장을 날짜와 테이블 번호로 변환해주는 매서드
	 * 
	 * @param searchSentence 검색 문장
	 * @return dateAndTableNumber[0] 날짜, dateAndTableNumber[1] 테이블 번호
	 */
	public static final String[] convertSearchSentenceIntoDateAndTableNumber ( String searchSentence ) {
		
		String[] dateAndTableNumber = new String[2];
		
		dateAndTableNumber[0] = null;
		dateAndTableNumber[1] = null;
		
		String sentenceToResearch = NumericManufacturer.getSubstringConsistingOnlyOfNumeric
				( searchSentence = NumericManufacturer.getSubstringStartingWithNumeric( searchSentence ) );
		
		if( sentenceToResearch == null )	return dateAndTableNumber;
		
		if( sentenceToResearch.length() == 8 ) {
			
			if( Integer.parseInt( sentenceToResearch.substring( 4, 6 ) ) > 12 ) {
				
				System.out.println("12월 넘음");
				
				return dateAndTableNumber;
				
			} else if( Integer.parseInt( sentenceToResearch.substring( 6, 8 ) ) > 31 ) {
				
				System.out.println("31일 넘음");
				
				return dateAndTableNumber;
				
			}
			
			dateAndTableNumber[0] = sentenceToResearch.substring( 2 );
			
		} else if( sentenceToResearch.length() == 6 ) {
			
			if( Integer.parseInt( sentenceToResearch.substring( 2, 4 ) ) > 12 ) {
				
				System.out.println("12월 넘음");
				
				return dateAndTableNumber;
				
			} else if( Integer.parseInt( sentenceToResearch.substring( 4, 6 ) ) > 31 ) {
				
				System.out.println("31일 넘음");
				
				return dateAndTableNumber;
				
			}

			dateAndTableNumber[0] = sentenceToResearch;
			
		} else if( sentenceToResearch.length() == 4  || sentenceToResearch.length() == 2 ) {
			
			if( sentenceToResearch.length() == 4 )		dateAndTableNumber[0] = sentenceToResearch.substring( 2 );
			else	dateAndTableNumber[0] = sentenceToResearch;
			
			searchSentence = NumericManufacturer.getSubstringStartingWithNumeric( searchSentence.substring( sentenceToResearch.length() ) );
			
			sentenceToResearch = NumericManufacturer.getSubstringConsistingOnlyOfNumeric( searchSentence );
			
			if( sentenceToResearch == null )	return dateAndTableNumber;
			
			if( sentenceToResearch.length() <= 2 ) {
				
				if( Integer.parseInt( sentenceToResearch ) > 12 ) {
					
					System.out.println("12월 넘음");
					
					return dateAndTableNumber;
				
				}
				
				dateAndTableNumber[0] += String.format( "%02d", Integer.parseInt( sentenceToResearch ) );
				
				searchSentence = NumericManufacturer.getSubstringStartingWithNumeric( searchSentence.substring( sentenceToResearch.length() ) );
				
				sentenceToResearch = NumericManufacturer.getSubstringConsistingOnlyOfNumeric( searchSentence );
				
				if( sentenceToResearch == null )	return dateAndTableNumber;
				
				if( sentenceToResearch.length() <= 2 ) {
					
					if( Integer.parseInt( sentenceToResearch ) > 31 ) {
						
						System.out.println("31일 넘음");
						
						return dateAndTableNumber;
					
					}
					
					dateAndTableNumber[0] += String.format( "%02d", Integer.parseInt( sentenceToResearch ) );
					
				} else {
					
					System.out.println("day 2글자 넘음");
					
					return dateAndTableNumber;
					
				}
				
			} else if( sentenceToResearch.length() == 4 ) {
				
				if( Integer.parseInt( sentenceToResearch.substring( 0, 2 ) ) > 12 || Integer.parseInt( sentenceToResearch.substring( 2, 4 ) ) > 31 ) {
					
					System.out.println("월 또는 일이 범위를 넘어섰음");
					
					return dateAndTableNumber;
					
				} else {
					
					dateAndTableNumber[0] += sentenceToResearch;
					
				}
				
			} else {
				
				System.out.println( "월 또는 일을 찾을 수 없음" );
				
				return dateAndTableNumber;
				
			}
			
		} else {
			
			System.out.println("year을 인식할 수 없음");
			
			return dateAndTableNumber;
			
		}
		
		searchSentence = NumericManufacturer.getSubstringStartingWithNumeric( searchSentence.substring( sentenceToResearch.length() ) );
		
		sentenceToResearch = NumericManufacturer.getSubstringConsistingOnlyOfNumeric( searchSentence );
		
		if( sentenceToResearch == null || sentenceToResearch.length() == 0 )	{
			
			System.out.println("테이블 번호가 없음");
			
			return dateAndTableNumber;
			
		} else {
			
			dateAndTableNumber[1] = "" + Integer.parseInt( sentenceToResearch );
			
		}
		
		return dateAndTableNumber;
		
	}
}
