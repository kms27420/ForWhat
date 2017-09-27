package com.kms.billiardcounter.size;

import java.awt.Dimension;

public class FrameSize {
	
	private final Dimension ADMIN_DOOR_LOCK_FRAME_SIZE = new Dimension( DeviceSize.getScreenSize().width / 5, DeviceSize.getScreenSize().height / 4 );
	private final Dimension ALERT_FRAME_SIZE = new Dimension( DeviceSize.getScreenSize().width / 4, DeviceSize.getScreenSize().height / 4 );
	private final Dimension BASE_FEE_SETTING_FRAME_SIZE = new Dimension( DeviceSize.getScreenSize().width / 4, DeviceSize.getScreenSize().height / 3 );
	private final Dimension DAY_TOTAL_SALES_FRAME_SIZE = BASE_FEE_SETTING_FRAME_SIZE;
	private final Dimension LOGIN_FRAME_SIZE = new Dimension( DeviceSize.getScreenSize().width / 5, DeviceSize.getScreenSize().height / 3 );
	private final Dimension MAIN_FRAME_SIZE = new Dimension( DeviceSize.getWindowSize().width, DeviceSize.getWindowSize().height );
	private final Dimension PASSWORD_CHANGE_FRAME_SIZE = LOGIN_FRAME_SIZE;
	private final Dimension PAYMENT_FRAME_SIZE = new Dimension( DeviceSize.getScreenSize().width / 3, DeviceSize.getScreenSize().height / 2 );
	private final Dimension SEARCH_RESULT_FRAME_MAX_SIZE = new Dimension( DeviceSize.getScreenSize().width / 2, DeviceSize.getScreenSize().height * 2 / 3 );
	private final Dimension SEARCH_RESULT_FRAME_MIN_SIZE = LOGIN_FRAME_SIZE;
	
	private static final FrameSize INSTANCE = new FrameSize();
	
	private FrameSize(){}
	
	public static Dimension getAdminDoorLockFrameSize() {
		
		return INSTANCE.ADMIN_DOOR_LOCK_FRAME_SIZE;
		
	}
	
	public static Dimension getAlertFrameSize() {
		
		return INSTANCE.ALERT_FRAME_SIZE;
		
	}
	
	public static Dimension getBaseFeeSettingFrameSize() {
		
		return INSTANCE.BASE_FEE_SETTING_FRAME_SIZE;
		
	}
	
	public static Dimension getDayTotalSalesFrameSize() {
		
		return INSTANCE.DAY_TOTAL_SALES_FRAME_SIZE;
		
	}
	
	public static Dimension getLoginFrameSize() {
		
		return INSTANCE.LOGIN_FRAME_SIZE;
		
	}
	
	public static Dimension getMainFrameSize() {
		
		return INSTANCE.MAIN_FRAME_SIZE;
		
	}
	
	public static Dimension getPasswordChangeFrameSize() {
	
		return INSTANCE.PASSWORD_CHANGE_FRAME_SIZE;
	
	}
	
	public static Dimension getPaymentFrameSize() {
	
		return INSTANCE.PAYMENT_FRAME_SIZE;
	
	}
	
	public static Dimension getSearchResultFrameMaxSize() {
		
		return INSTANCE.SEARCH_RESULT_FRAME_MAX_SIZE;
		
	}
	
	public static Dimension getSearchResultFrameMinSize() {
		
		return INSTANCE.SEARCH_RESULT_FRAME_MIN_SIZE;
		
	}
	
}
