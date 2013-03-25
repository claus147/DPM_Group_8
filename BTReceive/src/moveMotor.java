import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;

/**
 * @author Tuan-Kiet Luu
 * @version 1.0
 */

/**
 * @author Tuan-Kiet
 *
 */
public class moveMotor {
	

	public static void main(String [] args){
		
		LCD.drawString("ReadMe",0,1);
		
		NXTRegulatedMotor leftMotor = Motor.A;
		leftMotor.rotate(100);
	}
	

}
