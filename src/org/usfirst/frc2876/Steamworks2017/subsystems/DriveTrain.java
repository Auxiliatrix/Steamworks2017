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

import org.usfirst.frc2876.Steamworks2017.Robot;
import org.usfirst.frc2876.Steamworks2017.RobotMap;
import org.usfirst.frc2876.Steamworks2017.commands.DriveControl;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveTrain extends Subsystem {
	public boolean invertMotor = true;
	public double getDistanceReturn = 1;
	private boolean toggleInverseDrive = false;
	private boolean toggleHelp;

	private final double MAX_RPM = 3127.0f;
	private final double kTurnToleranceDegrees = 2.0f;
	private final double kDistanceTolerance = 2.0f;


	// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

	// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

	// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
	private final CANTalon frontLeftTalon = RobotMap.driveTrainFrontLeftTalon;
	private final CANTalon frontRightTalon = RobotMap.driveTrainFrontRightTalon;
	private final CANTalon rearLeftTalon = RobotMap.driveTrainRearLeftTalon;
	private final CANTalon rearRightTalon = RobotMap.driveTrainRearRightTalon;
	private final Relay lightSpike = RobotMap.driveTrainLightSpike;

	// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
	public RobotDrive myRobot;

	public CameraServer server;
	public PIDController straightController;
	public PIDController distanceController;
	public PIDController turnController;
	public AHRS navx;
	private CANTalon rightMaster, leftMaster;
	
	public void initDefaultCommand() {
		setDefaultCommand(new DriveControl());
		//setDefaultCommand(new DriveControlSimple());
		//setDefaultCommand(new DriveControlPercentVbus());
		
	}
	public DriveTrain() {
		navx = new AHRS(SPI.Port.kMXP);

		myRobot = new RobotDrive(frontLeftTalon, rearLeftTalon, frontRightTalon, rearRightTalon);
		myRobot.setSafetyEnabled(false);
		myRobot.setInvertedMotor(MotorType.kRearLeft, false);
		myRobot.setInvertedMotor(MotorType.kFrontRight, true);
		myRobot.setInvertedMotor(MotorType.kRearRight, true);
		myRobot.setInvertedMotor(MotorType.kFrontLeft, false);
		// for slave mode
		frontLeftTalon.changeControlMode(CANTalon.TalonControlMode.Follower);
		frontLeftTalon.set(rearLeftTalon.getDeviceID());
		frontRightTalon.changeControlMode(CANTalon.TalonControlMode.Follower);
		frontRightTalon.set(rearRightTalon.getDeviceID());
		leftMaster = rearLeftTalon;
		rightMaster = rearRightTalon;
		leftMaster.setF(.3271);
		rightMaster.setF(.3271);

		// TODO: do we need this?
		rightMaster.reverseOutput(true);

		setVelocityMode();

		// TODO: are we using a usb cam this year??
		// initializeCamera();

		straightController = new PIDController(10, 0, 0, navx, new PIDOutput() {
			public void pidWrite(double output) {
				SmartDashboard.putNumber("StraightPid Output", output);
				// Don't output any values to the talons to make robot move
				// here. Instead use the outputs in other places so we can
				// combine multiple PID controllers.
			}
		});
		straightController.setOutputRange(-MAX_RPM, MAX_RPM);

		distanceController = new PIDController(40, 0, 0, 10, new AvgEncoder(), new PIDOutput() {
			public void pidWrite(double output) {
				SmartDashboard.putNumber("DistancePid Output", output);
				// Don't output any values to the talons to make robot move
				// here. Instead use the outputs in other places so we can
				// combine multiple PID controllers.
			}
		});
		distanceController.setOutputRange(-MAX_RPM, MAX_RPM);
		distanceController.setAbsoluteTolerance(kDistanceTolerance);

		turnController = new PIDController(10, 0, 0, -3, navx, new PIDOutput() {
			public void pidWrite(double output) {
				SmartDashboard.putNumber("TurnPid Output", output);

				double minMove = 500.0f;
				output = minRpm(output, minMove);

				leftMaster.set(-output);
				rightMaster.set(output);
			}
		});

		turnController.setOutputRange(-MAX_RPM, MAX_RPM);
		turnController.setInputRange(-180.0f, 180.0f);
		turnController.setAbsoluteTolerance(kTurnToleranceDegrees);
		turnController.setContinuous(true);
	}

	public void initializeCamera() {
		server = CameraServer.getInstance();
		// server.setQuality(50);
		server.startAutomaticCapture("cam0", 0);

	}



	public void updateSmartDashboard() {
		
		SmartDashboard.putData("NavX", navx);
		SmartDashboard.putNumber("navX angle", navx.getAngle());
		
		// SmartDashboard.putBoolean("is navX connected", navx.isConnected());
		// SmartDashboard.putBoolean("is navX calibrating",
		// navx.isCalibrating());
		// SmartDashboard.putBoolean("is navX moving", navx.isMoving());
		// SmartDashboard.putBoolean("is navX rotating", navx.isRotating());
		// SmartDashboard.putNumber("navX pitch", navx.getPitch());
		// SmartDashboard.putNumber("navX yaw", navx.getYaw());
		// SmartDashboard.putNumber("navX roll", navx.getRoll());
		// SmartDashboard.putNumber("navX WorldX", navx.getWorldLinearAccelX());
		// SmartDashboard.putNumber("navX WorldY", navx.getWorldLinearAccelY());
		
		// SmartDashboard.putNumber("Accel RoboRio", accel.getAcceleration());
		
		// SmartDashboard.putNumber("frontLeftValue", frontLeftTalon.get());
		// SmartDashboard.putNumber("frontRightValue", frontRightTalon.get());
		// SmartDashboard.putNumber("rearLeftValue", rearLeftTalon.get());
		// SmartDashboard.putNumber("rearRightValue", rearRightTalon.get());

		SmartDashboard.putNumber("LeftMasterSetpoint", leftMaster.getSetpoint());
		SmartDashboard.putNumber("RightMasterSetpoint", rightMaster.getSetpoint());

		SmartDashboard.putData("StraightPID", straightController);
		SmartDashboard.putNumber("StraightPID get", straightController.get());

		SmartDashboard.putData("DistancePID", distanceController);
		SmartDashboard.putNumber("DistancePID get", distanceController.get());

		SmartDashboard.putData("TurnPID", turnController);
		SmartDashboard.putNumber("TurnPID get", turnController.get());

		SmartDashboard.putNumber("Distance", getDistance());
		SmartDashboard.putNumber("Left Encoder Value", leftMaster.getEncPosition());
		SmartDashboard.putNumber("Right Encoder Value", rightMaster.getEncPosition());
	}

	// Sometimes the robot is commanded to move but the rpm given is too small
	// to make the robot actually move(inertia, friction, ...). Use this method
	// to adjust the rpm to provide a min rpm to actually make the robot move.
	// This may be different if the robot is trying to turn vs just start moving
	// in a straight line.
	private double minRpm(double inputRpm, double minRpm) {
		double outputRpm = inputRpm;
		if (Math.abs(inputRpm) < minRpm) {
			if (outputRpm < 0) {
				outputRpm = -minRpm;
			} else if (outputRpm > 0) {
				outputRpm = minRpm;
			}
		}
		return outputRpm;
	}

	public void velocityTankStraightJoysticks(double speed) {
		//double straight = straightController.get();
		double straight = 0;
		rearRightTalon.set((speed * MAX_RPM) + straight);
		rearLeftTalon.set((speed * MAX_RPM) - straight);
	}

	public void velocityDistanceStraight() {
		double straight = straightController.get();
		double distance = -distanceController.get();
		double r = distance + straight;
		double l = distance - straight;

		SmartDashboard.putNumber("DistanceStraightRightOutput", r);
		SmartDashboard.putNumber("DistanceStraightLeftOutput", l);

		// TODO if r/l are less then some min, 300, 400? set to that min else
		// the robot won't move. Need a min value to make robot overcome
		// friction/inertia. Or better PID tuning?
//		double minMove = 300.0f;
//		r = minRpm(r, minMove);
//		l = minRpm(l, minMove);

		rightMaster.set(r);
		leftMaster.set(l);
	}

	public void setVelocityMode() {
		rightMaster.changeControlMode(TalonControlMode.Speed);
		leftMaster.changeControlMode(TalonControlMode.Speed);
	}

	// This is the 'default' mode Talons come on in. It is open loop control.
	// The kind of driving mode we have done in years past when no encoder is
	// used to determine speed.
	public void setVbusMode() {
		rightMaster.changeControlMode(TalonControlMode.PercentVbus);
		leftMaster.changeControlMode(TalonControlMode.PercentVbus);
	}

	// Use this method to control robot with joysticks. No pid is used to do
	// anything else. This is just driving the robot in velocity closed loop
	// mode.
	public void setVelocityArcadeJoysticks(double speed, double rotate) {
		if (speed > 0.0) {
			if (rotate > 0.0) {
				rearLeftTalon.set((speed - rotate) * MAX_RPM);
				rearRightTalon.set(Math.max(speed, rotate) * MAX_RPM);
			} else {
				rearLeftTalon.set(Math.max(speed, -rotate) * MAX_RPM);
				rearRightTalon.set((speed + rotate) * MAX_RPM);
			}
		} else {
			if (rotate > 0.0) {
				rearLeftTalon.set(-Math.max(-speed, rotate) * MAX_RPM);
				rearRightTalon.set((speed + rotate) * MAX_RPM);
			} else {
				rearLeftTalon.set((speed - rotate) * MAX_RPM);
				rearRightTalon.set(-Math.max(-speed, -rotate) * MAX_RPM);
			}
		}
	}
	
	public void setVelocityArcadeJoysticksStraight(double speed, double rotate) {
		double straight = straightController.get();
		if (speed > 0.0) {
			if (rotate > 0.0) {
				rearLeftTalon.set((speed - rotate) * MAX_RPM);
				rearRightTalon.set(Math.max(speed, rotate) * MAX_RPM);
			} else {
				rearLeftTalon.set(Math.max(speed, -rotate) * MAX_RPM);
				rearRightTalon.set((speed + rotate) * MAX_RPM);
			}
		} else {
			if (rotate > 0.0) {
				rearLeftTalon.set(-Math.max(-speed, rotate) * MAX_RPM);
				rearRightTalon.set((speed + rotate) * MAX_RPM);
			} else {
				rearLeftTalon.set((speed - rotate) * MAX_RPM);
				rearRightTalon.set(-Math.max(-speed, -rotate) * MAX_RPM);
			}
		}
	}

	public boolean toggleInverseDrive() {
		boolean buttonPressed = Robot.oi.bButton.get();
		if (buttonPressed && toggleHelp) {
			toggleInverseDrive = !toggleInverseDrive;
		}
//		leftMaster.setInverted(toggleInverseDrive);
//		rightMaster.setInverted(toggleInverseDrive);
		toggleHelp = !buttonPressed;
		return toggleInverseDrive;
	}

	// Start the distance PID. Turn off turn PID when starting distance. Zero
	// out encoders. And then go go go!
	public void startDistance(double distance) {
		stopTurn();
		distanceController.reset();
		resetEncoders();
		distanceController.setSetpoint(distance);
		distanceController.enable();
	}

	public boolean isDistanceRunning() {
		return distanceController.isEnabled();
	}

	public boolean isDistanceDone() {
		return distanceController.onTarget();
	}

	public void stopDistance() {
		distanceController.reset();
		leftMaster.set(0);
		rightMaster.set(0);

	}

	// Start the turn PID. Reset the navx angle so we can turn to a set/absolute
	// angle. Turn off the straight and distance PID controllers.
	public void startTurn(double angle) {
		SmartDashboard.putNumber("TurnPid Output", 0);
		// Reset angle to zero here so that we know we are going to turn from
		// zero to set angle right before we start PID controller. If we reset
		// somewhere else could be someone has turned the robot to line up for
		// testing or maybe robot was bumped by another robot.
		navx.reset();
		stopStraight();
		stopDistance();
		turnController.reset();
		turnController.setSetpoint(angle);
		turnController.enable();
	}

	public boolean isTurnRunning() {
		return turnController.isEnabled();
	}
	
	public boolean isStraightRunning() {
		return straightController.isEnabled();
	}

	public boolean isTurnDone() {
		return turnController.onTarget();
	}

	public void stopTurn() {
		SmartDashboard.putNumber("TurnPid Output", 0);
		turnController.reset();
		leftMaster.set(0);
		rightMaster.set(0);
		// navx.reset();
	}

	public void startStraight() {
		// Don't need to reset navx angle to zero here. To drive straight we
		// just need to pick whatever angle the robot is at when we start the
		// PID controller.
		stopTurn();
		straightController.reset();
		straightController.setSetpoint(navx.getAngle());
		straightController.enable();
	}

	public void stopStraight() {
		straightController.reset();
		leftMaster.set(0);
		rightMaster.set(0);

	}

	public void stopAllPID() {
		stopStraight();
		stopDistance();
		stopTurn();
	}
	public PIDController getDistancePID() {
		return distanceController;
	}

	public PIDController getTurnPID() {
		return turnController;
	}

	public PIDController getStraightPID() {
		return straightController;
	}
	private static final double WHEEL_DIAMETER = 6;
	private static final double PULSES_PER_REV = 4096;
	
	public double nativeToInches(double nativeUnits) {
		double circumference = Math.PI * WHEEL_DIAMETER;
		return (nativeUnits / PULSES_PER_REV) * circumference;
	}

	public void resetEncoders() {
		leftMaster.setEncPosition(0);
		rightMaster.setEncPosition(0);
	}

	public double getDistance() {
		double l = nativeToInches(leftMaster.getEncPosition());
		double r = nativeToInches(rightMaster.getEncPosition());
		double av = (r - l) / 2;
		SmartDashboard.putNumber("Left distance", l);
		SmartDashboard.putNumber("Right distance", r);
		SmartDashboard.putNumber("Average distance", av);
		return av;
	}
	public double getMaxRpm(){
		return MAX_RPM;
	}

	// Create a class inside a class(no idea what this is in Java terminology)
	// to be able to pass in the average of left and right encoder distance.
	// Need to create a class so we can implement the PIDSource interface.
	private class AvgEncoder implements PIDSource {

		public double pidGet() {
			return getDistance();
		}

		@Override
		public void setPIDSourceType(PIDSourceType pidSource) {
			// TODO Auto-generated method stub

		}

		@Override
		public PIDSourceType getPIDSourceType() {
			return PIDSourceType.kDisplacement;
		}
	}
}
