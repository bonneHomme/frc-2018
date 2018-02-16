package org.usfirst.frc.team6135.robot.commands.teleopcommands;

import org.usfirst.frc.team6135.robot.OI;
import org.usfirst.frc.team6135.robot.Robot;
import org.usfirst.frc.team6135.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ElevatorAnalog extends Command {
	
	static final double DEADZONE = 0.15;

    public ElevatorAnalog() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.elevatorSubsystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	final double joystickVal = OI.attachmentsController.getRawAxis(RobotMap.ControllerMap.LSTICK_Y_AXIS);
    	if(Math.abs(joystickVal) > DEADZONE) {
    		RobotMap.elevatorVictor.set(joystickVal * RobotMap.Speeds.ELEVATOR_SPEED);
    	}
    	else {
    		RobotMap.elevatorVictor.set(0);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	RobotMap.elevatorVictor.set(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	RobotMap.elevatorVictor.set(0);
    }
}