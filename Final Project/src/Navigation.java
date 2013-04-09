import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;


public class Navigation {
	// put your navigation code here 
	
	private static final NXTRegulatedMotor leftMotor = Motor.A, rightMotor = Motor.B;
	public static final double DEFAULT_LEFT_RADIUS = 2.75;
	public static final double DEFAULT_RIGHT_RADIUS = 2.75;
	public static final double DEFAULT_WIDTH = 23.7;
	private Odometer odo;
	public static int FORWARD_SPEED = 5;
	public static int ROTATION_SPEED = 15;
	double [] pos = new double [3];
	double [] wantedPos = new double [3];
	boolean[] update = new boolean [3];
	private static Object lock = new Object(); 		// lock object for mutual exclusion
	
	public Navigation(Odometer odo) {
		this.odo = odo;
	}
	
	public void travelTo(double x, double y) {
		// USE THE FUNCTIONS setForwardSpeed and setRotationalSpeed from TwoWheeledRobot!
		//navigating = true; //required from spec
		wantedPos[0] = x;
		wantedPos[1] = y;
		double distance;

		//while (isNavigating()){ //robot will keep coradRightecting itself untill deemed close enough to required coordinates
			
			odo.getPosition(pos); //get odo readings
			
			if (pos[2]> 180)	//turning to the +/- 180 format
				pos[2]=-360;
			pos[2] = Math.toRadians(pos[2]); //turn into radians
			
			if (pos[2]> 180)	//turning to the +/- 180 format
				pos[2]=-360;
			pos[2] = Math.toRadians(pos[2]); //turn into radians
				
			// Making sure the theta by the odometer is between -pi and pi 
			//ie anticlockwise 0->180 positive, clockwise -0 -> -180 negative
			if (pos[2]  <= - Math.PI && pos[2]  >= (-2*Math.PI))
				pos[2] = pos[2] + 2*Math.PI; 							
			else if (pos[2]  > Math.PI && pos[2]  <=(2*Math.PI))
				pos[2] = pos[2] - 2*Math.PI;
	
			
			// Calculating the desiredTheta - needed to do above as atan2 need both pos and neg values of theta depending on orientation
			wantedPos[2] = Math.atan2(wantedPos[1] - pos[1], wantedPos[0] - pos[0]);
			
			
			// Finding distance to travel depending on coordinates
			 distance = Math.sqrt(Math.pow(wantedPos[1] - pos[1], 2) + Math.pow(wantedPos[0] - pos[0], 2));
			 
			 
			 // if not coradRightect angle, turn
			//if (pos[2]  < (wantedPos[2] - angleError) || pos[2] > (wantedPos[2] + angleError) ){
			//	turnTo(wantedPos[2]);
			//}
			 
			if (pos[2] != wantedPos[2]){
				turnTo(wantedPos[2]);
			}

			synchronized (lock) { //the actual forward move

				leftMotor.setSpeed(FORWARD_SPEED);
				rightMotor.setSpeed(FORWARD_SPEED);
			
				leftMotor.forward(); 
				rightMotor.forward();
				
				leftMotor.rotate(convertDistance(DEFAULT_LEFT_RADIUS, distance), true);
				rightMotor.rotate(convertDistance(DEFAULT_LEFT_RADIUS, distance), false); 
			}		
			
			// end condition for while loop at top - (if robot is close enough to deem successful in reaching coords)
			//if (Math.abs(this.x - desiredX) < positionError && Math.abs(this.y - desiredY) < positionError){
			//	navigating = false;
			//}	 
		
		//}
		
		
	}
	
	public void turnTo(double angle) {
		// USE THE FUNCTIONS setForwardSpeed and setRotationalSpeed from TwoWheeledRobot!
		//navigating = true; //starts navigating (by spec)
		
		double deltaTheta = pos[2] - wantedPos[2];
		//may not rotate efficiently - not 100% sure
		
		// Making sure the rotation is minimal between -180 and 180 
		//- changed to 360, doesnt seem to make a difference
		if (deltaTheta  <= - Math.PI && deltaTheta  >= (-2*Math.PI))
			deltaTheta = deltaTheta + 2*Math.PI;
		else if (deltaTheta  > Math.PI && deltaTheta  <=(2*Math.PI))
			deltaTheta = deltaTheta - 2*Math.PI;
	
	   // Converting deltaTheta to degrees
		double temp = Math.toDegrees(deltaTheta);
		deltaTheta = temp;
		
		synchronized (lock) { //the rotation
			leftMotor.setSpeed(ROTATION_SPEED); 
			rightMotor.setSpeed(ROTATION_SPEED);

			leftMotor.rotate(convertAngle(DEFAULT_LEFT_RADIUS, DEFAULT_WIDTH, deltaTheta), true); 
			rightMotor.rotate(-convertAngle(DEFAULT_RIGHT_RADIUS, DEFAULT_WIDTH, deltaTheta), false);
		}
	}
	
	/**
	 * converts to distance in number of tachoturns - radius in deg
	 * @param radius - of wheel
	 * @param distance - to move forward in cm
	 * @return - tacho degrees needed to turn
	 */
	private  int convertDistance(double radius, double distance) {
		return (int) ((180.0 * distance) / (Math.PI * radius));
	}
	
	
	/**
	 * converts angle needed to turn in degrees to tachocounts needed to rotate by
	 * @param radius - of wheel
	 * @param width, width apart of both wheels
	 * @param angle, wanted to turn - degrees
	 * @return - the equivalent tacho turns
	 */
	private  int convertAngle(double radius, double width, double angle) {
		return convertDistance(radius, Math.PI * width * angle / 360.0);
	}
	//Continuous rotation (+ve is clock wise, -ve is anti)
	public void keepRotating(double rotationalSpeed) {
		double leftSpeed, rightSpeed;

		leftSpeed = (rotationalSpeed * DEFAULT_WIDTH * Math.PI / 360.0) *
				180.0 / (DEFAULT_LEFT_RADIUS * Math.PI);
		rightSpeed = (- rotationalSpeed * DEFAULT_WIDTH * Math.PI / 360.0) *
				180.0 / (DEFAULT_RIGHT_RADIUS * Math.PI);

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

}
