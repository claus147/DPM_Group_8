package modes;
import odometry.Odometry;
import lejos.nxt.Sound;
import motion.Navigation;


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
		Navigation nav = new Navigation(odo);
		odo.start();
		double headingAngle = nav.findHeadingAngle(-60,60,odo.getX(), odo.getY() );
		control.turnTo(headingAngle);
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				
			}
 		
		control.travelTo(-60, 60);
		
	}
	/**
	 * TODO: Call the strategy established in AttackerStrat1
	 */
	public void setStrategy(){
		
		
		
	}
	
	
	
}

