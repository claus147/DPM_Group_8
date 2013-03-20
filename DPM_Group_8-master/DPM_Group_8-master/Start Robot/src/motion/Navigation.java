package motion;

import data.LCDinfo;
import odometry.Odometry;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;

/*
 * This class will implement all the navigation algorithms and calculations.
 * ported from Tuan / Yu Yang's lab 3
 * @author Tuan-Kiet Luu
 * @author Kornpat Choy (Claus)
 * @version 1.0
 */

public class Navigation extends Thread{
	

	

	boolean travelToCalled = false;
	boolean turnToCalled = false;
	
	private Odometry odo = new Odometry();
	
	private static final int FORWARD_SPEED = 150;
	private static final int ROTATE_SPEED = 100;
	
	NXTRegulatedMotor leftMotor = Motor.A;
	NXTRegulatedMotor rightMotor = Motor.B;
	
	final double leftRadius = 2.8;
	final double rightRadius = 2.8;
	final double width = 15.6;
	
	public enum WheelSide { LEFT, RIGHT };
	
	public Navigation() {
	}
	
	public Navigation(Odometry odometer) {
		// TODO Auto-generated constructor stub
		this.odo = odometer;
	}
	
	public void run(){
		/*
		for (NXTRegulatedMotor motor : new NXTRegulatedMotor[] { leftMotor, rightMotor }) {
			motor.stop();
			motor.setAcceleration(1000);
		}
		*/

		// wait 2 seconds
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// there is nothing to be done here because it is not expected that
			// the odometer will be interrupted by another thread
		}
			
			
			turnTo(63.435);
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// there is nothing to be done here because it is not expected that
				// the odometer will be interrupted by another thread
			}
			
			travelTo(60 , 30);
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// there is nothing to be done here because it is not expected that
				// the odometer will be interrupted by another thread
			}
			
			turnTo(-90);
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// there is nothing to be done here because it is not expected that
				// the odometer will be interrupted by another thread
			}
			
			travelTo(30 , 30);
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// there is nothing to be done here because it is not expected that
				// the odometer will be interrupted by another thread
			}
			
			turnTo(0);
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// there is nothing to be done here because it is not expected that
				// the odometer will be interrupted by another thread
			}
			
			travelTo(30 , 60);
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// there is nothing to be done here because it is not expected that
				// the odometer will be interrupted by another thread
			}
			
			turnTo(153.435);
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// there is nothing to be done here because it is not expected that
				// the odometer will be interrupted by another thread
			}
			
			travelTo(60 , 0);
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// there is nothing to be done here because it is not expected that
				// the odometer will be interrupted by another thread
			}	
		
	}
	
	/**
	 * This method will move the robot to a specific coordinate. This method will call the turnTo method.
	 * Basically, it will first use the input coordinates to calculate its path. After the calculation, it will call
	 * the turnTo method to turn the robot to the desitnation, then it will navigate to the target location.
	 * @param x
	 * @param y
	 */
	public void travelTo( double x, double y){
		travelToCalled = true;
		
		double headingAngle, currentX, currentY,currentT; //Initialising
		while(true){
	
			currentX = odo.getX();
			currentY = odo.getY();
			currentT = odo.getTheta();
			
			leftMotor.forward();		//move forward at forward_speed
			rightMotor.forward();
			leftMotor.setSpeed(FORWARD_SPEED);
			rightMotor.setSpeed(FORWARD_SPEED);
			
			double atanTemp = Math.toDegrees(Math.atan((x - currentX)/(y - currentY) )); //do calculations
			if(Math.abs(atanTemp - currentT ) > 30 && atanTemp > 0){
				headingAngle = (atanTemp - 180);
			}else if(Math.abs(atanTemp - currentT ) > 30 && atanTemp < 0){
				headingAngle = (atanTemp + 180);
			}else{
				headingAngle = atanTemp;
			}
			
			turnTo(headingAngle);
			
			
			if( Math.abs(currentX - x) < 1 && Math.abs(currentY - y) < 1) 
				break;
			
			try { Thread.sleep(100); } catch (InterruptedException e) {}  //100ms sleep
		}
		leftMotor.stop();
		rightMotor.stop();
		
		travelToCalled = false;
		
	}
	
	/**
	 * This method will take as input an angle theta, and it will turn the robot to a specific angle.
	 * @param theta
	 */
	public void turnTo( double theta ){
		
		
		
		turnToCalled = true;
		double currentAngle = 0;
		int currentSpeedL = 0;
		int currentSpeedR = 0;
		currentAngle = odo.getTheta();
		double angleDiff = theta - currentAngle;
		
		if(travelToCalled){
			
			currentSpeedL = leftMotor.getSpeed();
			currentSpeedR = rightMotor.getSpeed();
			
			if(angleDiff > 2){
				rightMotor.setSpeed(currentSpeedR - 50);
			}else if(angleDiff < -2 ){
				leftMotor.setSpeed(currentSpeedL - 50);
			}
			try { Thread.sleep(100); } catch (InterruptedException e) {}
					
		}else{
			
			leftMotor.setSpeed(ROTATE_SPEED);
			rightMotor.setSpeed(ROTATE_SPEED);
			leftMotor.rotate(toAngle(leftRadius, toDistForRotationLeft( width, angleDiff)), true);
			rightMotor.rotate(toAngle(rightRadius, - 1 * toDistForRotationLeft( width, angleDiff)), false);
		}
		
		turnToCalled = false;
		
	}
	
	/**
	 * for Localization class 
	 * @author Kornpat Choy (Claus)
	 * @param rotationalSpeed
	 */
	public void keepRotating(double rotationalSpeed) {
		double leftSpeed, rightSpeed;

		leftSpeed = (rotationalSpeed * width * Math.PI / 360.0) *
				180.0 / (leftRadius * Math.PI);
		rightSpeed = (- rotationalSpeed * width * Math.PI / 360.0) *
				180.0 / (rightRadius * Math.PI);

		// set motor directions
		if (leftSpeed > 0.0)
			leftMotor.forward();
		else {
			leftMotor.backward();
			leftSpeed = -leftSpeed;
		}
		
		if (rightSpeed > 0.0)
			rightMotor.forward();
		else {
			rightMotor.backward();
			rightSpeed = -rightSpeed;
		}
		
		// set motor speeds
		if (leftSpeed > 900.0)
			leftMotor.setSpeed(900);
		else
			leftMotor.setSpeed((int)leftSpeed);
		
		if (rightSpeed > 900.0)
			rightMotor.setSpeed(900);
		else
			rightMotor.setSpeed((int)rightSpeed);
	}
	
	/**
	 * Keeps going, does not stop - until stop method called
	 * @param forwardSpeed
	 */
	public void goforward(double forwardSpeed){
		leftMotor.setSpeed((int)forwardSpeed);
		rightMotor.setSpeed((int)forwardSpeed);
		leftMotor.forward();
		rightMotor.forward();
	}
	
	/**
	 * for localization - stop a single wheel
	 * @param side (LEFT, RIGHT)
	 */
	public void stop(WheelSide side){
		if (side == WheelSide.LEFT)
			leftMotor.setSpeed(0);
		else
			rightMotor.setSpeed(0);
	}
	
	/**
	 * stop both wheels
	 */
	public void stop(){
		leftMotor.setSpeed(0);
		rightMotor.setSpeed(0);
	}
	
	/**
	 * boolean isNavigating(), check if the robot is navigation
	 */
	boolean isNavigating(){
		if(travelToCalled || turnToCalled){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 
	 * This method will take as input the radius and distance of the wheel and it will convert to radians**.
	 * @param radius
	 * @param distance
	 * @return
	 */
	private static int toAngle(double radius, double distance) {
		return (int) ((180.0 * distance) / (Math.PI * radius));
	}

/**
 * This method will calculate the angle left for rotation
 * 
 * @param width
 * @param angle
 * @return
 */
	private static double toDistForRotationLeft( double width, double angle) {
		return angle * Math.PI * width / (180.0 * 2.0);
	}


}

