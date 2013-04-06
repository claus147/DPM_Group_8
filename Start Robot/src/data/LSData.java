package data;


/**
 * Will take data from the Lightsensor
 * @author Kornpat Choy (Claus)
 * @author Tuan-Kiet Luu
 * @version 2.0
 */

import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;
import lejos.util.TimerListener;
import lejos.util.Timer;

public class LSData implements TimerListener{
	private double lsValue;
	private LightSensor ls = new LightSensor(SensorPort.S1); 	//default port	
	private double [] lReadings = new double[5]; 				//5 readings stored at each instance
	private int average = 0;
	private int prevAvg;
	private int counter = 0; 									//for the array, so it doesnt do null pointer (wrap around)
	private int threshold;										//threshold level for line detection - tolerance
	private Timer timer;										//timer for timedOut()
	private int sleepTime = 10; 								//50 millisecond is optimal time for ls reading sleep
	private boolean isLine = false;								//boolean for line detection
	private double percentage;
	
	/**
	 * constructor
	 * default light sensor port w/ default 50 sleep time (optimal)
	 */
	public LSData(){	
		this.timer = new Timer(sleepTime,this);
		initialise();
	}
	
	
	/**
	 * constructor
	 * pass in light sensor w/ default 50 sleep time (optimal)
	 * @param ls - the light sensor to use
	 */
	public LSData(LightSensor ls){	
		this.ls =ls;
		this.timer = new Timer(sleepTime,this);
		initialise();
	}
	
	/**
	 * constructor
	 * pass in light sensor w/ default 50 sleep time (optimal) and determined percentage
	 * @param ls - the light sensor to use
	 * @param percentage - the threshold for light
	 */
	public LSData(LightSensor ls, double percentage){	
		this.ls =ls;
		this.timer = new Timer(sleepTime,this);
		this.percentage = percentage;
		initialise();
	}
	
	/**
	 * constructor
	 * pass in light sensor & user defined sleep time
	 * @param ls - the light sensor to use
	 * @param sleepTime - can choose the sleep time
	 */
	public LSData(LightSensor ls, int sleepTime){	
		this.ls =ls;
		this.sleepTime = sleepTime;
		this.timer = new Timer(this.sleepTime,this);
		initialise();
	}

	/**
	 * method to be repeated, gets the light readings once every (sleepTime - constant/or user chosen)
	 * then sets isLine to true if sees line.
	 */
	public void timedOut() {
	
		setIsLine(false);			//assume after every run of the method, there is no line.
		
		if (counter == 5) 			//always wrap around to get back into the right pos in array
			counter = 0;
		lReadings[counter] = getLSData();		//place reading in array
		
		prevAvg = average;
		average = 0; 							//reset the average
		
		getAverage();
		
		counter++;								//increment to get to the next array location
		threshold = (int) (average * percentage); 	//5% threshold
		
		if((prevAvg - average) > threshold  ){
			setIsLine(true);
			Sound.beepSequence();				//perform beep						
		}
	}
	
	/**
	 * Initialise the array of averages and get the average
	 */
	private void initialise(){
		for (int i = 0; i < 5; i++){ //reading 5 times, once every second - initial array
			lReadings[i] = getLSData();
			try { Thread.sleep(250); } catch (InterruptedException e) {} //sleep 1/4 of a sec
		}
		
		getAverage();
	}
	
	/**
	 * does the method for the average readings in the array at any instance.
	 */
	private void getAverage(){
		for (int i = 0; i < 5; i++){
			average = average + (int) lReadings[i]; 	//adding up the values in array
		}
		average = average/5;				//getting actual average
	}
	
	/**
	 * starts the timer
	 */
	public void start(){
		timer.start();
		setIsLine(false);
	}
	
	/**
	 * stop the timer
	 */
	public void stop(){
		timer.stop();
	}
	
	/**
	 * Will read the values of the light sensor
	 * @return lsValue
	 */
	public double getLSData(){
		lsValue = ls.getNormalizedLightValue();
		return lsValue;
	}
	
	/**
	 * set true or false (if there is a line)
	 * @param isLine
	 */
	public void setIsLine(boolean isLine){
		this.isLine = isLine;
	}
	
	/**
	 * 
	 * @return true if line detected, false otherwise
	 */
	public boolean getIsLine(){
		return isLine;
	}

	
}
