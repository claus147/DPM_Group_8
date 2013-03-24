package data;

import lejos.nxt.SensorPort;
import lejos.nxt.Sound;
import lejos.nxt.UltrasonicSensor;
import lejos.util.Timer;
import lejos.util.TimerListener;


/**
 * Will take the data from the Ultrasonic Sensor
 * @author Tuan-Kiet Luu
 * @author Kornpat Choy (Claus)
 * @version 1.1
 */


public class USData implements TimerListener{

	private UltrasonicSensor us = new UltrasonicSensor(SensorPort.S1);
	private int noWall = 80;
	private Timer timer;
	private int sleepTime = 50; //optimal sleepTime
	private boolean isWall = false;
	private int usData;
	
	/**
	 * constructor
	 * default ultrasonic sensor port w/ default 50 sleep time (optimal)
	 */
	public USData(){	
		this.timer = new Timer(sleepTime,this);
	}
	
	
	/**
	 * constructor
	 * pass in US sensor w/ default 50 sleep time (optimal)
	 * @param us - the light sensor to use
	 */
	public USData(UltrasonicSensor us){	
		this.us = us;
		this.timer = new Timer(sleepTime,this);
	}
	
	/**
	 * constructor
	 * pass in US sensor & user defined sleep time
	 * @param us - the light sensor to use
	 * @param sleepTime - can choose the sleep time
	 */
	public USData(UltrasonicSensor us, int sleepTime){	
		this.us =us;
		this.sleepTime = sleepTime;
		this.timer = new Timer(this.sleepTime,this);
	}
	
	public void timedOut() {
		setIsWall(false);
		if(getUSData(usData)<=noWall){
			setIsWall(true);
			Sound.twoBeeps();
		}
	}
	
	
	/**
	 * This method will take the distance using the ultrasonic Sensor
	 * @return usData
	 */
	private double getUSData(int usData){
		// do a ping
		// us.ping();
		usData = us.getDistance();
		
		//TODO : CHANGE THIS CONDITION
		 if (usData > noWall){				//if distance > 80 (noWall) set it to 80
			 usData = noWall;
		 }
		return usData;
	}
	
	/**
	 * starts the timer (default already started)
	 */
	public void start(){
		timer.start();
		setIsWall(false);
	}
	
	/**
	 * stop the timer
	 */
	public void stop(){
		timer.stop();
	}

	/**
	 * set true or false (if there is a wall)
	 * @param isLine
	 */
	public void setIsWall(boolean isWall){
		this.isWall = isWall;
	}
	
	/**
	 * 
	 * @return true if wall detected, false otherwise
	 */
	public boolean getIsWall(){
		return isWall;
	}



	

}
