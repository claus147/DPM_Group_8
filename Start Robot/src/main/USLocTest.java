package main;

import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import motion.Navigation;
import localization.USLocalizer;
import odometry.Odometry;
import data.LCDinfo;

/*
 * Test class for usloc
 * @author Kornpat Choy (Claus)
 * @version 1.0
 */

public class USLocTest{
	public static void main (String args[]) {
		UltrasonicSensor usL = new UltrasonicSensor(SensorPort.S1);
		UltrasonicSensor usR = new UltrasonicSensor(SensorPort.S2);
	
		Odometry odo = new Odometry();
		
		Navigation nav = new Navigation(odo);
		
		LCDinfo info = new LCDinfo(odo);
		
		USLocalizer usLoc = new USLocalizer(odo, usL, usR, USLocalizer.LocalizationType.RISING_EDGE);
		usLoc.doLocalization();
	}
}