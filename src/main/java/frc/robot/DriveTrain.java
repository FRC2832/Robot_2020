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
  }

  @Override
  public void periodic() {

    // This method will be called once per scheduler run
  }

  public void driveTank() {
    differentialDrive.tankDrive(controller.getRawAxis(0), controller.getRawAxis(3), true);
  }
  public void driveArcade() {
    // WRITE STUFF HERE ONCE YOU HAVE INPUTS
    differentialDrive.arcadeDrive(controller.getRawAxis(0), controller.getRawAxis(4), true);
  }
public void extendDriveTurn(){
  // ADD IMPLEMENTATION ONCE WE KNOW WHAT KIND OF ACTUATOR DOES THIS
}

public void retractDriveTurn(){
  // ADD IMPLEMENTATION ONCE WE KNOW WHAT KIND OF ACTUATOR DOES THIS
}

  @Override
  protected void initDefaultCommand() {
    // TODO Auto-generated method stub

  }
}
