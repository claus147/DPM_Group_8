package modes;
import connection.BTConnectTest;
import odometry.Odometry;
import lejos.nxt.LightSensor;
import lejos.nxt.Sound;
import localization.LightLocalizer;
import motion.Navigation;


/**
 * 
 * This class will choose which strategy to play the game by
 * calling one of its subclasses
 * @author Tuan-Kiet 
 * @version 1.0
 */
public class AttackerMode {
	Odometry odo = new Odometry();
	Controller control = new Controller(odo);
	private LightSensor lsr;
	private LightSensor lsl;
	Navigation navigate = new Navigation(odo);
	LightLocalizer lightLocalizer = new LightLocalizer(odo, navigate, lsl, lsr);

	
	/**
	 * TODO : develop an attacker algorithm
	 * Testing class;
	 * Activate motors
	 */
	public void attackAlgorithm(){
		
		//Odometry odo = new Odometry();
		Navigation nav = new Navigation(odo);
		odo.start();
		double headingAngle = nav.findHeadingAngle(60,120,odo.getX(), odo.getY() );
		control.turnTo(headingAngle);
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				
			}
 		
		
		
	}
	/**
	 * TODO: Call the strategy established in AttackerStrat1
	 */
	public void setStrategy(){
		
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
		
		//launch X and Y are the coordinates of the goal - 8 feet
		double launchX = 0;
		double launchY = 0;
		/* step 1 */
		/* step 2 DONE */
		/* step 3 */
		// ???
		/* step 4 */
		lightLocalizer.doLocalization();
		try { Thread.sleep(2000); } catch (InterruptedException e) {}
		/* step 5 + step 6, Obstacle avoidance not working yet */
		control.travelTo(launchX,launchY);
		/* step 7 */
		BTConnectTest connect = new BTConnectTest();
		try {
			connect.connect();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/* step 8 */
		control.travelTo(0, 0);
		
		
	}
	
	
	
}

