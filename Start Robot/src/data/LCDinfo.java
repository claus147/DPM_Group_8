/**
 * @author Tuan-Kiet Luu
 * @version 1.0
 */
package data;

import localization.USLocalizer;

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
import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.util.Timer;
import lejos.util.TimerListener;

	public class LCDinfo implements TimerListener{
		private LightSensor lsL = new LightSensor(SensorPort.S1);
		private LightSensor lsR = new LightSensor(SensorPort.S2);
		
		WheelsData wheels = new WheelsData();
		USData usData = new USData();
		LSData lsDataL = new LSData(lsL);
		LSData lsDataR = new LSData(lsR);
		Odometry odo = new Odometry();
		
		
		public static final int LCD_REFRESH = 100;
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
			LCD.drawString("LM :", 0, 0);	LCD.drawString("Dis: ", 8,0); //left motor &distance (loc)
			LCD.drawString("RM :", 0, 1);	LCD.drawString("Di2: ", 8,1); //right motor
			LCD.drawString("US :", 0, 2);	//ultrasonic sensor
			LCD.drawString("LSL:", 0, 3);	//light sensor left
			LCD.drawString("LSR:", 0, 4);	//light sensor Right	
			LCD.drawString("X:  ",0,5);	// X position
			LCD.drawString("Y:  ",0,6);	// y position
			LCD.drawString("Th: ", 0,7); // theta
			
			
			LCD.drawInt((int) wheels.getLTacho(), 4, 0);	LCD.drawInt((int)USLocalizer.distance, 11, 0);
			LCD.drawInt((int) wheels.getRTacho(), 4, 1);	LCD.drawInt((int)USLocalizer.distance2, 11, 1);
			LCD.drawInt((int) usData.getUSData(), 4, 2);
			LCD.drawInt((int) lsDataL.getLSData(), 4, 3);
			LCD.drawInt((int) lsDataR.getLSData(), 4, 4);
			
			/* START NOT TESTED YET*/
			LCD.drawInt((int) odo.getX(), 4, 5);
			LCD.drawInt((int) odo.getY(), 4, 6);
			LCD.drawInt((int) odo.getTheta(), 4, 7);
			
			/* END NOT TESTED YET */
			
			
		}
		
	

	}
	