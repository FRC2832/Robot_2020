/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.networktables.NetworkTableEntry;

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
    private final HoloTable holo = HoloTable.getInstance();
    private final Shooter shooter = new Shooter();
    private final Ingestor ingestor = new Ingestor();
    private final Hopper hopper = new Hopper();
    private String m_autoSelected;
    private final SendableChooser<String> m_chooser = new SendableChooser<>();
    public static double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput, fastTopRPM, fastBottomRPM, slowTopRPM,
            slowBottomRPM, setTop, setBottom;
    private static DriveTrain driveTrain;
    private static int visionCenterX = 640;
    private static int visionCenterY = 360;
    private NetworkTable table;
    private final double[] defaultValue = { -1 };
    private XboxController gamepad1;
    private JoystickButton buttonA, buttonB, buttonX;
    NetworkTableEntry cameraSelect = NetworkTableInstance.getDefault().getEntry("/camselect");

    /**
     * This function is run when the robot is first started up and should be used
     * for any initialization code.
     */
    @Override
    public void robotInit() {

        m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
        m_chooser.addOption("My Auto", kCustomAuto);
        SmartDashboard.putData("Auto choices", m_chooser);
        table = NetworkTableInstance.getDefault().getTable("datatable");
        kP = 0;
        kI = 0;
        kD = 0;
        kIz = 0;
        kFF = 0.0023;
        kMaxOutput = 1;
        kMinOutput = -1;
        fastTopRPM = -5700;
        fastBottomRPM = 5700;
        slowTopRPM = -3000;
        slowBottomRPM = 3000;

        buttonA = new JoystickButton(gamepad1, 1);
        buttonB = new JoystickButton(gamepad1, 2);
        buttonX = new JoystickButton(gamepad1, 3);

        // set PID coefficients
        /*
         * holo.bottomPID.setP(kP); holo.bottomPID.setI(kI); holo.bottomPID.setD(kD);
         * holo.bottomPID.setIZone(kIz); holo.bottomPID.setFF(kFF);
         * holo.bottomPID.setOutputRange(kMinOutput, kMaxOutput); holo.topPID.setP(kP);
         * holo.topPID.setI(kI); holo.topPID.setD(kD); holo.topPID.setIZone(kIz);
         * holo.topPID.setFF(kFF); holo.topPID.setOutputRange(kMinOutput, kMaxOutput);
         */
        // display PID coefficients on SmartDashboard
        SmartDashboard.putNumber("P Gain", kP);
        SmartDashboard.putNumber("I Gain", kI);
        SmartDashboard.putNumber("D Gain", kD);
        SmartDashboard.putNumber("I Zone", kIz);
        SmartDashboard.putNumber("Feed Forward", kFF);
        SmartDashboard.putNumber("Max Output", kMaxOutput);
        SmartDashboard.putNumber("Min Output", kMinOutput);

        driveTrain = new DriveTrain();

        CameraServer.getInstance().startAutomaticCapture();
        // CameraServer.getInstance().addServer(name, port);
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
        try {
            visionCenterX = (int) ((table.getEntry("x").getDoubleArray(defaultValue))[0]);
        } catch (final Exception e) {

        }

        /*try{
            visionCenterY = (int)((table.getEntry("y").getDoubleArray(defaultValue))[0]);}
            catch(Exception e){
    
            }*/
        //visionCenter = (table.getEntry("x").getNumber(defaultValue).intValue());
        //System.out.println("X value:");
        //System.out.println(visionCenterX);
        //System.out.println("Y value:");
        //System.out.println(visionCenterY);

        SmartDashboard.putNumber("x", visionCenterX);
        
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
        ingestor.RunIngestor();
        hopper.RunMotors();
        driveTrain.autoAlign(visionCenterX);
        try {
            shooter.runShooter();
        } catch (final InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        driveTrain.driveTank();

        if (gamepad1.getAButtonPressed()) {
            cameraSelect.setDouble(0);  // or setString("My Pi Camera Name")
          }
        if (gamepad1.getBButtonPressed()) {
            cameraSelect.setDouble(1);
          }
        /*if (gamepad1.getXButtonPressed()) {
            cameraSelect.setDouble(2);
        }*/

    }

    /**
     * This function is called periodically during test mode.
     */
    @Override
    public void testPeriodic() {

    }
}
