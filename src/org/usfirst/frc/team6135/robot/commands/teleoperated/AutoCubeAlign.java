package org.usfirst.frc.team6135.robot.commands.teleoperated;

import org.usfirst.frc.team6135.robot.Robot;
import org.usfirst.frc.team6135.robot.commands.autonomous.AutoTurn;
import org.usfirst.frc.team6135.robot.subsystems.VisionSubsystem;
import org.usfirst.frc.team6135.robot.vision.VisionException;

import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *	Uses a VisionSubsystem to find the Power Cube's angle offset from the robot,
 *	then starts a new command to automatically turn towards it and thus aligning the two.
 *
 *	This is an InstantCommand
 */
public class AutoCubeAlign extends InstantCommand {

	protected final double speed;
    public AutoCubeAlign(double speed) {
        super();
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(Robot.drive);
        requires(Robot.visionSubsystem);
        this.speed = speed;
    }

    // Called once when the command executes
    protected void initialize() {
    	Robot.visionSubsystem.setMode(VisionSubsystem.Mode.VISION);
    	try {
			Thread.sleep(1000);
		} 
    	catch (InterruptedException e1) {
			e1.printStackTrace();
		}
    	
    	double angleRaw;
    	try {
    		angleRaw = Robot.visionSubsystem.getCubeAngleOpenCV();
    	}
    	catch(VisionException e) {
    		return;
    	}
    	catch(Exception e) {
    		SmartDashboard.putString("Error:", "Unexpected Exception in Vision: " + e.toString());
    		return;
    	}
    	int angle = (int) (-Math.round(Math.toDegrees(angleRaw)));
    	SmartDashboard.putNumber("Angle: ", angle);
    	(new AutoTurn(angle)).start();
    	
    	Robot.visionSubsystem.setMode(VisionSubsystem.Mode.VIDEO);
    }

}
