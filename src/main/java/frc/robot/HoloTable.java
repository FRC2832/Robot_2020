package frc.robot;

//import java.util.HashMap;                                       For Hashmap

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;

public final class HoloTable {

    private static HoloTable instance = null;
    // public static HashMap map = new HashMap<String, Object>(); For Hashmap
    private static WPI_TalonSRX driveTurn;
    private static CANSparkMax driveRightFront;
    private static CANSparkMax driveLeftFront;
    private static CANSparkMax driveRightRear;
    private static CANSparkMax driveLeftRear;
    private static PigeonIMU gyro;
    private static Solenoid singleSolenoid;
    private static DoubleSolenoid turnSolenoid;
    private static XBoxController controller;
    // private static Insert Camera Here;
    // private static Insert Color Sensor Here;

    private HoloTable() {

        // map.put("driveTurn", ); For Hashmap
        driveTurn = new WPI_TalonSRX(0);
        driveRightFront = new CANSparkMax(0, MotorType.kBrushless);
        driveLeftFront = new CANSparkMax(1, MotorType.kBrushless);
        driveRightRear = new CANSparkMax(2, MotorType.kBrushless);
        driveLeftRear = new CANSparkMax(3, MotorType.kBrushless);
        gyro = new PigeonIMU(0);
        singleSolenoid = new Solenoid(0);
        turnSolenoid = new DoubleSolenoid(1, 2);
        controller = new XBoxController(0);

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
        return driveRightFront;
    }

    public CANSparkMax getDriveRightRear() {
        return driveRightFront;
    }

    public CANSparkMax getDriveLeftRear() {
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
    public XBoxController getController(){
        return controller;
    }

}