package frc.robot.commands.auton;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.HoloTable;
import frc.robot.Robot;

public class Option4 extends Command {
    HoloTable holo = HoloTable.getInstance();
    PigeonIMU gyro = holo.getGyro();
    CANSparkMax leftFront = holo.getDriveLeftFront();
    CANSparkMax rightFront = holo.getDriveRightFront();
    CANSparkMax leftRear = holo.getDriveLeftRear();
    CANSparkMax rightRear = holo.getDriveRightRear();
    SpeedControllerGroup leftMotors = new SpeedControllerGroup(leftFront, leftRear);
    SpeedControllerGroup rightMotors = new SpeedControllerGroup(rightFront, rightRear);
    DoubleSolenoid dropIntake1 = holo.getDropIntake1();
    DoubleSolenoid dropIntake2 = holo.getDropIntake2();
    WPI_TalonSRX intake = holo.getIntake();
    WPI_TalonSRX ejector = holo.getEjector();
    WPI_TalonSRX hopper = holo.getHopper();
    Timer timer = new Timer();
    double angle;
    int targetPixel = 640;
    int visionCenter;

    @Override
    protected void initialize() {
        timer.reset();
        timer.start();
        dropIntake1.set(Value.kForward);
        dropIntake2.set(Value.kForward);
        leftMotors.setInverted(true);
        rightMotors.setInverted(true);

        // TODO Auto-generated method stub
        super.initialize();
    }

    @Override
    protected void execute() {
        if (timer.get() >= 0 && timer.get() <= 5) {
            /*
             * if (angle < 25){ leftMotors.set(-0.35); rightMotors.set(0.35);
             * 
             * }else if(angle >= 25 && angle <= 30){ if (visionCenter - targetPixel >= 10) {
             * while (visionCenter - targetPixel >= 10) { leftMotors.set(0.2);
             * rightMotors.set(-0.2); } } if (visionCenter - targetPixel <= -10) { while
             * (visionCenter - targetPixel <= -10) { leftMotors.set(-0.2);
             * rightMotors.set(0.2); } }
             * 
             * if(Math.abs(visionCenter - targetPixel) <= 10){ leftMotors.set(0);
             * rightMotors.set(0); Robot.setTop = Robot.fastTopRPM; Robot.setBottom =
             * Robot.fastBottomRPM; hopper.set(0.5); while(Math.abs(visionCenter -
             * targetPixel) <= 10){ Thread.sleep(1000); ejector.set(1); Thread.sleep(500);
             * ejector.set(0); } }
             * 
             * }else if(angle > 30){ leftMotors.set(0.35); rightMotors.set(-0.35); }
             */
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
                gyro.setYaw(angle, 28);
                leftMotors.set(0);
                rightMotors.set(0);
                Robot.setTop = Robot.fastTopRPM;
                Robot.setBottom = Robot.fastBottomRPM;
                hopper.set(0.5);
                while ((visionCenter - targetPixel) <= 10) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    ejector.set(1);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    ejector.set(0);
                }
            }
        if(timer.get() >=5 && timer.get() <= 7){
            if(angle <= 30 && angle >= 5){
                leftMotors.set(0.4);
                rightMotors.set(-0.4);
            }
            if(angle <= 5 && angle > 0){
                leftMotors.set(0.15);
                rightMotors.set(-0.15);
            }
            if(angle == 0){
                leftMotors.set(0);
                
                rightMotors.set(0);
            }
            if(angle <= -5 && angle > -0){
                leftMotors.set(-0.15);
                rightMotors.set(0.15);
            }
            if(angle <= -30 && angle >= -5){
                leftMotors.set(-0.4);
                rightMotors.set(0.4);
            }
            if(timer.get() >=7 && timer.get() <= 10){
                /*Lidar stuff to make when Lidar is ready.
                Outline: check if center of the robot is 27.75 inches from the wall;
                                if not, move to it
                                if yes, move back 204 inches and intake the balls
                                if moved back 204 inches, turn 14 degrees(not sure about actual angle this is just a estimate)
                                if turned 14 degrees,move forward 84 inches
                                if moved forward 84 inches, start shooter
                */

            }
        }
    }
        holo.topPID.setReference(Robot.setTop, ControlType.kVelocity);
        holo.bottomPID.setReference(Robot.setBottom, ControlType.kVelocity);
        
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