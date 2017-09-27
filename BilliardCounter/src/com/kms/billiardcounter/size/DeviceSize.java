package com.kms.billiardcounter.size;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Toolkit;

public final class DeviceSize {
	
	private final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
	private final Rectangle WINDOW_SIZE = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
	
	private static final DeviceSize INSTANCE = new DeviceSize();
	
	private DeviceSize(){}
	
	public static Dimension getScreenSize() {
		
		return INSTANCE.SCREEN_SIZE;
		
	}
	
	public static Rectangle getWindowSize() {
		
		return INSTANCE.WINDOW_SIZE;
		
	}
	
}
