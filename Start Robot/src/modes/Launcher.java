package modes;

/** 
 * @author Andrea Ngo
 * @author Kornpat Choy (Claus)
 * @version 1.0
*/
import java.io.DataInputStream;
import java.io.IOException;

import lejos.nxt.*;
import lejos.nxt.comm.Bluetooth;
import lejos.nxt.comm.NXTConnection;

public class Launcher {
	public static void main(String[] args) throws InterruptedException {
		NXTRegulatedMotor leftMotor = Motor.A;
		NXTRegulatedMotor rightMotor = Motor.B;
		NXTRegulatedMotor feederMotor= Motor.C;
		int speed = 2000 ; 							//speed of launch
		int returnSpeed = 50;						//speed of return to original position
		int angle = -70;     						//angle the arm turns to (final position of catapult arm) - negative because initally not geared       
		int reAdjust = -10;							//angle to re adjust by(initial angle before launch)
		//boolean go = true;							//for the loop (continuous launching)
		
		int counter=0;								//number of balls to be launched
		
		//adjusting at a slow speed to the inital launch position
		leftMotor.setSpeed(returnSpeed);
		rightMotor.setSpeed(returnSpeed);
		leftMotor.resetTachoCount();
		rightMotor.resetTachoCount();
		leftMotor.rotateTo(reAdjust, true);
		rightMotor.rotateTo(reAdjust);
		
		/* Wait for bluetooth connection */
		 
		NXTConnection conn = Bluetooth.waitForConnection(); //Establish connection and wait for bluetooth signal
		
		DataInputStream dis = conn.openDataInputStream();	//Open data stream
		
		try {
			dis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		conn.close();									//Close connection 
	
		while (counter<6){ 								//With 6 balls
		feederMotor.rotate(120);
		Thread.sleep(5000);								//Wait 5 seconds before firing so the ball can fall into the arm
		
		
		//do the firing of the catapult
		leftMotor.setSpeed(speed);
		rightMotor.setSpeed(speed);
		leftMotor.rotateTo(angle, true);
		rightMotor.rotateTo(angle);
		
		//do return to original position
		leftMotor.setSpeed(returnSpeed);
		rightMotor.setSpeed(returnSpeed);
		leftMotor.rotateTo(reAdjust, true);
		rightMotor.rotateTo(reAdjust);
		
		Thread.sleep(5000);						//Wait another 5 seconds to make sure the arm is ready to receive a ball
		counter++;
		}
		
}
}
