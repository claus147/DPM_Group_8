package localization;

/**
 *  @author Kornpat Choy (Claus)
 *	@author Yu Yang Liu
 *	turns only, does not travel to (0,0)
 *	orients correctly to angle 0
 */

import java.util.Arrays; 

import data.USData;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;
import lejos.nxt.UltrasonicSensor;
import modes.Controller;
import motion.Navigation;
import odometry.Odometry;

<<<<<<< HEAD
public class USLocalizer {
=======
public class USLocalizer extends Thread{
>>>>>>> update
	
	
	
	
//	public enum LocalizationType { FALLING_EDGE, RISING_EDGE };
	public static double ROTATION_SPEED = 30;

<<<<<<< HEAD
	Odometry odo;
	Controller control;
	
	static double angleA = 0, angleB = 0;
	
	int filterControl = 0;
	int FILTER_OUT = 20;
	
	public USLocalizer(Odometry odom, Controller cont){
		
		this.odo = odom;
		this.control = cont;
=======
	Odometry odo = new Odometry();
	Controller control = new Controller(odo);
	
	static double angleA = 0, angleB = 0;
	
	int filterControl = 0;
	int FILTER_OUT = 20;
	
	USData USData;
	
//	public UltrasonicSensor us1 = new UltrasonicSensor(SensorPort.S2);
//	public UltrasonicSensor us2  = new UltrasonicSensor(SensorPort.S3);
//	
//	
//	public int getUS1Data (){
//		return us1.getDistance();
//	}
//	public int getUS2Data (){
//		return us2.getDistance();
//	}
	
	
	public USLocalizer(Odometry odom, Controller cont, USData usD){
		
		this.odo = odom;
		this.control = cont;
		this.USData = usD;
>>>>>>> update
	}

//	public USLocalizer(Odometer odo, UltrasonicSensor us, LocalizationType locType) {
//		this.odo = odo;
//		
//		this.us = us;
//		this.locType = locType;
//		
//		// switch off the ultrasonic sensor
//		us.off();
//	}
	
<<<<<<< HEAD
	public void doLocalization() {
		
		
		
		double detect = 50;
		double margin = 10;
=======
	public void run() {
		
		
		
		double detect = 20;
		double margin = 5;
>>>>>>> update
		
		control.turnClockWise();
		
		
//		if (locType == LocalizationType.FALLING_EDGE) {
			
			
		boolean wall = true;
		while(true){
<<<<<<< HEAD
				
				
			if(USData.getFilteredUS1Data() > (detect + margin)){
					wall = false;
			}
			if(!wall && USData.getFilteredUS2Data() < (detect - margin)){
=======
			
//			if(USData.getUS1Data() != 255 ){
//				Sound.beep();
//			}
			if(USData.us1Data > 100 && USData.us2Data > 100){
					wall = false;
					
			}
			if(!wall && USData.us1Data < (detect - margin)){
>>>>>>> update
					wall = true;
					angleA = odo.getTheta();
					control.motorsStop();
					Sound.beep();
					

<<<<<<< HEAD
=======
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {	}
>>>>>>> update
					
					control.turnCounterClockWise();
					
					
					while(true){
<<<<<<< HEAD
						if(USData.getFilteredUS2Data() > detect + margin){
							wall = false;
							
						}
						if(!wall && USData.getFilteredUS1Data() < detect - margin){
=======
						if(USData.us2Data > 100 && USData.us1Data > 100){
							wall = false;
							
						}
						if(!wall && USData.us2Data < detect - margin){
>>>>>>> update
							wall = true;
							Sound.beep();
							angleB = odo.getTheta();
							
							control.motorsStop();
<<<<<<< HEAD
							Motor.A.stop();
							Motor.B.stop();
=======
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {	}
>>>>>>> update
							
							break;
						}
					}
					
					
					
				}
				if(angleA != 0 && angleB != 0){
					break;
				}
			}
			double trueAng = 0;
			if(angleA > angleB){
				trueAng = odo.getTheta() + (225.0 - (angleA + angleB) / 2.0) + 180;
			}else{
				trueAng = odo.getTheta() + (45.0 - (angleA + angleB) / 2.0) + 180;
			}
			
//			double[] position = new double[3];
//			boolean[] upd = {false, false, true};
//			position[2] = trueAng;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {	}
			
			//odo.setPosition(position , upd);
			odo.setTheta(trueAng);
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {	}
			
			control.turnTo(0);
			
		
		}
	}