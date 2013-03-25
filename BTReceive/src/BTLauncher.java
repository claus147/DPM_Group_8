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
 * @author Lawrie Griffiths
 * @author Tuan-Kiet Luu
 * @author Nabil Zoldjalali
 *
 */
public class BTLauncher {


static int speed = 3000; //speed of launch
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
			
			int turn = 180; 
			int angle1 = 90;
			NXTRegulatedMotor leftMotor = Motor.A;
			NXTRegulatedMotor turnMotor = Motor.C;
			LCD.drawString(waiting,0,0);
			LCD.refresh();


	        BTConnection btc = Bluetooth.waitForConnection();

			LCD.clear();
			LCD.drawString(connected,0,0);
			/* START MOVE ROBOT HERE; INSERT ALGORITHM TODO*/
			
			//LCD.drawString("got something", 4, 4);
			//leftMotor.rotate(100);
			//LCD.refresh();	
			
			/*for( x = 0; x<1000; x++){

			x=x+6;
			x=x-58;
			x=x+53;


			}*/
			
			for(int i= 0; i<5; i++){
			Motor.C.setSpeed(turn);
			//Motor.C.rotateTo(angle);
			Motor.C.rotate(angle1);


			try {
			Thread.sleep(4000);
			} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}


			Motor.A.setSpeed(speed);
			Motor.B.setSpeed(speed);
			Motor.A.rotateTo(angle, true);
			Motor.B.rotateTo(angle);

			//do return to original position
			Motor.A.setSpeed(returnSpeed);
			Motor.B.setSpeed(returnSpeed);
			Motor.A.rotateTo(reAdjust, true);
			Motor.B.rotateTo(reAdjust);

			/* END MOVE ROBOT HERE; INSERT ALGORITHM TODO */
			
			DataInputStream dis = btc.openDataInputStream();
			DataOutputStream dos = btc.openDataOutputStream();

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
	}
	}