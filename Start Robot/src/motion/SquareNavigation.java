package motion;

import data.*;  
import odometry.Odometry;
import lejos.nxt.LightSensor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;
import lejos.nxt.UltrasonicSensor;


import lejos.nxt.*;
import motion.Navigation.WheelSide;


public class SquareNavigation {
	
	NXTRegulatedMotor leftMotor = WheelsData.leftMotor;
 	NXTRegulatedMotor rightMotor = WheelsData.rightMotor;
 	
	
 	public LightSensor lsLeft, lsRight;
 	public LSData lsDataL, lsDataR;
 	public LSData lsDataL2, lsDataR2;
 	public double leftThreshold = 0.04; 				//threshold of light sensor - left (larger value is larger tolerance - less sensitive)
	public double rightThreshold = 0.045;//threshold of light sensor - right (smaller value is smaller tolerance - more sensitive)
	
 	
 	
 	public UltrasonicSensor usLeft;
	public UltrasonicSensor usRight;
	public USData usDataLeft;// = new USData(new UltrasonicSensor(SensorPort.S2));
	public USData usDataRight;// = new USData(new UltrasonicSensor(SensorPort.S3));
	
	
	final double leftRadius = 2.4;
 	final double rightRadius = 2.4;
 	final double width = 17.5;
 	
 	Odometry odo;
 	
 	public double lastBrickX = 0;
 	public double lastBrickY = 0;
 	public double lastBrickT = 0;
 	
 	private static final int FORWARD_SPEED = 180;

