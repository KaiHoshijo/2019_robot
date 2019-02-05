/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import com.ctre.phoenix.motorcontrol.ControlMode;
import frc.robot.RobotMap;
import frc.robot.OI;

public class LeadScrewDrivingCommand extends Command {
  public LeadScrewDrivingCommand() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    RobotMap.leadScrewMotor.set(ControlMode.PercentOutput, 0);
    }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if (OI.drive.getRawButton(6)) {
      // Lower elevator
      RobotMap.leadScrewMotor.set(ControlMode.PercentOutput, 0.3);
    } else if (OI.drive.getRawButton(5)) {
      // Raise elevator
      RobotMap.leadScrewMotor.set(ControlMode.PercentOutput, -0.3);
    } else {
      RobotMap.leadScrewMotor.set(ControlMode.PercentOutput, 0);
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
      RobotMap.leadScrewMotor.set(ControlMode.PercentOutput, 0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    RobotMap.leadScrewMotor.set(ControlMode.PercentOutput, 0);
  }
}
