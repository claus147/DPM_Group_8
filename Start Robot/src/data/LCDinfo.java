/**
 * @author Tuan-Kiet Luu
 * @author Kornpat Choy
 * @version 1.0
 * This class will show all data on the LCD Screen
 */
package data;

import java.util.*;

import odometry.Odometry;
import lejos.nxt.*;
import lejos.util.TimerListener;


	import lejos.nxt.LCD;
	import lejos.nxt.UltrasonicSensor;
	import lejos.util.Timer;
	import lejos.util.TimerListener;

	public class LCDInfo implements TimerListener{
		
		WheelsData wheels = new WheelsData();
		USData usData = new USData();
		LSData lsData = new LSData();
		Odometry odo = new Odometry();
		
		
		public static final int LCD_REFRESH = 100;
		private UltrasonicSensor us;
		private Timer lcdTimer;
		
		// arrays for displaying data
		private double [] pos;
		
		public LCDInfo(Odometry odo) {
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
			LCD.drawString("X :",0,5);	// X position
			LCD.drawString("Y :",0,6);	// y position
			LCD.drawString("Theta: ", 0,7); // theta
	
			LCD.drawInt((int) wheels.getLTacho(), 6, 0);
			LCD.drawInt((int) wheels.getRTacho(), 6, 1);
			LCD.drawInt((int) usData.getUSData(), 6, 2);
			LCD.drawInt((int) lsData.getLSDataL(), 6, 3);
			LCD.drawInt((int) lsData.getLSDataR(), 6, 4);
			
			/* START NOT TESTED YET*/
			LCD.drawInt((int) odo.getX(), 6, 5);
			LCD.drawInt((int) odo.getY(), 6, 6);
			LCD.drawInt((int) odo.getTheta(), 6, 7);
			
			/* END NOT TESTED YET */
			
			
		}
		
	

	}
	
