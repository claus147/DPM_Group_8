
import java.io.DataInputStream;
import java.io.DataOutputStream;

import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.Sound;
import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;

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
	static int reAdjust = 10;	

	public static void main(String [] args)  throws Exception 
	{
		String connected = "Connected";
        String waiting = "Waiting...!!!";
        String closing = "Closing...";
    
		while (true)
		{
			// Launcher motors
			NXTRegulatedMotor leftMotor = Motor.B;
			NXTRegulatedMotor rightMotor = Motor.C;
			//Feeding mechanism
			NXTRegulatedMotor turnMotor = Motor.A;
			int turn = 180; 
			int angle1 = 90;
			LCD.drawString(waiting,0,0);
			LCD.refresh();
			

	        BTConnection btc = Bluetooth.waitForConnection();
	        
			LCD.clear();
			LCD.drawString(connected,0,0);
			
			/* START MOVE ROBOT HERE */ 
			
	//		LCD.drawString("got something", 4, 4);
	/*		leftMotor.rotate(-100);
			rightMotor.rotate(-100);
			turnMotor.rotate(360);
			*/
			LCD.refresh();	
			
			for(int i= 0; i<5; i++){
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


			
			
			
			
			

			/* END MOVE ROBOT HERE */
			DataInputStream dis = btc.openDataInputStream();
			DataOutputStream dos = btc.openDataOutputStream();
			
			
		/*	
			if(dis.readInt() == 1){
				LCD.drawString("got 1", 4, 4);
				Sound.beep();
			}
			
			for(int i=0;i<100;i++) {
				int n = dis.readInt();
				LCD.drawInt(n,7,0,1);
				LCD.refresh();
				dos.writeInt(-n);
				dos.flush();
			}
			
			
			*/
			dis.close();
			dos.close();
			Thread.sleep(100); // wait for data to drain
			LCD.clear();
			LCD.drawString(closing,0,0);
			LCD.refresh();
			btc.close();
			LCD.clear();
		}
	}
}}


