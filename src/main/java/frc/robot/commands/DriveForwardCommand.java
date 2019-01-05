package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveForwardCommand extends Command {
	
	double startTime;

    public DriveForwardCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.drivetrainSubsystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	startTime = Timer.getFPGATimestamp();
    	RobotMap.frontRightMotor.set(ControlMode.PercentOutput, 0);
    	RobotMap.frontLeftMotor.set(ControlMode.PercentOutput, 0);
    	RobotMap.backRightMotor.set(ControlMode.Follower, 1);
    	RobotMap.backLeftMotor.set(ControlMode.Follower, 3);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	RobotMap.frontRightMotor.set(ControlMode.PercentOutput, -0.2);
    	RobotMap.frontLeftMotor.set(ControlMode.PercentOutput, 0.2);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        boolean finished = Timer.getFPGATimestamp() >= startTime + 2;
        return finished;
    }

    // Called once after isFinished returns true
    protected void end() {
    	RobotMap.frontRightMotor.set(ControlMode.PercentOutput, 0.0);
    	RobotMap.frontLeftMotor.set(ControlMode.PercentOutput, 0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
