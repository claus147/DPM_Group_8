package localization;

/*
 * @author Kornpat Choy (Claus)
 *
 * code assumes the robot did USLocaliser successfully and it is facing the positive y axis
 * moves fowards to the x axis, as one light hits the line it will stop the wheel on that side 
 * and same for the other side.
 * repeat after rotating 90 degrees with y axis
 * 
 */

import motion.Navigation;
import odometry.Odometry;
import lejos.nxt.LightSensor;
import lejos.nxt.Sound;

public class LightLocalizer {
	private Odometry odo;
	private Navigation nav;
	private LightSensor ls;
	public static double FORWARD_SPEED = 1;			
	public static double ROTATION_SPEED = 40; //was 15
	public static int lightReading;					//value from light sensor
	public static int prevLR;
	public static double darknessEdge = 510.0;		//the value the lightsensor reading must drop below to consider passing a line
	private double lightMountOffSet = 12.0;			//offset from center was 21.5
	public static double [] thetas = new double [4]; //first 2 entries are for start for x then y last 2 are for end x then y
	public static double x, y, xTheta, yTheta;
	public static double [] pos = new double [3];	//to access x, y theta from 2 Wheeled robot
	public static int average = 0;
	public static double thetaCorrTo;
	
	
	public LightLocalizer(Odometry odo, Navigation nav, LightSensor ls) {
		this.odo = odo;
		this.nav = nav;
		this.ls = ls;
		
		// turn on the light
		ls.setFloodlight(true);
	}
	
	public void doLocalization() { //assumes facing 0 theta at start.
		// drive to location listed in tutorial
		// start rotating and clock all 4 gridlines
		// do trig to compute (0,0) and 0 degrees
		// when done travel to (0,0) and turn to 0 degrees
		

		boolean[] update = new boolean [3]; //initialize the update (for two wheeled robot)
		
		//Attempted to get the average of the light readings in the first 5 seconds, did not work very well 
		
		
		double [] lReadings = new double[5]; //5 readings stored at each instance
		
		for (int i = 0; i < 5; i++){ //reading 5 times, once every second - initial array
			lReadings[i] = getLightReading();
			try { Thread.sleep(200); } catch (InterruptedException e) {} //sleep 1/4 of a sec
		}
		
		
		for (int i = 0; i < 5; i++){
			average = average + (int) lReadings[i]; //adding up the values in array
		}
		average = average/5;
		
		int prevAvg = 0;
		int counter = 0;				//count for array of light readings
		int threshold;
		
		nav.keepRotating(ROTATION_SPEED);
		odo.getPosition(pos, update);
		int count = 0;	//number of lines crossed
		getLightReading();	
		while (count < 4){ //while not crossing the negative y axis
			
			//300 milisecond sleep (if reduced could increase odo's theta accuracy
			//try { Thread.sleep(300); } catch (InterruptedException e) {}	
			
			if (counter == 5) //always wrap around
				counter = 0;
			lReadings[counter] = getLightReading();
			
			prevAvg = average;
			average = 0; //reset the average
			for (int i = 0; i < 5; i++){
				average = average + (int) lReadings[i]; //adding up the values in array
			}
			average = average/5;		//getting actual average
			counter++;
			
			threshold = (int) (average * 0.01); //1% threshold
			//prevLR = lightReading;
			//getLightReading();						//get the light reading
			
			odo.getPosition(pos, update);					//get pos from odometer
			if((prevAvg - average) > threshold  ){
	//		if(lightReading < darknessEdge){		//if passes a line
	
				Sound.beepSequence();				//perform beep
				thetas[count] = pos[2];				//"thetas" hold the value for (init theta of x, y and final theta x,y) 
													//		see initialised variables for details 
				count++;							
			}
			
		}
		nav.keepRotating(0);				//stop rotating when hits the negative y axis 

		//double thetaCorrTo;							//second theta correction (first in USLocaliser)
		
		if (thetas[3]< thetas[1])					//if angle wraps around past 360 , undo the wrap
			thetas[3] = thetas[3] + 360.0;
		
		xTheta = thetas[2] - thetas[0];				//getting xTheta
		yTheta = thetas[3] - thetas[1];				//getting yTheta
		y = (- lightMountOffSet * Math.cos(Math.toRadians(xTheta/2.0))); 		//get x
		x = (- lightMountOffSet * Math.cos(Math.toRadians(yTheta/2.0)));		//get y (formula from tutorial notes)
		//thetaCorrTo = 90.0 + yTheta/2.0 + (thetas[3] - 180.0);										//get corrected theta
		//thetaCorrTo = thetaCorrTo + thetas[3];
		thetaCorrTo = 90.0 - yTheta/2.0;
		
		if (thetaCorrTo > 360)
			thetaCorrTo = thetaCorrTo - 360.0;
		
		pos[0] = x;
		pos[1] = y;
		pos[2] = 360.0 - thetaCorrTo;											//setting pos so to set odometer
				
		update[0] = true;
		update[1] = true;
		update[2] = true;
		odo.setPosition(pos, update);											//setting odometer with new values
		
		ls.setFloodlight(false);												//turn off light sensor to save battery
		
		nav.turnTo(0);
/*		while (pos[2] >180){													//turn to theta = 0
			odo.getPosition(pos);
			robot.setRotationSpeed(ROTATION_SPEED);
		}
		robot.setRotationSpeed(0);
*/		
		//Light Localization done, faces 0 direction but not on (0,0) yet
		
		
		
		/* moving to (0,0)
		 * start by moving forward until y = 0
		 * turn to 90 degrees
		 * move until x = 0
		 * turn to 0 degrees
		 * done
		 */

	/*	while (pos[1] < 0){							//moves to y = 0
			odo.getPosition(pos);
			robot.setForwardSpeed(FORWARD_SPEED);	
		}
		robot.setForwardSpeed(0);
		try { Thread.sleep(500); } catch (InterruptedException e) {}
		
		
		while (pos[2] <90){							//turns to 90 degrees
			odo.getPosition(pos);
			robot.setRotationSpeed(ROTATION_SPEED);
		}
		robot.setRotationSpeed(0);
		try { Thread.sleep(500); } catch (InterruptedException e) {}
		
		
		while (pos[0] <0){							//moves to x = 0
			odo.getPosition(pos);
			robot.setForwardSpeed(FORWARD_SPEED);
		}
		robot.setForwardSpeed(0);
		try { Thread.sleep(500); } catch (InterruptedException e) {}
		
		
		while (pos[2] > 0){							//turn to 0 degrees
			odo.getPosition(pos);
			robot.setRotationSpeed(-ROTATION_SPEED);
			if (pos[2] > 180)						// if accidently passes 0 then stop
				break;
		}
		robot.setRotationSpeed(0);
		try { Thread.sleep(500); } catch (InterruptedException e) {}
		
		//done
		*/
	}
	
	public int getLightReading(){				//to read light sensor
		 // wait for the ping to complete
		try { Thread.sleep(50); } catch (InterruptedException e) {}		//50 milisecond sleep
		
		lightReading = ls.getNormalizedLightValue();
		//lightReading = ls.readValue();
		
		return lightReading;
	}

}