 	private static final int ROTATE_SPEED = 130;

 	
 	public boolean justRelocalized = false;
 	public enum WheelSide { LEFT, RIGHT };
 	
 	
 	public SquareNavigation (Odometry odometer , LightSensor lsL, LightSensor lsR, USData usDL, USData usDR ) {
 		
 		this.odo = odometer;
 		this.lsLeft = lsL;
 		this.lsRight = lsR;
// 		this.usLeft = usL;
// 		this.usRight = usR;
 		
 		this.lsDataL = new LSData(lsL, 10, leftThreshold);
		this.lsDataR = new LSData(lsR, 10, rightThreshold);
		
		this.lsDataL2 = new LSData(lsL, 50, leftThreshold);
		this.lsDataR2 = new LSData(lsR, 50, rightThreshold);
		
		
		lsLeft.setFloodlight(true);
		lsRight.setFloodlight(true);
		
		this.usDataLeft = usDL;
		this.usDataRight = usDR;
 	}
 	
 	
 	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
 	int travelledBrickCount = 0; // relocalize every 4 bricks
	boolean turnNotAvoidingObstacle = false;
 	public void travelTo( int x, int y){
		
		usDataLeft.start();
		usDataRight.start();
		

		
		
		while(Math.sqrt( (odo.getX() - x) * (odo.getX() - x) + (odo.getY() - y) * (odo.getY() - y)) > 25){
			Sound.beep();
			double loopInitialX = odo.getX();
			double loopInitialY = odo.getY();
			
			int squareAngle1 = supposedSquareHeading(x, y);
			turnTo(squareAngle1);
			try {Thread.sleep(500);} catch (InterruptedException e) {}
			int squareAngle2 = squareAngle1;
			if(turnNotAvoidingObstacle){
				squareAngle2 = supposedSquareHeading(x, y);
				turnTo(squareAngle2);
				turnNotAvoidingObstacle = false;
				try {Thread.sleep(500);} catch (InterruptedException e) {}
			}
			// go in front 1 brick
			lsDataL.start();
		    lsDataR.start();
 			rightMotor.setSpeed(FORWARD_SPEED);
 			leftMotor.setSpeed(FORWARD_SPEED);
			//
 			leftMotor.rotate(toAngle(leftRadius, 40), true);
			rightMotor.rotate(toAngle(rightRadius, 40), true); 
			
			try {Thread.sleep(2500);} catch (InterruptedException e) {}
			
			
 			
			//commented to test
			
//			try {Thread.sleep(2000);} catch (InterruptedException e) {}
			
			
		    
		    boolean isLineL = false; 					//assume not on a line (left)
		    boolean isLineR = false; 
		    
				
			boolean leftDetect = false;
			boolean rightDetect = false;
			
//			boolean leftTried = false;
//			boolean leftReturnFrontTried = true;
//			boolean rightTried = false;
//			boolean rightReturnFrontTried = true;
//			
//			int leftCount = 0;
//			int rightCount = 0;
			while (!leftDetect || !rightDetect){ //while not detected one of two sides
				
				
				
				isLineR = lsDataR.getIsLine();
				if (isLineR && rightDetect == false){
					rightMotor.stop(false);
					lsDataR.stop();
					rightDetect = true;
				}
				isLineL = lsDataL.getIsLine();
				if (isLineL && leftDetect == false) {
					leftMotor.stop(false);
					lsDataL.stop();
					leftDetect =  true;
				}
				
			
				
				
				
				//if lost a line, go back to assumed position
				if(leftMotor.getRotationSpeed() == 0 && !leftDetect){
					
					leftMotor.stop(true);
					//Sound.beep();
					
					leftMotor.setSpeed(-100);
					leftMotor.rotate(toAngle(leftRadius, -10), true);
					leftDetect = true;
					try {Thread.sleep(3000);} catch (InterruptedException e) {}
					
				}
				
				if(rightMotor.getRotationSpeed() == 0  && !rightDetect){
					
					rightMotor.stop(true);
					//Sound.beep();
					
					rightMotor.setSpeed(-100);
					rightMotor.rotate(toAngle(rightRadius, -10), true);
					rightDetect = true;	
					try {Thread.sleep(3000);} catch (InterruptedException e) {}
				}
			
			}// end of motor control while loop
			travelledBrickCount++;
			justRelocalized = false;	
			try {Thread.sleep(300);} catch (InterruptedException e) {}
			
			//odometry correction
			boolean[] upd = {false, false, false};
			double[] pos = {0,0,0};
			if(squareAngle2 == 0){
				pos[0] = loopInitialX;
				upd[0] = true;
				pos[1] = loopInitialY + 30;
				upd[1] = true;
				pos[2] = squareAngle2;
				upd[2] = true;
				odo.setPosition(pos, upd);
			}else if(squareAngle2 == 90){
				pos[0] = loopInitialX + 30;
				upd[0] = true;
				pos[1] = loopInitialY;
				upd[1] = true;
				pos[2] = squareAngle2;
				upd[2] = true;
				odo.setPosition(pos, upd);
			}else if(squareAngle2 == 180){
				pos[0] = loopInitialX;
				upd[0] = true;
				pos[1] = loopInitialY - 30;
				upd[1] = true;
				pos[2] = squareAngle2;
				upd[2] = true;
				odo.setPosition(pos, upd);
			}else{
				pos[0] = loopInitialX - 30;
				upd[0] = true;
				pos[1] = loopInitialY;
				upd[1] = true;
				pos[2] = squareAngle2;		
				upd[2] = true;
				odo.setPosition(pos, upd);
			}
			//end of odo correction
		
			lastBrickX = pos[0];
			lastBrickY = pos[1];
			lastBrickT = pos[2];
			//re-localize
			if( travelledBrickCount >= 4 ){
				relocalize(pos[0],pos[1],pos[2]);
				
			}
			try {Thread.sleep(500);} catch (InterruptedException e) {}
			
			
			
			
			
			
			
		}//end of travelTo main while loop
		
		//At this point, the robot is within 25cm from the destination
		Sound.twoBeeps();
		try {Thread.sleep(500);} catch (InterruptedException e) {}
		double intersectionX = odo.getX();
		double intersectionY = odo.getY();
		
		if(x - intersectionX > 2){
			turnTo(90);
			try {Thread.sleep(1000);} catch (InterruptedException e) {}
			rightMotor.setSpeed(FORWARD_SPEED);
 			leftMotor.setSpeed(FORWARD_SPEED);
			
 			leftMotor.rotate(toAngle(leftRadius, (x - intersectionX)), true);
			rightMotor.rotate(toAngle(leftRadius, (x - intersectionX)), true);
			try {Thread.sleep(1000);} catch (InterruptedException e) {}

		}else if(x - intersectionX < -2){
			turnTo(270);
			try {Thread.sleep(1000);} catch (InterruptedException e) {}

			rightMotor.setSpeed(FORWARD_SPEED);
 			leftMotor.setSpeed(FORWARD_SPEED);
			
 			leftMotor.rotate(toAngle(leftRadius, (intersectionX - x)), true);
			rightMotor.rotate(toAngle(leftRadius, (intersectionX - x)), true);
			try {Thread.sleep(1000);} catch (InterruptedException e) {}

		}
		
		if(y - intersectionY > 2){
			turnTo(0);
			try {Thread.sleep(1000);} catch (InterruptedException e) {}

			rightMotor.setSpeed(FORWARD_SPEED);
 			leftMotor.setSpeed(FORWARD_SPEED);
			
 			leftMotor.rotate(toAngle(leftRadius, (y - intersectionY)), true);
			rightMotor.rotate(toAngle(leftRadius, (y - intersectionY)), true);
			try {Thread.sleep(1000);} catch (InterruptedException e) {}

		}else if(y - intersectionY < -2){
			turnTo(180);
			try {Thread.sleep(1000);} catch (InterruptedException e) {}

			rightMotor.setSpeed(FORWARD_SPEED);
 			leftMotor.setSpeed(FORWARD_SPEED);
			
 			leftMotor.rotate(toAngle(leftRadius, (intersectionY - y)), true);
			rightMotor.rotate(toAngle(leftRadius, (intersectionY - y)), true);
			try {Thread.sleep(1000);} catch (InterruptedException e) {}

		}
		
		
		
		
		
	}//End of travelTo()
 	
