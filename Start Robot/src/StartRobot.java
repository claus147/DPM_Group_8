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

	
	
	public static void main(String args[]){
	 
		
		UltrasonicSensor us = new UltrasonicSensor(SensorPort.S2);
		LightSensor ls = new LightSensor(SensorPort.S1);
		int buttonChoice;

		do {
			// clear the display
			LCD.clear();

			// ask the user whether the motors should drive in a square or float
			LCD.drawString("<Atker  | Def>", 0, 0);
			LCD.drawString("        |         	 ", 0, 1);
			LCD.drawString(" Mode   | Mode"			, 0, 2);
			LCD.drawString(" edge   |  "			, 0, 3);
				

			buttonChoice = Button.waitForAnyPress();
		} while (buttonChoice != Button.ID_LEFT
				&& buttonChoice != Button.ID_RIGHT);
		// perform the ultrasonic localization
		
		if (buttonChoice == Button.ID_LEFT) {
/*			
			LCDInfo lcd = new LCDInfo(odo);
			USLocalizer usl = new USLocalizer(odo, us, USLocalizer.LocalizationType.FALLING_EDGE);
		//	usl.doLocalization();
			LightLocalizer lsl = new LightLocalizer(odo, ls);
			lsl.doLocalization();	
		} else {
			LCDInfo lcd = new LCDInfo(odo);
			USLocalizer usl = new USLocalizer(odo, us, USLocalizer.LocalizationType.RISING_EDGE);
		//	usl.doLocalization();
			
*/
			
			
			
			WheelAData wheelA = new WheelAData();
			wheelA.TravelTo();
		}else{

			LSData LSdata = new LSData();
			LSdata.takeData();
			LSdata.printSomething();
			
		}
	
		
// 		perform the light sensor localization
//		LightLocalizer lsl = new LightLocalizer(odo, ls);
//		lsl.doLocalization();			
		
		Button.waitForAnyPress();
		
		
		
	}
	
	/*
	public void timedOut(){
		
		
		LCD.clear();
		LCD.drawString("LSData: ", 0, 0);
		LCD.drawInt(LSdata.data, 5, 0);
	}
	*/
	/*
	public void checkClassAccess(){
		
			System.out.println("StartRobot Class Online...");
		attackerMode.checkAccess();
		defenderMode.checkAccess();
		LSdata.checkAccess();
	}
	
	*/
	
	
	
}
