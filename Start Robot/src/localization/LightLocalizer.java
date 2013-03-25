package localization;

/**
 * @author Kornpat Choy (Claus)
 *
 * @version 2.0
 */

import motion.Navigation;
import connection.StartCorner;
import data.LSData;
import odometry.Odometry;
import lejos.nxt.LightSensor;

public class LightLocalizer {
	private Odometry odo;
	private Navigation nav;
	private LightSensor lsL, lsR;
	private LSData LSDataL, LSDataR;
	public static double FORWARD_SPEED = 1;			
	public static double ROTATION_SPEED = 40; //was 15
	public static double [] pos = new double [3];	//to access x, y theta from 2 Wheeled robot
	private double leftThreshold = 0.06;
	private double rightThreshold = 0.01;
	private StartCorner sc = StartCorner.BOTTOM_LEFT; //set default as 0,0
	private double angle1, angle2; //starting angle, second angle = start angle + 90
	
	/**
	 * Constructor for Light localization - all params to be set
	 * @param odo - the odometer
	 * @param nav - navigation
	 * @param lsL - left light sensor
	 * @param lsR - right light sensor
	 * @param sc - startCorner
	 */
	public LightLocalizer(Odometry odo, Navigation nav, LightSensor lsL, LightSensor lsR, StartCorner sc) {
		this.odo = odo;
		this.nav = nav;
		this.lsL = lsL;
		this.lsR = lsR;
		this.sc = sc;
		this.LSDataL = new LSData(lsL, leftThreshold);
		this.LSDataR = new LSData(lsR, rightThreshold);
		angle1 = (sc.getId()-1)*90;  //c1 facing 0, c2 facing 90, c3 facing 180, c4 facing 270
		angle2 = (angle1 + 90)% 360; //wrap around to 0 if 360
		
		// turn on the light
		lsL.setFloodlight(true);
		lsR.setFloodlight(true);
	}
	
	/**
	 * Constructor for Light localization - all params to be set except StartCorner sc -> default 0,0
	 * @param odo - the odometer
	 * @param nav - navigation
	 * @param lsL - left light sensor
	 * @param lsR - right light sensor
	 */
	public LightLocalizer(Odometry odo, Navigation nav, LightSensor lsL, LightSensor lsR) {
		this.odo = odo;
		this.nav = nav;
		this.lsL = lsL;
		this.lsR = lsR;
		this.LSDataL = new LSData(lsL, leftThreshold);
		this.LSDataR = new LSData(lsR, rightThreshold);
		angle1 = (sc.getId()-1)*90; //c1 facing 0, c2 facing 90, c3 facing 180, c4 facing 270
		angle2 = (angle1 + 90)% 360; //wrap around to 0 if 360
		
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
		
		if (sc.getId()<=2){
			pos[0] = sc.getX();
			update[0] = true;
		} else {
			pos[1] = sc.getY();
			update[1] = true;
		}

		pos[2] = angle1;
		update[2] = true;
		
		odo.setPosition(pos, update);
		nav.turnTo(90); //97
		//Sound.buzz();
		nav.goforward(ROTATION_SPEED);
		
		isLineL = false;
		isLineR = false;
		
		LSDataL.setIsLine(false);
		LSDataR.setIsLine(false);
		
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
		
		if (sc.getId()<=2){
			pos[1] = sc.getY();
			update[1] = true;
		} else {
			pos[0] = sc.getX();
			update[0] = true;
		}
		pos[2] = angle2;
		update[2] = true;
		odo.setPosition(pos, update);
		
		nav.turnTo(0); //cant turnTo(0) something wrong with turnTo method 355
	
		//while(true){}
	}
}
