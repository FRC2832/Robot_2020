package frc.robot.commands.auton;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.HoloTable;
import frc.robot.Robot;

public class Option4 extends Command {

    HoloTable holo = HoloTable.getInstance();

    PigeonIMU gyro = HoloTable.getGyro();

    CANSparkMax leftFront = holo.getDriveLeftFront();
    CANSparkMax rightFront = holo.getDriveRightFront();
    CANSparkMax leftRear = holo.getDriveLeftRear();
    CANSparkMax rightRear = holo.getDriveRightRear();
    SpeedControllerGroup leftMotors = new SpeedControllerGroup(leftFront, leftRear);
    SpeedControllerGroup rightMotors = new SpeedControllerGroup(rightFront, rightRear);
    DifferentialDrive drive = new DifferentialDrive(leftMotors, rightMotors);

    CANEncoder encoder = leftFront.getEncoder();

    DoubleSolenoid dropIntake = holo.getDropIntake();

    WPI_TalonSRX intake = holo.getIntake();
    WPI_TalonSRX ejector = holo.getEjector();
    WPI_TalonSRX hopper = holo.getHopper();

    NetworkTable table = NetworkTableInstance.getDefault().getTable("datatable");
    NetworkTableEntry R_Angle = NetworkTableInstance.getDefault().getTable("datatable").getEntry("Lidar Angle");

    double lidarDistance = NetworkTableInstance.getDefault().getTable("datatable").getEntry("Lidar lidarDistance").getDouble(-1);
    double lazerAngle;
    double radius = 7.56;
    double circumfrance = Math.PI * radius * 2;
    double length = 84;
    double distance = 204;
    double lengthRevolutions = length / circumfrance;
    double distanceRevolutions = distance / circumfrance;
    int targetPixel = 640;
    boolean lie = true;
    boolean first = true;
    boolean second = false;
    boolean third = false;
    boolean fourth = false;
    boolean fifth = false;
    boolean sixth = false;
    boolean seventh = false;

    @Override
    protected void initialize() {
        R_Angle.getDouble(lazerAngle);
        dropIntake.set(Value.kForward);
        leftMotors.setInverted(true);
        rightMotors.setInverted(true);

        // TODO Auto-generated method stub
        super.initialize();

    }

