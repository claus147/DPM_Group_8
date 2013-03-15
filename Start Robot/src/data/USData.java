package data;
import lejos.nxt.*;


/**
 * Will take the data from the Ultrasonic Sensor
 * @author Tuan-Kiet Luu
 * @version 1.0
 */


public class USData {

	private UltrasonicSensor us;
	double usValue;
	
	

	
	public double getLSData(){
		usValue = us.getDistance();
		return usValue;
	}
	

}
