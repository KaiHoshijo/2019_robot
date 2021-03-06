/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DrivingCommand extends Command {

    public DrivingCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.drivetrainSubsystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	RobotMap.frontRightMotor.set(ControlMode.PercentOutput, 0);
    	RobotMap.frontLeftMotor.set(ControlMode.PercentOutput, 0);
    	RobotMap.backRightMotor.set(
            ControlMode.Follower, RobotMap.frontRightMotorID);
    	RobotMap.backLeftMotor.set(
            ControlMode.Follower, RobotMap.frontLeftMotorID);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    // 	RobotMap.frontRightMotor.set(ControlMode.PercentOutput, 0.5);
    // }
		double driveSpeedCoeff = 0.35;

		double inputY = OI.drive.getRawAxis(1);
		double inputX = OI.drive.getRawAxis(0);
		   
		double y = Math.pow(inputY, 2) * Math.signum(inputY) * driveSpeedCoeff;
		double x = Math.pow(inputX, 2) * Math.signum(inputX) * driveSpeedCoeff;

		if (y < 0.1 && y > -0.1) {
			y = 0;
		}
		
		if ( x < 0.1 && x > -0.1) {
			x = 0;
		}
		
		RobotMap.frontLeftMotor.set(ControlMode.PercentOutput, -y + x);
		RobotMap.frontRightMotor.set(ControlMode.PercentOutput, -y - x);
   }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false; 
    }

    // Called once after isFinished returns true
    protected void end() {
    	RobotMap.frontRightMotor.set(ControlMode.PercentOutput, 0);
    	RobotMap.frontLeftMotor.set(ControlMode.PercentOutput, 0);
    	RobotMap.backRightMotor.set(ControlMode.PercentOutput, 0);
    	RobotMap.backLeftMotor.set(ControlMode.PercentOutput, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	RobotMap.frontRightMotor.set(ControlMode.PercentOutput, 0);
    	RobotMap.frontLeftMotor.set(ControlMode.PercentOutput, 0);
    	RobotMap.backRightMotor.set(ControlMode.PercentOutput, 0);
    	RobotMap.backLeftMotor.set(ControlMode.PercentOutput, 0);
    }
}
