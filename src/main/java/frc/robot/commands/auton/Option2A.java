package frc.robot.commands.auton;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.HoloTable;
import frc.robot.Robot;
import frc.robot.DriveTrain;
//Top Position
public class Option2A extends Command {
    HoloTable holo = HoloTable.getInstance();
    PigeonIMU gyro = holo.getGyro();
    CANSparkMax leftFront = holo.getDriveLeftFront();
    CANSparkMax rightFront = holo.getDriveRightFront();
    CANSparkMax leftRear = holo.getDriveLeftRear();
    CANSparkMax rightRear = holo.getDriveRightRear();
    SpeedControllerGroup leftMotors = new SpeedControllerGroup(leftFront, leftRear);
    SpeedControllerGroup rightMotors = new SpeedControllerGroup(rightFront, rightRear);
    DifferentialDrive differentialDrive = new DifferentialDrive(leftMotors, rightMotors);
    DriveTrain driveTrain = new DriveTrain();
    Timer timer = new Timer();
    CANEncoder encoder = new CANEncoder(leftFront);
    NetworkTable table = NetworkTableInstance.getDefault().getTable("datatable");
    double[] defaultValue = {-1};
    WPI_TalonSRX hopper = holo.getHopper();
    WPI_TalonSRX ejector = holo.getEjector();
    NetworkTableEntry lidarValue;
    double radius = 7.56; //(In cm)
    double distance = 151.8; //Distance to travel after shooting (In cm) 
    protected void initialize(){
        timer.reset();
        timer.start();
        leftMotors.setInverted(true);
        rightMotors.setInverted(true);
        gyro.setYaw(208.0);
        super.initialize();
    }
    public void execute(){
        try{
            Thread.sleep(6000);
        }
        catch(Exception e){}
        
                Robot.setTop = Robot.fastTopRPM;
                Robot.setBottom = Robot.fastBottomRPM;
                hopper.set(0.5);
                ejector.set(1);
        
        holo.topPID.setReference(Robot.setTop, ControlType.kVelocity);
        holo.bottomPID.setReference(Robot.setBottom, ControlType.kVelocity);
        try{
            Thread.sleep(3000);
        }
        catch(Exception e){}

        hopper.set(0);
        ejector.set(0);
        holo.topPID.setReference(0, ControlType.kVelocity);
        holo.bottomPID.setReference(0, ControlType.kVelocity);
        
        double[] ypr = new double[3];
        gyro.getYawPitchRoll(ypr);
        while(ypr[0] > 175.0){
            gyro.getYawPitchRoll(ypr);
            differentialDrive.arcadeDrive(0, .5);
        }
        while(ypr[0] > 178.0){
            gyro.getYawPitchRoll(ypr);
            differentialDrive.arcadeDrive(0, .3);
        }
        differentialDrive.arcadeDrive(0, 0);
        double rotations = encoder.getPosition();
        //In order to read distance to the wall, the robot will face backwards and back up into the correct
        //position, then rotate back forward at the end
        lidarValue = table.getEntry("distance0");
        double lidarDistance = lidarValue.getDouble(-1);
        if(lidarDistance != -1){
            while(lidarDistance < distance){
                differentialDrive.tankDrive(-.4, -.4);
                lidarDistance = table.getEntry("distance0").getDouble(-1);
            }
        }
       
        else {
            //Uses Encoders when lidar data is unavailable
            while(rotations * 2 * Math.PI * radius < distance)
                differentialDrive.tankDrive(-.4, -.4);
                rotations = encoder.getPosition();
        }
        differentialDrive.tankDrive(0,0);
        
        while(ypr[0] > 5.0){
            gyro.getYawPitchRoll(ypr);
            differentialDrive.arcadeDrive(0, .5);
        }
        while(ypr[0] > 1.0){
            gyro.getYawPitchRoll(ypr);
            differentialDrive.arcadeDrive(0, .3);
        }
        differentialDrive.arcadeDrive(0, 0);
        
    }
    @Override
    protected boolean isFinished() {
        return true;
    }



}