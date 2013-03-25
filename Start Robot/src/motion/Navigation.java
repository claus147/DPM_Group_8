package motion;

import data.*;
import odometry.Odometry;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;

/*
 * This class will implement all the navigation algorithms and calculations.
 * ported from Tuan / Yu Yang's lab 3
 * @author Tuan-Kiet Luu
 * @author Kornpat Choy (Claus)
 * @author Yu Yang Liu
 * @version 2.0
 */
 import lejos.nxt.LCD;
 import lejos.nxt.*;

 
 public class Navigation{// extends Thread{
 	
 	//Variables Assignment
 	boolean travelToCalled = false;
 	boolean turnToCalled = false;//
 	
 	private Odometry odo = new Odometry();

 	private static final int FORWARD_SPEED = 180;
 	private static final int ROTATE_SPEED = 100;
 	
 	NXTRegulatedMotor leftMotor = WheelsData.leftMotor;
 	NXTRegulatedMotor rightMotor = WheelsData.rightMotor;
 	
 
 	/* START TODO : CLEAN THIS CODE */
 	
 	final double leftRadius = 2.7;
 	final double rightRadius = 2.7;
 	final double width = 17.0;
 	
 	/* END TODO : CLEAN THIS CODE */
 	
 	/*
 	final double leftRadius = odo.leftRadius;
 	final double rightRadius = odo.rightRadius;
 	final double width = odo.WB;
 	*/
 	//Destination point
 	double destinationX;
 	double destinationY;
 	
 	//Class constructor has odometer as argument
 	public Navigation (Odometry odometer) {
 		
 		this.odo = odometer;
 	}
 	/*
 	// This method will run instructions to the targeted coordinates
 	public void run(){

 		try {
 			Thread.sleep(2000);
 		} catch (InterruptedException e) {
 			// there is nothing to be done here because it is not expected that
 			// the odometer will be interrupted by another thread
 		}
 			double destinationX1 = 60;
 			double destinationY1 = 150;
 			int finalOrientation1 = -90;
 			
 			double destinationX2 = 15;
 			double destinationY2 = 90;
 			int finalOrientation2 = 0;
 			
 			
 			travelTo(destinationX1, destinationY1);
 			try {
 				Thread.sleep(3000);
 			} catch (InterruptedException e) {
 				
 			}
 			turnTo(finalOrientation1);
 			
 			
 			try {
 				Thread.sleep(1000);
 			} catch (InterruptedException e) {
 				
 			}
 			travelTo(destinationX2, destinationY2);
 			try {
 				Thread.sleep(3000);
 			} catch (InterruptedException e) {
 				
 			}
 			turnTo(finalOrientation2);
 			
 	}
 	
 	*/
 	

 	//Will cause the robot to travel to an absolute destination. The coordinates in input is the destination.
 	
 /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
 	
 																						//TRAVELTO

 ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

 	public void travelTo( double x, double y){
 		destinationX = x;
 		destinationY = y;
 		
// 		double headingAngle = findHeadingAngle(x,y,odo.getX(), odo.getY() );
//		turnTo(headingAngle);
//			
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				
//			}
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				
//			}
// 		
 		travelToCalled = true;
 		
 		
 		
 		double initialX = odo.getX();
 		double initialY = odo.getY();
 		while(true){
 			//Draw on the screen the distance read by the sensor
// 			LCD.drawString(""+getFilteredDistA(), 0, 4 );
// 			LCD.drawString(""+getFilteredDistB(), 0, 5 );
 			//Set variables and initial speeds
 			
 			double currentX = odo.getX();
 			double currentY = odo.getY();	
 			double currentT = odo.getTheta();
 			
 			
 			
 			leftMotor.forward();
 			rightMotor.forward();
 			
 			rightMotor.setAcceleration(3000);
 			leftMotor.setAcceleration(3000);
 			rightMotor.setSpeed(FORWARD_SPEED);
 			leftMotor.setSpeed(FORWARD_SPEED); /////////////////////////////////////////////////////////////////////////////////////*********************************************
 			double headingAngle = findHeadingAngle(x,y,currentX,currentY);
 			//Calls turnTo method to change the angle of the robot
 			turnTo(headingAngle);
 			
 			currentT = odo.getTheta();

 			if(wentOverDestination(initialX, initialY, destinationX, destinationY, currentX, currentY)){
 				turn(headingAngle - currentT);
 			}
 			
 			
 			//If sensor detects a wall, call the wall follower method                     make sure the wall is not the obstacle...	
 			int dataA = getFilteredDistA();
 			int dataB = getFilteredDistB();
 			if((dataA < 30 || dataB < 30) && Math.sqrt((currentX-x)*(currentX-x) + (currentY-y)*(currentY-y)) > 40){
 				avoidWall(currentT);
 			}
 			
// 			leftMotor.stop();
// 			rightMotor.stop();
// 			turnTo(headingAngle);
// 			
 			
 			// When the robot reaches the destination, break from the while loop.
 			if( Math.abs(currentX - x) < 3 && Math.abs(currentY - y) < 3) break;
 			try {
 				Thread.sleep(200);
 			} catch (InterruptedException e) {
 				// there is nothing to be done here because it is not expected that
 				// the odometer will be interrupted by another thread
 			}
 		}
 		//Stop both motors
 		Sound.beep();
 		rightMotor.stop(true);
 		leftMotor.stop(true);
 		
 		
 		travelToCalled = false;
 		
 		
 		
 	}
 	
 	
 	public double findHeadingAngle(double destX, double destY, double curX, double curY){
 		//Calculates the angle and converts to degrees /////////////////////////////////////////////////////////////////////////////////////////CALCULATE HEADING ANGLE!!!!!!!!!!!
 		double headingAngle;
 		double deltaX = (destX - curX);
 		double deltaY = (destY - curY);
 		double tanTemp = deltaX / deltaY;
 		double atanTemp = Math.toDegrees(Math.atan(tanTemp ));
 		
 		if( tanTemp > 0 && deltaY < 0){
 			headingAngle = (atanTemp - 180);
 		}else if(tanTemp < 0 && deltaY < 0){
 			headingAngle = (atanTemp + 180);
 		}else{
 			headingAngle = atanTemp;
 		}
 		return headingAngle;
 	}
 	
 	public void turnClockWise(){
 		leftMotor.setAcceleration(3000);
 		rightMotor.setAcceleration(3000);
 		
 		
 		
 		leftMotor.forward();
 		rightMotor.backward();
 		leftMotor.setSpeed(ROTATE_SPEED); 
 		rightMotor.setSpeed(ROTATE_SPEED);
 	}
 	
 	public void turnCounterClockWise(){
 		leftMotor.setAcceleration(3000);
 		rightMotor.setAcceleration(3000);
 		
 		
 		
 		leftMotor.backward();
 		rightMotor.forward();
 		leftMotor.setSpeed(ROTATE_SPEED); 
 		rightMotor.setSpeed(ROTATE_SPEED);
 	}
 	
 	public void turn( double t){
 		leftMotor.setAcceleration(3000);
 		rightMotor.setAcceleration(3000);
 		leftMotor.setSpeed(ROTATE_SPEED); 
 		rightMotor.setSpeed(ROTATE_SPEED);
 		
 		
 		leftMotor.rotate(toAngle(leftRadius, toDistForRotationLeft( width, t)), true);
 		rightMotor.rotate(toAngle(rightRadius, - 1 * toDistForRotationLeft( width, t)), false);
 	}
 	
 	
 	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
 	
 																//TURNTO
 	
 	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
 	
 	public void turnTo( double theta ){
 		//initializes the variables
 		turnToCalled = true;
 		
 		double currentAngle = 0;
// 		int currentSpeedL = 0;
// 		int currentSpeedR = 0;
 		currentAngle = odo.getTheta();
 		double angleDiff = theta - currentAngle;
 		
 		//to make the robot turn the smallest angle possible. angleDiff is between -180 to 180
 		if(angleDiff > 180) angleDiff -= 360;
 		else if (angleDiff < -180) angleDiff +=360;
 		

 		
// 		currentSpeedL = leftMotor.getSpeed();
// 			
// 		currentSpeedR = rightMotor.getSpeed();
 		//When travelTo method is called, when it is still traveling ,it will adjust itself
 		if(travelToCalled){//currentSpeedL != 0 || currentSpeedR !=0
 			
// 			if(Math.abs(angleDiff) > 120){
// 				leftMotor.stop(true);
// 				rightMotor.stop(true);
// 				turn(180);
// 			}
 			
 			if(angleDiff > 2){
 				rightMotor.setAcceleration(800);
 				rightMotor.setSpeed(FORWARD_SPEED - 50);
 			}else if(angleDiff < -2 ){
 				leftMotor.setAcceleration(800);
 				leftMotor.setSpeed(FORWARD_SPEED - 50);
 			}else{}
 			
 			try {
 				Thread.sleep(500);
 			} catch (InterruptedException e) {
 				
 			}
 			
 			
 		}
 		// Stops the robot and turns to the angle for the next call on travelTo
 		else{
 			leftMotor.setSpeed(ROTATE_SPEED);
 			rightMotor.setSpeed(ROTATE_SPEED);
 			leftMotor.setAcceleration(3000);
 			rightMotor.setAcceleration(3000);
 			
 			leftMotor.rotate(toAngle(leftRadius, toDistForRotationLeft( width, angleDiff)), true);
 			rightMotor.rotate(toAngle(rightRadius, - 1 * toDistForRotationLeft( width, angleDiff)), false);
 			
 			
 			
 		}
 		
 		turnToCalled = false;
 	}
 	
 	
 	//It uses the ultrasonic Sensor to detect a wall and then adjusts itself to follow it.
 	void avoidWall( double theta){
 		leftMotor.stop(true);
 		rightMotor.stop(true);
 		try {
 			Thread.sleep(2000);
 		} catch (InterruptedException e) {
 			
 		}
 		if(getFilteredDistA() < 30 && getFilteredDistB() > 30){ // brick at LEFT
 			turn(70); //TURN RIGHT
 			try {
 				Thread.sleep(1000);
 			} catch (InterruptedException e) {
 				
 			}
 			leftMotor.forward();
 			rightMotor.forward();
 			leftMotor.setSpeed(FORWARD_SPEED - 30);  /////////////////////////////////////////////////////////////////////////
 			rightMotor.setSpeed(FORWARD_SPEED); /////////////////////////////////////////////////////////////////////////
 			while(true)
 			{
 				if( Math.abs( odo.getTheta() - theta ) < 5 ) break;
 			}			
 			
 		}else if(getFilteredDistB() < 30 && getFilteredDistA() > 30){ // BRICK AT RIGHT
 			turn(-70); // TURN LEFT
 			try {
 				Thread.sleep(500);
 			} catch (InterruptedException e) {
 				
 			}
 			leftMotor.forward();
 			rightMotor.forward();
 			leftMotor.setAcceleration(800);
 			rightMotor.setAcceleration(800);
 			
 			leftMotor.setSpeed(FORWARD_SPEED);  /////////////////////////////////////////////////////////////////////////
 			rightMotor.setSpeed(FORWARD_SPEED - 30); /////////////////////////////////////////////////////////////////////////
 			
 			while(true)
 			{
 				if( Math.abs( odo.getTheta() - theta ) < 5 ) break;
 			}	
 			
 		}
 		else if(getFilteredDistB() < 30 && getFilteredDistA() < 30){ // brick at middle
 			if(getFilteredDistB() > getFilteredDistA()){  // more on the left
 				do{
 					turn(90); //TURN RIGHT
 				
 					leftMotor.forward();
 					rightMotor.forward();
 					leftMotor.setSpeed(FORWARD_SPEED);  /////////////////////////////////////////////////////////////////////////
 					rightMotor.setSpeed(FORWARD_SPEED); /////////////////////////////////////////////////////////////////////////
 				
 					try {
 						Thread.sleep(3000);
 					} catch (InterruptedException e) {
 						
 					}
 					
 					leftMotor.stop();
 					rightMotor.stop();
 					try {
 						Thread.sleep(500);
 					} catch (InterruptedException e) {
 						
 					}
 					turn(-90); //TURN left
 				}while(getFilteredDistB() < 30);
 			
 			
 			}else{                                       // more on the right
 				do{
 					turn(-90); //TURN left
 				
 					leftMotor.forward();
 					rightMotor.forward();
 					leftMotor.setSpeed(FORWARD_SPEED);  /////////////////////////////////////////////////////////////////////////
 					rightMotor.setSpeed(FORWARD_SPEED); /////////////////////////////////////////////////////////////////////////
 				
 					try {
 						Thread.sleep(3000);
 					} catch (InterruptedException e) {
 						
 					}
 					
 					leftMotor.stop();
 					rightMotor.stop();
 					try {
 						Thread.sleep(500);
 					} catch (InterruptedException e) {
 						
 					}
 					turn(90); //TURN RIGHT
 				}while(getFilteredDistA() < 30);
 			}
 		}
 		
 		
// 		
//// 		leftMotor.stop();
//// 		rightMotor.stop();
// 		double error = 0;
// 		double bandCenter = 30;
// 		double bandWidth = 4;
// 		//Error = reference control value - measured distance from the wall.
// 		double distanceA = getFilteredDistA();
// 		double distanceB = getFilteredDistB();
// 		
// 		if(distanceB < bandCenter && distanceA > bandCenter)
// 		{
//// 			turnTo(theta -90);
// 			while(true){
// 			
// 				
// 				
// 				
// 				distanceB = getFilteredDistB();
// 				error = ( distanceB - bandCenter );
 //
// 						// if there is no error, the robot is on track
// 						// stay stable
// 				
//// 							if(distanceA >= 100){
//// 								leftMotor.stop();
//// 								rightMotor.stop();
//// 								turn( 90);
//// 							}
// 				
// 							if(Math.abs(error) <= bandWidth){
// 								leftMotor.setSpeed(FORWARD_SPEED);
// 								rightMotor.setSpeed(FORWARD_SPEED);
// 							
// 							}
// 						//if the robot is too far from the wall (sensor is on the left)
// 						// left motor should decrease, and right motor increase speed to go back in
// 							else if(error > 0){
// 							
// 								leftMotor.setSpeed(FORWARD_SPEED + 50);
// 								rightMotor.setSpeed(FORWARD_SPEED - 50);
// 							
// 							}
// 						
// 						// if the robot is too close to the wall (sensor is on the left)
// 						// left motor should increase, right motor decrease so it will go out
// 							else {
// 						
// 							
// 								leftMotor.setSpeed(FORWARD_SPEED - 50 );
// 								rightMotor.setSpeed(FORWARD_SPEED + 60);
// 							
// 							}
// 							
// 							if( Math.abs( odo.getTheta() - theta ) < 5 ) break;
// 							
// 							try { Thread.sleep(100); } catch(Exception e){} 
// 			}
// 		}
// 		
// 		else if(distanceA  <= bandCenter){
// 		//	turn(90);
// 		while(true){
// 			distanceA = getFilteredDistA();
// 			
// 			error = ( distanceA - bandCenter );
 //
// 						// if there is no error, the robot is on track
// 						// stay stable
// 			
//// 							if(distanceA >= 100){
//// 								leftMotor.stop();
//// 								rightMotor.stop();
//// 								turn( -90);
//// 							}
// 							
// 							
// 							if(Math.abs(error) <= bandWidth){
// 								leftMotor.setSpeed(FORWARD_SPEED);
// 								rightMotor.setSpeed(FORWARD_SPEED);
// 							
// 							}
// 						//if the robot is too far from the wall (sensor is on the left)
// 						// left motor should decrease, and right motor increase speed to go back in
// 							else if(error > 0){
// 							
// 								rightMotor.setSpeed(FORWARD_SPEED + 30);
// 								leftMotor.setSpeed(FORWARD_SPEED - 50);
// 							
// 							}
// 						
// 						// if the robot is too close to the wall (sensor is on the left)
// 						// left motor should increase, right motor decrease so it will go out
// 							else {
// 						
// 							
// 								rightMotor.setSpeed(FORWARD_SPEED - 50 );
// 								leftMotor.setSpeed(FORWARD_SPEED + 60);
// 							
// 							}
// 							
// 							
// 							
// 							if( Math.abs( odo.getTheta() - theta ) < 5 ) break;
// 							
// 							try { Thread.sleep(100); } catch(Exception e){} 
// 		}
// 		}
 				
 					
 				
 				
 				
 				
 	}
 	// Checks if the robot is navigating or not
 	
 	boolean isNavigating(){
 		if(travelToCalled || turnToCalled){
 			return true;
 		}else{
 			return false;
 		}
 	}
 	
 	int getDist(UltrasonicSensor us){
 		return us.getDistance();
 	}
 	
 	int getFilteredDistA(){
 		
// 		int filterControl = 0;
// 		int FILTER_OUT = 5;
// 		int distance = usSensorA.getDistance();
// 		int filteredVal = 0;
// 		if (distance == 255 && filterControl < FILTER_OUT) {
// 			// bad value, do not set the distance var, however do increment the filter value
// 			filterControl ++;
// 		} else if (distance == 255){
// 			// true 255, therefore set distance to 255
// 			filteredVal = distance;
// 		} else {
// 			// distance went below 255, therefore reset everything.
// 			filterControl = 0;
// 			filteredVal = distance;
// 			
// 		}
// 		return filteredVal;
 		
 		return USData.getUS1Data(); 
 	}
 	
 	
 	
 	boolean wentOverDestination(double iniX, double iniY, double destX, double destY , double curX, double curY){
 		boolean over = false;
// 		double distance = Math.sqrt((destX-curX)*(destX-curX)+(destY-curY)*(destY-curY));
// 		double currentX = odo.getX();
// 		double currentY = odo.getY();
 		double limit = 10;
 		//left-bottom ~ right-top
 		if(iniX <= destX 
 				&& iniY <= destY 
 				&& (curX - destX > limit || curY - destY > limit) ){
 			over = true;
 			
 		}
 		//left-top ~ right-bottom
 		else if(iniX <= destX 
 				&& iniY >= destY 
 				&& (curX - destX > limit || curY - destY < -1*limit) ){
 			over = true;
 		}
 		
 		//right-bottom ~ left~top
 		else if(iniX >= destX 
 				&& iniY <= destY 
 				&& (curX - destX < -1*limit || curY - destY > limit) ){
 			over = true;
 		}
 		
 		//right-top ~ left-bottom
 		else if(iniX >= destX 
 				&& iniY >= destY 
 				&& (curX - destX < -1*limit || curY - destY < -1*limit) ){
 			over = true;
 		}
 		
 		return over;
 	}
 	
 	int getFilteredDistB(){
 		
// 		int filterControl = 0;
// 		int FILTER_OUT = 5;
// 		int distance = usSensorB.getDistance();
// 		int filteredVal = 0;
// 		if (distance == 255 && filterControl < FILTER_OUT) {
// 			// bad value, do not set the distance var, however do increment the filter value
// 			filterControl ++;
// 		} else if (distance == 255){
// 			// true 255, therefore set distance to 255
// 			filteredVal = distance;
// 		} else {
// 			// distance went below 255, therefore reset everything.
// 			filterControl = 0;
// 			filteredVal = distance;
// 			
// 		}
// 		return filteredVal;
 		return USData.getUS2Data(); 
 	}
 	
 	// calculates the angle based on the radius and the distance
 	// method taken from lab 2
 	private static int toAngle(double radius, double distance) {
 		return (int) ((180.0 * distance) / (Math.PI * radius));
 	}
 	// converts distance to angle
 	private static double toDistForRotationLeft( double width, double angle) {
 		return angle * Math.PI * width / (180.0 * 2.0);
 	}
 	

 }
