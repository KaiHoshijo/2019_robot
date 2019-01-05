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
	
	// 11 10
	//  9 12

	public static TalonSRX frontRightMotor = new TalonSRX(1);
	public static TalonSRX backRightMotor = new TalonSRX(2);
	public static TalonSRX frontLeftMotor = new TalonSRX(3);
	public static TalonSRX backLeftMotor = new TalonSRX(4);
	
	public static TalonSRX rightIntakeMotor = new TalonSRX(7);
	public static TalonSRX leftIntakeMotor = new TalonSRX(8);
	
	//public static TalonSRX masterElevatorMotor = new TalonSRX(5);
	//public static TalonSRX slaveElevatorMotor = new TalonSRX(6);
}
