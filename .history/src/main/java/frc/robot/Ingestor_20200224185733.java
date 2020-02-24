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
    DoubleSolenoid dropIntake = holoTable.getDropIntake();
    Joystick joystick = holoTable.getJoystickRight();
    private boolean intakeDown;
    

    public Ingestor() {
        intakeDown = false;
    }
    public void runIngestor(){
        System.out.println("@@@@@@@"+intakeDown);
        if (gamepad1.getBButtonPressed() && intakeDown == false){
            System.out.println("expell");
            dropIntake.set(Value.kForward);
            intakeDown = true;
        }
        else if (gamepad1.getBButtonPressed() && intakeDown == true){
            System.out.println("retract");
            dropIntake.set(Value.kReverse);
            intakeDown = false;
        }
        if (joystick.getPOV() == 180){
            System.out.println("intake run");
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