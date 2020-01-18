package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.XboxController;

public class Ingestor{

    HoloTable holoTable = HoloTable.getInstance();
    WPI_TalonSRX intake = holoTable.getIntake();
    XboxController gamepad1 = holoTable.getGamepad1();
    private boolean motorRunning;
    

    public Ingestor() {
        motorRunning = false;
    
    }
    public void RunIngestor(){
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