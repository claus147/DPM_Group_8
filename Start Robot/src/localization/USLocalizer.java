package localization;

/*
 *  @author Kornpat Choy (Claus)
 *	
 *	turns only, does not travel to (0,0)
 *	orients correctly to angle 0
 */

import java.util.Arrays;

import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import motion.Navigation;
import odometry.Odometry;

public class USLocalizer {
	public enum LocalizationType { FALLING_EDGE, RISING_EDGE };
	public static double ROTATION_SPEED = 40;
	public static double SECOND_ROTATION_SPEED = 20;

	public static double angleA, angleB;	//made public so set/get methods dont need to be written to display
	public static double turn = 0.0;		//turn to value (the corrected theta)
	public static int distance = 0;			//distance from wall measured by usSensor					
	public static int distance2 = 0;	
	private Odometry odo;
	private Navigation nav;
	
	private UltrasonicSensor usL = new UltrasonicSensor(SensorPort.S3);
	private UltrasonicSensor usR = new UltrasonicSensor(SensorPort.S4);
	private LocalizationType locType;
	private int noWall = 80;				//distance to be considered "no wall" - for usSensor
	
	public USLocalizer(Odometry odo, UltrasonicSensor usR,UltrasonicSensor usL, LocalizationType locType) {
		this.odo = odo;
		//this.usL = usL;
		//this.usR = usR;
		this.locType = locType;
		this.nav = new Navigation(odo);
		//makes sure us is working
		//usL.reset();
		//usR.reset();
	}
	
	public void doLocalization() {
		double [] pos = new double [3];			//to get/set odometer x,y, theta values
		boolean[] update = new boolean [3];		//tells which of x,y,theta to update
		Arrays.fill(update, Boolean.TRUE);
		double dtheta;							//the change in theta
		
		
		if (locType == LocalizationType.FALLING_EDGE) {
			// rotate the robot until it sees no wall
			//robot.setRotationSpeed(0);
			// switch direction and wait until it sees no wall
			// keep rotating until the robot sees a wall, then latch the angle
			// angleA is clockwise from angleB, so assume the average of the
			// angles to the right of angleB is 45 degrees past 'north'
			
			distance = getFilteredDataR();					//start getting distance from usSensor
			nav.keepRotating(ROTATION_SPEED);
			//robot.setRotationSpeed(ROTATION_SPEED); 			//rotate clockwise first
			
			while (distance < noWall){ 							//while facing wall (can detect wall)
				distance = getFilteredDataR();				//keep getting distance from usR
			}
			odo.getPosition(pos, update);						//finally doesnt find the wall, record position from odometer
			angleA = pos[2];									//set the angle given by odometer
			
			
			
			nav.keepRotating(-ROTATION_SPEED);
			//robot.setRotationSpeed(-ROTATION_SPEED); 			//now anticlockwise
				
			//sleep so it doesnt think that the wall edge just detected is the other wall edge
			try { Thread.sleep(3000); } catch (InterruptedException e) {}		
			
			distance2= getFilteredDataL();						//start getting distance from us again
			while (distance2 < noWall){ 							//while facing wall (can detect wall)
				distance2 = getFilteredDataL();				//keep getting distance from usL
			}
			odo.getPosition(pos, update);						//finally doesnt find the wall, record position from odometer
			angleB = pos[2];									//set the angle given by odometer
		

		} else {
			/*
			 * The robot should turn until it sees the wall, then look for the
			 * "rising edges:" the points where it no longer sees the wall.
			 * This is very similar to the FALLING_EDGE routine, but the robot
			 * will face toward the wall for most of it.
			 */
			
			distance = getFilteredDataR();				//start getting distance from usSensor
			nav.keepRotating(ROTATION_SPEED);
			//robot.setRotationSpeed(ROTATION_SPEED); 		//rotate clockwise first
			
			while (distance >= noWall){ 					//while facing away from wall (cant detect wall)
				distance = getFilteredDataR();			//keep getting distance from usSensor
			}
			odo.getPosition(pos, update);							//finally finds the wall, record position from odometer
			angleA = pos[2];								//set the angle given by odometer
			
			
			distance2= noWall;								//set to noWall value so it doesnt accidently detect a wall
			nav.keepRotating(-ROTATION_SPEED);
			//robot.setRotationSpeed(-ROTATION_SPEED); 		//now anticlockwise
			//sleep so it doesnt think that the wall edge just detected is the other wall edge
			try { Thread.sleep(3000); } catch (InterruptedException e) {}
			
			while (distance2 >= noWall){ 					//while facing away from wall (cant detect wall)
				distance2 = getFilteredDataL();			//keep getting distance from usSensor
			}
			
			odo.getPosition(pos, update);					//finally finds the wall, record position from odometer
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
		
		
		//do the turns to get to the (0,0)
		while (pos[2] < turn){
			odo.getPosition(pos, update);
			nav.keepRotating(SECOND_ROTATION_SPEED);
			//robot.setRotationSpeed(SECOND_ROTATION_SPEED);
		}
		while (pos[2] > turn){
			odo.getPosition(pos, update);
			nav.keepRotating(-SECOND_ROTATION_SPEED);
			//robot.setRotationSpeed(-SECOND_ROTATION_SPEED);
		}
			
		nav.keepRotating(0);
		
		//set the new theta to 0
		pos[2] = 0;
		update [2] = true;
		odo.setPosition(pos, update);
	}
	
	private int getFilteredDataL() {			//do a reading every 50 miliseconds
		int distanceL;

		// do a ping
		usL.ping();
		
		 // wait for the ping to complete
		try { Thread.sleep(50); } catch (InterruptedException e) {}

		distanceL = usL.getDistance();
		
		 if (distanceL > noWall)				//if distance > 80 (noWall) set it to 80
			 distanceL = noWall;

				
		return distanceL;
	}
	private int getFilteredDataR() {			//do a reading every 50 miliseconds
		int distanceR;

		// do a ping
		usR.ping();
		
		 // wait for the ping to complete
		try { Thread.sleep(50); } catch (InterruptedException e) {}

		distanceR = usR.getDistance();
		
		 if (distanceR > noWall)				//if distance > 80 (noWall) set it to 80
			 distanceR = noWall;

				
		return distanceR;
	}

}