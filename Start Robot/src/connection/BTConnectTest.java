
package connection;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import javax.bluetooth.RemoteDevice;

import lejos.nxt.LCD;
import lejos.nxt.Sound;
import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;
import lejos.nxt.comm.NXTConnection;
import modes.Controller;

/**
 * 
 * Test of NXT to NXT Bluetooth comms.
 * 
 * Connects to another NXT, sends 100 ints, and receives the 
 * replies. Then closes the connection and shuts down.
 * 
 * Works with the BTReceive or NXTReceive sample running on the slave NXT.
 * 
 * Change the name string to the name of your slave NXT, and make sure
 * it is in the known devices list of the master NXT. To do this, turn
 * on the slave NXT and make sure Bluetooth is on and the device
 * is visible. Use the Bluetooth menu on the slave for this. Then,
 * on the master, select the Bluetooth menu and then select Search and Pair.
 * The name of the slave NXT should appear. Select Pair to add
 * it to the known devices of the master. You can check this has
 * been done by selecting Devices from the Bluetooth menu on the
 * master.
 * 
 * @author Lawrie Griffiths
 * @author Tuan-Kiet Luu
 * @date March, 2013
 * @class ECSE 211 - Design Principle and Methods
 *
 */
public class BTConnectTest {
	private Transmission trans;
	
	/*
	public BTConnectTest(){
		LCD.clear();
		LCD.drawString("Starting BT connection", 0, 0);
		
		NXTConnection conn = Bluetooth.waitForConnection();
		DataInputStream dis = conn.openDataInputStream();
		LCD.drawString("Opened DIS", 0, 1);
		this.trans = ParseTransmission.parse(dis);
		LCD.drawString("Finished Parsing", 0, 2);
		try {
			dis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		conn.close();
		
	}
	*/

	
	
	public void connect(char x) throws Exception {
		//slave name : headyin / T04M / ShootingBrick / ANTBOT
		String name = "ANTBOT";
		
	//	NXTConnection conn = Bluetooth.waitForConnection();
	
		
		LCD.drawString("Connecting...", 0, 0);
		LCD.refresh();
		
		RemoteDevice btrd = Bluetooth.getKnownDevice(name);

		if (btrd == null) {
			LCD.clear();
			LCD.drawString("No such device", 0, 0);
			LCD.refresh();
			Thread.sleep(2000);
			System.exit(1);
		}
		
		BTConnection btc = Bluetooth.connect(btrd);
		DataOutputStream out = btc.openDataOutputStream();
		DataInputStream in = btc.openDataInputStream();
		if (btc == null) {
			LCD.clear();
			LCD.drawString("Connect fail", 0, 0);
			LCD.refresh();
			Thread.sleep(2000);
			System.exit(1);
		}
		// START TEST 
	//	Controller control = new Controller(null);
		out.writeChar(x);

	
		/*
		if(in.readInt()==10){
			Sound.buzz();
	//		control.travelTo(3, 3);
		}
		*/
		in.close();
		
		
		// END TEST 
		LCD.clear();
		LCD.drawString("Connected", 0, 0);
		LCD.refresh();
		
	//	DataInputStream dis = btc.openDataInputStream();
	//	DataOutputStream dos = btc.openDataOutputStream();
				
	/*
		for(int i=0;i<100;i++) {
			try {
				LCD.drawInt(i*30000, 8, 0, 2);
				LCD.refresh();
				dos.writeInt(i*30000);
				dos.flush();			
			} catch (IOException ioe) {
				LCD.drawString("Write Exception", 0, 0);
				LCD.refresh();
			}
			
			try {
				LCD.drawInt(dis.readInt(),8, 0,3);
				LCD.refresh();
			} catch (IOException ioe) {
				LCD.drawString("Read Exception ", 0, 0);
				LCD.refresh();
			}
		}
		*/
		try {
			LCD.drawString("Closing...    ", 0, 0);
			LCD.refresh();
			in.close();
			out.close();
			//conection close
			btc.close();
		} catch (IOException ioe) {
			LCD.drawString("Close Exception", 0, 0);
			LCD.refresh();
		}
		
		LCD.clear();
		LCD.drawString("Finished",3, 4);
		LCD.refresh();

		//Thread.sleep(200);
		Thread.sleep(3000);
	}
}
