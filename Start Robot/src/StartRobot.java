import lejos.nxt.LCD;


import lejos.nxt.*;

/**
 * The Class StartRobot.
 * 
 * @author Tuan-Kiet 
 * 		   This class will be the main one that calls the other
 *         classes to start the robot It will take as input the coordinates of
 *         the ball dispenser. This class will also call what mode the robot
 *         will play the game; Attacker or Defender
 */
public class StartRobot {

	private static AttackerMode attackerMode = new AttackerMode();
	private static DefenderMode defenderMode = new DefenderMode();
	private static LSData LSdata = new LSData();
	
	
	/*public static void main(String args[]){
	 
		System.out.println("StartRobot Class Online...");
		attackerMode.checkAccess();
		defenderMode.checkAccess();
		LSdata.checkAccess();
		
		
		
	}
	*/
	
	public void timedOut(){
		
		
		LCD.clear();
		LCD.drawString("LSData: ", 0, 0);
		LCD.drawInt(LSdata.data, 5, 0);
	}
	
	
	
	
	
}
