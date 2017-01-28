// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc2876.Steamworks2017.subsystems;

import org.usfirst.frc2876.Steamworks2017.RobotMap;
import org.usfirst.frc2876.Steamworks2017.commands.*;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;


/**
 *
 */
public class DriveTrain extends Subsystem {

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    private final CANTalon frontLeftTalon = RobotMap.driveTrainFrontLeftTalon;
    private final CANTalon frontRightTalon = RobotMap.driveTrainFrontRightTalon;
    private final CANTalon rearLeftTalon = RobotMap.driveTrainRearLeftTalon;
    private final CANTalon rearRightTalon = RobotMap.driveTrainRearRightTalon;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public RobotDrive myRobot;
    
    public CameraServer server;
    
    public DriveTrain(){
    	
    	
    	myRobot = new RobotDrive(frontLeftTalon, rearLeftTalon, frontRightTalon, rearRightTalon);
    	myRobot.setSafetyEnabled(false);
    
    	initializeCamera();
    	
    }

    public void initializeCamera(){
    	server = CameraServer.getInstance();
//    	server.setQuality(50);
    	server.startAutomaticCapture("cam0", 0);
    	
    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        setDefaultCommand(new DriveControl());

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }
    
}

