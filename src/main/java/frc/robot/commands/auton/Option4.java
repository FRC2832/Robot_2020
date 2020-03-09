package frc.robot.commands.auton;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;
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

    CANEncoder encoder = leftFront.getEncoder();

    DoubleSolenoid dropIntake = holo.getDropIntake();

    WPI_TalonSRX intake = holo.getIntake();
    WPI_TalonSRX ejector = holo.getEjector();
    WPI_TalonSRX hopper = holo.getHopper();

    NetworkTable table = NetworkTableInstance.getDefault().getTable("datatable");
    NetworkTableEntry R_Angle = NetworkTableInstance.getDefault().getTable("datatable").getEntry("Lidar Angle");

    double distance = NetworkTableInstance.getDefault().getTable("datatable").getEntry("Lidar Distance").getDouble(-1);
    double lazerAngle;
    double radius = 7.56;
    double circumfrance = Math.PI * radius * 2;
    double length = 84;
    double revolutions = length / circumfrance;
    int targetPixel = 640;
    int visionCenter;
    boolean lie = true;

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
        if (Timer.getFPGATimestamp() >= 0 && Timer.getFPGATimestamp() <= 4) {
            
            if (visionCenter - targetPixel >= 10) {
                while (visionCenter - targetPixel >= 10) {
                    leftMotors.set(0.2);
                    rightMotors.set(-0.2);
                }
            }
            if (visionCenter - targetPixel <= -10) {
                while (visionCenter - targetPixel <= -10) {
                    leftMotors.set(-0.2);
                    rightMotors.set(0.2);
                }
            }

            if (Math.abs(visionCenter - targetPixel) <= 10) {
                if(lie == true){
                    gyro.setYaw(90 - lazerAngle);
                    gyro.setFusedHeading(90 - lazerAngle);
                    lie = false;
                }
                leftMotors.set(0);
                rightMotors.set(0);

                Robot.setTop = Robot.fastTopRPM;
                Robot.setBottom = Robot.fastBottomRPM;
                holo.topPID.setReference(Robot.setTop, ControlType.kVelocity);
                holo.bottomPID.setReference(Robot.setBottom, ControlType.kVelocity);

                hopper.set(0.5);
                ejector.set(1);
                }
        }
        
        if(Timer.getFPGATimestamp() >= 4 && Timer.getFPGATimestamp() <= 7) {

            Robot.setTop = 0;
            Robot.setBottom = 0;
            holo.topPID.setReference(Robot.setTop, ControlType.kVelocity);
            holo.bottomPID.setReference(Robot.setBottom, ControlType.kVelocity);

            hopper.set(0);
            ejector.set(0);

            double[] ypr = new double[3];
            gyro.getYawPitchRoll(ypr);
            double yaw = ypr[0];
            
            if(yaw <= 45 && yaw >= 8){
                leftMotors.set(0.4);
                rightMotors.set(-0.4);
            }
            if(yaw <= 8 && yaw > 3){
                leftMotors.set(0.15);
                rightMotors.set(-0.15);
            }
            if(yaw < 3 && yaw > -3){
                leftMotors.set(0);
                
                rightMotors.set(0);
            }
            if(yaw <= -8 && yaw > -3){
                leftMotors.set(-0.15);
                rightMotors.set(0.15);
            }
            if(yaw <= -45 && yaw >= -8){
                leftMotors.set(-0.4);
                rightMotors.set(0.4);
            }
        }
        if(Timer.getFPGATimestamp() >= 7 && Timer.getFPGATimestamp() <= 9) {
            if(distance >= 100 && distance <= 275 ){
                leftMotors.set(1);
                rightMotors.set(1);
            }
            if(distance >= 275 && distance <= 292){
                leftMotors.set(.5);
                rightMotors.set(.5);
            }
            if(distance >= 292 && distance <= 306){
                leftMotors.set(.25);
                rightMotors.set(.25);
            }
            if(distance >= 306 && distance <= 310){
                leftMotors.set(0);
                rightMotors.set(0);
            }
        }
        if(Timer.getFPGATimestamp() >= 9 && Timer.getFPGATimestamp() <= 11){
            if(gyro.getFusedHeading() >= 45 && gyro.getFusedHeading() <= 28){
                leftMotors.set(0.4);
                rightMotors.set(-0.4);
            }
            if(gyro.getFusedHeading() >= 28 && gyro.getFusedHeading() <= 16){
                leftMotors.set(0.15);
                rightMotors.set(-0.15);
            }
            if(gyro.getFusedHeading() >= 16 && gyro.getFusedHeading() <= 12){
                leftMotors.set(0);
                rightMotors.set(0);
            }
            if(gyro.getFusedHeading() >= 12 && gyro.getFusedHeading() <= 0){
                leftMotors.set(0.15);
                rightMotors.set(-0.15);
            }
            if(gyro.getFusedHeading() >= 0 && gyro.getFusedHeading() <= -45){
                leftMotors.set(0.4);
                rightMotors.set(-0.4);
            }
        }
        if(Timer.getFPGATimestamp() >= 11 && Timer.getFPGATimestamp() <= 12){
            if(revolutions > encoder.getCountsPerRevolution()){
                leftMotors.set(.5);
                rightMotors.set(.5);
            }
            if(revolutions <= encoder.getCountsPerRevolution()){
                leftMotors.set(0);
                rightMotors.set(0 );
            }
        }
        if(Timer.getFPGATimestamp() >= 13 && Timer.getFPGATimestamp() <= 14.75){
            if (visionCenter - targetPixel >= 10) {
                while (visionCenter - targetPixel >= 10) {
                    leftMotors.set(0.2);
                    rightMotors.set(-0.2);
                }
            }
            if (visionCenter - targetPixel <= -10) {
                while (visionCenter - targetPixel <= -10) {
                    leftMotors.set(-0.2);
                    rightMotors.set(0.2);
                }
            }

            if (Math.abs(visionCenter - targetPixel) <= 10) {
                if(lie == true){
                    gyro.setYaw(90 - lazerAngle);
                    gyro.setFusedHeading(90 - lazerAngle);
                    lie = false;
                }
                leftMotors.set(0);
                rightMotors.set(0);

                Robot.setTop = Robot.fastTopRPM;
                Robot.setBottom = Robot.fastBottomRPM;
                holo.topPID.setReference(Robot.setTop, ControlType.kVelocity);
                holo.bottomPID.setReference(Robot.setBottom, ControlType.kVelocity);

                hopper.set(0.5);
                ejector.set(1);
                }
        }
        if(Timer.getFPGATimestamp() == 14.9){
            Robot.setTop = 0;
            Robot.setBottom = 0;
            holo.topPID.setReference(Robot.setTop, ControlType.kVelocity);
            holo.bottomPID.setReference(Robot.setBottom, ControlType.kVelocity);

            hopper.set(0);
            ejector.set(0);
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
        // TODO Auto-generated method stub
        return true;
    }

}