 	public void relocalize(double locX, double locY, double locT){ 
	 	if(justRelocalized == false){	
 			turn(90);
	 		
	 		lsDataL.start();
		    lsDataR.start();

			rightMotor.setSpeed(150);
			leftMotor.setSpeed(150);
		
			leftMotor.rotate(toAngle(leftRadius, 10), true);
			rightMotor.rotate(toAngle(rightRadius, 10), true); 
			try {Thread.sleep(2000);} catch (InterruptedException e) {}

			
			
			
			boolean isLineL = false; 					//assume not on a line (left)
		    boolean isLineR = false; 
			    
					
			boolean leftDetect = false;
			boolean rightDetect = false;
				
			boolean notFoundFront = false;
			boolean notFoundBack = false;
	
				
			while(!rightDetect || !leftDetect){		
					
					
				isLineR = lsDataR.getIsLine();
				if (isLineR && rightDetect == false){
					rightMotor.stop(false);
					lsDataR.stop();
					rightDetect = true;
				}
				isLineL = lsDataL.getIsLine();
				if (isLineL && leftDetect == false) {
					leftMotor.stop(false);
					lsDataL.stop();
					leftDetect =  true;
				}
					
					
	
					
					//if lost a line, go back to assumed position
				if(leftMotor.getRotationSpeed() == 0 && !leftDetect && rightDetect){
							
					leftMotor.stop(false);
							//Sound.beep();

					leftMotor.setSpeed(-150);


					leftMotor.rotate(toAngle(leftRadius, -8), false);
					
					leftDetect = true;
					
							
				}
						
				if(rightMotor.getRotationSpeed() == 0  && !rightDetect && leftDetect){
							
					rightMotor.stop(false);
							//Sound.beep();
							

					rightMotor.setSpeed(-150);

					rightMotor.rotate(toAngle(rightRadius, -8), true);
					rightDetect = true;	
					
				}
				
				if(rightMotor.getRotationSpeed() == 0 && leftMotor.getRotationSpeed() == 0 && !rightDetect && !leftDetect){
					notFoundFront = true;
					break;
					
				}
			}
			
			if(notFoundFront){
	
		 		lsDataL.start();
			    lsDataR.start();
				rightMotor.setSpeed(-150);
				leftMotor.setSpeed(-150);

			
				leftMotor.rotate(toAngle(leftRadius, -20), true);
				rightMotor.rotate(toAngle(rightRadius, -20), true); 

				try {Thread.sleep(800);} catch (InterruptedException e) {}

				
				
				
					
				while(!rightDetect || !leftDetect){		
						
						
					isLineR = lsDataR.getIsLine();
					if (isLineR && rightDetect == false){
						rightMotor.stop(false);
						lsDataR.stop();
						rightDetect = true;
					}
					isLineL = lsDataL.getIsLine();
					if (isLineL && leftDetect == false) {
						leftMotor.stop(false);
						lsDataL.stop();
						leftDetect =  true;
					}
						
						
	
						
						//if lost a line, go back to assumed position
					if(leftMotor.getRotationSpeed() == 0 && !leftDetect && rightDetect){
								
						leftMotor.stop(false);
								//Sound.beep();

						leftMotor.setSpeed(-150);

						leftMotor.rotate(toAngle(leftRadius, 5), false);
						leftDetect = true;
						
								
					}
							
					if(rightMotor.getRotationSpeed() == 0  && !rightDetect && leftDetect){
								
						rightMotor.stop(false);
								//Sound.beep();
								

						rightMotor.setSpeed(-150);

						rightMotor.rotate(toAngle(rightRadius, 5), true);
						rightDetect = true;	
						
					}
					
					if(rightMotor.getRotationSpeed() == 0  && leftMotor.getRotationSpeed() == 0 && !rightDetect && !leftDetect){
						notFoundBack = true;

						rightMotor.setSpeed(150);
						leftMotor.setSpeed(-150);

					
						leftMotor.rotate(toAngle(leftRadius, 10), true);
						rightMotor.rotate(toAngle(rightRadius, 10), true); 
						break;
						
					}
				}//end of while
			}//end of if not found in front
			
			turn(-90);
			if(notFoundBack && notFoundFront){
				return;
			}
			
			boolean[] upd = {true, true, true};
			double[] pos = {locX,locY, locT};
			odo.setPosition(pos, upd);
			travelledBrickCount = 0;
			justRelocalized = true;
	 	}
	 	
	}//end of reloc method
 	
	
	public void launchLoc(double locX, double locY, double locT) {
		lsDataL2.start();
	    lsDataR2.start();
	
		rightMotor.setSpeed(150);
		leftMotor.setSpeed(150);
	
		leftMotor.rotate(toAngle(leftRadius, 5), true);
		rightMotor.rotate(toAngle(rightRadius, 5), true);
		try {Thread.sleep(2000);} catch (InterruptedException e) {}
		leftMotor.stop();
		rightMotor.stop();
		rightMotor.setSpeed(-50);
		leftMotor.setSpeed(-50);
	
		leftMotor.rotate(toAngle(leftRadius, -10), true);
		rightMotor.rotate(toAngle(rightRadius, -10), true); 
		try {Thread.sleep(2000);} catch (InterruptedException e) {}
		
		boolean isLineL = false; 					//assume not on a line (left)
	    boolean isLineR = false; 
		    
				
		boolean leftDetect = false;
		boolean rightDetect = false;
			
	
		while(!rightDetect || !leftDetect){		
				
				
			isLineR = lsDataR2.getIsLine();
			if (isLineR && rightDetect == false){
				rightMotor.stop(false);
				lsDataR2.stop();
				rightDetect = true;
			}
			isLineL = lsDataL2.getIsLine();
			if (isLineL && leftDetect == false) {
				leftMotor.stop(false);
				lsDataL2.stop();
				leftDetect =  true;
			}
				
				
	
				
				//if lost a line, go back to assumed position
			if(leftMotor.getRotationSpeed() == 0 && !leftDetect){
						
				leftMotor.stop(false);
						//Sound.beep();
	
				leftMotor.setSpeed(100);
	
	
				leftMotor.rotate(toAngle(leftRadius, 5), true);
				
				leftDetect = true;
				lsDataL2.stop();
						
			}
					
			if(rightMotor.getRotationSpeed() == 0  && !rightDetect){
						
				rightMotor.stop(false);
						//Sound.beep();
						
	
				rightMotor.setSpeed(150);
	
				rightMotor.rotate(toAngle(rightRadius, 5), true);
				rightDetect = true;	
				lsDataR2.stop();
			}
			
			
		}

		boolean[] upd = {true, true, true};
		double[] pos = {locX,locY, locT};
		odo.setPosition(pos, upd);
		
		
	}
	
