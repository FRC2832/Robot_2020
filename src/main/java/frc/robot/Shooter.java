package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shooter {
    public boolean shooterOff;
    HoloTable holo = HoloTable.getInstance();
    public XboxController gamepad1 = holo.getController();
    public Joystick joystick = holo.getJoystickRight();
    public WPI_TalonSRX ejector = holo.getEjector();
    public CANSparkMax rightRear = holo.getDriveRightRear();
    public CANSparkMax rightFront = holo.getDriveRightFront();
    private SpeedControllerGroup rightMotors = new SpeedControllerGroup(rightFront, rightRear);

    public void runShooter() throws InterruptedException{

        // read PID coefficients from SmartDashboard
        double p = SmartDashboard.getNumber("P Gain", 0);
        double i = SmartDashboard.getNumber("I Gain", 0);
        double d = SmartDashboard.getNumber("D Gain", 0);
        double iz = SmartDashboard.getNumber("I Zone", 0);
        double ff = SmartDashboard.getNumber("Feed Forward", 0);
        double max = SmartDashboard.getNumber("Max Output", 0);
        double min = SmartDashboard.getNumber("Min Output", 0);

        // if PID coefficients on SmartDashboard have changed, write new values to
        // controller
        if ((p != Robot.kP)) {
            holo.topPID.setP(p);
            Robot.kP = p;
        }
        if ((i != Robot.kI)) {
            holo.topPID.setI(i);
            Robot.kI = i;
        }
        if ((d != Robot.kD)) {
            holo.topPID.setD(d);
            Robot.kD = d;
        }
        if ((iz != Robot.kIz)) {
            holo.topPID.setIZone(iz);
            Robot.kIz = iz;
        }
        if ((ff != Robot.kFF)) {
            holo.topPID.setFF(ff);
            Robot.kFF = ff;
        }
        if ((max != Robot.kMaxOutput) || (min != Robot.kMinOutput)) {
            holo.topPID.setOutputRange(min, max);
            Robot.kMinOutput = min;
            Robot.kMaxOutput = max;
        }
        if (joystick.getTriggerPressed()) {
            Robot.setTop = Robot.fastTopRPM;
            Robot.setBottom = Robot.fastBottomRPM;
            shooterOff = false;
            System.out.println("@@@@@@");
            while(shooterOff = false){
                Thread.sleep(1000);
                ejector.set(1);
                Thread.sleep(500);
                ejector.set(0);
                if(joystick.getTriggerReleased()){
                    shooterOff = true;
                }
                if(joystick.getTriggerReleased()){
                    Robot.setTop = 0;
                    Robot.setBottom = 0;
                    ejector.set(0);
                    System.out.println("XXXXXXX");
                }
                if(joystick.getRawButtonPressed(3)){
                    Robot.setTop = Robot.slowTopRPM;
                    Robot.setBottom = Robot.slowBottomRPM;
                    ejector.set(.5);
                }
                if(joystick.getRawButtonReleased(3)){
                    Robot.setTop = 0;
                    Robot.setBottom = 0;
                    ejector.set(0);
                }
                if(joystick.getRawButtonPressed(2)){
                    
                }
                if(joystick.getRawButtonReleased(2)){
                    
                }
            }
        }
        holo.topPID.setReference(Robot.setTop, ControlType.kVelocity);
        holo.bottomPID.setReference(Robot.setBottom, ControlType.kVelocity);

    }
}