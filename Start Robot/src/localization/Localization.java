package localization;

import connection.StartCorner;
import data.LCDinfo;
import odometry.Odometry;
import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import modes.Controller;
import motion.Navigation;

/**
 * This class will serve as a map for the robot. It will localize itself, the ball dispenser, the
 * zones, and the goal.
 * @author Tuan-Kiet Luu
 * @author Kornpat Choy (Claus) 
 * @version 1.0
 */

public class Localization {
	//test
	public static void main(String[] args) {
		// setup the odometer, display, and ultrasonic and light sensors
		
		Odometry odo = new Odometry(true);
		Navigation nav = new Navigation(odo);
		Controller con = new Controller(odo);
		
		LightSensor lsL = new LightSensor(SensorPort.S1);
		LightSensor lsR = new LightSensor(SensorPort.S4);
		UltrasonicSensor usL = new UltrasonicSensor(SensorPort.S2);
		UltrasonicSensor usR = new UltrasonicSensor(SensorPort.S3);
		
		StartCorner sc = StartCorner.BOTTOM_LEFT;
		
		USLocalizer usloc;
		
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
			if (usR.getDistance() < 80||usL.getDistance() < 80){							//80 is the number specified to be the "end" of wall
				usloc = new USLocalizer(odo, nav, usL, usR, USLocalizer.LocalizationType.FALLING_EDGE);
			} else {
				usloc = new USLocalizer(odo, nav, usL, usR, USLocalizer.LocalizationType.RISING_EDGE);
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
				usloc = new USLocalizer(odo, nav, usL, usR, USLocalizer.LocalizationType.RISING_EDGE);		//set to rising edge
			} else {
				usloc = new USLocalizer(odo, nav, usL, usR, USLocalizer.LocalizationType.FALLING_EDGE);		//set to falling edge
			}
		}
		
		LCDinfo lcd = new LCDinfo(odo);								//start the display
		lcd.start();
		// perform the ultrasonic localization
		usloc.doLocalization();												
		
		// perform the light sensor localization
		LightLocalizer lsl = new LightLocalizer(odo, nav, lsL, lsR, sc);
		lsl.doLocalization();
		
		
		
		while (Button.waitForAnyPress() != Button.ID_ESCAPE);		//to exit program
		System.exit(0);
	}
}