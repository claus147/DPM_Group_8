package localization;

/*
 *  @author Kornpat Choy (Claus)
 *	
 *	turns only, does not travel to (0,0)
 *	orients correctly to angle 0
 */

import java.util.Arrays;

import data.USData;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;
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
	private USData USDataL, USDataR;
	
	
	public USLocalizer(Odometry odo, Navigation nav,UltrasonicSensor usR,UltrasonicSensor usL, LocalizationType locType) {
		this.odo = odo;
		this.usL = usL;
		this.usR = usR;
		this.locType = locType;
		this.nav = nav;
		this.USDataL = new USData(usL);
		this.USDataR = new USData(usR);
	}
	
	public void doLocalization() {
		double [] pos = new double [3];			//to get/set odometer x,y, theta values
		boolean[] update = new boolean [3];		//tells which of x,y,theta to update
		Arrays.fill(update, Boolean.TRUE);
		double dtheta;							//the change in theta
		
		USDataL.start();
		USDataR.start();
		boolean isWallL = false;
		boolean isWallR = false;
		
		if (locType == LocalizationType.FALLING_EDGE) {
			// rotate the robot until it sees no wall
			//robot.setRotationSpeed(0);
			// switch direction and wait until it sees no wall
			// keep rotating until the robot sees a wall, then latch the angle
			// angleA is clockwise from angleB, so assume the average of the
			// angles to the right of angleB is 45 degrees past 'north'
			
			nav.turnClockWise();//rotate clockwise first		
			
			//nav.turnClockWise();
			while (isWallR){ //while not crossing the negative x axis (left)
				isWallR = USDataR.getIsWall();
				if (!isWallR){
					nav.stop();
					USDataR.stop();
				}
				
			}
			odo.getPosition(pos);						//finally doesnt find the wall, record position from odometer
			angleA = pos[2];									//set the angle given by odometer
			
			
			nav.turnCounterClockWise();					//now anticlockwise		
				
			//sleep so it doesnt think that the wall edge just detected is the other wall edge
			try { Thread.sleep(3000); } catch (InterruptedException e) {}		
			
			//distance2= getFilteredDataL();						//start getting distance from us again
			while (isWallL){ //while not crossing the negative x axis (left)
				isWallL = USDataL.getIsWall();
				if (!isWallL){
					nav.stop();
					USDataL.stop();
				}	
			}
			odo.getPosition(pos);						//finally doesnt find the wall, record position from odometer
			angleB = pos[2];									//set the angle given by odometer
			//nav.stop();

		} else {
			/*
			 * The robot should turn until it sees the wall, then look for the
			 * "rising edges:" the points where it no longer sees the wall.
			 * This is very similar to the FALLING_EDGE routine, but the robot
			 * will face toward the wall for most of it.
			 */
			
			//distance = getFilteredDataR();				//start getting distance from usSensor
			nav.turnClockWise();
			while (!isWallR){ //while not crossing the negative x axis (left)
				isWallR = USDataR.getIsWall();
				if (isWallR){
					nav.stop();
					USDataR.stop();
				}
				
			}
		
			Sound.beepSequence();
			odo.getPosition(pos);							//finally finds the wall, record position from odometer
			angleA = pos[2];								//set the angle given by odometer
			

			nav.turnCounterClockWise();
			
			//sleep so it doesnt think that the wall edge just detected is the other wall edge
			try { Thread.sleep(3000); } catch (InterruptedException e) {}	
			
			while (!isWallL){ //while not crossing the negative x axis (left)
				isWallL = USDataL.getIsWall();
				if (isWallL){
					nav.stop();
					USDataL.stop();
				}	
			}
			Sound.beepSequence();
			odo.getPosition(pos);					//finally finds the wall, record position from odometer
			angleB = pos[2];						//set the angle given by odometer
			//nav.stop();
			
		}
		USDataL.stop();
		USDataR.stop();
		
		
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
		
		nav.turnTo(turn);
		
		
//		//do the turns to get to the (0,0)
//		while (pos[2] < turn){
//			odo.getPosition(pos);
//			nav.keepRotating(SECOND_ROTATION_SPEED);
//			//robot.setRotationSpeed(SECOND_ROTATION_SPEED);
//		}
//		while (pos[2] > turn){
//			odo.getPosition(pos, update);
//			nav.keepRotating(-SECOND_ROTATION_SPEED);
//			//robot.setRotationSpeed(-SECOND_ROTATION_SPEED);
//		}
//			
//		nav.keepRotating(0);
		
		//set the new theta to 0
		pos[2] = 0;
		update [2] = true;
		odo.setPosition(pos, update);
	}

}