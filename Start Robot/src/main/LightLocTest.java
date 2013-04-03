package main;

import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;
import modes.Controller;
import motion.Navigation;
import localization.LightLocalizer;
import odometry.Odometry;
import data.LCDinfo;
import connection.BluetoothConnection;
import connection.StartCorner;

/*
 * Test class for lsData
 * @author Kornpat Choy (Claus)
 * @version 1.0
 */

public class LightLocTest{
	public static void main (String args[]) {
		LightSensor lsL = new LightSensor(SensorPort.S1);
		LightSensor lsR = new LightSensor(SensorPort.S4);
		
		Odometry odo = new Odometry(true);
		
		Navigation nav = new Navigation(odo);
		
		//BluetoothConnection bc = new BluetoothConnection();
		//Sound.buzz();
		Controller con = new Controller(odo);
		LCDinfo info = new LCDinfo(odo);
		info.start();
		
		StartCorner sc = StartCorner.BOTTOM_LEFT;
		
		LightLocalizer lightLoc = new LightLocalizer(odo, nav, lsL, lsR, sc); //bc.getTransmission().startingCorner
		lightLoc.doLocalization();
		
		while(true){}
	}
}