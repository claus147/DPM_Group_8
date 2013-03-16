/**
 * @author Tuan-Kiet Luu
 * @version 1.0
 */
package data;

import java.util.*;

import odometry.Odometry;
import lejos.nxt.*;
import lejos.util.TimerListener;

/**
 * @author Tuan-Kiet
 * @author Yu Yang Liu
 * @author Kornpat Choy
 * @author Nabil Zoldjalali
 * This class will show all data on the LCD Screen
 */

	import lejos.nxt.LCD;
	import lejos.nxt.UltrasonicSensor;
	import lejos.util.Timer;
	import lejos.util.TimerListener;

	public class LCDinfo implements TimerListener{
		
		WheelsData wheels = new WheelsData();
		USData usData = new USData();
		LSData lsData = new LSData();
		
		public static final int LCD_REFRESH = 100;
		private Odometry odo;
		private UltrasonicSensor us;
		private Timer lcdTimer;
		
		// arrays for displaying data
		private double [] pos;
		
		public LCDinfo(Odometry odo) {
			this.odo = odo;
			this.lcdTimer = new Timer(LCD_REFRESH, this);
			
			// initialise the arrays for displaying data
			pos = new double [3];
			
			// start the timer
			lcdTimer.start();
		}
		
		
		public void timedOut() { 
			LCD.clear();
			LCD.drawString("LM :", 0, 0);	//left motor
			LCD.drawString("RM :", 0, 1);	//right motor
			LCD.drawString("US :", 0, 2);	//ultrasonic sensor
			LCD.drawString("LSL :", 0, 3);	//light sensor left
			LCD.drawString("LSR :", 0, 4);	//light sensor Right	
	
			LCD.drawInt((int) wheels.getLTacho(), 6, 0);
			LCD.drawInt((int) wheels.getRTacho(), 6, 1);
			LCD.drawInt((int) usData.getUSData(), 6, 2);
			LCD.drawInt((int) lsData.getLSDataL(), 6, 3);
			LCD.drawInt((int) lsData.getLSDataR(), 6, 4);
		}
		
	

	}
	
