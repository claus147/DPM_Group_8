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

	private final NXTRegulatedMotor leftMotor = Motor.A, rightMotor = Motor.B;
	private int tachoWheelA;
	private int tachoWheelB;
	
	public int getLTacho() {
		tachoWheelA = leftMotor.getTachoCount();
		try { Thread.sleep(50); } catch (InterruptedException e) {}
		return tachoWheelA;
	}
	
	public int getRTacho() {
		tachoWheelB = rightMotor.getTachoCount();
		try { Thread.sleep(50); } catch (InterruptedException e) {}
		return tachoWheelB;
	}

	
}