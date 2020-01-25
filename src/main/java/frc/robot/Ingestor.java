package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Ingestor{

    HoloTable holoTable = HoloTable.getInstance();
    WPI_TalonSRX intake = holoTable.getIntake();
    XboxController gamepad1 = holoTable.getGamepad1();
    DoubleSolenoid dropIntake = holoTable.getDropIntake();
    private boolean motorRunning;
    private boolean intakeDown;
    

    public Ingestor() {
        motorRunning = false;
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
        if (gamepad1.getAButtonPressed() && !motorRunning){
            intake.set(.5);
            motorRunning = true;
        }
        else if (gamepad1.getAButtonPressed() && motorRunning){
            intake.set(0);
            motorRunning = false;
        }
        if (gamepad1.getStartButtonPressed()){
            intake.set(-.5);
        }
        else if (gamepad1.getStartButtonReleased()){
            intake.set(0);
        }
    }

}