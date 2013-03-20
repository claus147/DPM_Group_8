/*
 * Lab3.java
 * Nabil Zoldjalali 260450152
 * Kornpat Choy 260454385
 * 
 * modified first lab interface for this class
 * 
 * added new class - Navigation.java
 * 
 * added Odometer.java (unmodified)
 * added OdometeryDisplay.java (unmodified)
 * added UltrasonicController.java (unmodified)
 * added UltrasonicPoller.java (unmodified)
 */


import lejos.nxt.*;
public class Lab3 {


	public static void main(String[] args){
	
TwoWheeledRobot patBot = new TwoWheeledRobot(Motor.A, Motor.B);
		
		Odometer odometer = new Odometer(patBot, true); 								//odometer
		//OdometryDisplay odometryDisplay = new OdometryDisplay(odometer); 	//odometer display
		UltrasonicSensor usSensor = new UltrasonicSensor(SensorPort.S2); 	//ultrasonic sensor
		Navigation nav = new Navigation(odometer);
		
		//choice for part 1 - navigate or part 2 - avoid of the lab
		int buttonChoice;
	
		do {
			// clear the display
			LCD.clear();

			// navigate or avoid
			LCD.drawString("< Left | Right >", 0, 0);
			LCD.drawString("       |        ", 0, 1);
			LCD.drawString(" Navig | Avoid  ", 0, 2);

			buttonChoice = Button.waitForAnyPress();
		} while (buttonChoice != Button.ID_LEFT
				&& buttonChoice != Button.ID_RIGHT);

		if (buttonChoice == Button.ID_LEFT) {
			//nav.turnTo(90.0);
			nav.keepRotating(100);

		} else {
//
//			Navigation part2 = new Navigation(1, odometer, usSensor); 			//1 is the selection for part 2 of lab
///			part2.start(); 														//our code from Navigation class - part 2 of lab
//			odometryDisplay.start(); 											//start display
//			UltrasonicPoller poller = new UltrasonicPoller(usSensor, part2); 	//initialise poller
//			poller.start(); 													//start poller
		}
		
		while (Button.waitForAnyPress() != Button.ID_ESCAPE);
		System.exit(0);
	}

		
	
}
