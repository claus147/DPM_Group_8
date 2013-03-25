package odometry;

import lejos.nxt.Motor;

/**
 * ported Tuan / Yu Yang's odometer from lab 3 (navigation & avoid lab)
 * @author Tuan-Kiet Luu
 * @author Kornpat Choy (Claus)
 * @version 1.0
 */


public class Odometry {
	// robot position
	private double x, y, theta;
	static double XPosTotal = 0;
	static double YPosTotal = 0;

	// odometer update period, in ms
	private static final long ODOMETER_PERIOD = 25;

	// lock object for mutual exclusion
	private Object lock;

	double leftRadius = 2.8;
	double rightRadius = 2.8;
	
	// default constructor
	public Odometry() {
		x = 0.0;
		y = 0.0;
		theta = 0.0;
		lock = new Object();
	}

	// run method (required for Thread)
	public void run() {
		long updateStart, updateEnd;
		


		double leftRadius = 2.8; // left wheel radius
		double rightRadius = 2.8; // right wheel radius

		double WB = 15.24;

		double currentTachoL = 0;
		double currentTachoR = 0;
		double last_tacho_l = 0;
		double last_tacho_r = 0;
		double instantDistanceL = 0;
		double instantDistanceR = 0;
		double tachoDiffL = 0;
		double tachoDiffR = 0;
		double instantDistanceDiff = 0;
		double instantAngle = 0;
		double totalAngle = 0;
		
		double XPos = 0;
		
		double YPos = 0;
	
		
		while (true) {
			updateStart = System.currentTimeMillis();

			
			//left motor = Motor A, returns in degrees
			 currentTachoL = Motor.A.getTachoCount();
			//right motor = Motor B
			 currentTachoR = Motor.B.getTachoCount();
			 
			 //Difference of tachometer degrees between the current reading and previous reading 
			 tachoDiffL = currentTachoL - last_tacho_l;
			 tachoDiffR = currentTachoR - last_tacho_r;
			 
			 //Calculate d1 = (Rw PI thetaL)/180
			 //Calculate d2 = (Rw PI thetaR/180
			 //instant distance for the right and left wheel
			 instantDistanceL = ( leftRadius * tachoDiffL * Math.PI  ) / 180.0;
			 instantDistanceR = ( rightRadius * tachoDiffR * Math.PI ) / 180.0;
			 
		 	 //Calculate d = d2 - d1
		   	 // difference between both instant readings; right wheel and left wheel
			 instantDistanceDiff = instantDistanceL - instantDistanceR;
			 
			 //Calculate theta = d / WB
			 //instant angle calculated with the above formula
			 instantAngle = instantDistanceDiff / WB; 
			 //Sum of the instant angles
			 totalAngle = totalAngle + instantAngle;
			 
			

			 //Magnitude of displacement in X and Y
			 //Formula taken from the tutorial
			 XPos = (instantDistanceL + instantDistanceR) * Math.sin( totalAngle + instantAngle / 2.0) / 2.0;
			 YPos = (instantDistanceL + instantDistanceR) * Math.cos( totalAngle + instantAngle / 2.0) / 2.0;
			
			 //Sum of previous and current X & Y position
			 XPosTotal = XPosTotal + XPos;
			 YPosTotal = YPosTotal + YPos;
			 
			 // add values from last loop
			 last_tacho_l = currentTachoL;
			 last_tacho_r = currentTachoR;

			
			
			

			synchronized (lock) {
				// don't use the variables x, y, or theta anywhere but here!
			
				//outputs these values on the screen
				x = XPosTotal;
				y = YPosTotal;
				theta =  Math.toDegrees(totalAngle) % 360;
				
			}

			// this ensures that the odometer only runs once every period
			updateEnd = System.currentTimeMillis();
			if (updateEnd - updateStart < ODOMETER_PERIOD) {
				try {
					Thread.sleep(ODOMETER_PERIOD - (updateEnd - updateStart));
				} catch (InterruptedException e) {
					// there is nothing to be done here because it is not
					// expected that the odometer will be interrupted by
					// another thread
				}
			}
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// there is nothing to be done here because it is not expected that
				// the odometer will be interrupted by another thread
			}
		}
	}

	// accessors
	public void getPosition(double[] position, boolean[] update) {
		// ensure that the values don't change while the odometer is running
		synchronized (lock) {
			if (update[0])
				position[0] = x;
			if (update[1])
				position[1] = y;
			if (update[2])
				position[2] = theta;
		}
	}

	public double getX() {
		double result;

		synchronized (lock) {
			result = x;
		}

		return result;
	}

	public double getY() {
		double result;

		synchronized (lock) {
			result = y;
		}

		return result;
	}

	public double getTheta() {
		double result;

		synchronized (lock) {
			result = theta;
		}

		return result;
	}
	// mutators
	public void setPosition(double[] position, boolean[] update) {
		// ensure that the values don't change while the odometer is running
		synchronized (lock) {
			if (update[0])
				x = position[0];
			if (update[1])
				y = position[1];
			if (update[2])
				theta = position[2];
		}
	}

	public void setX(double x) {
		//XPosTotal = x;
		synchronized (lock) {
			this.x = x;
		}
	}

	public void setY(double y) {
		//YPosTotal = y;
		synchronized (lock) {
			this.y = y;
		}
	}

	public void setTheta(double theta) {
		synchronized (lock) {
			this.theta = theta;
		}
	}
}
