package main;

import motion.Navigation;
import odometry.Odometry;
import data.LCDinfo;


/*
 * Test class for lsData
 * @author Kornpat Choy (Claus)
 * @version 1.0
 */

public class NavTest{
	public static void main (String args[]) {
	
		Odometry odo = new Odometry();
		
		Navigation nav = new Navigation(odo);
		
		LCDinfo info = new LCDinfo(odo);
		
		nav.goforward(100);
		nav.turnTo(100);
	}
}