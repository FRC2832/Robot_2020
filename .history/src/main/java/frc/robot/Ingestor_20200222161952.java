 package frc.robot;

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
    

    public Ingestor() {
        intakeDown = false;
    }
    public void runIngestor(){
        if (gamepad1.getBButtonPressed() && intakeDown == false){
            dropIntake1.set(Value.kForward);
            //dropIntake2.set(Value.kForward);
            intakeDown = true;
        }
        if (gamepad1.getBButtonPressed() && intakeDown == true){
            dropIntake1.set(Value.kReverse);
            //dropIntake2.set(Value.kReverse);
            intakeDown = false;
        }
        if (joystick.getPOV() == 180){
            intake.set(.5);
        }
        else{
            intake.set(0);
        }

    }
    public void stopIngestor(){
        intake.set(0);
    }
}