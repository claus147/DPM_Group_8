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

	public Odometry odo = AttackerMode.odo;
	USData usData = AttackerMode.usData;

	Navigation navigate = new Navigation(odo);
	
	
	WheelsData wheels = new WheelsData();
	int data;
	
	
	public Controller(Odometry odom){
		this.odo = odom;
	}
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
	
	public void goForward(){
		navigate.goForward();
	}
	
	public void goBackward(){
		navigate.goBackward();
	}
	
	public void motorsStop(){
		navigate.motorsStop();
	}
	
	public void leftMotorStop(){
		navigate.leftMotorStop();
	}
	
	public void rightMotorStop(){
		navigate.rightMotorStop();
	}
	
	public void travelTo(double x, double y){
		navigate.travelTo(x, y);
	}
	
	public void turnTo(double t){
		navigate.turnTo(t);
	}
	
	public void turn(double t){
		navigate.turn(t);
	}
	
	public void turnClockWise(){
		navigate.turnClockWise();
	}
	
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
