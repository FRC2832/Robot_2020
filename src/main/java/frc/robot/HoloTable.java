package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.XboxController;

public final class HoloTable {

    private static HoloTable instance = null;
    private static WPI_TalonSRX driveTurn;
    private static CANSparkMax driveRightFront;
    private static PigeonIMU gyro;
    private static Solenoid singleSolenoid;
    private static DoubleSolenoid turnSolenoid;
    private static XboxController gamepad1;

    private static WPI_TalonSRX intake;

    private static WPI_TalonSRX conveyor;
    private static DigitalInput infraredConvayor1;
    private static DigitalInput infraredConvayor2;
    private static DigitalInput infraredConvayor3;
    private static DigitalInput infraredConvayor4;
    private static DigitalInput infraredConvayor5;

    private static WPI_TalonSRX shooterTop;
    private static WPI_TalonSRX shooterBottom;
    private static SpeedControllerGroup shooterMotors;
    // private static Insert Camera Here;
    // private static Insert Color Sensor Here;

    private HoloTable() {

        driveTurn = new WPI_TalonSRX(0);
        driveRightFront = new CANSparkMax(0, MotorType.kBrushless);
        gyro = new PigeonIMU(0);
        singleSolenoid = new Solenoid(0);
        turnSolenoid = new DoubleSolenoid(1, 2);
        gamepad1 = new XboxController(1);

        intake = new WPI_TalonSRX(0);

        conveyor = new WPI_TalonSRX(0);
        infraredConvayor1 = new DigitalInput(0);
        infraredConvayor2 = new DigitalInput(1);
        infraredConvayor3 = new DigitalInput(2);
        infraredConvayor4 = new DigitalInput(3);
        infraredConvayor5 = new DigitalInput(4);

        shooterTop = new WPI_TalonSRX(0);
        shooterBottom = new WPI_TalonSRX(1);
        shooterBottom.setInverted(true);
        shooterMotors = new SpeedControllerGroup(shooterTop, shooterBottom);
    }

    public static HoloTable getInstance() {

        if (instance == null) {
            instance = new HoloTable();
        }

        return instance;

    }

    public WPI_TalonSRX getIntake() {
        return intake;
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

    public Solenoid getSingleSoleniod() {
        return singleSolenoid;
    }

    public DoubleSolenoid getTurnSolenoid() {
        return turnSolenoid;
    }
    public XboxController getGamepad1() {
        return gamepad1;
    }

    public SpeedControllerGroup getMotors() {

        return shooterMotors;
    }

    public DigitalInput getInfraredConvayer1() {

        return infraredConvayor1;
    }

    public DigitalInput getInfraredConvayer2() {

        return infraredConvayor2;
    }

    public DigitalInput getInfraredConvayer3() {

        return infraredConvayor3;
    }

    public DigitalInput getInfraredConvayer4(){

        return infraredConvayor4;
    }

    public DigitalInput getInfraredConvayer5(){

        return infraredConvayor5;
    }

    public WPI_TalonSRX getConvayor(){
    
        return conveyor;
    }
}