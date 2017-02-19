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
import org.usfirst.frc2876.Steamworks2017.Robot;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveTrain extends Subsystem {
	public boolean invertMotor = true;
	public double getDistanceReturn = 1;
	private boolean toggleInverseDrive = false;
	private boolean toggleHelp;
	private static final double WHEEL_DIAMETER = 6;
//	private static final double GEAR_RATIO = 12.75;
	private static final double PULSES_PER_REV = 1024;
	public double average;

	// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

	// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

	// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
	private final CANTalon frontLeftTalon = RobotMap.driveTrainFrontLeftTalon;
	private final CANTalon frontRightTalon = RobotMap.driveTrainFrontRightTalon;
	private final CANTalon rearLeftTalon = RobotMap.driveTrainRearLeftTalon;
	private final CANTalon rearRightTalon = RobotMap.driveTrainRearRightTalon;
	private final Relay lightSpike = RobotMap.driveTrainLightSpike;
	private final Encoder leftEncoder = RobotMap.driveTrainLeftEncoder;
	private final Encoder rightEncoder = RobotMap.driveTrainRightEncoder;

	// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
	public RobotDrive myRobot;

	public CameraServer server;
	public PIDController straightController, distanceController;
	
	
	
	

	public DriveTrain() {
//		frontRightTalon.setInverted(invertMotor);
//		rearRightTalon.setInverted(invertMotor);
		

		myRobot = new RobotDrive(frontLeftTalon, rearLeftTalon, frontRightTalon, rearRightTalon);
		myRobot.setSafetyEnabled(false);
		myRobot.setInvertedMotor(MotorType.kRearLeft, false);
		myRobot.setInvertedMotor(MotorType.kFrontRight, true);
		myRobot.setInvertedMotor(MotorType.kRearRight, true);
		myRobot.setInvertedMotor(MotorType.kFrontLeft, false);

		initializeCamera();
		
		straightController = new PIDController(.03, 0, 0, Robot.navX, new PIDOutput(){ 
			public void pidWrite(double output) {
				
			}
		});
		straightController.setOutputRange(-1, 1);
//		leftEncoder.setDistancePerPulse(Math.PI * WHEEL_DIAMETER / 1024 * GEAR_RATIO);
//		rightEncoder.setDistancePerPulse(Math.PI * WHEEL_DIAMETER / 1024 * GEAR_RATIO);
		
//		rearLeftTalon.configEncoderCodesPerRev((int)PULSES_PER_REV);
//		rearRightTalon.configEncoderCodesPerRev((int)PULSES_PER_REV);
		
		//for slave mode
		frontLeftTalon.changeControlMode(CANTalon.TalonControlMode.Follower);
		frontLeftTalon.set(rearLeftTalon.getDeviceID());
		frontRightTalon.changeControlMode(CANTalon.TalonControlMode.Follower);
		frontRightTalon.set(rearRightTalon.getDeviceID());
		
		rearRightTalon.reverseOutput(true);
		
		
		
		

	}

	public void initializeCamera() {
		server = CameraServer.getInstance();
		// server.setQuality(50);
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

	public void updateSmartDashboard() {
		// boolean sensorPluggedIn =
		// (frontRightTalon.isSensorPresent(FeedbackDevice.QuadEncoder) ==
		// FeedbackDeviceStatus.FeedbackStatusPresent);
		// SmartDashboard.putBoolean("fr encoder isSensorPresent ",
		// sensorPluggedIn);
		// SmartDashboard.putData("Left Encoder", getLeftEncoder());
		// SmartDashboard.putData("Right Encoder", getRightEncoder());
		// SmartDashboard.putNumber("Right Encoder d",
		// leftEncoder.getDistance());
		// SmartDashboard.putNumber("Left Encoder d",
		// rightEncoder.getDistance());
		
		SmartDashboard.putBoolean("is navX connected", Robot.navX.isConnected());
		SmartDashboard.putBoolean("is navX calibrating", Robot.navX.isCalibrating());
		SmartDashboard.putData("NavX", Robot.navX);
		SmartDashboard.putBoolean("is navX moving", Robot.navX.isMoving());
		SmartDashboard.putBoolean("is navX rotating", Robot.navX.isRotating());
		SmartDashboard.putNumber("navX angle", Robot.navX.getAngle());
		SmartDashboard.putNumber("navX pitch", Robot.navX.getPitch());
		SmartDashboard.putNumber("navX yaw", Robot.navX.getYaw());
		SmartDashboard.putNumber("navX roll", Robot.navX.getRoll());
		SmartDashboard.putNumber("navX WorldX", Robot.navX.getWorldLinearAccelX());
		SmartDashboard.putNumber("navX WorldY", Robot.navX.getWorldLinearAccelY());
		SmartDashboard.putNumber("navX WorldZ", Robot.navX.getWorldLinearAccelZ());
		// SmartDashboard.putNumber("Accel RoboRio", accel.getAcceleration());
		// SmartDashboard.putData("pid", turnController);
		// SmartDashboard.putNumber("pid output", turnController.get());
		// SmartDashboard.putBoolean("is crossing", isCrossing);
		SmartDashboard.putNumber("frontLeftValue", frontLeftTalon.get());
		SmartDashboard.putNumber("frontRightValue", frontRightTalon.get());
		SmartDashboard.putNumber("rearLeftValue", rearLeftTalon.get());
		SmartDashboard.putNumber("rearRightValue", rearRightTalon.get());
		SmartDashboard.putNumber("PID Output", straightController.get());
		SmartDashboard.putNumber("Distance", getDistance());
		SmartDashboard.putNumber("Left Encoder Value", rearLeftTalon.getEncPosition());
		SmartDashboard.putNumber("Right Encoder Value", rearRightTalon.getEncPosition());
		SmartDashboard.putNumber("average", average);
		
	}
	
	public void resetEncoders() {
		rearLeftTalon.setEncPosition(0);
		rearRightTalon.setEncPosition(0);
	}
	
	public double getDistance() {
		average =  (rearLeftTalon.getEncPosition() + rearRightTalon.getEncPosition()) / 2; 
//		return average / (Math.PI * WHEEL_DIAMETER * GEAR_RATIO);
		return average / PULSES_PER_REV * Math.PI * WHEEL_DIAMETER;
	}
	
	public void setSpeed(double speed) {
		myRobot.arcadeDrive(speed, -straightController.get(), true);
		SmartDashboard.putNumber("Speed", speed);
	}
	
	public void setVelocityMode() {
		rearRightTalon.changeControlMode(TalonControlMode.Speed);
		rearLeftTalon.changeControlMode(TalonControlMode.Speed);
	}
	
	public void setVbusMode() {
		// TODO Fill this in
	}
	
	public void setVelocity(double speed, double rotate) {
	    if (speed > 0.0) {
	        if (rotate > 0.0) {
	          rearLeftTalon.set((speed - rotate) * 3127);
	          rearRightTalon.set(Math.max(speed, rotate) * 3127);
	        } else {
	          rearLeftTalon.set(Math.max(speed, -rotate) * 3127);
	          rearRightTalon.set((speed + rotate) * 3127);
	        }
	      } else {
	        if (rotate > 0.0) {
	          rearLeftTalon.set(-Math.max(-speed, rotate) * 3127);
	          rearRightTalon.set((speed + rotate) * 3127);
	        } else {
	          rearLeftTalon.set((speed - rotate) * 3127);
	          rearRightTalon.set(-Math.max(-speed, -rotate) * 3127);
	        }
	      }
//		rearRightTalon.set(speed * 3127);
//		rearLeftTalon.set (speed * 3127);
		
		//we multiplied speed (-1 - 1) by 3127, which is the native units per 100 ms
		
	}
	public boolean toggleInverseDrive(){
		
		if(Robot.oi.selectButton.get() && toggleHelp){
			toggleInverseDrive = !toggleInverseDrive;
		}
		toggleHelp = !Robot.oi.selectButton.get();
		return toggleInverseDrive;
		
	}

}
