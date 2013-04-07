package main;

import connection.BTConnectTest;
import connection.BTReceive;
import connection.BluetoothConnection;
import connection.PlayerRole;
import connection.StartCorner;
import odometry.Odometry;

import modes.AttackerMode;
import modes.DefenderMode;
import motion.Navigation;
import motion.SquareNavigation;
import connection.Transmission;
import data.LCDinfo;
import data.USData;

import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import localization.LightLocalizer;
import localization.USLocalizer;

/**
 * The Class StartRobot.
 * 
 * @author Tuan-Kiet 
 * @author Kornpat Choy (Claus)
 * 		   This class will be the main one that calls the other
 *         classes to start the robot It will take as input the coordinates of
 *         the ball dispenser. This class will also call what mode the robot
 *         will play the game; Attacker or Defender. We used the menu from Lab04 and modified it.
 */
public class StartRobot {

	//static BluetoothConnection compConnect = new BluetoothConnection();	*****Used!!!*
	
	/*
	 * Main function will ask the user to choose between attacker (left button) or defender mode (right button).
	 * It will call AttackerMode and DefenderMode class.
	 */
	
	
	
	public static void main(String args[]){

		
		//compConnect.printTransmission(); ****USED****

		Start();
	
		
	}
	
	/**
	 * Will start the robot
	 */
	public static void Start(){
		
		/**
		 * the bt values
		 */
//		BluetoothConnection compConnect = new BluetoothConnection(); //will wait for btooth
//		Transmission t = compConnect.getTransmission();
		
		
		
		Odometry odo = new Odometry(true);		//odo has started
		Navigation navigate = new Navigation(odo);
		LCDinfo lcd = new LCDinfo(odo);			//the display
		lcd.start(); //start display
		
		//init sensors
		LightSensor lsl = new LightSensor(SensorPort.S1);
		LightSensor lsr = new LightSensor(SensorPort.S4);
		UltrasonicSensor usl = new UltrasonicSensor(SensorPort.S2);
		UltrasonicSensor usr = new UltrasonicSensor(SensorPort.S3);
		
		//init the data collection
		USData usDataL = new USData(usl , 45, 20);
		USData usDataR = new USData(usr , 45, 20);
		
		//init navigation
		SquareNavigation sqrNav = new SquareNavigation(odo, lsl, lsr, usDataL, usDataR);
		
		//init loc
		USLocalizer usloc = new USLocalizer(odo, navigate, usl, usr);
		LightLocalizer lsloc = new LightLocalizer(odo, navigate, lsl, lsr, StartCorner.BOTTOM_LEFT);
		//LightLocalizer lsloc = new LightLocalizer(odo, navigate, lsl, lsr, t.startingCorner);	//--NEEDED
		
		//init modes
		AttackerMode attack = new AttackerMode(2,11, 0, 0, 0, sqrNav);
		DefenderMode defend = new DefenderMode(0, 0, 0, sqrNav);
		
//		AttackerMode attack = new AttackerMode(t.bx, t.by, t.w1, t.w2, t.d1, sqrNav); //--NEEDED for bt
//		DefenderMode defend = new DefenderMode(t.w1, t.w2, t.d1, sqrNav);				//--NEEDED for bt
		
		//do the localizations
		usloc.doLocalization(); //******************************* LOCALIZATIONS!!!!!!!!!!!!!!!**********************************
		lsloc.doLocalization();
		
		//chosing which role to do - attack/defense
	//	if (t.role == PlayerRole.ATTACKER) --NEEDED
			attack.attackAlgorithm();		//do the attack
		//else
	//		defend.defenseAlgorithm();		//do the defense __NEEDED
		
		
		//BTConnectTest connect = new BTConnectTest();
		//BTReceive connectReceive = new BTReceive();
	
		
		
//		//Odometry odo = new Odometry(true);
//		
//		
//		WheelsData wheels = new WheelsData();
//		
//		UltrasonicSensor us = new UltrasonicSensor(SensorPort.S1);
//		LightSensor lsL = new LightSensor(SensorPort.S2);
//		LightSensor lsR = new LightSensor(SensorPort.S3);
//		
//		//Controller control = new Controller(odo);
//		//LCDinfo LCDinfo = new LCDinfo(odo);
//		int buttonChoice;
//		
//		if(compConnect.isAttacker()){
//			attack.attackAlgorithm();
//		}else{
//			defend.defenseAlgorithm();
//		}
//			**USED!!!!!**
//	
//		buttonChoice = Button.ID_LEFT;
//
//		do {
//			// clear the display
//			LCD.clear();
//
//			// left : AttackerMode
//			// right : DefenderMode
//			LCD.drawString("<Atker  | Def>", 0, 0);
//			LCD.drawString("        |         	 ", 0, 1);
//			LCD.drawString(" Mode   | Mode"			, 0, 2);
//			LCD.drawString(" edge   |  "			, 0, 3);
//		
//
//		//	buttonChoice = Button.waitForAnyPress();
//		} while (buttonChoice != Button.ID_LEFT
//				&& buttonChoice != Button.ID_RIGHT);
//		
//		//Attacker Mode
//		if (buttonChoice == Button.ID_LEFT) {
//
//			try { Thread.sleep(10000); } catch (InterruptedException e) {} //********TAKE OFF 
//
//			Sound.beep();	
//			//LCDinfo LCDinfo = new LCDinfo(odo);
//			attack.attackAlgorithm();
//		}
//		// Defender Mode
//		else{
//		
		
	//	compConnect.printTransmission();
//			
//		// Testing the Launcher via bluetooth;
//		BTConnectTest connect = new BTConnectTest();
//		try {
//			connect.connect();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		
//		// turnTo works!
//		// navigate.turnTo(180);
//		
//
//			
//		}
//			
//		
//		Button.waitForAnyPress();
//		
//			
	}
}

