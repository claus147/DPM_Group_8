
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.Sound;
import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;
import lejos.nxt.comm.NXTConnection;

/**
 * Receive data from another NXT, a PC, a phone, 
 * or another bluetooth device.
 * 
 * Waits for a connection, receives an int and returns
 * its negative as a reply, 100 times, and then closes
 * the connection, and waits for a new one.
 * 
 * This class should be opened in the launcher brick. In summary, it will always be
 * waiting for a signal to shoot. When the master brick  connects to this brick, it will shoot.
 * 
 * 
 * @author Lawrie Griffiths
 * @author Tuan-Kiet Luu
 * @author Nabil Zoldjalali
 *
 */
public class BTReceive {
	
	static int speed = 1100; //speed of launch
	static int returnSpeed = 80;	 //speed of return to original position
	static int angle = -80; //angle the arm turns to (final position of catapult arm) - negative because initally not geared 
	static int reAdjust = 100;	
	// Launcher motors
	static NXTRegulatedMotor leftMotor = Motor.B;
	static NXTRegulatedMotor rightMotor = Motor.C;
	//Feeding mechanism
	static NXTRegulatedMotor turnMotor = Motor.A;
	static int turn = 180; 
	static int angle1 = 90;
	static String connected = "Connected";
    static String waiting = "Waiting...!!!";
    static String closing = "Closing...";
	
	public static void main(String [] args)  throws Exception 
	{
		  BTConnection btc = Bluetooth.waitForConnection();
	        DataInputStream input = btc.openDataInputStream();
    	
		while (true)
		{

	
			LCD.drawString(waiting,0,0);
			LCD.refresh();
	    	
	        //OPTION A
	        if(input.readChar()=='a'){
	    		Sound.buzz();
	    	}
	        
	        if(input.readChar()=='b'){
	        	launchSixBricks();
	        	
	        }
	        
	        if(input.readChar()=='c'){
	        	launchSevenBricks();
	        	
	        }
	        if(input.readChar()=='d'){
	        	launchEightBricks();
	        }
	        Thread.sleep(100);
	    	}
		
		// input.close();
        // btc.close();
	}

	
public static void launchFiveBricks(){
	
	Sound.buzz();
}

public static void launchSixBricks(){
	
}

public static void launchSevenBricks() throws IOException, InterruptedException{
	



}
public static void launchEightBricks() throws IOException, InterruptedException{
	for(int i= 0; i<5; i++){
		
		//put the launcher down
		rightMotor.rotateTo(reAdjust, true);
		leftMotor.rotateTo(reAdjust);
		
		turnMotor.setSpeed(turn);
		//Motor.C.rotateTo(angle);
		turnMotor.rotate(angle1);


		try {
		Thread.sleep(4000);
		} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}


		rightMotor.setSpeed(speed);
		leftMotor.setSpeed(speed);
		rightMotor.rotateTo(angle, true);
		leftMotor.rotateTo(angle);

		//do return to original position
		rightMotor.setSpeed(returnSpeed);
		leftMotor.setSpeed(returnSpeed);
		rightMotor.rotateTo(reAdjust, true);
		leftMotor.rotateTo(reAdjust);
	
}
	
}

}


