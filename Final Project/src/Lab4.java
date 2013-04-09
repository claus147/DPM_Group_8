/*
 * Lab4.java
 * Kornpat Choy 260454385
 * Nabil Zoldjalali 260450152
 *
 * Did not use a Navigation class
 * Odometer.java (from Zip file lab 4) unchanged
 * TwoWheeledRobot.java unchanged
 */

import lejos.nxt.*;

public class Lab4 {

	public static void main(String[] args) {
		// setup the odometer, display, and ultrasonic and light sensors
		TwoWheeledRobot patBot = new TwoWheeledRobot(Motor.A, Motor.B);
		
		Odometer odo = new Odometer(patBot, true);
		
		UltrasonicSensor us = new UltrasonicSensor(SensorPort.S2);
		LightSensor ls = new LightSensor(SensorPort.S1);
		USLocalizer usl;
		
		int buttonChoice; // left/right menu display
		
		do {
			// clear the display
			LCD.clear();

			//auto mode auto detects if it is facing a wall or not and runs the FALLING_EDGE or RISING EDGE that it deems fits
			LCD.drawString("< Left | Right >", 0, 0);	//select brings you to the next menu so you can choose to be falling or rising edge
			LCD.drawString("  Auto | Select ", 0, 1);	

			buttonChoice = Button.waitForAnyPress();
		} while (buttonChoice != Button.ID_LEFT
				&& buttonChoice != Button.ID_RIGHT);
		
		if (buttonChoice == Button.ID_LEFT) {					//for the auto selector
			if (us.getDistance() < 80){							//80 is the number specified to be the "end" of wall
				usl = new USLocalizer(odo, us, USLocalizer.LocalizationType.FALLING_EDGE);
			} else {
				usl = new USLocalizer(odo, us, USLocalizer.LocalizationType.RISING_EDGE);
			}
		} else {												//brings into new menu if "selection" is selected	
			buttonChoice = -1;									//not left id or right id
			do {
				// clear the display
				LCD.clear();

				LCD.drawString("< Left | Right >", 0, 0);
				LCD.drawString("FaceOut| FaceIn ", 0, 1);		//left is rising edge (facing out/away)
				LCD.drawString("Rising | Falling", 0, 2);		//right is falling edge (facing in)

				buttonChoice = Button.waitForAnyPress();
			} while (buttonChoice != Button.ID_LEFT
					&& buttonChoice != Button.ID_RIGHT);

			if (buttonChoice == Button.ID_LEFT) {
				usl = new USLocalizer(odo, us, USLocalizer.LocalizationType.RISING_EDGE);		//set to rising edge
			} else {
				usl = new USLocalizer(odo, us, USLocalizer.LocalizationType.FALLING_EDGE);		//set to falling edge
			}
		}
		
		LCDInfo lcd = new LCDInfo(odo);								//start the display
		
		// perform the ultrasonic localization
		//usl.doLocalization();												
		
		// perform the light sensor localization
		LightLocalizer lsl = new LightLocalizer(odo, ls);
		lsl.doLocalization();
		
		
		
		while (Button.waitForAnyPress() != Button.ID_ESCAPE);		//to exit program
		System.exit(0);
	}

}
