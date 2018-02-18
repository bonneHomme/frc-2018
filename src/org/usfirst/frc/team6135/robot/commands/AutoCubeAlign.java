package org.usfirst.frc.team6135.robot.commands;

import org.usfirst.frc.team6135.robot.Robot;
import org.usfirst.frc.team6135.robot.commands.autoutils.AutoTurn;
import org.usfirst.frc.team6135.robot.subsystems.VisionSubsystem;

import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class AutoCubeAlign extends InstantCommand {

	double speed;
    public AutoCubeAlign(double speed) {
        super();
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(Robot.drive);
        this.speed = speed;
    }

    // Called once when the command executes
    protected void initialize() {
    	Robot.visionSubsystem.setMode(VisionSubsystem.Mode.VISION);
    	try {
			Thread.sleep(500);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
    	
    	double angleRaw;
    	try {
    		angleRaw = Robot.visionSubsystem.getCubeAngle();
    	}
    	catch(VisionSubsystem.VisionException e) {
    		return;
    	}
    	catch(Exception e) {
    		SmartDashboard.putString("Error:", "Unexpected Exception in Vision: " + e.toString());
    		return;
    	}
    	int angle = (int) (-Math.round(Math.toDegrees(angleRaw)));
    	(new AutoTurn(angle, speed)).start();
    	
    	Robot.visionSubsystem.setMode(VisionSubsystem.Mode.VIDEO);
    }

}
