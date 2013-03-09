/*
 * LightLocalization.java
 * Kornpat Choy 260454385
 * Nabil Zoldjalali 260450152
 *
 * code assumes the robot did USLocaliser successfully and it is facing the positive y axis
 * and if it rotates clockwise it's light sensor would detect (in this order) the negative x, 
 * positive y, positive x, and positive y axis
 * it will then calculate its x, y values and correct theta (theta not corrected too well yet, still small degree error)
 * 
 * then travels to (0,0)
 */

import lejos.nxt.LightSensor;
import lejos.nxt.Sound;

public class LightLocalizer {
	private Odometer odo;
	private TwoWheeledRobot robot;
	private LightSensor ls;
	public static double FORWARD_SPEED = 1;			
	public static double ROTATION_SPEED = 40; //was 15
	public static int lightReading;					//value from light sensor
	public static int prevLR;
	public static double darknessEdge = 510.0;		//the value the lightsensor reading must drop below to consider passing a line
	private double lightMountOffSet = 22.5;			//offset from center
	public static double [] thetas = new double [4]; //first 2 entries are for start for x then y last 2 are for end x then y
	public static double x, y, xTheta, yTheta;
	public static double [] pos = new double [3];	//to access x, y theta from 2 Wheeled robot
	public static int average = 0;
	
	
	public LightLocalizer(Odometer odo, LightSensor ls) {
		this.odo = odo;
		this.robot = odo.getTwoWheeledRobot();
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
		
		for (int i = 0; i < 5; i++){ //reading 5 times, once every second
			lReadings[i] = getLightReading();
			try { Thread.sleep(200); } catch (InterruptedException e) {} //sleep 1/4 of a sec
		}
		
		
		for (int i = 0; i < 5; i++){
			average = average + (int) lReadings[i]; //adding up the values in array
		}
		average = average/5;
		
		int prevAvg = 0;
		int counter = 0;
		int threshold;
/*		double avgLightReading = 0;
		for (int i = 0; i < 5; i++){ //reading 5 times, once every second
			avgLightReading = avgLightReading + getLightReading();
			try { Thread.sleep(200); } catch (InterruptedException e) {} //sleep 1/4 of a sec
		}
		avgLightReading = avgLightReading/5.0;
	//	darknessEdge = avgLightReading - 12.0;
*/	
		
		robot.setRotationSpeed(ROTATION_SPEED);
		odo.getPosition(pos);
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
			
			threshold = (int) (average * 0.01); //1.25% threshold
			//prevLR = lightReading;
			//getLightReading();						//get the light reading
			
			odo.getPosition(pos);					//get pos from odometer
			if((prevAvg - average) > threshold  ){
	//		if(lightReading < darknessEdge){		//if passes a line
	
				Sound.beepSequence();				//perform beep
				thetas[count] = pos[2];				//"thetas" hold the value for (init theta of x, y and final theta x,y) 
													//		see initialised variables for details 
				count++;							
			}
			
		}
		robot.setRotationSpeed(0);					//stop rotating when hits the negative y axis 

		double thetaCorrTo;							//second theta correction (first in USLocaliser)
		
		xTheta = thetas[2] - thetas[0];				//getting xTheta
		yTheta = thetas[3] - thetas[1];				//getting yTheta
		y = (- lightMountOffSet * Math.cos(Math.toRadians(xTheta/2.0))); 		//get x
		x = (- lightMountOffSet * Math.cos(Math.toRadians(yTheta/2.0)));		//get y (formula from tutorial notes)
		thetaCorrTo = 90.0 - yTheta/2.0;										//get corrected theta
		
		pos[0] = x;
		pos[1] = y;
		pos[2] = 360.0 - thetaCorrTo;											//setting pos so to set odometer
				
		update[0] = true;
		update[1] = true;
		update[2] = true;
		odo.setPosition(pos, update);											//setting odometer with new values
		
		ls.setFloodlight(false);												//turn off light sensor to save battery
		
		while (pos[2] >180){													//turn to theta = 0
			odo.getPosition(pos);
			robot.setRotationSpeed(ROTATION_SPEED);
		}
		robot.setRotationSpeed(0);
		
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
