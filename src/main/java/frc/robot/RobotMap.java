/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	// For example to map the left and right motors, you could define the
	// following variables to use with your drivetrain subsystem.
	// public static int leftMotor = 1;
	// public static int rightMotor = 2;

	// If you are using multiple modules, make sure to define both the port
	// number and the module. For example you with a rangefinder:
	// public static int rangefinderPort = 1;
	// public static int rangefinderModule = 1;

	public static final int frontRightMotorID = 14;
	public static final int backRightMotorID = 9;
	public static final int frontLeftMotorID = 4;
	public static final int backLeftMotorID = 8;

	public static final int leftClimbingID = 6;
	public static final int rightClimbingID = 2;

	public static final int leadScrewMotorID = 7;

	public static final int rightIntakeMotorID = 3;
	public static final int leftIntakeMotorID = 10;

	public static TalonSRX frontRightMotor = new TalonSRX(frontRightMotorID);
	public static TalonSRX backRightMotor = new TalonSRX(backRightMotorID);
	public static TalonSRX frontLeftMotor = new TalonSRX(frontLeftMotorID);
	public static TalonSRX backLeftMotor = new TalonSRX(backLeftMotorID);

	public static TalonSRX leftClimbingMotor = new TalonSRX(leftClimbingID);
	public static TalonSRX rightClimbingMotor = new TalonSRX(rightClimbingID);

	public static TalonSRX leadScrewMotor = new TalonSRX(leadScrewMotorID);

	public static TalonSRX rightIntakeMotor = new TalonSRX(rightIntakeMotorID);
	public static TalonSRX leftIntakeMotor = new TalonSRX(leftIntakeMotorID);

	public static final double limelightAngle = 0.4596396144303d;
	public static final double limelightHeight = 0.5;
	// public static final double targetHeight = 38.43645499d;
	public static final double targetHeight = 34.5d;

	public static final double crosshairYAngle = 0.283616d;

	public static final double drivetrainEncoderTicksPerRev = 4096;
	public static final double drivetrainGearRatio = 1.0 / 1.0; // FIXME
}
