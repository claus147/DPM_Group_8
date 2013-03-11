import lejos.nxt.*;

/**
 * Will take data from the Lightsensor
 * @author Tuan-Kiet Luu
 * @version 1.0
 */


public class LSData {

	private LightSensor ls;
	int data;
	
	/*
	public LSData(){	
		this.data = ls.readValue();
	}
*/
	
	public void checkAccess(){
		System.out.println("LSData Class Online...");
		
	}
	
	public void takeData(){
		int counter = 0;
		
		ls.setFloodlight(true);
		
		while(counter < 5){
		    data = ls.readValue();
			try{Thread.sleep(5000);} catch(InterruptedException e){}
			counter++;
		}
	
		ls.setFloodlight(false);
		
	}
	
	public void printSomething(){
		
		LCD.clear();
		LCD.drawString("YO", 0, 0);
	}
	
	
	
	
	
	
	
}
