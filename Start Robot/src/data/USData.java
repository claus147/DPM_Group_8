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
	
	/* TEST */
	
	NXTRegulatedMotor leftMotor = Motor.A;
	NXTRegulatedMotor rightMotor = Motor.B;
	/* END TEST */
	

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
	

}
