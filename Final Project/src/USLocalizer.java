/*
 * USLocalizer.java
 * Kornpat Choy 260454385
 * Nabil Zoldjalali 260450152
 *	
 *	turns only, does not travel to (0,0)
 *	orients correctly to angle 0
 */

import lejos.nxt.UltrasonicSensor;

public class USLocalizer {
	public enum LocalizationType { FALLING_EDGE, RISING_EDGE };
	public static double ROTATION_SPEED = 40;
	public static double SECOND_ROTATION_SPEED = 20;

	public static double angleA, angleB;	//made public so set/get methods dont need to be written to display
	public static double turn = 0.0;		//turn to value (the corrected theta)
	public static int distance = 0;			//distance from wall measured by usSensor					
	private Odometer odo;
	private TwoWheeledRobot robot;
	private Navigation nav;
	private UltrasonicSensor us;
	private LocalizationType locType;
	private int noWall = 80;				//distance to be considered "no wall" - for usSensor
	
	public USLocalizer(Odometer odo, UltrasonicSensor us, LocalizationType locType) {
		this.odo = odo;
		this.robot = odo.getTwoWheeledRobot();
		this.us = us;
		this.locType = locType;
		this.nav = new Navigation(odo);
		//makes sure us is working
		us.reset();
	}
	
	public void doLocalization() {
		double [] pos = new double [3];			//to get/set odometer x,y, theta values
		boolean[] update = new boolean [3];		//tells which of x,y,theta to update
		double dtheta;							//the change in theta
		
		
		if (locType == LocalizationType.FALLING_EDGE) {
			// rotate the robot until it sees no wall
			//robot.setRotationSpeed(0);
			// switch direction and wait until it sees no wall
			// keep rotating until the robot sees a wall, then latch the angle
			// angleA is clockwise from angleB, so assume the average of the
			// angles to the right of angleB is 45 degrees past 'north'
			
			distance = getFilteredData();						//start getting distance from usSensor
			nav.keepRotating(ROTATION_SPEED);
			//robot.setRotationSpeed(ROTATION_SPEED); 			//rotate clockwise first
			
			while (distance < noWall){ 							//while facing wall (can detect wall)
				distance = getFilteredData();					//keep getting distance from usSensor
			}
			odo.getPosition(pos);								//finally doesnt find the wall, record position from odometer
			angleA = pos[2];									//set the angle given by odometer
			
			
			
			nav.keepRotating(-ROTATION_SPEED);
			//robot.setRotationSpeed(-ROTATION_SPEED); 			//now anticlockwise
			
			//sleep so it doesnt think that the wall edge just detected is the other wall edge
			try { Thread.sleep(3000); } catch (InterruptedException e) {}		
			
			distance= getFilteredData();						//start getting distance from us again
			while (distance < noWall){ 							//while facing wall (can detect wall)
				distance = getFilteredData();					//keep getting distance from usSensor
			}
			odo.getPosition(pos);								//finally doesnt find the wall, record position from odometer
			angleB = pos[2];									//set the angle given by odometer
		

		} else {
			/*
			 * The robot should turn until it sees the wall, then look for the
			 * "rising edges:" the points where it no longer sees the wall.
			 * This is very similar to the FALLING_EDGE routine, but the robot
			 * will face toward the wall for most of it.
			 */
			
			distance = getFilteredData();					//start getting distance from usSensor
			nav.keepRotating(ROTATION_SPEED);
			//robot.setRotationSpeed(ROTATION_SPEED); 		//rotate clockwise first
			
			while (distance >= noWall){ 					//while facing away from wall (cant detect wall)
				distance = getFilteredData();				//keep getting distance from usSensor
			}
			odo.getPosition(pos);							//finally finds the wall, record position from odometer
			angleA = pos[2];								//set the angle given by odometer
			
			
			distance= noWall;								//set to noWall value so it doesnt accidently detect a wall
			nav.keepRotating(-ROTATION_SPEED);
			//robot.setRotationSpeed(-ROTATION_SPEED); 		//now anticlockwise
			//sleep so it doesnt think that the wall edge just detected is the other wall edge
			try { Thread.sleep(3000); } catch (InterruptedException e) {}
			
			while (distance >= noWall){ 					//while facing away from wall (cant detect wall)
				distance = getFilteredData();				//keep getting distance from usSensor
			}
			
			odo.getPosition(pos);							//finally finds the wall, record position from odometer
			angleB = pos[2];								//set the angle given by odometer
			
		}
		
		//Now calculations
		double sumAngles = angleA + angleB;				//if added is more than 360, minus 360 from it
		if (sumAngles >= 360.0)
			sumAngles =- 360.0;
		
		if (angleA > angleB){							//formula's finding theta from tutorial
			dtheta = 45.0 - (angleA + angleB)/2.0;
		}  else {
			dtheta = 225.0 - (angleA + angleB)/2.0;
		}
		
		turn = 360 - dtheta; 							//where i want it to turn to
		if (turn < 0)									//setting turn correctly if < 0 (add 360)
			turn = turn + 360;
		
		if (turn >= 360.0){								//setting turn correctly if >= 360 (minus 360)
			turn = turn -360.0;
		}
		
		
		//code for making the turning process extremely efficient - turn min angle (took too long to get to do the right thing so commented out)
		/**if (turn - pos[2] > 1noWall){ //if it would be inefficient to turn counter clockwise
			while (pos[2] < 360){
				odo.getPosition(pos);
				robot.setRotationSpeed(ROTATION_SPEED);
			}			
			while (pos[2] < turn){
				odo.getPosition(pos);
				robot.setRotationSpeed(ROTATION_SPEED);
			}
		} else {
			//while (pos[2] > 1noWall){
			//	odo.getPosition(pos);
			//	robot.setRotationSpeed(-ROTATION_SPEED);
			//}
			while (pos[2] > turn){
				odo.getPosition(pos);
				robot.setRotationSpeed(-ROTATION_SPEED);
			}
			
		}*/
		//do the turns to get to the (0,0)
		while (pos[2] < turn){
			odo.getPosition(pos);
			nav.keepRotating(SECOND_ROTATION_SPEED);
			//robot.setRotationSpeed(SECOND_ROTATION_SPEED);
		}
		while (pos[2] > turn){
			odo.getPosition(pos);
			nav.keepRotating(-SECOND_ROTATION_SPEED);
			//robot.setRotationSpeed(-SECOND_ROTATION_SPEED);
		}
			
		nav.keepRotating(0);
		//robot.setRotationSpeed(0); //dont need to stop the robot anymore as lightLocalization is called right after
		
		//set the new theta to 0
		pos[2] = 0;
		update [2] = true;
		odo.setPosition(pos, update);
	}
	
	private int getFilteredData() {			//do a reading every 50 miliseconds
		int distance;

		// do a ping
		us.ping();
		
		 // wait for the ping to complete
		try { Thread.sleep(50); } catch (InterruptedException e) {}

		distance = us.getDistance();
		
		 if (distance > noWall)				//if distance > 80 (noWall) set it to 80
			 distance = noWall;

				
		return distance;
	}

}
