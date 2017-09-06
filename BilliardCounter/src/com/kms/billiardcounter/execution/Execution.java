package com.kms.billiardcounter.execution; 

import com.kms.billiardcounter.core.ancillaryframe.BaseFeeSettingFrame;
import com.kms.billiardcounter.core.mainframe.BilliardCounterFrame;
import com.kms.billiardcounter.dao.basefee.BaseFeeLoader;

/**
 * 
 * 실행 클래스, main함수가 있다.
 * 
 * @author Kwon
 *
 */
public class Execution {
	
	public static void main(String[] args) {
		
		new BilliardCounterFrame();
		
		if( !BaseFeeLoader.isBaseFeeInited() ) {
			
			new BaseFeeSettingFrame();
			
		}
		
	}
	
}