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

	public Odometry odo;// = AttackerMode.odo; quick fix
	USData usData; //= AttackerMode.usData; quick fix

	Navigation navigate = new Navigation(odo);
	
	
	WheelsData wheels = new WheelsData();
	int data;
	
	/**
	 * This is a constructor Controller taking as input an Odometry
	 * @param Odometry
	 */
	public Controller(Odometry odom){
		this.odo = odom;
	}

	/**
	 * Simple command to go forward from the Navigation Class
	 */
	public void goForward(){
		navigate.goForward();
	}
	/**
	 * Simple command to go backward from the Navigation Class
	 */
	public void goBackward(){
		navigate.goBackward();
	}
	/**
	 * Simple command to stop motors from the Navigation Class
	 */
	public void motorsStop(){
		navigate.motorsStop();
	}
	/**
	 * Simple command to stop left motor from the Navigation Class
	 */
	public void leftMotorStop(){
		navigate.leftMotorStop();
	}
	/**
	 * Simple command to stop right motor from the Navigation Class
	 */
	public void rightMotorStop(){
		navigate.rightMotorStop();
	}
	/**
	 * Simple command to go to a specific coordinate from the Navigation Class
	 */
	public void travelTo(double x, double y){
		navigate.travelTo(x, y);
	}
	
	/**
	 * Simple command to turn the robot to a specific angle from the Navigation Class
	 */
	public void turnTo(double t){
		navigate.turnTo(t);
	}
	/**
	 * Simple command to turn to a number of degrees from the Navigation Class
	 */
	public void turn(double t){
		navigate.turn(t);
	}
	/**
	 * Simple command to turn clockwise from the Navigation Class
	 */
	public void turnClockWise(){
		navigate.turnClockWise();
	}
	/**
	 * Simple command to turn counterclockwise from the Navigation Class
	 */
	public void turnCounterClockWise(){
		navigate.turnCounterClockWise();
	}
	
//	
//	public void setX(double x){
//		odo.setX(x);
//	}
//	
//	public void setY(double y){
//		odo.setY(y);
//	}
//	
//	public void setTheta (double t){
//		odo.setTheta(t);
//	}
	
	
	/* START METHODS TESTING */
	
	//WORKS!
//	public void callUSData(){
//		usData.getUSData(data);
//	}
	

	
	
	/* END METHODS TESTING */
}
