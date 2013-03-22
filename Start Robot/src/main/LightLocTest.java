package main;

import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import motion.Navigation;
import localization.LightLocalizer;
import odometry.Odometry;
import data.LCDinfo;

/*
 * Test class for lsData
 * @author Kornpat Choy (Claus)
 * @version 1.0
 */

public class LightLocTest{
	public static void main (String args[]) {
		LightSensor lsL = new LightSensor(SensorPort.S1);
		LightSensor lsR = new LightSensor(SensorPort.S2);
	
		Odometry odo = new Odometry();
		
		Navigation nav = new Navigation();
		
		LCDinfo info = new LCDinfo(odo);
		
		LightLocalizer lightLoc = new LightLocalizer(odo, nav, lsL, lsR);
		lightLoc.doLocalization();
	}
}