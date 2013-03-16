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
	 * travelTo method
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
	 * turnTo method
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
	 * method isNavigating() 
	 */
	boolean isNavigating(){
		if(travelToCalled || turnToCalled){
			return true;
		}else{
			return false;
		}
	}
	
	private static int toAngle(double radius, double distance) {
		return (int) ((180.0 * distance) / (Math.PI * radius));
	}

	private static double toDistForRotationLeft( double width, double angle) {
		return angle * Math.PI * width / (180.0 * 2.0);
	}


}

