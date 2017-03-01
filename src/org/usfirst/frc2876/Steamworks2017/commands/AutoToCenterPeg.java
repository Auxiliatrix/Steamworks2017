// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

package org.usfirst.frc2876.Steamworks2017.commands;

import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc2876.Steamworks2017.Robot;
import org.usfirst.frc2876.Steamworks2017.RobotMap;
import org.usfirst.frc2876.Steamworks2017.subsystems.GearTarget;

/**
 *
 */
public class AutoToCenterPeg extends Command {
	
	int counter;

	public AutoToCenterPeg() {
        // This is suppose to turn to the peg
		// TODO - rename this class
		requires(Robot.driveTrain);
		requires(Robot.vision);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		counter = 0;
		RobotMap.driveTrainLightSpike.set(Value.kForward);
//		GearTarget t = Robot.vision.getGearTarget();
//			SmartDashboard.putString("Gear Target is", "null");
//			SmartDashboard.putString("Gear Target", t.toString());
//			SmartDashboard.putString("Gear Target is", "ok");
			for(int i = 0; i < 100; i++){
				GearTarget t = Robot.vision.getGearTarget();
				if (t != null){ 
					Robot.driveTrain.startTurn(t.angle());
					SmartDashboard.putString("Gear Target t", t.toString());
					break;
				}
			}
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		GearTarget t = Robot.vision.getGearTarget();
		if (t == null) {
			SmartDashboard.putString("GTExecStatus", "null " + ++counter);
		} else {
			SmartDashboard.putString("GTExec", t.toString());
			SmartDashboard.putString("GTExecStatus", "ok");
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return Robot.driveTrain.isTurnDone();
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.driveTrain.stopTurn();
		GearTarget t = Robot.vision.getGearTarget();
		if (t == null) {
			SmartDashboard.putString("Gear Target is", "null");
		} else {
			SmartDashboard.putString("Gear Target", t.toString());
			SmartDashboard.putString("Gear Target is", "ok");
			
		}
//		RobotMap.driveTrainLightSpike.set(Value.kOff);

		
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
