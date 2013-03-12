/**
 * Will take the data from the wheel A motor (tachometer)
 * @author Tuan-Kiet Luu
 * @version 1.0
 */
import lejos.nxt.*;

/**
 * @author Tuan-Kiet
 * @version 1.0
 */

public class WheelAData {
	
	private final NXTRegulatedMotor leftMotor = Motor.A, rightMotor = Motor.B;
	private int tachoWheelA;
	
	public WheelAData(){
		this.tachoWheelA = leftMotor.getTachoCount();
		
		
		
	}
	
	//Test to see if this class is accessed
	public void TravelTo(){

		leftMotor.rotate(50);
		rightMotor.rotate(50);
	
	}
	

	/**
	 * This method is a getter that will get the tachometer count of the left motor (Motor A)
	 * @param leftMotor
	 */
	public int getTachoWheelA() {
		tachoWheelA = leftMotor.getTachoCount();
		return tachoWheelA;
	}
	
	
	
	
	
}
