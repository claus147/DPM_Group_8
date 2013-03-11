/**
 * Will take the data from the wheel A motor (tachometer)
 * @author Tuan-Kiet Luu
 * @version 1.0
 */
import lejos.nxt.*;

public class WheelAData {
	
	private final NXTRegulatedMotor leftMotor = Motor.A, rightMotor = Motor.B;
	
	
	public WheelAData(){
		
		
		
	}
	
	
	public void TravelTo(){
		
		
		leftMotor.rotate(50);
		rightMotor.rotate(50);
		
		
	}
	
	
}
