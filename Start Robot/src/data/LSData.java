package data;
import lejos.nxt.*;

/**
 * Will take data from the Lightsensor
 * @author Tuan-Kiet Luu
 * @version 1.0
 */


public class LSData {
	double lsValueL;
	double lsValueR;
	LightSensor lsL = new LightSensor(SensorPort.S2);
	LightSensor lsR = new LightSensor(SensorPort.S3);
	int data;
	
	/*
	public LSData(){	
		this.data = ls.readValue();
	}
*/

	/**
	 * Will read the values of the light sensor
	 * @return lsValue
	 */
public double getLSDataL(){
	lsValueL = lsL.readValue();
	try { Thread.sleep(100); } catch (InterruptedException e) {}

	return lsValueL;
}
	
public double getLSDataR(){
	lsValueR = lsR.readValue();
	try { Thread.sleep(100); } catch (InterruptedException e) {}

	return lsValueR;
}
	
	
	
	
	
}
