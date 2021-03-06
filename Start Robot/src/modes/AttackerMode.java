package modes;

import connection.BTConnectTest;
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
	
	double XBricksBeforeGetBalls = 0;
	double YBricksBeforeGetBalls = 0;
	
	
	double XBricksGetBalls = 0;
	double YBricksGetBalls = 0;
	double TGetBalls = 0;
	BTConnectTest connect = new BTConnectTest();
	
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
		
		//sqrNav.travelTo(bx, by); //bx + 1 because we will add a pushing button method
		//push button method here//
		
		//goal is at (5,10)
		//sqrNav.travelTo(5, d1 - 1); //travel to right in front of goal - 1 so it doesnt go in the "no go" zone
		
		//btconnect stuff to slave here//
		
		//sqrNav.travelTo(0, 0); //return home
		
		
		
		
		if(bx == -1){
			 XBricksBeforeGetBalls = bx + 2;
			 YBricksBeforeGetBalls = by;
			 
			 XBricksGetBalls = bx + 1;
			 YBricksGetBalls = by;
			 
			 TGetBalls = 270;
			
		}else if(bx == 11){
			 XBricksBeforeGetBalls = bx - 2;
			 YBricksBeforeGetBalls = by;
			 
			 XBricksGetBalls = bx - 1;
			 YBricksGetBalls = by;
			 
			 TGetBalls = 90;
			
		}else if(by == -1){
			 XBricksBeforeGetBalls = bx;
			 YBricksBeforeGetBalls = by + 2;
			 
			 XBricksGetBalls = bx;
			 YBricksGetBalls = by + 1;
			 
			 TGetBalls = 180;
			
		}else if(by == 11){
			 XBricksBeforeGetBalls = bx;
			 YBricksBeforeGetBalls = by - 2;
			 
			 XBricksGetBalls = bx;
			 YBricksGetBalls = by - 1;
			 
			 TGetBalls = 0;
			
		}else{
			
		}
		setStrategy();
		
		
	}
	/**
	 * TODO: Call the strategy established in AttackerStrat1
	 */
	public void setStrategy(){
		
		
//		double bx = -1;
//		double by = 4;
//		double w1 = 4;
//		double w2 = 4;
//		double d1 = 8;
//		double totalY = 8;
	

//		sqrNav.travelTo( (int)bx * 30, (int) by * 30);
//		try {Thread.sleep(500);} catch (InterruptedException e) {}
//		sqrNav.relocalize(sqrNav.lastBrickX, sqrNav.lastBrickY, sqrNav.lastBrickT);
//		/* PUSH THE DISPENSER */
//		
//		
//		/* */
//		// y = 11
//		sqrNav.travelTo( 5*30 , 2 ) ;
//		try {Thread.sleep(500);} catch (InterruptedException e) {}
//		sqrNav.turnTo(0);


		
		// ==== TEST 1 : Square drive 3x3
		
//		
//		
//		sqrNav.travelTo(0,90);
//		try {Thread.sleep(500);} catch (InterruptedException e) {}
//		sqrNav.travelTo( 90,90);
//		try {Thread.sleep(500);} catch (InterruptedException e) {}
//		sqrNav.travelTo( 90,0);
//		try {Thread.sleep(500);} catch (InterruptedException e) {}
//		sqrNav.travelTo( 0,0);
//		try {Thread.sleep(500);} catch (InterruptedException e) {}
//		double x = sqrNav.lastBrickX;
//		double y = sqrNav.lastBrickY;
//		double t = sqrNav.lastBrickT;
//		sqrNav.relocalize(x, y, t);

		
		// ==== TEST 2 : 7 points and go 0,0
		
		/*
		sqrNav.travelTo(60, 90);
		try {Thread.sleep(500);} catch (InterruptedException e) {}
		sqrNav.travelTo(30, 60);
		try {Thread.sleep(500);} catch (InterruptedException e) {}
		sqrNav.travelTo(0,0);
		try {Thread.sleep(500);} catch (InterruptedException e) {}
		sqrNav.travelTo(90, 90);
		try {Thread.sleep(500);} catch (InterruptedException e) {}
		sqrNav.travelTo(0,30);
		try {Thread.sleep(500);} catch (InterruptedException e) {}
		sqrNav.travelTo(90,60);
		try {Thread.sleep(500);} catch (InterruptedException e) {}
		sqrNav.travelTo(0,0);
		try {Thread.sleep(500);} catch (InterruptedException e) {}
		*/
		
		// ==== TEST 3 : ADD BRICKS
		
		
		// ================================================ START COMPETITION ALGO ===================================================
		//travel to reloc position to get balls
		//int counter=0;
		while(true){
			
			
		
			sqrNav.travelTo( (int)XBricksBeforeGetBalls * 30, (int) YBricksBeforeGetBalls * 30);
			try {Thread.sleep(500);} catch (InterruptedException e) {}
			//reloc
			sqrNav.relocalize(sqrNav.lastBrickX, sqrNav.lastBrickY, sqrNav.lastBrickT);
			try {Thread.sleep(500);} catch (InterruptedException e) {}
			//turn to appropriate angle
			sqrNav.turnTo(TGetBalls);
			try {Thread.sleep(500);} catch (InterruptedException e) {}
		    // travel to the brick 1 unit away from dispenser, the angle should be already good
			sqrNav.travelTo( (int)XBricksGetBalls * 30, (int) YBricksGetBalls * 30);
			try {Thread.sleep(500);} catch (InterruptedException e) {}
			// get the balls
			sqrNav.getBalls(sqrNav.lastBrickX, sqrNav.lastBrickY, sqrNav.lastBrickT);
			try {Thread.sleep(500);} catch (InterruptedException e) {}
			// go to launching position
			//sqrNav.travelTo( (int)5 * 30, (int) 2 * 30);
			sqrNav.travelTo( (int)5 * 30, (int) 2 * 30);
			try {Thread.sleep(500);} catch (InterruptedException e) {}
			
			
			sqrNav.turnTo(0);
			try {Thread.sleep(500);} catch (InterruptedException e) {}
			sqrNav.relocalize(sqrNav.lastBrickX, sqrNav.lastBrickY, sqrNav.lastBrickT);
			try {Thread.sleep(500);} catch (InterruptedException e) {}
			sqrNav.launchLoc(sqrNav.lastBrickX, sqrNav.lastBrickY, sqrNav.lastBrickT);
			try {
				connect.connect('a');
			try {Thread.sleep(60000);} catch (InterruptedException e) {}	
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//counter++;
		}
//		sqrNav.travelTo(0,0);
//		
//		//travel to launch position
//		//sqrNav.travelTo( (int)5 * 30, (int) d1 * 30); ///////////************************************************* LAUNCH���Уϣӣɣԣɣϣ�
//		try {Thread.sleep(500);} catch (InterruptedException e) {}
//		
//		try {Thread.sleep(500);} catch (InterruptedException e) {}
//
//		/* START PUSH THE DISPENSER */
		
//		sqrNav.travelTo( 0*30 , 5*30 );
//		try {Thread.sleep(500);} catch (InterruptedException e) {}
//		double currentX = sqrNav.lastBrickX;
//		double currentY = sqrNav.lastBrickY;
//		double currentT = sqrNav.lastBrickT;
//		sqrNav.relocalize(currentX, currentY, currentT);
//		sqrNav.turnTo(270);
//		currentX = sqrNav.lastBrickX;
//		currentY = sqrNav.lastBrickY;
//		currentT = sqrNav.lastBrickT;
//		sqrNav.getBalls(currentX, currentY, currentT);
//		/* END PUSH DISPENSER */
//		try {Thread.sleep(500);} catch (InterruptedException e) {}
//		sqrNav.travelTo( 0*30 , 0*30 );
//		currentX = sqrNav.lastBrickX;
//		currentY = sqrNav.lastBrickY;
//		currentT = sqrNav.lastBrickT;
//		sqrNav.relocalize(currentX, currentY, currentT);
//		
//		try {Thread.sleep(500);} catch (InterruptedException e) {}
//		sqrNav.turnTo(0);
//	
		// ================================================ END COMPETITION ALGO ===================================================

		

//		usloc.doLocalization();
//		
//		lsloc.doLocalization();
//		


		/* ========================== BEFORE COMPETITION ALGO ============================
		sqrNav.travelTo(90, 120);
=======
		
		sqrNav.travelTo(180, 90);
		try {Thread.sleep(500);} catch (InterruptedException e) {}
		sqrNav.relocalize(sqrNav.lastBrickX, sqrNav.lastBrickY, sqrNav.lastBrickT);
		try {Thread.sleep(500);} catch (InterruptedException e) {}
		sqrNav.turnTo(270);
		try {Thread.sleep(1000);} catch (InterruptedException e) {}
		sqrNav.travelTo(90, 90);
		try {Thread.sleep(500);} catch (InterruptedException e) {}
		sqrNav.relocalize(sqrNav.lastBrickX, sqrNav.lastBrickY, sqrNav.lastBrickT);
		sqrNav.turnTo(0);
		try {Thread.sleep(500);} catch (InterruptedException e) {}
		sqrNav.travelTo(0 , 0);
		try {Thread.sleep(500);} catch (InterruptedException e) {}
		sqrNav.turnTo(0);
		*/
		
		
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

