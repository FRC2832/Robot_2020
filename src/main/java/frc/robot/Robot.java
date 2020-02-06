/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.XboxController;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
    private static final String kDefaultAuto = "Default";
    private static final String kCustomAuto = "My Auto";
    private static final BallCount tracker = new BallCount();
    private HoloTable holo = HoloTable.getInstance();
    private Shooter shooter = new Shooter();
    private String m_autoSelected;
    private final SendableChooser<String> m_chooser = new SendableChooser<>();
    public static double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput, topRPM, bottomRPM, setTop, setBottom;
    public CANPIDController topPID;
    public XboxController gamepad1 = holo.getGamepad1(); 
    public CANPIDController bottomPID;
    CANSparkMax topShooter = holo.getTopShooter();
    CANSparkMax bottomShooter = holo.getBottomShooter();

    /**
     * This function is run when the robot is first started up and should be used
     * for any initialization code.
     */
    @Override
    public void robotInit() {
        m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
        m_chooser.addOption("My Auto", kCustomAuto);
        SmartDashboard.putData("Auto choices", m_chooser);
        topPID = topShooter.getPIDController();
        bottomPID = bottomShooter.getPIDController();
        
        kP = 0;
    kI = 0;
    kD = 0;
    kIz = 0;
    kFF = 0.0023;
    kMaxOutput = 1;
    kMinOutput = -1;
    topRPM = -5700;
    bottomRPM = 5700;
    
    // set PID coefficients
    bottomPID.setP(kP);
    bottomPID.setI(kI);
    bottomPID.setD(kD);
    bottomPID.setIZone(kIz);
    bottomPID.setFF(kFF);
    bottomPID.setOutputRange(kMinOutput, kMaxOutput);
    topPID.setP(kP);
    topPID.setI(kI);
    topPID.setD(kD);
    topPID.setIZone(kIz);
    topPID.setFF(kFF);
    topPID.setOutputRange(kMinOutput, kMaxOutput);
    

    // display PID coefficients on SmartDashboard
         SmartDashboard.putNumber("P Gain", kP);
         SmartDashboard.putNumber("I Gain", kI);
         SmartDashboard.putNumber("D Gain", kD);
         SmartDashboard.putNumber("I Zone", kIz);
         SmartDashboard.putNumber("Feed Forward", kFF);
         SmartDashboard.putNumber("Max Output", kMaxOutput);
         SmartDashboard.putNumber("Min Output", kMinOutput);
         
   
    }

    /**
     * This function is called every robot packet, no matter the mode. Use this for
     * items like diagnostics that you want ran during disabled, autonomous,
     * teleoperated and test.
     *
     * <p>
     * This runs after the mode specific periodic functions, but before LiveWindow
     * and SmartDashboard integrated updating.
     */
    @Override
    public void robotPeriodic() {
        SmartDashboard.putBoolean("Slot 1", tracker.countBalls());
        SmartDashboard.putBoolean("Slot 2", tracker.countBalls2());
        SmartDashboard.putBoolean("Slot 3", tracker.countBalls3());
        SmartDashboard.putBoolean("Slot 4", tracker.countBalls4());
        SmartDashboard.putBoolean("Slot 5", tracker.countBalls5());
    }

    /**
     * This autonomous (along with the chooser code above) shows how to select
     * between different autonomous modes using the dashboard. The sendable chooser
     * code works with the Java SmartDashboard. If you prefer the LabVIEW Dashboard,
     * remove all of the chooser code and uncomment the getString line to get the
     * auto name from the text box below the Gyro
     *
     * <p>
     * You can add additional auto modes by adding additional comparisons to the
     * switch structure below with additional strings. If using the SendableChooser
     * make sure to add them to the chooser code above as well.
     */
    @Override
    public void autonomousInit() {
        m_autoSelected = m_chooser.getSelected();
        // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
        System.out.println("Auto selected: " + m_autoSelected);
    }

    /**
     * This function is called periodically during autonomous.
     */
    @Override
    public void autonomousPeriodic() {
        switch (m_autoSelected) {
        case kCustomAuto:
            // Put custom auto code here
            break;
        case kDefaultAuto:
        default:
            // Put default auto code here
            break;
        }
    }

    /**
     * This function is called periodically during operator control.
     */
    @Override
    public void teleopPeriodic() {
                // read PID coefficients from SmartDashboard
   double p = SmartDashboard.getNumber("P Gain", 0);
   double i = SmartDashboard.getNumber("I Gain", 0);
   double d = SmartDashboard.getNumber("D Gain", 0);
   double iz = SmartDashboard.getNumber("I Zone", 0);
   double ff = SmartDashboard.getNumber("Feed Forward", 0);
   double max = SmartDashboard.getNumber("Max Output", 0);
   double min = SmartDashboard.getNumber("Min Output", 0);

        // if PID coefficients on SmartDashboard have changed, write new values to controller
if((p != Robot.kP)) { topPID.setP(p); Robot.kP = p; }
if((i != Robot.kI)) { topPID.setI(i); Robot.kI = i; }
if((d != Robot.kD)) { topPID.setD(d); Robot.kD = d; }
if((iz != Robot.kIz)) { topPID.setIZone(iz); Robot.kIz = iz; }
if((ff != Robot.kFF)) { topPID.setFF(ff); Robot.kFF = ff; }
if((max != Robot.kMaxOutput) || (min != Robot.kMinOutput)) { 
    topPID.setOutputRange(min, max); 
    Robot.kMinOutput = min; Robot.kMaxOutput = max;
 } 
 if (gamepad1.getXButtonPressed()){
    Robot.setTop = Robot.topRPM;
    Robot.setBottom = Robot.bottomRPM;
    //shooterOff = false;
    System.out.println("@@@@@@");
}
if (gamepad1.getXButtonReleased()){
    Robot.setTop = 0;
    Robot.setBottom = 0;
    //shooterOff = true;
    System.out.println("XXXXXXX");
}
topPID.setReference(Robot.setTop, ControlType.kVelocity);
bottomPID.setReference(Robot.setBottom, ControlType.kVelocity);
        /*try {
            shooter.runShooter();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/

    }

    /**
     * This function is called periodically during test mode.
     */
    @Override
    public void testPeriodic() {
    }
}
