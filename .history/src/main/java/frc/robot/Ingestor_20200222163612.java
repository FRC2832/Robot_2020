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
        System.out.println("@@@@@@@"+intakeDown);
        if (gamepad1.getXButtonPressed() && intakeDown == true){
            System.out.println("expell");
            dropIntake1.set(Value.kForward);
            //dropIntake2.set(Value.kForward);
            intakeDown = false;
        }
        if (gamepad1.getXButtonPressed() && intakeDown == false){
            System.out.println("retract");
            dropIntake1.set(Value.kReverse);
            //dropIntake2.set(Value.kReverse);
            intakeDown = true;
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