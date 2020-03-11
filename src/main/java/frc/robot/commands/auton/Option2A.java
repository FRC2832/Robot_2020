package frc.robot.commands.auton;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.HoloTable;
import frc.robot.Robot;
import frc.robot.Ingestor;
import frc.robot.Hopper;
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
    CANEncoder encoder = new CANEncoder(leftFront);
    WPI_TalonSRX hopper = holo.getHopper();
    WPI_TalonSRX ejector = holo.getEjector();
    double angle = 208;
    double radius = 7.56; //(In cm)
    double distance = 60; //Distance to travel after shooting (In cm)
    
    protected void initialize(){
        leftMotors.setInverted(true);
        rightMotors.setInverted(true);
        gyro.setYaw(angle, 0);
        super.initialize();
    }
    public void execute(){
        try{
            Thread.sleep(6000);
        }
        catch(Exception e){}

        Robot.setTop = Robot.fastTopRPM;
        Robot.setBottom = Robot.fastBottomRPM;
        hopper.set(.5);
        
        holo.topPID.setReference(Robot.setTop, ControlType.kVelocity);
        holo.bottomPID.setReference(Robot.setBottom, ControlType.kVelocity);

        try{Thread.sleep(1000);}
        catch(Exception e){}
        ejector.set(1);
        try{Thread.sleep(500);}
        catch(Exception e){}
        ejector.set(0);

        try{Thread.sleep(1000);}
        catch(Exception e){}
        ejector.set(1);
        try{Thread.sleep(500);}
        catch(Exception e){}
        ejector.set(0);
        
        try{Thread.sleep(1000);}
        catch(Exception e){}
        ejector.set(1);
        try{Thread.sleep(500);}
        catch(Exception e){}
        ejector.set(0);

        try{Thread.sleep(1000);}
        catch(Exception e){}
        
        hopper.set(0);
        holo.topPID.setReference(0, ControlType.kVelocity);
        holo.bottomPID.setReference(0, ControlType.kVelocity);
        double[] ypr = new double[3];
        gyro.getYawPitchRoll(ypr);
        while(ypr[0] < 355.0){
            gyro.getYawPitchRoll(ypr);
            differentialDrive.arcadeDrive(0, -.5);
        }
        while(ypr[0] < 360.0){
            gyro.getYawPitchRoll(ypr);
            differentialDrive.arcadeDrive(0, -.3);
        }
        double rotations = encoder.getPosition(); //Relies on a CANEncoder, hopefully this works otherwise use LIDAR data
        while(rotations*(2*Math.PI*radius) < distance){
            differentialDrive.tankDrive(.4, 0);
            rotations = encoder.getPosition();
        }
        differentialDrive.tankDrive(0,0);
    }
    @Override
    protected boolean isFinished() {
        // TODO Auto-generated method stub
        return false;
    }



}