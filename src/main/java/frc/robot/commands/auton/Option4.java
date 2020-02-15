package frc.robot.commands.auton;

import com.ctre.phoenix.sensors.PigeonIMU;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.HoloTable;

public class Option4 extends Command {
    HoloTable holo = HoloTable.getInstance();
    PigeonIMU gyro = holo.getGyro();
    CANSparkMax leftFront = holo.getDriveLeftFront();
    CANSparkMax rightFront = holo.getDriveRightFront();
    CANSparkMax leftRear = holo.getDriveLeftRear();
    CANSparkMax rightRear = holo.getDriveRightRear();
    SpeedControllerGroup leftMotors = new SpeedControllerGroup(leftFront, leftRear);
    SpeedControllerGroup rightMotors = new SpeedControllerGroup(rightFront, rightRear);
    double angle;
    

    @Override
    protected void initialize() {
        leftMotors.setInverted(true);
        rightMotors.setInverted(true);
        gyro.setYaw(angle, 0);
        
        // TODO Auto-generated method stub
        super.initialize();
    }

    @Override
    protected void execute() {
        if (angle < 28){
            leftMotors.set(-0.3);
            rightMotors.set(0.3);

        }else if(angle == 28){
            

        }else if(angle > 28){
            leftMotors.set(0.3);
            rightMotors.set(-0.3);

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