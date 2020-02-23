package frc.robot;

import java.util.logging.Logger;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Ingestor{

    HoloTable holoTable = HoloTable.getInstance();
    WPI_TalonSRX intake = holoTable.getIntake();
    XboxController gamepad1 = holoTable.getController();
    DoubleSolenoid dropIntake1 = holoTable.getDropIntake1();
    DoubleSolenoid dropIntake2 = holoTable.getDropIntake2();
    Joystick joystick = holoTable.getJoystickRight();
    private boolean intakeDown;
    private final Logger logger = Logger.getLogger(this.getClass().getName());
    

    public Ingestor() {
        intakeDown = false;
    }
    public void runIngestor(){
        if (gamepad1.getBButtonPressed()){
            if(!intakeDown){
                dropIntake1.set(Value.kForward);
            // dropIntake2.set(Value.kForward);
                intakeDown = true;
            }
            else if (intakeDown){
                dropIntake1.set(Value.kReverse);
                //dropIntake2.set(Value.kReverse);
                intakeDown = false;
            }
        }
            if (joystick.getPOV() == 180){
                intake.set(.8);
            }
            else{
                intake.set(0);
                
            }
            
            if (joystick.getRawButtonPressed(5)){
                logger.warning("Hi");
            }
        
        
    }
    public void stopIngestor(){
        intake.set(0);
    }


}