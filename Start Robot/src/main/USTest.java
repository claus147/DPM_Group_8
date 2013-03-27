package main;

import lejos.util.Timer;
import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;
import lejos.nxt.UltrasonicSensor;
import data.LSData;
import data.USData;
import motion.Navigation;
import odometry.Odometry;

/*
 * Test class for lsData
 * @author Kornpat Choy (Claus)
 * @version 1.0
 */

public class USTest{
	public static void main (String args[]) {
		UltrasonicSensor usL = new UltrasonicSensor(SensorPort.S2);
		UltrasonicSensor usR = new UltrasonicSensor(SensorPort.S3);
	
//		while(true){
//			usL.ping();
//			if (usL.getDistance()<80){
//				Sound.buzz();
//			}
//		}
		
		Odometry odo = new Odometry(true);
		
		Navigation nav = new Navigation(odo);
		
		USData usDataL = new USData(usL);
		USData usDataR = new USData(usR);
		
		//nav.goforward(100);
		boolean usLState = false;
		boolean usRState = false;
		
		usDataL.start();
		usDataR.start();
		while(true){}
		
//		while(!usLState || !usRState){
//			usLState = usDataL.getIsWall();
//			if (usLState) {
//				//nav.stop(Navigation.WheelSide.LEFT);
//				//usDataL.stop();
//			}
//			
//			usRState = usDataR.getIsWall();
//			if (usRState){
//				//nav.stop(Navigation.WheelSide.RIGHT);
//				//usDataR.stop();
//			}
//		}
		
		//nav.goforward(100);
	}
}