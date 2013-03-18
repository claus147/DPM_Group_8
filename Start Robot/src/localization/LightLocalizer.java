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
	private LightSensor lsL, lsR;
	public static double FORWARD_SPEED = 1;			
	public static double ROTATION_SPEED = 40; //was 15
	public static int lightReading;					//value from light sensor
	public static int prevLR;
	public static double darknessEdge = 510.0;		//the value the lightsensor reading must drop below to consider passing a line
	private double lightMountOffSetL = 2.0;			//offset from center was 21.5
	private double lightMountOffSetR = 2.0;
	public static double [] thetas = new double [4]; //first 2 entries are for start for x then y last 2 are for end x then y
	public static double x, y, xTheta, yTheta;
	public static double [] pos = new double [3];	//to access x, y theta from 2 Wheeled robot
	public static int average = 0;
	public static double thetaCorrTo;
	
	
	public LightLocalizer(Odometry odo, Navigation nav, LightSensor lsL, LightSensor lsR) {
		this.odo = odo;
		this.nav = nav;
		this.lsL = lsL;
		this.lsR = lsR;
		
		// turn on the light
		lsL.setFloodlight(true);
		lsR.setFloodlight(true);
	}
	
	public void doLocalization() { //assumes facing 0 theta at start.
		// drive to location listed in tutorial
		// start rotating and clock all 4 gridlines
		// do trig to compute (0,0) and 0 degrees
		// when done travel to (0,0) and turn to 0 degrees
		

		boolean[] update = new boolean [3]; //initialize the update (for two wheeled robot)
		
		//Attempted to get the average of the light readings in the first 5 seconds, did not work very well 
		
		
		double [] lReadingsL = new double[5]; //5 readings stored at each instance (left)
		
		double [] lReadingsR = new double[5]; //5 readings stored at each instance (right)
		
		//------------initialize light array (left)-------------
		for (int i = 0; i < 5; i++){ //reading 5 times, once every second - initial array
			lReadingsL[i] = getLightReading(lsL);
			try { Thread.sleep(200); } catch (InterruptedException e) {} //sleep 1/4 of a sec
		}
		
		//get the average
		for (int i = 0; i < 5; i++){
			average = average + (int) lReadingsL[i]; //adding up the values in array
		}
		average = average/5;
		//-----------end left init---------------
		
		//--------initialize light array (right)
		for (int i = 0; i < 5; i++){ //reading 5 times, once every second - initial array
			lReadingsR[i] = getLightReading(lsR);
			try { Thread.sleep(200); } catch (InterruptedException e) {} //sleep 1/4 of a sec
		}
		
		//get the average
		for (int i = 0; i < 5; i++){
			average = average + (int) lReadingsR[i]; //adding up the values in array
		}
		average = average/5;
		//------------end right init--------------
		
		
		int prevAvg = 0;
		int counter = 0;				//count for array of light readings
		int threshold;
		
		nav.goforward(ROTATION_SPEED);
		
		getLightReading(lsL);	
		boolean isLineL = false;
		boolean isLineR = false;
		
		while (!isLineL){ //while not crossing the negative x axis (left)
			
			if (counter == 5) //always wrap around
				counter = 0;
			lReadingsL[counter] = getLightReading(lsL);
			
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
		
		nav.travelTo(0, 0);
		
	}
	
	public int getLightReading(LightSensor side){				//to read light sensor
		 // wait for the ping to complete
		try { Thread.sleep(50); } catch (InterruptedException e) {}		//50 milisecond sleep
		
		lightReading = side.getNormalizedLightValue();
		//lightReading = ls.readValue();
		
		return lightReading;
	}
	

}
