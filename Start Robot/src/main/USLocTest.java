package main;

import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import modes.Controller;
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
		UltrasonicSensor usL = new UltrasonicSensor(SensorPort.S3);
		UltrasonicSensor usR = new UltrasonicSensor(SensorPort.S2);
	
		Odometry odo = new Odometry(true);
		
		Navigation nav = new Navigation(odo);
		
		Controller con = new Controller(odo);
		LCDinfo info = new LCDinfo(odo);
		info.start();
		
		USLocalizer usLoc = new USLocalizer(odo, nav, usL, usR, USLocalizer.LocalizationType.RISING_EDGE);
		usLoc.doLocalization();
	}
}