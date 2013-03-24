package localization;

/**
 * @author Kornpat Choy (Claus)
 *
 * @version 2.0
 */

import motion.Navigation;
import data.LSData;
import odometry.Odometry;
import lejos.nxt.LightSensor;
import lejos.nxt.Sound;

public class LightLocalizer {
	private Odometry odo;
	private Navigation nav;
	private LightSensor lsL, lsR;
	private LSData LSDataL, LSDataR;
	public static double FORWARD_SPEED = 1;			
	public static double ROTATION_SPEED = 40; //was 15
	public static double [] pos = new double [3];	//to access x, y theta from 2 Wheeled robot
	
	public LightLocalizer(Odometry odo, Navigation nav, LightSensor lsL, LightSensor lsR) {
		this.odo = odo;
		this.nav = nav;
		this.lsL = lsL;
		this.lsR = lsR;
		this.LSDataL = new LSData(lsL);
		this.LSDataR = new LSData(lsR);
		
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

		nav.goforward(ROTATION_SPEED);
		
		LSDataL.start();
		LSDataR.start();
		boolean isLineL = false;
		boolean isLineR = false;
		
		while (!isLineL || !isLineR){ //while not crossing the negative x axis (left)
			
			isLineL = LSDataL.getIsLine();
			if (isLineL) {
				nav.stop(Navigation.WheelSide.LEFT);
				LSDataL.stop();
			}
			
			isLineR = LSDataR.getIsLine();
			if (isLineR){
				nav.stop(Navigation.WheelSide.RIGHT);
				LSDataR.stop();
			}
			
		}
		
		
		pos[0] = 0;
		pos[2] = 0;
		update[0] = true;
		update[2] = true;
		
		odo.setPosition(pos, update);
		nav.turnTo(97);
		//Sound.buzz();
		nav.goforward(ROTATION_SPEED);
		
		isLineL = false;
		isLineR = false;
		
		LSDataL.start();
		LSDataR.start();
		while (!isLineL || !isLineR){ //while not crossing the y axis 
			
			isLineL = LSDataL.getIsLine();
			if (isLineL) {
				nav.stop(Navigation.WheelSide.LEFT);
				LSDataL.stop();
			}
			
			isLineR = LSDataR.getIsLine();
			if (isLineR){
				nav.stop(Navigation.WheelSide.RIGHT);
				LSDataR.stop();
			}
			
		}
		
		pos[1] = 0;
		pos[2] = 90;
		update[1] = true;
		update[2] = true;
		odo.setPosition(pos, update);
		
		nav.turnTo(355); //cant turnTo(0) something wrong with turnTo method.
	
		while(true){}
	}
}