    @Override
    protected void execute() {
        if (first) {
            double visionCenter = NetworkTableInstance.getDefault().getTable("datatable").getEntry("x").getDouble(-1);
            if (visionCenter == -1){
                Robot.setTop = Robot.fastTopRPM;
                Robot.setBottom = Robot.fastBottomRPM;
                holo.topPID.setReference(Robot.setTop, ControlType.kVelocity);
                holo.bottomPID.setReference(Robot.setBottom, ControlType.kVelocity);
                Timer.delay(3);
                first = false;
                second = true;
            }
            else{
                if (visionCenter - targetPixel >= 10) {
                    while (visionCenter - targetPixel >= 10) {
                        visionCenter = NetworkTableInstance.getDefault().getTable("datatable").getEntry("x").getDouble(-1);
                        drive.tankDrive(0.2, -0.2, false);
                    }
                }
                else if (visionCenter - targetPixel <= -10) {
                    while (visionCenter - targetPixel <= -10) {
                        visionCenter = NetworkTableInstance.getDefault().getTable("datatable").getEntry("x").getDouble(-1);
                        drive.tankDrive(-0.2, 0.2, false);
                    }
                }

                if (Math.abs(visionCenter - targetPixel) <= 10) {
                    if (lie == true) {
                        gyro.setYaw(90 - lazerAngle);
                        gyro.setFusedHeading(90 - lazerAngle);
                        lie = false;
                    }
                    drive.tankDrive(0, 0, false);

                    Robot.setTop = Robot.fastTopRPM;
                    Robot.setBottom = Robot.fastBottomRPM;
                    holo.topPID.setReference(Robot.setTop, ControlType.kVelocity);
                    holo.bottomPID.setReference(Robot.setBottom, ControlType.kVelocity);

                    hopper.set(0.5);
                    ejector.set(1);
                    Timer.delay(3);
                    first = false;
                    second = true;
                    
                }
            }
        }

        else if (second) {

            Robot.setTop = 0;
            Robot.setBottom = 0;
            holo.topPID.setReference(Robot.setTop, ControlType.kVelocity);
            holo.bottomPID.setReference(Robot.setBottom, ControlType.kVelocity);

            hopper.set(0);
            ejector.set(0);

            double[] ypr = new double[3];
            gyro.getYawPitchRoll(ypr);
            double yaw = ypr[0];

            if (yaw <= 45 && yaw >= 8) {
                drive.tankDrive(0.4, 0.4, false);
            }
            if (yaw <= 8 && yaw > 3) {
                drive.tankDrive(0.15, 0.15, false);
            }
            if (yaw < 3 && yaw > -3) {
                drive.tankDrive(0, 0, false);
                Timer.delay(.25);
                second = false;
                third = true;
            }
            if (yaw <= -8 && yaw > -3) {
                drive.tankDrive(-0.15, -0.15, false);
            }
            if (yaw <= -45 && yaw >= -8) {
                drive.tankDrive(-0.4, -0.4, false);
            }
        }
        else if (third) {
            if (lidarDistance != -1) {
                if (lidarDistance >= 100 && lidarDistance <= 275) {
                    drive.tankDrive(1, 1, false);
                }
                if (lidarDistance >= 275 && lidarDistance <= 292) {
                    drive.tankDrive(0.5, 0.5, false);
                }
                if (lidarDistance >= 292 && lidarDistance <= 306) {
                    drive.tankDrive(0.25, 0.25, false);
                }
                if (lidarDistance >= 306 && lidarDistance <= 310) {
                    drive.tankDrive(0, 0, false);
                    Timer.delay(.25);
                    third = false;
                    fourth = true;
                }
            }
            if (lidarDistance == -1){
                if (distanceRevolutions > encoder.getCountsPerRevolution()) {
                    drive.tankDrive(-0.5, -0.5, false);
                }
                if (distanceRevolutions <= encoder.getCountsPerRevolution()) {
                    drive.tankDrive(0, 0, false);
                    Timer.delay(.25);
                    third = false;
                    fourth = true;
                }
            }
        }
        else if (fourth) {
            if (gyro.getFusedHeading() >= 45 && gyro.getFusedHeading() <= 28) {
                drive.tankDrive(0.4, 0.4, false);
            }
            if (gyro.getFusedHeading() >= 28 && gyro.getFusedHeading() <= 16) {
                drive.tankDrive(0.15, 0.15, false);
            }
            if (gyro.getFusedHeading() >= 16 && gyro.getFusedHeading() <= 12) {
                drive.tankDrive(0, 0, false);
                Timer.delay(.25);
                    fourth = false;
                    fifth = true;
            }
            if (gyro.getFusedHeading() >= 12 && gyro.getFusedHeading() <= 0) {
                drive.tankDrive(-0.15, -0.15, false);
            }
            if (gyro.getFusedHeading() >= 0 && gyro.getFusedHeading() <= -45) {
                drive.tankDrive(-0.4, -0.4, false);
            }
        }
        else if (fifth) {
            if (lengthRevolutions > encoder.getCountsPerRevolution()) {
                drive.tankDrive(0.5, 0.5, false);
            }
            if (lengthRevolutions <= encoder.getCountsPerRevolution()) {
                drive.tankDrive(0, 0, false);
                Timer.delay(.25);
                    fifth = false;
                    sixth = true;
            }
        }
        if (sixth) {
            double visionCenter = NetworkTableInstance.getDefault().getTable("datatable").getEntry("x").getDouble(-1);
            if (visionCenter == -1){
                Robot.setTop = Robot.fastTopRPM;
                Robot.setBottom = Robot.fastBottomRPM;
                holo.topPID.setReference(Robot.setTop, ControlType.kVelocity);
                holo.bottomPID.setReference(Robot.setBottom, ControlType.kVelocity);
                Timer.delay(3);
                    sixth = false;
                    seventh = true;
            }
            else{
                if (visionCenter - targetPixel >= 10) {
                    while (visionCenter - targetPixel >= 10) {
                        visionCenter = NetworkTableInstance.getDefault().getTable("datatable").getEntry("x").getDouble(-1);
                        drive.tankDrive(0.2, 0.2, false);
                    }
                }
                else if (visionCenter - targetPixel <= -10) {
                    while (visionCenter - targetPixel <= -10) {
                        visionCenter = NetworkTableInstance.getDefault().getTable("datatable").getEntry("x").getDouble(-1);
                        drive.tankDrive(-0.2, -0.2, false);
                    }
                }
            }
            if (Math.abs(visionCenter - targetPixel) <= 10) {
                drive.tankDrive(0, 0, false);

                Robot.setTop = Robot.fastTopRPM;
                Robot.setBottom = Robot.fastBottomRPM;
                holo.topPID.setReference(Robot.setTop, ControlType.kVelocity);
                holo.bottomPID.setReference(Robot.setBottom, ControlType.kVelocity);

                hopper.set(0.5);
                ejector.set(1);
                Timer.delay(3);
                    sixth = false;
                    seventh = true;
            }
        }
        if (seventh) {
            Robot.setTop = 0;
            Robot.setBottom = 0;
            holo.topPID.setReference(Robot.setTop, ControlType.kVelocity);
            holo.bottomPID.setReference(Robot.setBottom, ControlType.kVelocity);

            hopper.set(0);
            ejector.set(0);
            Timer.delay(.25);
            seventh = false;
        }

        // TODO Auto-generated method stub
        super.execute();
    }

    @Override
    protected void end() {
        // TODO Auto-generated method stub
        super.end();
    }

    @Override
    public boolean isFinished() {
        if(!seventh){
        // TODO Auto-generated method stub
        return true;
        }
        return false;
    }
}