package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Ingestor{

    HoloTable holoTable = HoloTable.getInstance();
    WPI_TalonSRX intake = holoTable.getIntake();
    XboxController gamepad1 = holoTable.getGamepad1();
    DoubleSolenoid dropIntake = holoTable.getDropIntake();
    Joystick joystick = holoTable.getJoystick();
    private boolean intakeDown;
    

    public Ingestor() {
        intakeDown = false;
    }
    public void RunIngestor(){
        if (gamepad1.getBButtonPressed() && !intakeDown){
            dropIntake.set(Value.kForward);
            intakeDown = true;
        }
        if (gamepad1.getBButtonPressed() && intakeDown){
            dropIntake.set(Value.kReverse);
            intakeDown = false;
        }
        if (joystick.getPOV() == 180){
            intake.set(.5);
        }
        else{
            intake.set(0);
        }

    }
}