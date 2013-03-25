
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
 * @author Lawrie Griffiths
 *
 */
public class BTReceive {

	public static void main(String [] args)  throws Exception 
	{
		String connected = "Connected";
        String waiting = "Waiting...!!!";
        String closing = "Closing...";
    
    	
    	
		while (true)
		{
			NXTRegulatedMotor leftMotor = Motor.A;
			LCD.drawString(waiting,0,0);
			LCD.refresh();
			

	        BTConnection btc = Bluetooth.waitForConnection();
	        
			LCD.clear();
			LCD.drawString(connected,0,0);
			LCD.drawString("got something", 4, 4);
			leftMotor.rotate(100);
			LCD.refresh();	

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
}

