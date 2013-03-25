/**
 * @author Tuan-Kiet Luu
 * @version 1.0
 */
package data;

import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;

/**
 * @author Tuan-Kiet
 *
 */
public class WheelsData {

	public static NXTRegulatedMotor leftMotor = Motor.A, rightMotor = Motor.B;
	
	
	public static int getLTacho() {
		 
//		try { Thread.sleep(50); } catch (InterruptedException e) {}
		return leftMotor.getTachoCount();
	}
	
	public static int getRTacho() {
		
		//try { Thread.sleep(50); } catch (InterruptedException e) {}
		return rightMotor.getTachoCount();
	}

	
}
