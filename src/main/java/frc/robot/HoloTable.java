package frc.robot;

//import java.util.HashMap;                                       For Hashmap

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ColorSensorV3;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Solenoid;

public final class HoloTable {

    private static HoloTable instance = null;
    private final I2C.Port i2cPort = I2C.Port.kOnboard;
    private final ColorSensorV3 colorSensor = new ColorSensorV3(i2cPort);
    private static WPI_TalonSRX wheelMotor = new WPI_TalonSRX(12);
    private static DigitalInput proxTrigger = new DigitalInput(0);
    private static DigitalInput proxTrigger1 = new DigitalInput(1);
    // public static HashMap map = new HashMap<String, Object>(); For Hashmap
    private static WPI_TalonSRX driveTurn;
    private static CANSparkMax driveRightFront;
    private static PigeonIMU gyro;
    private static Solenoid singleSolenoid;
    private static DoubleSolenoid turnSolenoid;

    // private static Insert Camera Here;
    // private static Insert Color Sensor Here;

    private HoloTable() {

        // map.put("driveTurn", ); For Hashmap
        driveTurn = new WPI_TalonSRX(0);
        driveRightFront = new CANSparkMax(0, MotorType.kBrushless);
        gyro = new PigeonIMU(0);
        singleSolenoid = new Solenoid(0);
        turnSolenoid = new DoubleSolenoid(1, 2);
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

    public Solenoid getSingleSoleniod() {
        return singleSolenoid;
    }

    public DoubleSolenoid getTurnSolenoid() {
        return turnSolenoid;
    }

    public I2C.Port getI2cPort(){
        return i2cPort;
    }
    public ColorSensorV3 getColorSensor(){
        return colorSensor;
    }

    public WPI_TalonSRX getWheelMotor(){
        return wheelMotor;
    }

    public DigitalInput getProxTrigger(){
        return proxTrigger;
    }

    public DigitalInput getProxTrigger1(){
        return proxTrigger1;
    }

}