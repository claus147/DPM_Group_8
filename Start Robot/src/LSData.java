import lejos.nxt.*;

/**
 * Will take data from the Lightsensor
 * @author Tuan-Kiet Luu
 * @version 1.0
 */


public class LSData {
	double lsValue;
	private LightSensor ls;
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
public double getLSData(){
	lsValue = ls.readValue();
	return lsValue;
}
	
	
	
	
	
	
}
