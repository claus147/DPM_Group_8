package localization;

/**
 * @author Kornpat Choy (Claus)
 * 
 * can localise at any corner, if at bottom of field will end facing upfield and vice versa
 * !!assumes starting location is with the wall to its left (at any corner)
 * corrects the direction it is facing first then turns 90 and corrects the next direction
 * after will face upfield or downfield depending on corner.
 * 
 * TODO leftThreshold still not good - must configure this
 * @version 3.0 - added the any corner localisation
 */

//import modes.AttackerMode; 
import motion.Navigation;
import connection.StartCorner;
import data.LSData;
import odometry.Odometry;
import lejos.nxt.LightSensor;

public class LightLocalizer {
	private Odometry odo;
	private Navigation nav;
	private LightSensor lsL, lsR;
	private LSData LSDataL, LSDataR;					//the class that constantly polls light data (left, right)
	public static double FORWARD_SPEED = 40;			
	public static double ROTATION_SPEED = 40; 			//was 15
	public static double [] pos = new double [3];		//to access x, y theta from 2 Wheeled robot
	public static boolean[] update = new boolean [3]; 	//initialize the update
	private double leftThreshold = 0.04; 				//threshold of light sensor - left (larger value is larger tolerance - less sensitive)
	
	/**----------------TODO------------------------------------
	 * Change this leftThreshold value - fiddle around with it (its different 
	 * depending on where the card is stuck to it - we should tape the card 
	 * down on the left side (right is ok)
	 * ----------------End TODO-------------------------------*/
	
	private double rightThreshold = 0.04;				//threshold of light sensor - right (smaller value is smaller tolerance - more sensitive)
	private StartCorner sc = StartCorner.BOTTOM_LEFT; 	//set default as 0,0
	private double angle1, angle2; 						//starting angle, second angle = start angle + 90
	private boolean isLineL = false; 					//assume not on a line (left)
	private boolean isLineR = false; 					//assume not on a line (right)
	
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
		
//		if(this.sc.getId() == 2)
//			this.sc.setId(4);
//		else if(this.sc.getId() == 4)
//			this.sc.setId(2);
		
		
		this.LSDataL = new LSData(lsL, leftThreshold);
		this.LSDataR = new LSData(lsR, rightThreshold);
		angle1 = (sc.getId()-1)*90;  		//c1 facing 0, c2 facing 90, c3 facing 180, c4 facing 270
		angle2 = (angle1 + 90)% 360; 		//wrap around to 0 if 360
		
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
		angle1 = (sc.getId()-1)*90; 		//c1 facing 0, c2 facing 90, c3 facing 180, c4 facing 270
		angle2 = (angle1 + 90)% 360; 		//wrap around to 0 if 360
		
		// turn on the light
		lsL.setFloodlight(true);
		lsR.setFloodlight(true);
	}

	/**
	 * assumes wall is on its left (any corner)
	 * corrects the direction it is facing first then turns 90 and corrects the next direction
	 * after will face upfield or downfield depending on corner.
	 */
	public void doLocalization() {
		
		nav.goforward(FORWARD_SPEED);
		
		LSDataL.start();
		LSDataR.start();
	
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

		if (sc == StartCorner.BOTTOM_LEFT || sc == StartCorner.TOP_RIGHT){			//if localizing in left of field correct x first
			pos[0] = sc.getX()*30;  //turning to cm
			update[0] = true;
		} else {					//else correct y first
			pos[1] = sc.getY()*30;	//turning to cm
			update[1] = true;
		}

		pos[2] = angle1;
		update[2] = true;
		
		odo.setPosition(pos, update);
		nav.turnTo(angle2); //97
		//Sound.buzz();
		try { Thread.sleep(1000); } catch (InterruptedException e) {}
		nav.goforward(FORWARD_SPEED);
		
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
		
		//updating odometer
		if (sc == StartCorner.BOTTOM_LEFT){// || sc == StartCorner.TOP_RIGHT){ 		//if localizing in left of field correct y second
			pos[0] = 0;//sc.getY()*30;	//turn to cm
			//update[1] = true;
			pos[1] = 0;
			pos[2] = 90;
		} else if(sc == StartCorner.BOTTOM_RIGHT){					//else correct x second
			pos[0] = 300;
			pos[1] = 0;
			pos[2] = 0;
			
			//pos[0] = 300;//sc.getX()*30;	//turn to cm
			//update[0] = true;
		}else if(sc == StartCorner.TOP_RIGHT){					//else correct x second
			pos[0] = 300;
			pos[1] = 300;
			pos[2] = 270;
		}else if(sc == StartCorner.TOP_LEFT){					//else correct x second
			pos[0] = 0;
			pos[1] = 300;
			pos[2] = 180;
		}
		
		
//		
//		pos[2] = angle2;
//		update[2] = true;
		update[0] = true;
		update[1] = true;
		update[2] = true;
		odo.setPosition(pos, update);
//		
//
//		if (sc.getId() == 1 || sc.getId() == 4)	//if at the bottom of the field we want to face upfield,
//			nav.turnTo(0);
//		else									//else at top of field so face downfield
//			nav.turnTo(180);
		
		

	}
}