	public int supposedSquareHeading(int destX, int destY){
		
		
		try {Thread.sleep(500);} catch (InterruptedException e) {}
		boolean isWallL = false;
		boolean isWallR = false;
		isWallL = usDataLeft.getIsWall();
		isWallR = usDataRight.getIsWall();
		double currentX = odo.getX();
		double currentY = odo.getY();
		double currentT = odo.getTheta();
		double distance = Math.sqrt((destX - currentX)*(destX - currentX) + (destY - currentY)*(destY - currentY));
		
		
		double limit = 20;
		
		int heading = 0;
		
		//OBSTACLE??
		//There is an OBSTACLE
			//
			if((isWallL || isWallR) && (currentT >= 0 && currentT < 30 || currentT > 330 && currentT <= 360) && (currentY < 280) && distance > 50){ //heading 0
				
				if(destX - currentX > limit){ // at the right of robot, turn right 
					heading = 90;
				}else if(destX - currentX < -limit){// at the left of robot, turn left 
					heading = 270;
				}else if(destY - currentY < -limit){ // bottom, turn around
					heading = 180;
				}else if(destY - currentY > limit){// same direction
					if(currentX > 150){
						int check = checkObstacles(0);
						if(check == 0){
							heading = 270;
						}else if(check == 1){
							heading = 90;
						}else{
							heading = 270;
						}
						
					}else if(currentX <= 150){
						int check = checkObstacles(1);
						if(check == 1){
							heading = 90;
						}else if(check == 0){
							heading = 270;
						}else{
							heading = 90;
						}
						
					}
					
				}else{
					//???
				}
				
			}else if((isWallL || isWallR) && (currentT > 60 && currentT < 120) && (currentX < 280) && distance > 50){ // heading 90
				
				if(destY - currentY > limit){ //
					heading = 0;
				}else if(destY - currentY < -limit){//
					heading = 180;
				}else if(destX - currentX < -limit){ // 
					heading = 270;
				}else if(destX - currentX > limit){
					
					if(currentY > 150){
						int check = checkObstacles(1);
						if(check == 1){
							heading = 180;
						}else if(check == 0){
							heading = 0;
						}else{
							heading = 180;
						}
						
						
					}
					
					else{
						int check = checkObstacles(0);
						if(check == 0){
							heading = 0;
						}else if(check == 1){
							heading = 180;
						}else{
							heading = 0;
						}
					}
				}else{
					//???
				}
				
			}else if((isWallL || isWallR) && (currentT > 150 && currentT < 210) && (currentY > 20 ) && distance > 50){// heading 180
				
				if(destX - currentX > limit){ //  
					heading = 90;
				}else if(destX - currentX < -limit){// 
					heading = 270;
				}else if(destY - currentY > limit){ // 
					heading = 0;
				}else if(destY - currentY < -limit){//
					if(currentX > 150){
						int check = checkObstacles(1);
						if(check == 1){
							heading = 270;
						}else if(check == 0){
							heading = 90;
						}else{
							heading = 270;
						}
						
					}
						
					else{
						int check = checkObstacles(0);
						if(check == 0){
							heading = 90;
						}else if(check == 1){
							heading = 270;
						}else{
							heading = 90;
						}
					}
				}else{
					//???
				}
			}else if((isWallL || isWallR) && (currentT > 240 && currentT < 300) && (currentX > 20) && distance > 50){//heading 270
				
				if(destY - currentY > limit){ //
					heading = 0;
				}else if(destY - currentY < -limit){//
					heading = 180;
				}else if(destX - currentX > limit){ // 
					heading = 90;
				}else if(destX - currentX < -limit){
					if(currentY > 150){
						int check = checkObstacles(0);
						if(check == 0){
							heading = 180;
						}else if(check == 1){
							heading = 0;
						}else{
							heading = 180;
						}
					}
						
					else{
						int check = checkObstacles(1);
						if(check == 1){
							heading = 0;
						}else if(check == 0){
							heading = 180;
						}else{
							heading = 0;
						}
						
					}
				}else{
					//???
				}
			}
			
		//There is NO OBSTACLE
			
			else if(currentT >= 0 && currentT < 30 || currentT > 330 && currentT <= 360){ //heading 0
				if(destY - currentY > limit){// infront of robot, can go in the same direction
					heading = 0;
					
				}else if(destX - currentX > limit){ // at the right of robot, turn right 
					heading = 90;
					turnNotAvoidingObstacle = true;
				}else if(destX - currentX < -limit){// at the left of robot, turn left 
					heading = 270;
					turnNotAvoidingObstacle = true;
				}else if(destY - currentY < -limit){ // bottom, turn around
					heading = 180;
					turnNotAvoidingObstacle = true;
				}else{
					//???
				}
				
			}else if(currentT > 60 && currentT < 120){ // heading 90
				if(destX - currentX > limit){
					heading = 90;
				}else if(destY - currentY > limit){ //
					heading = 0;
					turnNotAvoidingObstacle = true;
				}else if(destY - currentY < -limit){//
					heading = 180;
					turnNotAvoidingObstacle = true;
				}else if(destX - currentX < -limit){ // 
					heading = 270;
					turnNotAvoidingObstacle = true;
				}else{
					//???
				}
				
			}else if(currentT > 150 && currentT < 210){// heading 180
				if(destY - currentY < -limit){//
					heading = 180;
				}else if(destX - currentX > limit){ //  
					heading = 90;
					turnNotAvoidingObstacle = true;
				}else if(destX - currentX < -limit){// 
					heading = 270;
					turnNotAvoidingObstacle = true;
				}else if(destY - currentY > limit){ // 
					heading = 0;
					turnNotAvoidingObstacle = true;
				}else{
					//???
				}
			}else if(currentT > 240 && currentT < 300){//heading 270
				if(destX - currentX < -limit){
					heading = 270;
				}else if(destY - currentY > limit){ //
					heading = 0;
					turnNotAvoidingObstacle = true;
				}else if(destY - currentY < -limit){//
					heading = 180;
					turnNotAvoidingObstacle = true;
				}else if(destX - currentX > limit){ // 
					heading = 90;
					turnNotAvoidingObstacle = true;
				}else{
					//???
				}
			}else{
				//ERROR!!! if reaches here!
			}
			
		//end of OBSTACLE? if-else's
		
		
		return heading;
		
	}// end of supposedSquareHeading() method
	
	
	public int checkObstacles(int side){ //0 = left  , 1 = right , 2 = both return the side(s) that is clear priorityzing the larger side
		int result = 0;
		if(side == 0){ // turn left
			turn(-90);
			boolean isWallL = false;
			boolean isWallR = false;
			isWallL = usDataLeft.getIsWall();
			isWallR = usDataRight.getIsWall();
			
			if(isWallL || isWallR){
				turn(180);
				isWallL = usDataLeft.getIsWall();
				isWallR = usDataRight.getIsWall();
				if(isWallL || isWallR){
					result = 2;
					return 2;
				}else{
					result = 1;
					return 1;
				}
			}else{
				result = 0;
				return 0;
			}
		}else if(side == 1){ // turn right
			turn(90);
			boolean isWallL = false;
			boolean isWallR = false;
			isWallL = usDataLeft.getIsWall();
			isWallR = usDataRight.getIsWall();
			
			if(isWallL || isWallR){
				turn(180);
				isWallL = usDataLeft.getIsWall();
				isWallR = usDataRight.getIsWall();
				if(isWallL || isWallR){
					result = 2;
					return 2;
				}else{
					result = 0;
					return 0;
				}
			}else{
				result = 1;
				return 1;
			}
		}
		return result;
	}
	
