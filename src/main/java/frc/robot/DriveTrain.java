/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/


import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;



public class DriveTrain extends SubsystemBase{
  private WPI_TalonSRX driveTurn;
  private CANSparkMax leftFront; 
	private CANSparkMax rightFront;
	private CANSparkMax leftRear;
	private CANSparkMax rightRear;
  private DifferentialDrive differentialDrive;
  /**
   * Creates a new DriveTrain.
   */
  public DriveTrain() {
    driveTurn = HoloTable.getDriveTurn();
    leftFront = HoloTable.getDriveLeftFront();
    rightFront = HoloTable.getDriveRightFront();
    leftRear= HoloTable.getDriveLeftRear();
    rightRear = HoloTable.getDriveRightRear();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void setDifferentialDriveCommand(double xSpeed, double zRotation) {
    //WRITE STUFF HERE ONCE YOU HAVE INPUTS
    differentialDrive.arcadeDrive(xSpeed, zRotation, true);
}
}
