//package main;
//
//import connection.BTConnectTest;
//import connection.BTReceive;
//import connection.BluetoothConnection;
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
//	//static BluetoothConnection compConnect = new BluetoothConnection();	*****Used!!!*
//	
//	/*
//	 * Main function will ask the user to choose between attacker (left button) or defender mode (right button).
//	 * It will call AttackerMode and DefenderMode class.
//	 */
//	public static void main(String args[]){
//
//	 
//		/* bluetooth testings + launcher*/
//	//	BTCompLauncherTest();
//		
//	//	BluetoothConnection compConnect = new BluetoothConnection();
//		/* real test below*/
//	//	compConnect.printTransmission();
//		//****USED****
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
//		BTReceive connectReceive = new BTReceive();
//	
//		AttackerMode attack = new AttackerMode();
//		DefenderMode defend = new DefenderMode();
//		Odometry odo = new Odometry(true);
//		
//		
//		WheelsData wheels = new WheelsData();
//		
//		UltrasonicSensor usL = new UltrasonicSensor(SensorPort.S2);
//		UltrasonicSensor usR = new UltrasonicSensor(SensorPort.S3);
//		LightSensor lsL = new LightSensor(SensorPort.S1);
//		LightSensor lsR = new LightSensor(SensorPort.S4);
//		
//		Controller control = new Controller(odo);
//		//LCDinfo LCDinfo = new LCDinfo(odo);
//		int buttonChoice;
//		
//		
//		/**
//		 * MUST CHANGE TO ID_LEFT FOR DEMO
//		 */
////		if(compConnect.isAttacker()){
////			buttonChoice = Button.ID_RIGHT;
////		}else{
////			buttonChoice = Button.ID_LEFT;
////		} 
//		//**USED!!!!!**
//	
//	//	buttonChoice = Button.ID_LEFT;
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
//			try { Thread.sleep(10000); } catch (InterruptedException e) {} //********TAKE OFF 
//
//			//Sound.beep();	
//			//LCDinfo LCDinfo = new LCDinfo(odo);
//			attack.attackAlgorithm();
//
//			
//		}
//		// Defender Mode
//		else{
//			//BluetoothConnection compConnect = new BluetoothConnection();
//			//compConnect.printTransmission();
//			Sound.buzz();
//	   // Testing the Launcher via bluetooth;
//		BTConnectTest connect = new BTConnectTest();
//		try {
//			connect.connect();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//			
//		}
//			
//		
//		Button.waitForAnyPress();
//		
//			
//	}
//	public static void BTCompLauncherTest(){
//	
//		// TESTING THE BLUETOOTH WITHOUT COMPUTER
//		//BluetoothConnection compConnect = new BluetoothConnection();
//		//compConnect.printTransmission();
//		
//		BTReceive connectReceive = new BTReceive();
//			
//				AttackerMode attack = new AttackerMode();
//				DefenderMode defend = new DefenderMode();
//				Odometry odo = new Odometry(true);
//				
//				
//				WheelsData wheels = new WheelsData();
//				
//				UltrasonicSensor us = new UltrasonicSensor(SensorPort.S1);
//				LightSensor lsL = new LightSensor(SensorPort.S2);
//				LightSensor lsR = new LightSensor(SensorPort.S3);
//				
//				Controller control = new Controller(odo);
//				//LCDinfo LCDinfo = new LCDinfo(odo);
//				int buttonChoice;
//				
////				if(compConnect.isAttacker()){
////					buttonChoice = Button.ID_RIGHT;
////				}else{
////					buttonChoice = Button.ID_LEFT;
////				}
//			
//			//	buttonChoice = Button.ID_LEFT;
//
//				do {
//					// clear the display
//					LCD.clear();
//
//					// left : AttackerMode
//					// right : DefenderMode
//					LCD.drawString("<Atker  | Def>", 0, 0);
//					LCD.drawString("        |         	 ", 0, 1);
//					LCD.drawString(" Mode   | Mode"			, 0, 2);
//					LCD.drawString(" edge   |  "			, 0, 3);
//				
//
//					buttonChoice = Button.waitForAnyPress();
//				} while (buttonChoice != Button.ID_LEFT
//						&& buttonChoice != Button.ID_RIGHT);
//				
//				//Attacker Mode
//				if (buttonChoice == Button.ID_LEFT) {
//
//					try { Thread.sleep(10000); } catch (InterruptedException e) {} //********TAKE OFF 
//
//					//Sound.beep();	
//					//LCDinfo LCDinfo = new LCDinfo(odo);
//					attack.attackAlgorithm();
//				}
//				// Defender Mode
//				else{
//				
//					
//					
//				// Testing the Launcher via bluetooth;
//				BTConnectTest connect = new BTConnectTest();
//				try {
//					connect.connect();
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				
//				
//				// turnTo works!
//				// navigate.turnTo(180);
//		
//	}
//		}}
//


