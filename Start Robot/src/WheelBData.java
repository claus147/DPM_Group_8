import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;

/**
 * Will take the data from the WheelB motor (tachometer)
 * @author Tuan-Kiet Luu
 * @version 1.0
 */


public class WheelBData {

	private int tachoWheelB;
	private final NXTRegulatedMotor leftMotor = Motor.A, rightMotor = Motor.B;

	
	
	
	
	//Test to see if this class is accessed
	public void TravelTo(){
			
			
			leftMotor.rotate(50);
			rightMotor.rotate(50);
			
			
		}
	
	
	
	
	/**
	 * This method is a getter that will get the tachometer count of the right motor (Motor B)
	 * @param rightMotor
	 */
	public double getTachoWheelB() {
		
		tachoWheelB = rightMotor.getTachoCount();
		return tachoWheelB;
	}
	
	
}
