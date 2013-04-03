package modes;

import connection.BTConnectTest; 
import connection.BluetoothConnection;
import connection.Transmission;
import data.LCDinfo;
import data.USData;
import odometry.Odometry;
import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;
import lejos.nxt.UltrasonicSensor;
import localization.LightLocalizer;
import localization.USLocalizer;
import motion.Navigation;
import motion.SquareNavigation;
import connection.StartCorner;


/**
 * 
 * This class will choose which strategy to play the game by
 * calling one of its subclasses
 * @author Tuan-Kiet 
 * @version 1.0
 */
public class AttackerMode {

	public static Odometry odo = new Odometry(true);;
	Controller control = new Controller(odo);
	
	private LightSensor lsl = new LightSensor(SensorPort.S1);
	private LightSensor lsr = new LightSensor(SensorPort.S4);
	public UltrasonicSensor usl = new UltrasonicSensor(SensorPort.S2);
	public UltrasonicSensor usr = new UltrasonicSensor(SensorPort.S3);
	
	public USData usDataL = new USData(usl , 40);
	public USData usDataR = new USData(usr , 40);
	Navigation navigate = new Navigation(odo);
	StartCorner sc = StartCorner.BOTTOM_LEFT;
	SquareNavigation sqrNav = new SquareNavigation(odo, lsl, lsr, usDataL, usDataR);

	LCDinfo lcd = new LCDinfo(odo);
	/**
	 * TODO : develop an attacker algorithm
	 * Testing class;
	 * Activate motors
	 */
	public void attackAlgorithm(){
		
		
		
		lcd.start();
		
		
		setStrategy();

		
	}
	/**
	 * TODO: Call the strategy established in AttackerStrat1
	 */
	public void setStrategy(){
		
		
		//31 March test!
		
		sqrNav.travelTo(0, 210);
		try {Thread.sleep(500);} catch (InterruptedException e) {}
		sqrNav.turnTo(270);
		try {Thread.sleep(1000);} catch (InterruptedException e) {}
		sqrNav.travelTo(90, 90);
		try {Thread.sleep(500);} catch (InterruptedException e) {}
		sqrNav.turnTo(0);
		try {Thread.sleep(500);} catch (InterruptedException e) {}
		sqrNav.travelTo(0 , 0);
		try {Thread.sleep(500);} catch (InterruptedException e) {}
		sqrNav.turnTo(0);
		
		
		
		/**
		 * 1. The robot is placed in Corner 0. You will be asked to orient the robot in a 
			particular direction. The robot is preloaded with one or more balls.
			
			2. The Start button is pressed; no further handling of the robot is permitted. On 
			button press, the Bluetooth client starts and waits for data to be received from the 
			server.
			
			3. The w1 and w2 parameters will correspond to the (x,y) coordinates of the goal. 
			Here they will take on values such that 0 < x < 6, 0 < y < 10. All other 
			parameters are ignored.
			
			4. Once parameters are received, the robot then proceeds to localize.
			5. Knowing the location of the goal, a launching position is determined.
			6. The robot then proceeds to the launch position, navigating so as to avoid obstacles 
			along the way. The figure on the previous page gives an idea of the density and 
			composition of obstacles (wooden blocks).
			7. Once at the launch position, the robot fires on the goal with one or more balls.
			8. The robot then returns to the starting position and halts.
		 */

//		double launchingAngle = 0;
//		//launch X and Y are the coordinates of the goal - 8 feet
//		double launchXBlock = 0;
//		double launchYBlock = 0;
//		double launchX = 0;
//		double launchY = 0;
//		
//		//BluetoothConnection bc = new BluetoothConnection(); //must pass this in here somewhere /************used
//		
//		//Transmission trans = bc.getTransmission();//new Transmission(); //**********used*******
//		double goalXBlock = 5;//trans.w1;
//		double goalYBlock = 9;//trans.w2;
//		if(goalYBlock > 6){ 
//			launchXBlock = goalXBlock;
//			launchYBlock = goalYBlock - 8;
//			launchingAngle = 0;
//		}else{
//			launchXBlock = goalXBlock;
//			launchYBlock = goalYBlock - 8;
//			launchingAngle = 180;
//		}
//		launchX = launchXBlock * 30;
//		launchY = launchYBlock * 30;
//		/* step 1 */
//		
//		
//		/* step 2 DONE */
//		/* step 3 */
//		// ???
//		/* step 4 */// LOCALIZATIONS
//		//usData.start();
//		//USLocalizer usLoc = new USLocalizer(odo, control, usData);
//		//usLoc.start();
//		LightLocalizer lightLocalizer = new LightLocalizer(odo, navigate, lsl, lsr, sc);
//		lightLocalizer.doLocalization();
////		try { Thread.sleep(2000); } catch (InterruptedException e) {}
//		/* step 5 + step 6, Obstacle avoidance not working yet */
//			control.travelTo(60,60);
//			control.turnTo(launchingAngle );
//		/* step 7 */
//		BTConnectTest connect = new BTConnectTest();
//		try {
//			connect.connect();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		/* step 8 */
//		control.travelTo(0, 0);
//	
	
	
		
	}
	
	
	
}

