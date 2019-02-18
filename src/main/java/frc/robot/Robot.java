/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.AlignTargetCommand;
import frc.robot.commands.ClimbingCommand;
import frc.robot.commands.DriveForwardAndBackwardGroup;
import frc.robot.commands.DrivingCommand;
import frc.robot.commands.LeadScrewDrivingCommand;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.LimelightSystem;

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
	private static final double LED_OFF = 1;
	private static final double LED_ON = 3;

	public static final DrivetrainSubsystem drivetrainSubsystem 
		= new DrivetrainSubsystem();
	public static final LimelightSystem limelightSystem
		= new LimelightSystem();
	public static OI oi;

	Command autonomousCommand = new AlignTargetCommand();
	CommandGroup driveForwardAndBackward = new DriveForwardAndBackwardGroup();
	Command drivingCommand = new DrivingCommand();
	Command climbingCommand = new ClimbingCommand();
	Command leadScrewCommand = new LeadScrewDrivingCommand();
	SendableChooser<Command> chooser = new SendableChooser<>();

	NetworkTableEntry limelightLED;

	/**
	 * This function is run when the robot is first started up and should be used
	 * for any initialization code.
	 */

	public void robotInit() {
		oi = new OI();
		// chooser.addObject("My Auto", new MyAutoCommand());
		SmartDashboard.putData("Auto mode", chooser);

//		RobotMap.frontRightMotor.configOpenloopRamp(1, 0);
//		RobotMap.backRightMotor.configOpenloopRamp(1, 0);
//		RobotMap.frontLeftMotor.configOpenloopRamp(1, 0);
//		RobotMap.backLeftMotor.configOpenloopRamp(1, 0);
//		
		RobotMap.frontRightMotor.setNeutralMode(NeutralMode.Coast);
		RobotMap.backRightMotor.setNeutralMode(NeutralMode.Coast);
		RobotMap.frontLeftMotor.setNeutralMode(NeutralMode.Coast);
		RobotMap.backLeftMotor.setNeutralMode(NeutralMode.Coast);
		RobotMap.leftClimbingMotor.setNeutralMode(NeutralMode.Coast);
		RobotMap.rightClimbingMotor.setNeutralMode(NeutralMode.Coast);
		RobotMap.leadScrewMotor.setNeutralMode(NeutralMode.Coast);

		RobotMap.frontRightMotor.configSelectedFeedbackSensor(
			FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 0);
		RobotMap.frontLeftMotor.configSelectedFeedbackSensor(
			FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 0);
		RobotMap.rightClimbingMotor.configSelectedFeedbackSensor(
			FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 0);
		RobotMap.leftClimbingMotor.configSelectedFeedbackSensor(
			FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 0);
		RobotMap.leadScrewMotor.configSelectedFeedbackSensor(
			FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 0);

		RobotMap.frontRightMotor.setSelectedSensorPosition(0, 0, 0);
		RobotMap.frontLeftMotor.setSelectedSensorPosition(0, 0, 0);
		RobotMap.leftClimbingMotor.setSelectedSensorPosition(0, 0, 0);
		RobotMap.rightClimbingMotor.setSelectedSensorPosition(0, 0, 0);

		RobotMap.frontRightMotor.configNominalOutputForward(0, 0);
		RobotMap.frontRightMotor.configNominalOutputReverse(0, 0);
		RobotMap.frontRightMotor.configPeakOutputForward(1, 0);
		RobotMap.frontRightMotor.configPeakOutputReverse(-1, 0);
		
		RobotMap.frontLeftMotor.configNominalOutputForward(0, 0);
		RobotMap.frontLeftMotor.configNominalOutputReverse(0, 0);
		RobotMap.frontLeftMotor.configPeakOutputForward(1, 0);
		RobotMap.frontLeftMotor.configPeakOutputReverse(-1, 0);

		RobotMap.rightClimbingMotor.configNominalOutputForward(0, 0);
		RobotMap.rightClimbingMotor.configNominalOutputReverse(0, 0);
		RobotMap.rightClimbingMotor.configPeakOutputForward(1, 0);
		RobotMap.rightClimbingMotor.configPeakOutputReverse(-1, 0);
		
		RobotMap.leftClimbingMotor.configNominalOutputForward(0, 0);
		RobotMap.leftClimbingMotor.configNominalOutputReverse(0, 0);
		RobotMap.leftClimbingMotor.configPeakOutputForward(1, 0);
		RobotMap.leftClimbingMotor.configPeakOutputReverse(-1, 0);

		// FIXME
		RobotMap.frontRightMotor.config_kF(0, 0.5, 0);
		RobotMap.frontRightMotor.config_kP(0, 1, 0);
		RobotMap.frontRightMotor.config_kI(0, 0, 0);
		RobotMap.frontRightMotor.config_kD(0, 0, 0);

		RobotMap.frontLeftMotor.config_kF(0, 0.0, 0);
		RobotMap.frontLeftMotor.config_kP(0, 1, 0);
		RobotMap.frontLeftMotor.config_kI(0, 0, 0);
		RobotMap.frontLeftMotor.config_kD(0, 0, 0);

		RobotMap.rightClimbingMotor.config_kF(0, 0.5, 0);
		RobotMap.rightClimbingMotor.config_kP(0, 1, 0);
		RobotMap.rightClimbingMotor.config_kI(0, 0, 0);
		RobotMap.rightClimbingMotor.config_kD(0, 0, 0);

		RobotMap.leftClimbingMotor.config_kF(0, 0.0, 0);
		RobotMap.leftClimbingMotor.config_kP(0, 1, 0);
		RobotMap.leftClimbingMotor.config_kI(0, 0, 0);
		RobotMap.leftClimbingMotor.config_kD(0, 0, 0);

		RobotMap.frontRightMotor.setSensorPhase(false);
		RobotMap.frontLeftMotor.setSensorPhase(true);

		RobotMap.rightClimbingMotor.setSensorPhase(false);
		RobotMap.leftClimbingMotor.setSensorPhase(true);

		RobotMap.frontRightMotor.setInverted(true);
		RobotMap.backRightMotor.setInverted(true);
		RobotMap.frontLeftMotor.setInverted(false);
		RobotMap.backLeftMotor.setInverted(false);
		RobotMap.rightClimbingMotor.setInverted(true);
		RobotMap.leftClimbingMotor.setInverted(false);
		
		RobotMap.backLeftMotor.set(
			ControlMode.Follower, RobotMap.frontLeftMotorID);
		RobotMap.backRightMotor.set(
			ControlMode.Follower, RobotMap.frontRightMotorID);
		RobotMap.leftClimbingMotor.set(
			ControlMode.Follower, RobotMap.rightClimbingID);

//		RobotMap.masterElevatorMotor.configPeakOutputForward(0.78, 0);
//    	RobotMap.masterElevatorMotor.configPeakOutputReverse(0, 0);
//    	RobotMap.masterElevatorMotor.setNeutralMode(NeutralMode.Brake);
//		RobotMap.slaveElevatorMotor.setNeutralMode(NeutralMode.Brake);

		NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");

		this.limelightLED = table.getEntry("ledMode");
	}

	/**
	 * This function is called once each time the robot enters Disabled mode. You
	 * can use it to reset any subsystem information you want to clear when the
	 * robot is disabled.
	 */
	
	public void disabledInit() {
		drivingCommand.cancel();
		RobotMap.frontLeftMotor.set(ControlMode.PercentOutput, 0);
		RobotMap.frontRightMotor.set(ControlMode.PercentOutput, 0);
		RobotMap.frontRightMotor.setNeutralMode(NeutralMode.Coast);
		RobotMap.backRightMotor.setNeutralMode(NeutralMode.Coast);
		RobotMap.frontLeftMotor.setNeutralMode(NeutralMode.Coast);
		RobotMap.backLeftMotor.setNeutralMode(NeutralMode.Coast);
		RobotMap.rightClimbingMotor.setNeutralMode(NeutralMode.Coast);
		RobotMap.leftClimbingMotor.setNeutralMode(NeutralMode.Coast);
		RobotMap.leadScrewMotor.setNeutralMode(NeutralMode.Coast);

		this.limelightLED.setNumber(LED_OFF);
	}

	
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
	
	public void autonomousInit() {

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector", "Default");
		 * switch(autoSelected) { case "My Auto": autonomousCommand = new
		 * MyAutoCommand(); break; case "Default Auto": default: autonomousCommand = new
		 * ExampleCommand(); break; }
		 */
		RobotMap.frontLeftMotor.setNeutralMode(NeutralMode.Brake);
		RobotMap.frontRightMotor.setNeutralMode(NeutralMode.Brake);
		RobotMap.backLeftMotor.setNeutralMode(NeutralMode.Brake);
		RobotMap.backRightMotor.setNeutralMode(NeutralMode.Brake);
		RobotMap.rightClimbingMotor.setNeutralMode(NeutralMode.Brake);
		RobotMap.leftClimbingMotor.setNeutralMode(NeutralMode.Brake);
		RobotMap.leadScrewMotor.setNeutralMode(NeutralMode.Brake);

		RobotMap.backLeftMotor.set(
			ControlMode.Follower, RobotMap.frontLeftMotorID);
		RobotMap.backRightMotor.set(
			ControlMode.Follower, RobotMap.frontRightMotorID);
		
		RobotMap.frontRightMotor.setSelectedSensorPosition(0, 0, 0);
		RobotMap.frontLeftMotor.setSelectedSensorPosition(0, 0, 0);

		this.limelightLED.setNumber(LED_ON);

		autonomousCommand.start();
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	
	public void autonomousPeriodic() {
		
		Scheduler.getInstance().run();
		// RobotMap.frontLeftMotor.set(ControlMode.Position, 500);
		// RobotMap.frontRightMotor.set(ControlMode.Position, 500);
		// System.out.printf("Left out: %s; Right out: %s; Left Err: %s; Right Err: %s\n",
		// 		RobotMap.frontLeftMotor.getOutputCurrent(),
		// 		RobotMap.frontRightMotor.getOutputCurrent(),
		// 		RobotMap.frontLeftMotor.getClosedLoopError(0),
		// 		RobotMap.frontRightMotor.getClosedLoopError(0));
	}


	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null) {
			autonomousCommand.cancel();
		}

		RobotMap.frontLeftMotor.setNeutralMode(NeutralMode.Brake);
		RobotMap.frontRightMotor.setNeutralMode(NeutralMode.Brake);
		RobotMap.backLeftMotor.setNeutralMode(NeutralMode.Brake);
		RobotMap.backRightMotor.setNeutralMode(NeutralMode.Brake);

		RobotMap.backLeftMotor.set(
			ControlMode.Follower, RobotMap.frontLeftMotorID);
		RobotMap.backRightMotor.set(
			ControlMode.Follower, RobotMap.frontRightMotorID);
			
		this.limelightLED.setNumber(LED_ON);

		drivingCommand.start();
		// climbingCommand.start();
		// leadScrewCommand.start();
	}

	/**
	 * This function is called periodically during operator control.
	 */

	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		// RobotMap.backRightMotor.set(ControlMode.PercentOutput, 0.2);
		// RobotMap.backLeftMotor.set(ControlMode.PercentOutput, 0.2);
		// System.out.printf("right: front: %s; back: %s\n",
		//  	RobotMap.frontRightMotor.getOutputCurrent(),
		//   	RobotMap.backRightMotor.getOutputCurrent());
		// System.out.printf("left : front: %s; back: %s\n",
		// 	RobotMap.frontLeftMotor.getOutputCurrent(),
		// 	RobotMap.backLeftMotor.getOutputCurrent());
	}

	/**
	 * This function is called periodically during test mode.
	 */

	public void testPeriodic() {
	}
}
