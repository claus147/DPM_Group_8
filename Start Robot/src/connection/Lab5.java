package connection;
/*
 * Yu Yang Liu
 * Tuan-Kiet Luu
 */

import lejos.nxt.*;
public class Lab5 {
	public static void main(String[] args){
		int buttonChoice;
		
		//initialize a bluetooth connection object
		BluetoothConnection connect = new BluetoothConnection();
		//this command will print the information sent via bluetooth to the LCD screen.
		connect.printTransmission();
	
		
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {}
		
		//Initialization of the motors, all 3 are for the catapults.
		
		NXTRegulatedMotor motorA = Motor.A;
		NXTRegulatedMotor motorB = Motor.B;
		NXTRegulatedMotor motorC = Motor.C;
		do {
			// clear the display
			LCD.clear();

			// asks the user to press a key to launch
			LCD.drawString("LAUNCH!!!", 0, 0);
			LCD.drawString("<  or  >    ", 0, 1);
			
			

			buttonChoice = Button.waitForAnyPress();
		} while (buttonChoice != Button.ID_LEFT
				&& buttonChoice != Button.ID_RIGHT);
		
		
		/*
		 * After the user presses a key, it will execute the following code 
		 * which sets speed of the motors relatively fast, therefore the motion
		 * will throw the ball to the destination (ground) and will bounce to the
		 * target
		 */
		if (buttonChoice == Button.ID_LEFT || buttonChoice == Button.ID_RIGHT) {
			
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {}
			
			// set all motors speed to 800
			motorA.setSpeed(800);
			motorB.setSpeed(800);
			motorC.setSpeed(800);

			//rotate 40
			motorA.rotate(40, true);
			motorB.rotate(40, true);
			motorC.rotate(40, false);
			
			//rotate -40
			motorA.rotate(-40, true);
			motorB.rotate(-40, true);
			motorC.rotate(-40, false);

		//wait for the reset button to escape this program
		while (Button.waitForAnyPress() != Button.ID_ESCAPE);
		System.exit(0);
		
		
	}

	}}
