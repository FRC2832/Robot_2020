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
    private static PigeonIMU gyro;
    private static DoubleSolenoid dropIntake;
    private static DoubleSolenoid turnSolenoid;
    private static XboxController gamepad1;
    private static Joystick joystick;

    private static WPI_TalonSRX intake;

    private static WPI_TalonSRX color;    
    
    private static WPI_TalonSRX Hopper;
    private static DigitalInput infraredHopper1;
    private static DigitalInput infraredHopper2;
    private static DigitalInput infraredHopper3;
    private static DigitalInput infraredHopper4;
    private static DigitalInput infraredHopper5;

    public CANPIDController topPID; 
    public CANPIDController bottomPID;

    private static CANSparkMax shooterTop;
    private static CANSparkMax shooterBottom;
    private static WPI_TalonSRX ejector;

    HoloTable() {

        driveTurn = new WPI_TalonSRX(0);
        gyro = new PigeonIMU(0);
        dropIntake = new DoubleSolenoid(0, 1);
        turnSolenoid = new DoubleSolenoid(2, 3);
        gamepad1 = new XboxController(2);
        joystick = new Joystick(0); //CHANGE PORT

        intake = new WPI_TalonSRX(0);

        color = new WPI_TalonSRX(12);

        Hopper = new WPI_TalonSRX(0);
        infraredHopper1 = new DigitalInput(0);
        infraredHopper2 = new DigitalInput(1);
        infraredHopper3 = new DigitalInput(2);
        infraredHopper4 = new DigitalInput(3);
        infraredHopper5 = new DigitalInput(4);

        shooterTop = new CANSparkMax(3, MotorType.kBrushless);
        shooterBottom = new CANSparkMax(4, MotorType.kBrushless);
        topPID = shooterTop.getPIDController();
        bottomPID = shooterBottom.getPIDController();
        ejector = new WPI_TalonSRX(0);
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

    public PigeonIMU getGyro() {
        return gyro;
    }

    public DoubleSolenoid getDropIntake() {
        return dropIntake;
    }
    public DoubleSolenoid getTurnSolenoid() {
        return turnSolenoid;
    }

    public XboxController getGamepad1() {
        return gamepad1;
    }
    public Joystick getJoystick(){
        return joystick;
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