package main;

import connection.BTConnectTest;
import connection.BTReceive;
import odometry.Odometry;
import data.*;
import modes.*;
import motion.Navigation;

import lejos.nxt.*;

/**
 * The Class StartRobot.
 * 
 * @author Tuan-Kiet 
 * 		   This class will be the main one that calls the other
 *         classes to start the robot It will take as input the coordinates of
 *         the ball dispenser. This class will also call what mode the robot
 *         will play the game; Attacker or Defender. We used the menu from Lab04 and modified it.
 */
public class StartRobot {


	/*
	 * Main function will ask the user to choose between attacker (left button) or defender mode (right button).
	 * It will call AttackerMode and DefenderMode class.
	 */
	public static void main(String args[]){

		Start();


	}

	/**
	 * Will start the robot
	 */
	public static void Start(){

		//BTConnectTest connect = new BTConnectTest();
<<<<<<< HEAD
		BTReceive connectReceive = new BTReceive();

		AttackerMode attack = new AttackerMode();
		DefenderMode defend = new DefenderMode();
		Odometry odo = new Odometry(true);
		Navigation navigate = new Navigation(odo);
		USData usData = new USData();
		WheelsData wheels = new WheelsData();

		UltrasonicSensor us = new UltrasonicSensor(SensorPort.S1);
		LightSensor lsL = new LightSensor(SensorPort.S2);
		LightSensor lsR = new LightSensor(SensorPort.S3);

		Controller control = new Controller(odo);
=======
		//BTReceive connectReceive = new BTReceive();
	
		AttackerMode attack = new AttackerMode();
//		DefenderMode defend = new DefenderMode();
//		//Odometry odo = new Odometry(true);
//		
//		
//		WheelsData wheels = new WheelsData();
//		
//		UltrasonicSensor us = new UltrasonicSensor(SensorPort.S1);
//		LightSensor lsL = new LightSensor(SensorPort.S2);
//		LightSensor lsR = new LightSensor(SensorPort.S3);
		
		//Controller control = new Controller(odo);
>>>>>>> 1668534108f153c33e2e0e005e965343b40486d1
		//LCDinfo LCDinfo = new LCDinfo(odo);


		int buttonChoice;

		do {
			// clear the display
			LCD.clear();

			// left : AttackerMode
			// right : DefenderMode
			LCD.drawString("<Atker  | Def>", 0, 0);
			LCD.drawString("        |         	 ", 0, 1);
			LCD.drawString(" Mode   | Mode"			, 0, 2);
			LCD.drawString(" edge   |  "			, 0, 3);


			buttonChoice = Button.waitForAnyPress();
		} while (buttonChoice != Button.ID_LEFT
				&& buttonChoice != Button.ID_RIGHT);

		//Attacker Mode
		if (buttonChoice == Button.ID_LEFT) {

<<<<<<< HEAD
		Sound.beep();	
		//LCDinfo LCDinfo = new LCDinfo(odo);
		attack.attackAlgorithm();
=======
			try { Thread.sleep(10000); } catch (InterruptedException e) {} //********TAKE OFF 

			Sound.beep();	
			//LCDinfo LCDinfo = new LCDinfo(odo);
			attack.attackAlgorithm();
>>>>>>> 1668534108f153c33e2e0e005e965343b40486d1
		}
		// Defender Mode
		else{


		// Testing the Launcher via bluetooth;
		BTConnectTest connect = new BTConnectTest();
		try {
			connect.connect();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// turnTo works!
		// navigate.turnTo(180);



		}


		Button.waitForAnyPress();


	}
}