	public void turnTo( double theta ){
 		
 		
 		double currentAngle = 0;

 		currentAngle = odo.getTheta();
 		double angleDiff = theta - currentAngle;
 		
 		//to make the robot turn the smallest angle possible. angleDiff is between -180 to 180
 		if(angleDiff > 180) angleDiff -= 360;
 		else if (angleDiff < -180) angleDiff +=360;
 		

 		

 		//When travelTo method is called, when it is still traveling ,it will adjust itself
 		
 		leftMotor.setSpeed(ROTATE_SPEED);
 		rightMotor.setSpeed(ROTATE_SPEED);
 		leftMotor.setAcceleration(3000);
		rightMotor.setAcceleration(3000);
 		
		leftMotor.rotate(toAngle(leftRadius, toDistForRotationLeft( width, angleDiff)), true);
 		rightMotor.rotate(toAngle(rightRadius, - 1 * toDistForRotationLeft( width, angleDiff)), false);
 			
 		lastBrickT = theta;	
 			
 		
 		
 		
 	}
	
	public void turn( double t){
		double theta = odo.getTheta();
 		leftMotor.setAcceleration(3000);
 		rightMotor.setAcceleration(3000);
 		leftMotor.setSpeed(ROTATE_SPEED); 
 		rightMotor.setSpeed(ROTATE_SPEED);
 		
 		
 		leftMotor.rotate(toAngle(leftRadius, toDistForRotationLeft( width, t)), true);
 		rightMotor.rotate(toAngle(rightRadius, - 1 * toDistForRotationLeft( width, t)), false);
 		
 		lastBrickT = theta + t;	
 	}
	
