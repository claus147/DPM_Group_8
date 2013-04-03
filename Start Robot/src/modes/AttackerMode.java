package modes;

import motion.SquareNavigation;




/**
 * 
 * This class will choose which strategy to play the game by
 * calling one of its subclasses
 * @author Tuan-Kiet 
 * @author Kornpat Choy (Claus)
 * @version 1.0
 */
public class AttackerMode {

	private int bx;	//the ball dispenser zones (bx, by)
	private int by; 
	private int w1;	//the ball bounce zone (w1,w2)
	private int w2; //right now not used
	private int d1; //the no go zone
	private SquareNavigation sqrNav;
	
	public AttackerMode(int bx, int by, int w1, int w2, int d1, SquareNavigation sqrNav){
		this.bx = bx;
		this.by = by;
		this.w1 = w1;
		this.w2 = w2;
		this.d1 = d1;
		this.sqrNav = sqrNav;
	}
	
	/**
	 * TODO : develop an attacker algorithm
	 * Testing class;
	 * Activate motors
	 */
	public void attackAlgorithm(){
		
		sqrNav.travelTo(bx + 1, by); //bx + 1 because we will add a pushing button method
		//push button method here//
		
		//goal is at (5,10)
		sqrNav.travelTo(5, d1 - 1); //travel to right in front of goal - 1 so it doesnt go in the "no go" zone
		
		//btconnect stuff to slave here//
		
		sqrNav.travelTo(0, 0); //return home
		
		//setStrategy();
		
		
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

