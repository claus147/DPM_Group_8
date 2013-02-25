/*
 * LCDInfo.java
 * Kornpat Choy 260454385
 * Nabil Zoldjalali 260450152
 *
 * added many additional display items
 */

import lejos.nxt.LCD;
import lejos.nxt.UltrasonicSensor;
import lejos.util.Timer;
import lejos.util.TimerListener;

public class LCDInfo implements TimerListener{
	public static final int LCD_REFRESH = 100;
	private Odometer odo;
	private UltrasonicSensor us;
	private Timer lcdTimer;
	
	// arrays for displaying data
	private double [] pos;
	
	public LCDInfo(Odometer odo) {
		this.odo = odo;
		this.lcdTimer = new Timer(LCD_REFRESH, this);
		
		// initialise the arrays for displaying data
		pos = new double [3];
		
		// start the timer
		lcdTimer.start();
	}
	
	public void timedOut() { 
		odo.getPosition(pos);
		LCD.clear();
		LCD.drawString("X:  ", 0, 0);	LCD.drawString("0: ", 8, 0);	//0,1,2,3 are the elements in the array storing the 
		LCD.drawString("Y:  ", 0, 1);	LCD.drawString("1: ", 8, 1);	//starting and ending xTheta and yTheta values for the 
		LCD.drawString("H:  ", 0, 2);	LCD.drawString("2: ", 8, 2);	//light localization
		LCD.drawString("US: ", 0, 3);	LCD.drawString("3: ", 8, 3);	//US is the updated ultrasonic sensor (80 if no wall)
		LCD.drawString("tA: ", 0, 4);	LCD.drawString("DE:", 8, 4);	//thetaA from USLocalizer
		LCD.drawString("tB: ", 0, 5);									//thetaB from USLocalizer
		LCD.drawString("T:  ", 0, 6);									//turn to value (the positive y axis)
		LCD.drawString("LS: ", 0, 7);									//light sensor value
		
		//drawing all the values
		LCD.drawInt((int)(pos[0] * 10), 4, 0);			LCD.drawInt((int)LightLocalizer.thetas[0], 11, 0);
		LCD.drawInt((int)(pos[1] * 10), 4, 1);			LCD.drawInt((int)LightLocalizer.thetas[1], 11, 1);
		LCD.drawInt((int)pos[2], 4, 2);					LCD.drawInt((int)LightLocalizer.thetas[2], 11, 2);
		LCD.drawInt((int)USLocalizer.distance, 4, 3);	LCD.drawInt((int)LightLocalizer.thetas[3], 11, 3);
		LCD.drawInt((int)USLocalizer.angleA, 4, 4);		LCD.drawInt((int)LightLocalizer.darknessEdge, 11, 4);
		LCD.drawInt((int)USLocalizer.angleB, 4, 5);		
		LCD.drawInt((int)USLocalizer.turn, 4, 6);
		LCD.drawInt(LightLocalizer.lightReading, 4, 7);
	}
}
