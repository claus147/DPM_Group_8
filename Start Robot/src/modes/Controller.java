package modes;
import data.WheelAData;
import data.WheelBData;
/**
 * This class will be the controller class that will send orders to all motors.
 * @author Tuan-Kiet Luu
 * @version 1.0
 */

public class Controller {

	WheelAData leftMotor = new WheelAData();
	WheelBData rightMotor = new WheelBData();
	
	
	
	/**
	 * Testing phase : linking controller to wheels
	 */
	public void activateMotors(){
		
		leftMotor.TravelTo();
		try { Thread.sleep(2000); } catch (InterruptedException e) {}
		rightMotor.TravelTo();
	
		
	}
	

	/**
	 * Testing phase : linking controller to Light sensor
	 */
	public void activateLS(){
		
		
	}

	/**
	 * Testing phase : linking controller to Ultrasonic
	 */
	public void activateUS(){
		
		
	}
	
}
