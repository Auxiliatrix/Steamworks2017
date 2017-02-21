// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

package org.usfirst.frc2876.Steamworks2017;

import org.usfirst.frc2876.Steamworks2017.commands.AutoDoNothing;
import org.usfirst.frc2876.Steamworks2017.commands.AutoDriveDistance;
import org.usfirst.frc2876.Steamworks2017.commands.AutoTurning;
import org.usfirst.frc2876.Steamworks2017.commands.AutonomousCommand;
import org.usfirst.frc2876.Steamworks2017.subsystems.Climber;
import org.usfirst.frc2876.Steamworks2017.subsystems.DriveTrain;
import org.usfirst.frc2876.Steamworks2017.subsystems.FuelTank;
import org.usfirst.frc2876.Steamworks2017.subsystems.Intake;
import org.usfirst.frc2876.Steamworks2017.subsystems.Shooter;
import org.usfirst.frc2876.Steamworks2017.subsystems.Vision;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	Command autonomousCommand;
	public SendableChooser autoChoose;

	public static OI oi;
	// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
	public static DriveTrain driveTrain;
	public static Shooter shooter;
	public static Intake intake;
	public static FuelTank fuelTank;
	public static Climber climber;

	// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

	public static Vision vision;
	// public SendableChooser<Command> autoChoose = new
	// SendableChooser<Command>();
	// public SendableChooser autoChoose = new SendableChooser();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		RobotMap.init();

		// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
		driveTrain = new DriveTrain();
		shooter = new Shooter();
		intake = new Intake();
		fuelTank = new FuelTank();
		climber = new Climber();

		// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS

		vision = new Vision();

		// OI must be constructed after subsystems. If the OI creates Commands
		// (which it very likely will), subsystems are not guaranteed to be
		// constructed yet. Thus, their requires() statements may grab null
		// pointers. Bad news. Don't move it.
		oi = new OI();

		// instantiate the command used for the autonomous period
		// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=AUTONOMOUS

		autonomousCommand = new AutonomousCommand();

		// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=AUTONOMOUS
		autoPicker();
	}

	/**
	 * This function is called when the disabled button is hit. You can use it
	 * to reset subsystems before shutting down.
	 */
	public void disabledInit() {

	}

	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	public void autonomousInit() {
		// schedule the autonomous command (example)
		autonomousCommand = (Command) autoChoose.getSelected();
		if (autonomousCommand != null)
			autonomousCommand.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		SmartDashboard.putData(driveTrain);
		SmartDashboard.putData(intake);
		SmartDashboard.putData(shooter);
		SmartDashboard.putData(climber);
		driveTrain.updateSmartDashboard();
	}

	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null)
			autonomousCommand.cancel();
	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		driveTrain.updateSmartDashboard();
		SmartDashboard.putData(driveTrain);
		SmartDashboard.putData(intake);
		SmartDashboard.putData(shooter);
		SmartDashboard.putData(climber);

		// TODO: we need to move this out of here.. maybe put in in command that
		// uses vision... ok to leave here while testing/practicing.
		RobotMap.driveTrainLightSpike.set(Value.kForward);

	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {
		LiveWindow.addActuator("DriveSystem", "turnPID", driveTrain.getTurnPID());
		LiveWindow.addActuator("DriveSystem", "straightPID", driveTrain.getStraightPID());
		LiveWindow.run();
	}

	public void autoPicker() {
		autoChoose = new SendableChooser();
		autoChoose.addDefault("do nothing", new AutoDoNothing());
		autoChoose.addObject("Auto Drive Forward", new AutoDriveDistance(10));
		autoChoose.addObject("Auto Turn", new AutoTurning(90));
		SmartDashboard.putData("Autonomous", autoChoose);
	}
}
