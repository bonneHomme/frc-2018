package org.usfirst.frc.team6135.robot.commands.autonomous;

import org.usfirst.frc.team6135.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

import robot.pathfinder.core.trajectory.TankDriveTrajectory;
import robot.pathfinder.core.trajectory.TankDriveMoment;

/**
 * 	Makes the Robot follow the trajectory of a {@link robot.pathfinder.core.trajectory.TankDriveTrajectory TankDriveTrajectory}.<br>
 * 	<br>
 * 	Note: For this command to function correctly, the unit of length used in the trajectory must be <em>inches</em>,
 * 	and the unit for time must be <em>seconds</em>.
 *	@author Tyler
 */
public class FollowTrajectory extends Command {

	//Acceleration feedforward term, velocity feedforward term, proportional gain, derivative gain
	//Must tune later by trial and error
	//These are kept as non-constant to allow easy tuning from the SmartDashboard
	public static double kA = 0.00215, kV = 0.01, kP = 0.02225, kD = 0.001;
	
	TankDriveTrajectory trajectory;
	
	//For differentiation
	//Since the trajectory always starts at distance = 0, the errors are always 0 initially
	double lastTime, leftLastErr = 0, rightLastErr = 0;
	
    public FollowTrajectory(TankDriveTrajectory trajectory) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.drive);
    	this.trajectory = trajectory;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.drive.resetEncoders();
    	lastTime = Timer.getFPGATimestamp();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//Time difference
    	double dt = Timer.getFPGATimestamp() - lastTime;
    	double t = timeSinceInitialized();
    	
    	//Retrieve the data for the current time
    	TankDriveMoment m = trajectory.get(t);
    	//Left and right errors
    	double leftErr = m.getLeftPosition() - Robot.drive.getLeftDistance();
    	double rightErr = m.getRightPosition() - Robot.drive.getRightDistance();
    	//Calculate left and right position derivatives
    	//The desired velocity is subtracted to get the difference
    	double leftDeriv = (leftErr - leftLastErr) / dt 
    			- m.getLeftVelocity();
    	double rightDeriv = (rightErr - rightLastErr) / dt
    			- m.getRightVelocity();
    	//Calculate motor outputs
    	double leftOutput = kA * m.getLeftAcceleration() + kV * m.getLeftVelocity()
    			+ kP * leftErr + kD * leftDeriv;
    	double rightOutput = kA * m.getRightAcceleration() + kV * m.getRightVelocity()
    			+ kP * rightErr + kD * rightDeriv;
    	//Constrain
    	leftOutput = Math.max(-1, Math.min(1, leftOutput));
    	rightOutput = Math.max(-1, Math.min(1, rightOutput));
    	
    	Robot.drive.setMotorsVBus(leftOutput, rightOutput);
    	
    	lastTime = Timer.getFPGATimestamp();
    	leftLastErr = leftErr;
    	rightLastErr = rightErr;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return timeSinceInitialized() > trajectory.totalTime();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.drive.setMotorsVBus(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
