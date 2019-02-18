/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.util.Arrays;
import java.util.Comparator;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.tools.Contour;
import frc.robot.tools.PID;
import frc.robot.tools.Point;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.tan;
import static java.lang.Math.atan2;


public class AlignTargetCommand extends Command {

  NetworkTableEntry valid;
  NetworkTableEntry xOffset;
  NetworkTableEntry yOffset;
  NetworkTableEntry skew;
  // NetworkTableEntry centerX;

  PID xControlLoop;
  PID yControlLoop;

  public AlignTargetCommand() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.drivetrainSubsystem);
    requires(Robot.limelightSystem);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    System.out.println("Initializing");
    NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    this.valid = table.getEntry("tv");
    this.xOffset = table.getEntry("tx");
    this.yOffset = table.getEntry("ty");
    // this.area = table.getEntry("ta");
    this.skew = table.getEntry("ts");
    // NetworkTable table = NetworkTableInstance.getDefault().getTable("targetContours");
    // this.centerX = table.getEntry("centerX");

    this.xControlLoop = new PID(0.05, 0.0002, 0, 5);

    this.xControlLoop.setSetPoint(0);
    this.xControlLoop.setDeadband(1);
    this.xControlLoop.setThreshold(0.05);

    this.yControlLoop = new PID(0.025, 0.000, 0, 5);

    this.yControlLoop.setSetPoint(0);
    this.yControlLoop.setDeadband(1);
    this.yControlLoop.setThreshold(0.05);

    RobotMap.backRightMotor.set(ControlMode.Follower, RobotMap.frontRightMotorID);
    RobotMap.backLeftMotor.set(ControlMode.Follower, RobotMap.frontLeftMotorID);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {

    if (this.valid.getDouble(0) == 0) {
      System.out.println("No target found");
      RobotMap.frontRightMotor.set(ControlMode.PercentOutput, 0);
      RobotMap.frontLeftMotor.set(ControlMode.PercentOutput, 0);
      return;
    }

    double targetDist = 24;

    double skew = this.skew.getDouble(0) / 180 * Math.PI;
    double y = this.yOffset.getDouble(0) / 180 * Math.PI;

    double a = skew;
    double r = (RobotMap.targetHeight - RobotMap.limelightHeight)
      / tan(RobotMap.limelightAngle + y + RobotMap.crosshairYAngle);
    double l2 = r * cos(a) - targetDist;
    double d = r * sin(a);
    double theta = atan2(d, l2);
    double b = theta - a;
    double dt = d / sin(theta);

    System.out.printf("b: %+.2f, dt: %+.2f, theta: %+.2f", b, dt, theta);
   
    this.xControlLoop.updatePID(this.xOffset.getDouble(0));
    this.yControlLoop.updatePID(this.yOffset.getDouble(0));

    // System.out.printf("x: %+.2f; y: %+.2f; out x: %+.2f out y: %+.2f%n",
    //   this.xOffset.getDouble(0),
    //   this.yOffset.getDouble(0),
    //   this.xControlLoop.getResult(),
    //   this.yControlLoop.getResult());

    double turnError = this.xControlLoop.getResult();
    double distanceError = this.yControlLoop.getResult();

    // RobotMap.frontRightMotor.set(ControlMode.PercentOutput, turnError + distanceError);
    // RobotMap.frontLeftMotor.set(ControlMode.PercentOutput, -turnError + distanceError);
    // RobotMap.frontRightMotor.set(ControlMode.PercentOutput, turnError);
    // RobotMap.frontLeftMotor.set(ControlMode.PercentOutput, -turnError);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }

  // Prototype code for GRIP pipeline
  private double getDistanceToCenterContour() {
    // double[] centers = this.centerX.getDoubleArray(new double[]{});
    double[] centerXs = {-50, 100};
    double[] centerYs = {70, 70};
    double[] areas = {45, 50};

    if (centerXs.length < 2) {
      return -1;
    }
    
    Contour[] contours = new Contour[centerXs.length];

    for (int i = 0; i < centerXs.length; i++) {
      contours[i] = new Contour(new Point(centerXs[i], centerYs[i]), areas[i]);
    }

    Point target;

    if (contours.length > 2) {
      Comparator<Object> sorter  
        = Comparator.comparing(contour ->
          Point.origin.distance(((Contour) contour).center));

      Arrays.sort(contours, sorter);
    }

    target = contours[0].center.midpoint(contours[1].center);

    double targetAngle = 0;

    double distance = (RobotMap.targetHeight - RobotMap.limelightHeight)
      / Math.tan(RobotMap.limelightAngle + targetAngle);

    return distance;
  }
}
