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

import org.usfirst.frc2876.Steamworks2017.commands.*;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	//// CREATING BUTTONS
	// One type of button is a joystick button which is any button on a
	//// joystick.
	// You create one by telling it which joystick it's on and which button
	// number it is.
	// Joystick stick = new Joystick(port);
	// Button button = new JoystickButton(stick, buttonNumber);

	// There are a few additional built in buttons you can use. Additionally,
	// by subclassing Button you can create custom triggers and bind those to
	// commands the same as any other Button.

	//// TRIGGERING COMMANDS WITH BUTTONS
	// Once you have a button, it's trivial to bind it to a button in one of
	// three ways:

	// Start the command when the button is pressed and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenPressed(new ExampleCommand());

	// Run the command while the button is being held down and interrupt it once
	// the button is released.
	// button.whileHeld(new ExampleCommand());

	// Start the command when the button is released and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenReleased(new ExampleCommand());

	// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
	public JoystickButton xButton;
	public JoystickButton bButton;
	public Joystick controller;

	// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
	public JoystickButton leftStickButton;
	public JoystickButton aButton;
	public JoystickButton rightBumper;
	public JoystickButton startButton;
	public JoystickButton leftBumper;
	public JoystickButton yButton;

	public OI() {

		controller = new Joystick(0);

//		leftStickButton = new JoystickButton(controller, 9);
//		leftStickButton.whileHeld(new IntakeStart());

//		aButton = new JoystickButton(controller, 1);
//		aButton.whenPressed(new AutoToCenterPeg());

		yButton = new JoystickButton(controller, 4);
		yButton.whileHeld(new ShootBall());

		bButton = new JoystickButton(controller, 2);
		bButton.whileHeld(new AgitatorReverse());
		// selectButton.whenPressed(new InverseDrive());
		
		leftBumper = new JoystickButton(controller, 5);
		leftBumper.whileHeld(new ClimberStart(false));
		
		xButton = new JoystickButton(controller, 3);
		xButton.whileHeld(new UpdateForPegAngle());
		
		rightBumper = new JoystickButton(controller, 6);
		rightBumper.whileHeld(new ClimberStart(true));
		
		// SmartDashboard Buttons
		SmartDashboard.putData("Autonomous Command", new AutonomousCommand());

		SmartDashboard.putData("ShooterIdle", new ShooterIdle());
		SmartDashboard.putData("ShootBall", new ShootBall());

		SmartDashboard.putData("IntakeStart", new IntakeStart());
		SmartDashboard.putData("IntakeStop", new IntakeStop());

		SmartDashboard.putData("ClimberStart", new ClimberStart(true));
		SmartDashboard.putData("ClimberStop", new ClimberStop());

//		SmartDashboard.putData("AutoShoot", new AutoShoot());

		SmartDashboard.putData("AutoTurn", new AutoTurning(90));
		//SmartDashboard.putData("DriveStraight", new DriveStraight());
		SmartDashboard.putData("Drive Distance", new AutoDriveDistance(60));
		SmartDashboard.putData("Auto Drive Distance Straight", new AutoDriveStraightDistance(60));
		SmartDashboard.putData("CGAutoExample", new CGAutoExample());
		SmartDashboard.putData("Auto To Center Peg", new AutoToCenterPeg());
		SmartDashboard.putData("CG Gear Center", new CGAutoGearCenter());
		SmartDashboard.putData("AutoMobility", new AutoMobility());
		SmartDashboard.putData("CG Go To Peg", new CGGoToPeg());
		SmartDashboard.putData("Agitator Run", new AgitatorRun());
	}

	public Joystick getController() {
		return controller;
	}

	// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=FUNCTIONS
	public double getLeftX() {
		return controller.getRawAxis(LEFT_X_AXIS);
	}

	public double getLeftY() {
		return controller.getRawAxis(LEFT_Y_AXIS);
	}

	public double getRightX() {
		return controller.getRawAxis(RIGHT_X_AXIS);
	}

	public double getRightY() {
		return controller.getRawAxis(RIGHT_Y_AXIS);
	}

	public double getLeftTrigger() {
		return controller.getRawAxis(LEFT_TRIGGER);
	}

	public double getRightTrigger() {
		return controller.getRawAxis(RIGHT_TRIGGER);
	}

	public static final int LEFT_X_AXIS = 0, LEFT_Y_AXIS = 1, LEFT_TRIGGER = 2, RIGHT_TRIGGER = 3, RIGHT_X_AXIS = 4,
			RIGHT_Y_AXIS = 5, DPAD_X_AXIS = 6, DPAD_Y_AXIS = 7;

}
