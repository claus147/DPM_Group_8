package modes;
import odometry.Odometry;
import motion.*;
import data.*;
/**
 * This class will be the controller class that will send orders to all motors.
 * @author Tuan-Kiet Luu
 * @version 1.0
 */

public class Controller {

	WheelAData leftMotor = new WheelAData();
	WheelBData rightMotor = new WheelBData();
	private Odometry odo = new Odometry();
	Navigation navigate = new Navigation(odo);
	
	USData usData = new USData();
	WheelsData wheels = new WheelsData();
	int data;
	
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
	public void travelTo(double x, double y){
		navigate.travelTo(x, y);
	}
	/* START METHODS TESTING */
	
	//WORKS!
	public void callUSData(){
		usData.getUSData(data);
	}
	

	
	
	/* END METHODS TESTING */
}
