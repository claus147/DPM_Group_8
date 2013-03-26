package main;

import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;
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
		
		Odometry odo = new Odometry();
		
		Navigation nav = new Navigation(odo);
		
		//BluetoothConnection bc = new BluetoothConnection();
		//Sound.buzz();
		
		LCDinfo info = new LCDinfo(odo);
		
		StartCorner sc = StartCorner.TOP_RIGHT;
		
		LightLocalizer lightLoc = new LightLocalizer(odo, nav, lsL, lsR, sc); //bc.getTransmission().startingCorner
		lightLoc.doLocalization();
		
		while(true){}
	}
}