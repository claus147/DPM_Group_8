package modes;
import odometry.Odometry;
import lejos.nxt.Sound;


/**
 * 
 * This class will choose which strategy to play the game by
 * calling one of its subclasses
 * @author Tuan-Kiet 
 * @version 1.0
 */
public class AttackerMode {

	Controller control = new Controller();
	
	
	
	
	/**
	 * TODO : develop an attacker algorithm
	 * Testing class;
	 * Activate motors
	 */
	public void attackAlgorithm(){
		
		Odometry odo = new Odometry();
		odo.start();
		control.travelTo(60, 60);
		
	}
	/**
	 * TODO: Call the strategy established in AttackerStrat1
	 */
	public void setStrategy(){
		
		
		
	}
	
	
	
}

