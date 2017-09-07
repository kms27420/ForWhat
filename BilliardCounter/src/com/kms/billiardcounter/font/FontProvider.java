package com.kms.billiardcounter.font;

import java.awt.Font;

/**
 * 
 * 본 프로그램에서 사용되는 Font들을 제공하는 클래스
 * 
 * @author Kwon
 *
 */
public class FontProvider {
	
	private FontProvider(){}
	
	/**
	 * 
	 * 본 프로그램의 기본 Font를 반환하는 매서드
	 * 
	 * @return 본 프로그램의 기본 Font
	 */
	public static final Font getDefaultFont(){
		
		return new Font( "MD아트체", Font.BOLD, 15 );
		
	}
	
	/**
	 * 
	 * 기본 Font에서 사이즈를 지정해준 Font를 반환하는 매서드
	 * 
	 * @param fontSize 사용자가 사용하고자 하는 Font Size
	 * @return 본 프로그램의 기본 Font에 사이즈를 지정해준 Font
	 */
	public static final Font getDefaultFont( int fontSize ){
		
		return new Font( "MD아트체", Font.BOLD, fontSize );
		
	}
	
}
