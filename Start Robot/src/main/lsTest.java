package main;

import lejos.util.Timer;
import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import data.LSData;
import motion.Navigation;

/*
 * Test class for lsData
 * @author Kornpat Choy (Claus)
 * @version 1.0
 */

public class lsTest{
	public static void main (String args[]) {
		LightSensor lsL = new LightSensor(SensorPort.S1);
		LightSensor lsR = new LightSensor(SensorPort.S2);
	
		Navigation nav = new Navigation();
		
		LSData lsDataL = new LSData(lsL);
		LSData lsDataR = new LSData(lsR);
		
		nav.goforward(100);
		boolean lsLState = false;
		boolean lsRState = false;
		
		while(true){
			lsLState = lsDataL.getIsLine();
			if (lsLState) {
				nav.stop(Navigation.WheelSide.LEFT);
				lsDataL.stop();
			}
			
			lsRState = lsDataR.getIsLine();
			if (lsRState){
				nav.stop(Navigation.WheelSide.RIGHT);
				lsDataR.stop();
			}
		}
	}
}