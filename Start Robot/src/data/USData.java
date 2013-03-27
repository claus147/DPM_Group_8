package data;

//<<<<<<< HEAD
//=======
import lejos.nxt.LCD;
//>>>>>>> update
import lejos.nxt.SensorPort; 
import lejos.nxt.Sound;
import lejos.nxt.UltrasonicSensor;
import lejos.util.Timer;
import lejos.util.TimerListener;


/**
 * Will take the data from the Ultrasonic Sensor
 * @author Tuan-Kiet Luu
 * @author Kornpat Choy (Claus)
 * @author Yu Yang Liu
 * @version 1.1
 */


//<<<<<<< HEAD
public class USData {//implements TimerListener{

	
	
	public static SensorPort usPortA = SensorPort.S2;
	public static SensorPort usPortB = SensorPort.S3;
	public static UltrasonicSensor us1 = new UltrasonicSensor(usPortA);
	public static UltrasonicSensor us2 = new UltrasonicSensor(usPortB);
	
	
	public static int getUS1Data (){
		return us1.getDistance();
	}
	
	public static int getFilteredUS1Data(){
		int distance;
		int filtered = 0;
		int filterControl = 0;
		int FILTER_OUT = 20;
	    // there will be a delay here
		distance = us1.getDistance();
		
		// rudimentary filter
		if (distance == 255 && filterControl < FILTER_OUT) {
			// bad value, do not set the distance var, however do increment the filter value
			filterControl ++;
		} else if (distance == 255){
			// true 255, therefore set distance to 255
			filtered = distance;
		} else {
			// distance went below 255, therefore reset everything.
			filterControl = 0;
			filtered = distance;
		}
		
		return filtered;
	}
	
	public static int getFilteredUS2Data(){
//=======
public class USData extends Thread{//implements TimerListener{

	
	public static int us1Data = 0;
	public static int us2Data = 0;
	public static UltrasonicSensor us1 = new UltrasonicSensor(SensorPort.S2);
	public static UltrasonicSensor us2  = new UltrasonicSensor(SensorPort.S3);
	public USData(){
		
	}
	
	public void run(){
		while(true){
			us1Data = us1.getDistance();
			us2Data = us2.getDistance();
			try {
 			Thread.sleep(50);
			} catch (InterruptedException e) {
 			
			}
			LCD.drawString(""+us1Data, 0, 8);
		}
		
	}
	
	
	public static int getFilteredUS1Data(){
//>>>>>>> update
		int distance;
		int filtered = 0;
		int filterControl = 0;
		int FILTER_OUT = 20;
	    // there will be a delay here
//<<<<<<< HEAD
		distance = us2.getDistance();
		
		// rudimentary filter
		if (distance == 255 && filterControl < FILTER_OUT) {
			// bad value, do not set the distance var, however do increment the filter value
			filterControl ++;
		} else if (distance == 255){
			// true 255, therefore set distance to 255
			filtered = distance;
		} else {
			// distance went below 255, therefore reset everything.
			filterControl = 0;
			filtered = distance;
		}
		
		return filtered;
	}

	
	public static int getUS2Data (){
		return us2.getDistance();
	}
	
//=======
		distance = us1.getDistance();
		
		// rudimentary filter
		if (distance == 255 && filterControl < FILTER_OUT) {
			// bad value, do not set the distance var, however do increment the filter value
			filterControl ++;
		} else if (distance == 255){
			// true 255, therefore set distance to 255
			filtered = distance;
		} else {
			// distance went below 255, therefore reset everything.
			filterControl = 0;
			filtered = distance;
		}
		
		return filtered;
	}
	
	public static int getFilteredUS2Data(){
		int distance;
		int filtered = 0;
		int filterControl = 0;
		int FILTER_OUT = 20;
	    // there will be a delay here
		distance = us2.getDistance();
		
		// rudimentary filter
		if (distance == 255 && filterControl < FILTER_OUT) {
			// bad value, do not set the distance var, however do increment the filter value
			filterControl ++;
		} else if (distance == 255){
			// true 255, therefore set distance to 255
			filtered = distance;
		} else {
			// distance went below 255, therefore reset everything.
			filterControl = 0;
			filtered = distance;
		}
		
		return filtered;
	}

	public int getUS1Data (){
		return us1.getDistance();
	}
	public int getUS2Data (){
		return us2.getDistance();
	}
	
//>>>>>>> update
	
	
	
//	
//	private int noWall = 80;
//	private Timer timer;
//	private int sleepTime = 50; //optimal sleepTime
//	private boolean isWall = false;
//	private int usData;
//	
//	/**
//	 * constructor
//	 * default ultrasonic sensor port w/ default 50 sleep time (optimal)
//	 */
//	public USData(){	
//		this.timer = new Timer(sleepTime,this);
//	}
//	
//	
//	/**
//	 * constructor
//	 * pass in US sensor w/ default 50 sleep time (optimal)
//	 * @param us - the light sensor to use
//	 */
//	public USData(UltrasonicSensor us){	
//		this.us = us;
//		this.timer = new Timer(sleepTime,this);
//	}
//	
//	/**
//	 * constructor
//	 * pass in US sensor & user defined sleep time
//	 * @param us - the light sensor to use
//	 * @param sleepTime - can choose the sleep time
//	 */
//	public USData(UltrasonicSensor us, int sleepTime){	
//		this.us =us;
//		this.sleepTime = sleepTime;
//		this.timer = new Timer(this.sleepTime,this);
//	}
//	
//	public void timedOut() {
//		setIsWall(false);
//		if(getUSData(usData)<=noWall){
//			setIsWall(true);
//			Sound.twoBeeps();
//		}
//	}
//	
//	
//	/**
//	 * This method will take the distance using the ultrasonic Sensor
//	 * @return usData
//	 */
//	public double getUSData(int usData){
//		// do a ping
//		// us.ping();
//		usData = us.getDistance();
//		
//		//TODO : CHANGE THIS CONDITION
//		 if (usData > noWall){				//if distance > 80 (noWall) set it to 80
//			 usData = noWall;
//		 }
//		return usData;
//	}
//	
//	/**
//	 * starts the timer (default already started)
//	 */
//	public void start(){
//		timer.start();
//		setIsWall(false);
//	}
//	
//	/**
//	 * stop the timer
//	 */
//	public void stop(){
//		timer.stop();
//	}
//
//	/**
//	 * set true or false (if there is a wall)
//	 * @param isLine
//	 */
//	public void setIsWall(boolean isWall){
//		this.isWall = isWall;
//	}
//	
//	/**
//	 * 
//	 * @return true if wall detected, false otherwise
//	 */
//	public boolean getIsWall(){
//		return isWall;
//	}



	

}
