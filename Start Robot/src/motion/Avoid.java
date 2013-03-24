package motion;

import data.*;
import lejos.nxt.*;

/**
 * This class will avoid any obstacles in the way
 * @author Kornpat Choy (Claus)
 * @author Tuan-Kiet Luu
 * @version 1.0
 * 
 */

public class Avoid{
	
	
	private final int bandCenter, bandwith;
	private final int motorLow, motorHigh;
	//TODO: CHANGE THIS VARIABLE
	private final int motorStraight = 200;
	private final NXTRegulatedMotor leftMotor = Motor.A, rightMotor = Motor.B;
	private int distance;
	private int currentLeftSpeed;
	private int data;
	USData usData = new USData();
	
	public Avoid(int bandCenter, int bandwith, int motorLow, int motorHigh) {
		//Default Constructor
		this.bandCenter = bandCenter;
		this.bandwith = bandwith;
		this.motorLow = motorLow;
		this.motorHigh = motorHigh;
		leftMotor.setSpeed(motorStraight);
		rightMotor.setSpeed(motorStraight);
		leftMotor.forward();
		rightMotor.forward();
		currentLeftSpeed = 0;
		
	}
	
	/**
	 * This method is the algorithm for the wall following robot.
	 * The algorithm is a Bang Bang Controller, and this algorithm is mainly found from trial and error.
	 * Acceleration parameters are hardcoded values (trial and error)
	 * @param motorLow 
	 * @param motorHigh
	 */
	public void processUSData(int distance) {
		
		// This will read the distance from the sensor and store in the distance variable.
		this.distance = distance;
		distance = (int) usData.getUSData(data);
	
		// error : the position of the robot compared to the bandCenter
		int error = 0;
		
		error = ( distance - (bandCenter+5) );
		
		// Case 1 : if the error is between the bandwith range, move forward with both wheels at same acceleration.
		if(Math.abs(error) <= bandwith){
			leftMotor.setSpeed(motorStraight);
			rightMotor.setSpeed(motorStraight);
		}
		
		// Case 2 : if the robot is too far from the wall, accelerate the right wheel and decelerate the left wheel.
		else if(error > 0){
			
			leftMotor.setSpeed(motorLow);
			rightMotor.setSpeed(motorHigh);
			
		}
		
		//Case 3 : if the robot is too close from the wall, accelerate the left wheel, decelerate the right wheel.
		else {
		
			
			leftMotor.setSpeed(motorHigh + (motorHigh / 2) );
			rightMotor.setSpeed(motorLow / 2 );
			
			
		}

		
	}
	
	
	
}