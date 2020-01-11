package frc.robot;

//import java.util.HashMap;                                       For Hashmap

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;


public final class HoloTable {

  //  private static HoloTable instance = null;                   For Hashmap
  //  public static HashMap map = new HashMap<String, Object>();  For Hashmap
    public static WPI_TalonSRX driveTurn;
    public static CANSparkMax driveRightFront;
    public static PigeonIMU gyro;
    public static Solenoid singleSolenoid;
    public static DoubleSolenoid turnSolenoid;
    //public static Insert Camera Here;
    //public static Insert Color Sensor Here;
  

      private HoloTable() {

        //map.put("driveTurn", );                                 For Hashmap
        driveTurn = new WPI_TalonSRX(0);
        driveRightFront = new CANSparkMax(0, MotorType.kBrushless);
        gyro = new PigeonIMU(0);
        singleSolenoid = new Solenoid(0);
        turnSolenoid = new DoubleSolenoid(1, 2);
        

    }

    
        
}