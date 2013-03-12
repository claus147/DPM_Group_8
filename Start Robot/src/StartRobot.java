import lejos.nxt.*;

/**
 * The Class StartRobot.
 * 
 * @author Tuan-Kiet 
 * 		   This class will be the main one that calls the other
 *         classes to start the robot It will take as input the coordinates of
 *         the ball dispenser. This class will also call what mode the robot
 *         will play the game; Attacker or Defender. We used the menu from Lab04 and modified it.
 */
public class StartRobot {

	
	/*
	 * Main function will ask the user to choose between attacker (left button) or defender mode (right button).
	 * It will call AttackerMode and DefenderMode class.
	 */
	public static void main(String args[]){
	 
		Start();
	
		
	}
	
	/**
	 * Will start the robot
	 */
	public static void Start(){
		
		AttackerMode attack = new AttackerMode();
		DefenderMode defend = new DefenderMode();
		
		UltrasonicSensor us = new UltrasonicSensor(SensorPort.S2);
		LightSensor ls = new LightSensor(SensorPort.S1);
		int buttonChoice;

		do {
			// clear the display
			LCD.clear();

			// left : AttackerMode
			// right : DefenderMode
			LCD.drawString("<Atker  | Def>", 0, 0);
			LCD.drawString("        |         	 ", 0, 1);
			LCD.drawString(" Mode   | Mode"			, 0, 2);
			LCD.drawString(" edge   |  "			, 0, 3);
				

			buttonChoice = Button.waitForAnyPress();
		} while (buttonChoice != Button.ID_LEFT
				&& buttonChoice != Button.ID_RIGHT);
		// perform the ultrasonic localization
		
		//Attacker Mode
		if (buttonChoice == Button.ID_LEFT) {

		attack.attackAlgorithm();
		}
		// Defender Mode
		else{

		defend.defendAlgorithm();
			
		}
			
		
		Button.waitForAnyPress();
		
			
	}
}