/* START --------------------------------------------------------------------------------------------- TUAN TESTING BELOW */
//
//package main;
//
//import connection.BTConnectTest;
//import connection.BTReceive;
//import odometry.Odometry;
//import data.*;
//import modes.*;
//import motion.Navigation;
//
//import lejos.nxt.*;
//
///**
// * The Class StartRobot.
// * 
// * @author Tuan-Kiet 
// * 		   This class will be the main one that calls the other
// *         classes to start the robot It will take as input the coordinates of
// *         the ball dispenser. This class will also call what mode the robot
// *         will play the game; Attacker or Defender. We used the menu from Lab04 and modified it.
// */
//public class StartRobot {
//
//
//	/*
//	 * Main function will ask the user to choose between attacker (left button) or defender mode (right button).
//	 * It will call AttackerMode and DefenderMode class.
//	 */
//	public static void main(String args[]){
//
//		Start();
//
//
//	}
//
//	/**
//	 * Will start the robot
//	 */
//	public static void Start(){
//
//		//BTConnectTest connect = new BTConnectTest();
//
//		BTReceive connectReceive = new BTReceive();
//
//		AttackerMode attack = new AttackerMode(0, 0, 0, 0, 0, null);
//		DefenderMode defend = new DefenderMode(0, 0, 0, null);
//		Odometry odo = new Odometry(true);
//		Navigation navigate = new Navigation(odo);
//		USData usData = new USData();
//		WheelsData wheels = new WheelsData();
//
//		UltrasonicSensor us = new UltrasonicSensor(SensorPort.S1);
//		LightSensor lsL = new LightSensor(SensorPort.S2);
//		LightSensor lsR = new LightSensor(SensorPort.S3);
//
//		Controller control = new Controller(odo);
//		//BTReceive connectReceive = new BTReceive();
//
////		AttackerMode attack = new AttackerMode();
////		DefenderMode defend = new DefenderMode();
////		//Odometry odo = new Odometry(true);
////		
////		
////		WheelsData wheels = new WheelsData();
////		
////		UltrasonicSensor us = new UltrasonicSensor(SensorPort.S1);
////		LightSensor lsL = new LightSensor(SensorPort.S2);
////		LightSensor lsR = new LightSensor(SensorPort.S3);
//
//		//Controller control = new Controller(odo);
//
//		//LCDinfo LCDinfo = new LCDinfo(odo);
//
//
//		int buttonChoice;
//
//		do {
//			// clear the display
//			LCD.clear();
//
//			// left : AttackerMode
//			// right : DefenderMode
//			LCD.drawString("<Atker  | Def>", 0, 0);
//			LCD.drawString("        |         	 ", 0, 1);
//			LCD.drawString(" Mode   | Mode"			, 0, 2);
//			LCD.drawString(" edge   |  "			, 0, 3);
//
//
//			buttonChoice = Button.waitForAnyPress();
//		} while (buttonChoice != Button.ID_LEFT
//				&& buttonChoice != Button.ID_RIGHT);
//
//		//Attacker Mode
//		if (buttonChoice == Button.ID_LEFT) {
//
//		Sound.beep();	
//		//LCDinfo LCDinfo = new LCDinfo(odo);
//		attack.attackAlgorithm();
//
//			try { Thread.sleep(10000); } catch (InterruptedException e) {} //********TAKE OFF 
//
//			Sound.beep();	
//			//LCDinfo LCDinfo = new LCDinfo(odo);
//			attack.attackAlgorithm();
//
//		}
//		// Defender Mode
//		else{
//
//
//		// Testing the Launcher via bluetooth;
//		BTConnectTest connect = new BTConnectTest();
//		try {
//			connect.connect('b');
//			Sound.beep();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		// SLEEPS THE MASTER WHILE IT IS SHOOTING
//		try { Thread.sleep(20000); } catch (InterruptedException e) {}
//		control.turn(45);
//		
//		try {
//			connect.connect('a');
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		}
//
//
//		Button.waitForAnyPress();
//
//
//	}
//}

/* END --------------------------------------------------------------------------------------------- TUAN TESTING BELOW */