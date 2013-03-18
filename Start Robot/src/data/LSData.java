package data;
import lejos.nxt.*;

/**
 * Will take data from the Lightsensor
 * @author Tuan-Kiet Luu
 * @author Kornpat Choy (Claus)
 * @version 1.1
 */

import lejos.util.TimerListener;

public class LSData implements TimerListener{
	private double lsValue;
	private LightSensor ls = new LightSensor(SensorPort.S1); //default port	
	private double [] lReadings = new double[5]; //5 readings stored at each instance
	private int average = 0;
	private int counter = 0;
	private int prevAvg;
	private int threshold;
	private boolean isLine = false;
	
	
	public LSData(LightSensor ls){	
		this.ls =ls;
	}


	public void timedOut() {
	
		if (counter == 5) //always wrap around
			counter = 0;
		lReadings[counter] = getLSData();
		
		prevAvg = average;
		average = 0; //reset the average
		for (int i = 0; i < 5; i++){
			average = average + (int) lReadings[i]; //adding up the values in array
		}
		average = average/5;		//getting actual average
		counter++;
			
		threshold = (int) (average * 0.01); //1% threshold
		
		if((prevAvg - average) > threshold  ){
			setIsLine(true);
			Sound.beepSequence();				//perform beep						
		}
	}
	
	public void initialise(){
		for (int i = 0; i < 5; i++){ //reading 5 times, once every second - initial array
			lReadings[i] = getLSData();
			try { Thread.sleep(200); } catch (InterruptedException e) {} //sleep 1/4 of a sec
		}
		
		//get the average
		for (int i = 0; i < 5; i++){
			average = average + (int) lReadings[i]; //adding up the values in array
		}
		average = average/5;
	}
	
	/**
	 * Will read the values of the Left light sensor
	 * @return lsValue
	 */
	public double getLSData(){
		try { Thread.sleep(50); } catch (InterruptedException e) {}
		lsValue = ls.getNormalizedLightValue();
		
		return lsValue;
	}
	
	public void setIsLine(boolean isLine){
		this.isLine = isLine;
	}
	
	public boolean getIsLine(){
		return isLine;
	}

	
}
