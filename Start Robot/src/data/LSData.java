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
	 * Will read the values of the Left light sensor
	 * @return lsValueL
	 */
public double getLSDataL(){
	lsValueL = lsL.getNormalizedLightValue();
	try { Thread.sleep(50); } catch (InterruptedException e) {}

	return lsValueL;
	}
	/**
	 * Will read the values of the Right light sensor
	 * @return lsValueR
	 */
	public double getLSDataR(){
		lsValueR = lsR.getNormalizedLightValue();
		try { Thread.sleep(50); } catch (InterruptedException e) {}
	
		return lsValueR;
	}
		
	
	
	
	
}
