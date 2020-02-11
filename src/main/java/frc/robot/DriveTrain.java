package frc.robot;

/*----------------------------------------------------------------------------*/

/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;

public class DriveTrain extends Subsystem {
  private HoloTable holo = HoloTable.getInstance();
  private CANSparkMax leftFront;
  private CANSparkMax rightFront;
  private CANSparkMax leftRear;
  private CANSparkMax rightRear;
  private DifferentialDrive differentialDrive;
  private DoubleSolenoid turnSolenoid;
  private SpeedControllerGroup leftMotors;
  private SpeedControllerGroup rightMotors;
  private Joystick joystickLeft;
  private Joystick joystickRight;

  /**
   * Creates a new DriveTrain.
   */
  public DriveTrain() {
    leftFront = holo.getDriveLeftFront();
    rightFront = holo.getDriveRightFront();
    leftRear = holo.getDriveLeftRear();
    rightRear = holo.getDriveRightRear();
    joystickLeft = holo.getJoystickLeft();
    joystickRight = holo.getJoystickRight();
    turnSolenoid = holo.getTurnSolenoid();
    leftMotors = new SpeedControllerGroup(leftFront, leftRear);
    leftMotors.setInverted(true);
    rightMotors = new SpeedControllerGroup(rightFront, rightRear);
    rightMotors.setInverted(true);
    differentialDrive = new DifferentialDrive(leftMotors, rightMotors);
  }

  @Override
  public void periodic() {

    // This method will be called once per scheduler run
  }

  public void driveTank() {
    differentialDrive.tankDrive(joystickLeft.getY(), joystickRight.getY(), true);
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