	// calculates the angle based on the radius and the distance
	 	// method taken from lab 2
	 	private static int toAngle(double radius, double distance) {
	 		return (int) ((180.0 * distance) / (Math.PI * radius));
	 	}
	 	// converts distance to angle
	 	private static double toDistForRotationLeft( double width, double angle) {
	 		return angle * Math.PI * width / (180.0 * 2.0);
	 	}
	
	
	public void getBalls(double curX, double curY, double curT){
		leftMotor.setSpeed(80);
		rightMotor.setSpeed(80);
		
		
		
		
		//
		leftMotor.rotate(toAngle(leftRadius, 8), true);
		rightMotor.rotate(toAngle(rightRadius, 8), false);
		
		try {Thread.sleep(50000);} catch (InterruptedException e) {} //********************************** GETBALLS SLEEPTIME!!!
		Sound.buzz();
		lsDataL.start();
	    lsDataR.start();
		leftMotor.setSpeed(-1*150);
		rightMotor.setSpeed(-1*150);
		leftMotor.rotate(toAngle(leftRadius, -15), true);
		rightMotor.rotate(toAngle(rightRadius, -15), true);
		try {Thread.sleep(1000);} catch (InterruptedException e) {} 
		 boolean isLineL = false; 					//assume not on a line (left)
		    boolean isLineR = false; 
		    
				
			boolean leftDetect = false;
			boolean rightDetect = false;

			while (!leftDetect || !rightDetect){ //while not detected one of two sides
				
				
				
				isLineR = lsDataR.getIsLine();
				if (isLineR && rightDetect == false){
					rightMotor.stop(true);
					lsDataR.stop();
					rightDetect = true;
				}
				isLineL = lsDataL.getIsLine();
				if (isLineL && leftDetect == false) {
					leftMotor.stop(true);
					lsDataL.stop();
					leftDetect =  true;
				}
				
			
				
				
				
				//if lost a line, go back to assumed position
				if(leftMotor.getRotationSpeed() == 0 && !leftDetect){
					
					leftMotor.stop(true);
					//Sound.beep();
					
					leftMotor.setSpeed(ROTATE_SPEED);
					leftMotor.rotate(toAngle(leftRadius, 7), false);
					leftDetect = true;
					
				}
				
				if(rightMotor.getRotationSpeed() == 0  && !rightDetect){
					
					rightMotor.stop(true);
					//Sound.beep();
					
					rightMotor.setSpeed(ROTATE_SPEED);
					rightMotor.rotate(toAngle(rightRadius, 7), false);
					rightDetect = true;	
					
				}
			
			}// end of motor control while loop
			
			double[] pos = {0,0,0};
			boolean[] upd = {false,false,false};
			pos[0] = curX;
			upd[0] = true;
			pos[1] = curY;
			upd[1] = true;
			pos[2] = curT;
			upd[2] = true;
			odo.setPosition(pos, upd);
		
		
	}
	
