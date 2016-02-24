package org.usfirst.frc.team4041.robot;

import edu.wpi.first.wpilibj.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
// hey hey hey hey 
public class Robot extends SampleRobot {
	long shootSpeed = 2000;
	double loadSpeed = .75;
	Talon right = new Talon(0);
	Talon left = new Talon(1);
	Talon getBall = new Talon(2);
	Talon feedBall = new Talon(3);
	Talon shootBall = new Talon(4);
	Talon liftBot = new Talon(5);
	Talon extendLift = new Talon(6);
	//CANTalon shotBall = new CANTalon(0)
	Joystick rightJ = new Joystick(0);
	Joystick leftJ = new Joystick(1);
	RobotDrive driveTrain = new RobotDrive(right, left);
	Timer timer = new Timer();
	CameraServer server;
    public Robot() {
    	server = CameraServer.getInstance();
        server.setQuality(50);
        //the camera name (ex "cam0") can be found through the roborio web interface
        server.startAutomaticCapture("cam0");
    }
    //This function is called during autonomous
    public void autonomous() {
    	timer.start();
    	while(isAutonomous() && isEnabled()){
    		double timeNow = timer.get();
	    	if(timeNow <= 10){
	    		right.set(1);
	    		left.set(1);
	    	}
	    	else{
	    		right.set(0);
	    		left.set(0);
	    	}
    	}
    }
    //This function is called during operator control
    public void operatorControl() {
        while(isOperatorControl() && isEnabled()){
        	driveTrain.tankDrive(rightJ, leftJ);
	    	//Changes the speed of the loader
	        if ((rightJ.getRawButton(6) || leftJ.getRawButton(6)) && loadSpeed < 1){
	        	loadSpeed = loadSpeed + .1;
	        }
	        else if ((rightJ.getRawButton(7) || leftJ.getRawButton(7)) && loadSpeed > -1){
	        	loadSpeed = loadSpeed - .1;
	    	}
	        else{
	        }
	    	//Brings the ball in
	        if(rightJ.getRawButton(3) || leftJ.getRawButton(3)){
	        	getBall.set(loadSpeed*-1);
	        	feedBall.set(loadSpeed);
	        }
	        //Expels unwanted balls
	        else if(rightJ.getRawButton(2) || leftJ.getRawButton(2)){
	        	getBall.set(loadSpeed);
	        	feedBall.set(loadSpeed*-1);
	        }
	        else{
	        	getBall.set(0);
	        	feedBall.set(0);
	        }
	        //Shoots the ball
	        if(leftJ.getRawButton(1) || rightJ.getRawButton(1)){
	        	shootBall.set(-.9);
	        }
	        else{
	        	shootBall.set(0);
	        }
	        if(leftJ.getRawButton(4) || rightJ.getRawButton(4)){
	        	liftBot.set(1);
	        }
	        else{
	        	liftBot.set(0);
	        }
	        if(leftJ.getRawButton(5) || rightJ.getRawButton(5)){
	        	extendLift.set(.25);
	        }
	        else{
	        	extendLift.set(0);
	        }
	        //Possible CAN implementation
	        /*if(leftJ.getRawButton(1)||rightJ.getRawButton(1)){
    			shootBall.changeControlMode(CANTalon.TalonControlMode.Speed);
    			shootBall.set(shootSpeed);
    		}*/
        }
    }
}
