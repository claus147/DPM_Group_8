import lejos.nxt.Sound;


/**
 * The Class DefenderMode.
 * 
 * @author Tuan-Kiet
 */
public class DefenderMode {

	
	Controller control = new Controller();
	

	
	/**
	 * TODO : develop a defender algorithm
	 * Testing class;
	 * Activate motors
	 */
	public void defendAlgorithm(){
		
		Sound.beep();
		control.activateMotors();
		
		
	}
	/**
	 * TODO: Call the strategy established in the DefenderStrat1
	 */
	public void setStrategy(){
		
		
	}
	
}