	public void goforward(double forwardSpeed){
 		leftMotor.setSpeed((int)forwardSpeed);
 		rightMotor.setSpeed((int)forwardSpeed);
 		leftMotor.forward();
 	    rightMotor.forward();
 	}
 	  
 	/**
 	  * for localization - stop a single wheel
 	  * @param side (LEFT, RIGHT)
 	  */
 	public void stop(WheelSide side){
 		if (side == WheelSide.LEFT)
 	      leftMotor.setSpeed(0);
 	    else
 	      rightMotor.setSpeed(0);
 	}
 	  
 	/**
 	 * stop both wheels
 	 */
 	public void stop(){
 		leftMotor.setSpeed(0);
 		rightMotor.setSpeed(0);
 	}
 	
 	public void keepRotating(double rotationalSpeed) {
		double leftSpeed, rightSpeed;

		leftSpeed = (rotationalSpeed * width * Math.PI / 360.0) *
				180.0 / (leftRadius * Math.PI);
		rightSpeed = (- rotationalSpeed * width * Math.PI / 360.0) *
				180.0 / (rightRadius * Math.PI);

		// set motor directions
		if (leftSpeed > 0.0)
			leftMotor.forward();
		else {
			leftMotor.backward();
			leftSpeed = -leftSpeed;
		}
		
		if (rightSpeed > 0.0)
			rightMotor.forward();
		else {
			rightMotor.backward();
			rightSpeed = -rightSpeed;
		}
		
		// set motor speeds
		if (leftSpeed > 900.0)
			leftMotor.setSpeed(900);
		else
			leftMotor.setSpeed((int)leftSpeed);
		
		if (rightSpeed > 900.0)
			rightMotor.setSpeed(900);
		else
			rightMotor.setSpeed((int)rightSpeed);
	}
 	
