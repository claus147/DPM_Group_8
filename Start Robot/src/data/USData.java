package data;
import lejos.nxt.*;


/**
 * Will take the data from the Ultrasonic Sensor
 * @author Tuan-Kiet Luu
 * @version 1.1
 */


public class USData {

	
	double usValue;
	UltrasonicSensor us = new UltrasonicSensor(SensorPort.S1);
	int buttonChoice;
	

	

	/**
	 * This method will take the distance using the ultrasonic Sensor
	 * @return usData
	 */
	public double getUSData(){
	

		int usData;
		// do a ping
		// us.ping();
		
		 // wait for the ping to complete
		try { Thread.sleep(100); } catch (InterruptedException e) {}

		usData = us.getDistance();
		 if (usData > 80){				//if distance > 80 (noWall) set it to 80
			 usData = 80;
		 }
		return usData;
		
	
	}
	/**
	 * This method will print all data to the LCD Screen
	 */
	public void timedOut() { 

		// Step 1 : get info
	//	leftMotor.getTachoWheelA();
	//	rightMotor.getTachoWheelB();
		// Step 2 : clear screen
		LCD.clear();
		// Step 3 : print info
		
		/* START Motor Tachometer */
		LCD.drawString("LM :", 0, 0);
		LCD.drawString("RM :", 0, 1);
		LCD.drawString("US :", 0, 2);
		while(true){
			LCD.drawInt((int) getUSData(), 4, 2);
		}
		
		//LCD.drawInt((int) usValue, 4, 1);
	//	LCD.drawInt((int) leftMotor.getTachoWheelA(), 4, 0);
	//	LCD.drawInt((int) rightMotor.getTachoWheelB(), 4, 1);
		/* END Motor Tachometer */
		
		/*
		 * odo.getPosition(pos);
		LCD.clear();
		LCD.drawString("X:  ", 0, 0);	LCD.drawString("0: ", 8, 0);	//0,1,2,3 are the elements in the array storing the 
		LCD.drawString("Y:  ", 0, 1);	LCD.drawString("1: ", 8, 1);	//starting and ending xTheta and yTheta values for the 
		LCD.drawString("H:  ", 0, 2);	LCD.drawString("2: ", 8, 2);	//light localization
		LCD.drawString("US: ", 0, 3);	LCD.drawString("3: ", 8, 3);	//US is the updated ultrasonic sensor (80 if no wall)
		LCD.drawString("tA: ", 0, 4);	LCD.drawString("DE:", 8, 4);	//thetaA from USLocalizer //de is the darkness edge
		LCD.drawString("tB: ", 0, 5);	LCD.drawString("Av:", 8, 5);	//thetaB from USLocalizer //av is average
		LCD.drawString("T:  ", 0, 6);									//turn to value (the positive y axis)
		LCD.drawString("LS: ", 0, 7);									//light sensor value
		
		//drawing all the values
		LCD.drawInt((int)(pos[0] * 10), 4, 0);			LCD.drawInt((int)LightLocalizer.thetas[0], 11, 0);
		LCD.drawInt((int)(pos[1] * 10), 4, 1);			LCD.drawInt((int)LightLocalizer.thetas[1], 11, 1);
		LCD.drawInt((int)pos[2], 4, 2);					LCD.drawInt((int)LightLocalizer.thetas[2], 11, 2);
		LCD.drawInt((int)USLocalizer.distance, 4, 3);	LCD.drawInt((int)LightLocalizer.thetas[3], 11, 3);
		LCD.drawInt((int)USLocalizer.angleA, 4, 4);		LCD.drawInt((int)LightLocalizer.darknessEdge, 11, 4);
		LCD.drawInt((int)USLocalizer.angleB, 4, 5);		LCD.drawInt(LightLocalizer.average, 11, 5);
		LCD.drawInt((int)USLocalizer.turn, 4, 6);
		LCD.drawInt(LightLocalizer.lightReading, 4, 7);
		 */
	}

}
