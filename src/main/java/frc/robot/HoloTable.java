package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;

public final class HoloTable {

    private static HoloTable instance = null;
    private static WPI_TalonSRX driveTurn;
    private static CANSparkMax driveRightFront;
    private static CANSparkMax driveLeftFront;
    private static CANSparkMax driveRightRear;
    private static CANSparkMax driveLeftRear;
    private static PigeonIMU gyro;
    private static DoubleSolenoid dropIntake;
    private static DoubleSolenoid turnSolenoid;
    private static WPI_TalonSRX intake;

    private static WPI_TalonSRX color;    
    
    private static WPI_TalonSRX Hopper;
    private static DigitalInput infraredHopper1;
    private static DigitalInput infraredHopper2;
    private static DigitalInput infraredHopper3;
    private static DigitalInput infraredHopper4;
    private static DigitalInput infraredHopper5;
    private static DigitalInput infraredIntake;

    public CANPIDController topPID; 
    public CANPIDController bottomPID;
    private static XboxController controller;
    private static Joystick joystickLeft;
    private static Joystick joystickRight;

    

    // private static Insert Camera Here;
    // private static Insert Color Sensor Here;

    private static CANSparkMax shooterTop;
    private static CANSparkMax shooterBottom;
    private static WPI_TalonSRX ejector;

    HoloTable() {

        driveTurn = new WPI_TalonSRX(0);
        gyro = new PigeonIMU(0);
        dropIntake = new DoubleSolenoid(0, 1);

        intake = new WPI_TalonSRX(2);

        color = new WPI_TalonSRX(1);

        Hopper = new WPI_TalonSRX(12);
        infraredHopper1 = new DigitalInput(0);
        infraredHopper2 = new DigitalInput(1);
        infraredHopper3 = new DigitalInput(2);
        infraredHopper4 = new DigitalInput(3);
        infraredHopper5 = new DigitalInput(4);
        infraredIntake = new DigitalInput(5);

        shooterTop = new CANSparkMax(3, MotorType.kBrushless);
        shooterBottom = new CANSparkMax(4, MotorType.kBrushless);
        topPID = shooterTop.getPIDController();
        bottomPID = shooterBottom.getPIDController();
        ejector = new WPI_TalonSRX(0);
        driveRightFront = new CANSparkMax(2, MotorType.kBrushless);
        driveLeftFront = new CANSparkMax(8, MotorType.kBrushless);//the motor should be set to 4. changed to test shooter
        driveRightRear = new CANSparkMax(1, MotorType.kBrushless);
        driveLeftRear = new CANSparkMax(6, MotorType.kBrushless);//the motor should be set to 3. changed to test shooter
        turnSolenoid = new DoubleSolenoid(1, 2);
        controller = new XboxController(0);
        joystickLeft = new Joystick(0);
        joystickRight = new Joystick(1);
    }
    public static HoloTable getInstance() {

        if (instance == null) {
            instance = new HoloTable();
        }

        return instance;

    }

    public WPI_TalonSRX getDriveTurn() {
        return driveTurn;
    }

    public CANSparkMax getDriveRightFront() {
        return driveRightFront;
    }

    public CANSparkMax getDriveLeftFront() {
        return driveLeftFront;
    }

    public CANSparkMax getDriveRightRear() {
        return driveRightRear;
    }

    public CANSparkMax getDriveLeftRear() {
        return driveLeftRear;
    }

    public PigeonIMU getGyro() {
        return gyro;
    }

    public DoubleSolenoid getDropIntake() {
        return dropIntake;
    }
    public DoubleSolenoid getTurnSolenoid() {
        return turnSolenoid;
    }
    public XboxController getController(){
        return controller;
    }
    public Joystick getJoystickLeft() {
        return joystickLeft;
    }
    public Joystick getJoystickRight() {
        return joystickRight;
    }

    public WPI_TalonSRX getIntake() {
        return intake;
    }

    public DigitalInput getInfraredHopper1() {

        return infraredHopper1;
    }
    public DigitalInput getInfraredHopper2() {

        return infraredHopper2;
    }
    public DigitalInput getInfraredHopper3() {

        return infraredHopper3;
    }

    public DigitalInput getInfraredHopper4(){

        return infraredHopper4;
    }

    public DigitalInput getInfraredHopper5(){

        return infraredHopper5;
    }

    public DigitalInput getInfraredIntake(){

        return infraredIntake;
    }

    public WPI_TalonSRX getHopper(){
        
        return Hopper;
    }

    public CANSparkMax getTopShooter(){
        return shooterTop;
    }
    public CANSparkMax getBottomShooter(){
        return shooterBottom;
    }
    public WPI_TalonSRX getEjector(){
        return ejector;
    }

    public WPI_TalonSRX getColor(){
        return color;
    }
    
}