 	public void motorsStop(){
 		leftMotor.stop(true);
 		rightMotor.stop(true);
 	}
 	
 	public void leftMotorStop(){
 		leftMotor.stop(true);
 	}
 	public void rightMotorStop(){
 		rightMotor.stop(true);
 	}
 	
 	public void goForward(){
 		leftMotor.setAcceleration(3000);
 		rightMotor.setAcceleration(3000);
 		
 		
 		
 		leftMotor.forward();
 		rightMotor.forward();
 		leftMotor.setSpeed(FORWARD_SPEED); 
 		rightMotor.setSpeed(FORWARD_SPEED);
 	}
 	
 	public void goBackward(){
 		leftMotor.setAcceleration(3000);
 		rightMotor.setAcceleration(3000);
 		
 		
 		
 		leftMotor.backward();
 		rightMotor.backward();
 		leftMotor.setSpeed(FORWARD_SPEED); 
 		rightMotor.setSpeed(FORWARD_SPEED);
 	}
 	
 	public void turnClockWise(){
 		//leftMotor.setAcceleration(3000);
 		//rightMotor.setAcceleration(3000);
 		
 		
 		leftMotor.setSpeed(ROTATE_SPEED); 
 		rightMotor.setSpeed(ROTATE_SPEED);
 		leftMotor.forward();
 		rightMotor.backward();

 	}
 	
 	public void turnCounterClockWise(){
 		//leftMotor.setAcceleration(3000);
 		//rightMotor.setAcceleration(3000);
 		
 		
 		leftMotor.setSpeed(ROTATE_SPEED); 
 		rightMotor.setSpeed(ROTATE_SPEED);
 		leftMotor.backward();
 		rightMotor.forward();

 	}
 	
	
	
	

}
