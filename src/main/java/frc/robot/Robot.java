/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.commands.DriveForwardAndBackwardGroup;
import frc.robot.commands.DriveForwardCommand;
import frc.robot.commands.DrivingCommand;
import frc.robot.subsystems.DrivetrainSubsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {
	public static final DrivetrainSubsystem drivetrainSubsystem 
		= new DrivetrainSubsystem();
	public static OI oi;

	Command autonomousCommand = new DriveForwardCommand();
	CommandGroup driveForwardAndBackward = new DriveForwardAndBackwardGroup();
	Command drivingCommand = new DrivingCommand();
	SendableChooser<Command> chooser = new SendableChooser<>();

	/**
	 * This function is run when the robot is first started up and should be used
	 * for any initialization code.
	 */
	@Override
	public void robotInit() {
		oi = new OI();
		// chooser.addObject("My Auto", new MyAutoCommand());
		SmartDashboard.putData("Auto mode", chooser);
		
//		RobotMap.frontRightMotor.configOpenloopRamp(1, 0);
//		RobotMap.backRightMotor.configOpenloopRamp(1, 0);
//		RobotMap.frontLeftMotor.configOpenloopRamp(1, 0);
//		RobotMap.backLeftMotor.configOpenloopRamp(1, 0);
//		
//		RobotMap.frontRightMotor.setNeutralMode(NeutralMode.Brake);
//		RobotMap.backRightMotor.setNeutralMode(NeutralMode.Brake);
//		RobotMap.frontLeftMotor.setNeutralMode(NeutralMode.Brake);
//		RobotMap.backLeftMotor.setNeutralMode(NeutralMode.Brake);
//		
		RobotMap.frontRightMotor.configSelectedFeedbackSensor(
			FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 0);
		RobotMap.frontLeftMotor.configSelectedFeedbackSensor(
			FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 0);
		
		RobotMap.frontRightMotor.setSelectedSensorPosition(0, 0, 0);
		RobotMap.frontLeftMotor.setSelectedSensorPosition(0, 0, 0);
		
		 RobotMap.frontRightMotor.configNominalOutputForward(0, 0);
		 RobotMap.frontRightMotor.configNominalOutputReverse(0, 0);
		 RobotMap.frontRightMotor.configPeakOutputForward(1, 0);
		 RobotMap.frontRightMotor.configPeakOutputReverse(-1, 0);
		 
		 RobotMap.frontLeftMotor.configNominalOutputForward(0, 0);
		 RobotMap.frontLeftMotor.configNominalOutputReverse(0, 0);
		 RobotMap.frontLeftMotor.configPeakOutputForward(1, 0);
		 RobotMap.frontLeftMotor.configPeakOutputReverse(-1, 0);
		 
		 RobotMap.frontRightMotor.config_kF(0, 0.5, 0);
		 RobotMap.frontRightMotor.config_kP(0, 1, 0);
		 RobotMap.frontRightMotor.config_kI(0, 0, 0);
		 RobotMap.frontRightMotor.config_kD(0, 0, 0);
		 
		 RobotMap.frontLeftMotor.config_kF(0, 0.0, 0);
		 RobotMap.frontLeftMotor.config_kP(0, 1, 0);
		 RobotMap.frontLeftMotor.config_kI(0, 0, 0);
		 RobotMap.frontLeftMotor.config_kD(0, 0, 0);
			
		RobotMap.frontRightMotor.setSensorPhase(false);
		RobotMap.frontLeftMotor.setSensorPhase(true);
		
		RobotMap.frontRightMotor.setInverted(true);
		RobotMap.frontLeftMotor.setInverted(false);
		RobotMap.backRightMotor.setInverted(true);
		
		RobotMap.backLeftMotor.set(
			ControlMode.Follower, RobotMap.frontLeftMotorID);
		RobotMap.backRightMotor.set(
			ControlMode.Follower, RobotMap.frontRightMotorID);

//		RobotMap.masterElevatorMotor.configPeakOutputForward(0.78, 0);
//    	RobotMap.masterElevatorMotor.configPeakOutputReverse(0, 0);
//    	RobotMap.masterElevatorMotor.setNeutralMode(NeutralMode.Brake);
//		RobotMap.slaveElevatorMotor.setNeutralMode(NeutralMode.Brake);
	}

	/**
	 * This function is called once each time the robot enters Disabled mode. You
	 * can use it to reset any subsystem information you want to clear when the
	 * robot is disabled.
	 */
	@Override
	public void disabledInit() {
		drivingCommand.cancel();
		RobotMap.frontRightMotor.setNeutralMode(NeutralMode.Coast);
		RobotMap.backRightMotor.setNeutralMode(NeutralMode.Coast);
		RobotMap.frontLeftMotor.setNeutralMode(NeutralMode.Coast);
		RobotMap.backLeftMotor.setNeutralMode(NeutralMode.Coast);
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable chooser
	 * code works with the Java SmartDashboard. If you prefer the LabVIEW Dashboard,
	 * remove all of the chooser code and uncomment the getString code to get the
	 * auto name from the text box below the Gyro
	 *
	 * <p>
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons to
	 * the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector", "Default");
		 * switch(autoSelected) { case "My Auto": autonomousCommand = new
		 * MyAutoCommand(); break; case "Default Auto": default: autonomousCommand = new
		 * ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		
//		driveForwardAndBackward.start();
		
		RobotMap.backLeftMotor.set(
			ControlMode.Follower, RobotMap.frontLeftMotorID);
		RobotMap.backRightMotor.set(
			ControlMode.Follower, RobotMap.frontRightMotorID);
		
		RobotMap.frontRightMotor.setSelectedSensorPosition(0, 0, 0);
		RobotMap.frontLeftMotor.setSelectedSensorPosition(0, 0, 0);

	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		/*
		Scheduler.getInstance().run();
		RobotMap.frontLeftMotor.set(ControlMode.Position, 500);
		RobotMap.frontRightMotor.set(ControlMode.Position, 500);
		System.out.printf("Left out: %s; Right out: %s; Left Err: %s; Right Err: %s\n",
				RobotMap.frontLeftMotor.getOutputCurrent(),
				RobotMap.frontRightMotor.getOutputCurrent(),
				RobotMap.frontLeftMotor.getClosedLoopError(0),
				RobotMap.frontRightMotor.getClosedLoopError(0));*/
		RobotMap.frontRightMotor.set(ControlMode.PercentOutput, -0.5);
		System.out.println(RobotMap.frontRightMotor.getOutputCurrent());
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null) {
			autonomousCommand.cancel();
		}
		
		// drivingCommand.start();
		RobotMap.backLeftMotor.set(
			ControlMode.Follower, RobotMap.frontLeftMotorID);
		RobotMap.backRightMotor.set(
			ControlMode.Follower, RobotMap.frontRightMotorID);
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		RobotMap.backRightMotor.set(ControlMode.PercentOutput, 0.2);
		RobotMap.backLeftMotor.set(ControlMode.PercentOutput, 0.2);
		System.out.printf("right: front: %s; back: %s\n",
		 	RobotMap.frontRightMotor.getOutputCurrent(),
		  	RobotMap.backRightMotor.getOutputCurrent());
		System.out.printf("left : front: %s; back: %s\n",
			RobotMap.frontLeftMotor.getOutputCurrent(),
			RobotMap.backLeftMotor.getOutputCurrent());
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
