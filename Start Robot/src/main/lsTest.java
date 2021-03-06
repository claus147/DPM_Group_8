package main;

import lejos.util.Timer;
import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import data.LSData;
import motion.Navigation;
import odometry.Odometry;

/*
 * Test class for lsData
 * @author Kornpat Choy (Claus)
 * @version 1.0
 */

public class lsTest{
	public static void main (String args[]) {
		LightSensor lsL = new LightSensor(SensorPort.S1);
		LightSensor lsR = new LightSensor(SensorPort.S4);
	
		Odometry odo = new Odometry(true);
		
		Navigation nav = new Navigation(odo);
		
		LSData lsDataL = new LSData(lsL,0.05);
		LSData lsDataR = new LSData(lsR,0.05);
		
		
		boolean lsLState = false;
		boolean lsRState = false;
		
		lsDataL.start();
		lsDataR.start();
		
		nav.goforward(100);
		
		while(true){}
		
//		while(!lsLState || !lsRState){
//			lsLState = lsDataL.getIsLine();
//			if (lsLState) {
//				nav.stop(Navigation.WheelSide.LEFT);
//				lsDataL.stop();
//			}
//			
//			lsRState = lsDataR.getIsLine();
//			if (lsRState){
//				nav.stop(Navigation.WheelSide.RIGHT);
//				lsDataR.stop();
//			}
//		}
//		
//		nav.goforward(100);
	}
}