package frc.robot;

/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.command.Subsystem;

public class DriveTrain extends Subsystem {
  private HoloTable holo = HoloTable.getInstance();
  private WPI_TalonSRX driveTurn;
  private CANSparkMax leftFront;
  private CANSparkMax rightFront;
  private CANSparkMax leftRear;
  private CANSparkMax rightRear;
  private DifferentialDrive differentialDrive;
  private PigeonIMU gyro;
  private XboxController controller;
  private DoubleSolenoid turnSolenoid;
  private SpeedControllerGroup leftMotors;
  private SpeedControllerGroup rightMotors;
  private Joystick joystickLeft;
  private Joystick joystickRight;

  /**
   * Creates a new DriveTrain.
   */
  public DriveTrain() {
    driveTurn = holo.getDriveTurn();
    leftFront = holo.getDriveLeftFront();
    rightFront = holo.getDriveRightFront();
    leftRear = holo.getDriveLeftRear();
    rightRear = holo.getDriveRightRear();
    gyro = holo.getGyro();
    controller = holo.getController();
    joystickLeft = holo.getJoystickLeft();
    joystickRight = holo.getJoystickRight();
    turnSolenoid = holo.getTurnSolenoid();
    leftMotors = new SpeedControllerGroup(leftFront, leftRear);
    leftMotors.setInverted(true);
    rightMotors = new SpeedControllerGroup(rightFront, rightRear);
    rightMotors.setInverted(true);
    differentialDrive = new DifferentialDrive(leftMotors, rightMotors);
<<<<<<< Updated upstream
=======
    driveCoeff = 1;
    openLoopRampRate(0.8);
    setSmartCurrent(70);
  }

  public void setSmartCurrent(int limit) {
    leftFront.setSmartCurrentLimit(limit);
    leftRear.setSmartCurrentLimit(limit);
    rightRear.setSmartCurrentLimit(limit);
    rightFront.setSmartCurrentLimit(limit);
  }

  public void openLoopRampRate(double rate) {
    leftFront.setOpenLoopRampRate(rate);
    leftRear.setOpenLoopRampRate(rate);
    rightRear.setOpenLoopRampRate(rate);
    rightFront.setOpenLoopRampRate(rate);
>>>>>>> Stashed changes
  }

  @Override
  public void periodic() {

    // This method will be called once per scheduler run
  }

  public void driveTank() {
<<<<<<< Updated upstream
    differentialDrive.tankDrive(joystickLeft.getY(), joystickRight.getY(), true);
=======
    if (joystickRight.getRawButton(2))
      driveCoeff = .3;
    else
      driveCoeff = 1;
    differentialDrive.tankDrive(driveCoeff * Math.pow(joystickLeft.getY(), 3) * 0.5, driveCoeff * Math.pow(joystickRight.getY(), 3) * 0.5, false);
>>>>>>> Stashed changes
  }
  /*public void driveArcade() {
    differentialDrive.arcadeDrive(controller.getRawAxis(0), controller.getRawAxis(4), true);  Not Being Used
  }*/

  public void extendDriveTurn(){
    turnSolenoid.set(DoubleSolenoid.Value.kForward);
  }
  public void retractDriveTurn(){
    turnSolenoid.set(DoubleSolenoid.Value.kReverse);
  }

  @Override
  protected void initDefaultCommand() {
    // TODO Auto-generated method stub

  }
}
