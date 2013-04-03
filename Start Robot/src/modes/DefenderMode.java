package modes;
import motion.SquareNavigation;


/**
 * The Class DefenderMode.
 * 
 * @author Kornpat Choy (Claus)
 * @author Tuan-Kiet
 */
public class DefenderMode {

	
//	Controller control = new Controller();
//	
//
//	
//	/**
//	 * TODO : develop a defender algorithm
//	 * Testing class;
//	 * Activate motors
//	 */
//	public void defendAlgorithm(){
//		
//		Sound.beep();
//		control.activateMotors();
//		
//		
//	}
	private int w1; //the ball bounce zone (w1,w2) defender no go
	private int w2;
	private int d1; //the defender no go zones
	private SquareNavigation sqrNav;
	
	public DefenderMode(int w1, int w2, int d1, SquareNavigation sqrNav){
		this.w1 = w1;
		this.w2 = w2;
		this.d1 = d1;
		this.sqrNav = sqrNav;
	}
	/**
	 * TODO: Call the strategy established in the DefenderStrat1
	 */
	public void defenseAlgorithm(){
		
		//goal at 5, 10
		sqrNav.travelTo(5, w2 -1); //stand infront of the goal in x and the "no go" zone in y
	}
	
}
