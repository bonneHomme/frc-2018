package org.usfirst.frc.team6135.robot.subsystems;

import org.usfirst.frc.team6135.robot.RobotMap;
import org.usfirst.frc.team6135.robot.commands.TiltAnalog;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class TiltSubsystem extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	public void setSpeed(double speed) {
		RobotMap.tiltVictor.set(speed);
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new TiltAnalog());
    }
}

