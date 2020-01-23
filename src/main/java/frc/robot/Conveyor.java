package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.XboxController;

public final class Conveyor{

    HoloTable table = HoloTable.getInstance();

    WPI_TalonSRX conv;
    XboxController gamepad1;

    private Conveyor(){
        
    conv = table.getConvayor();
    gamepad1 = table.getGamepad1();


    }
   
    public void RunMotors(){
        if (gamepad1.getAButtonPressed()) {
            conv.set(.5);
        }
        else if (gamepad1.getAButtonReleased()) {
            conv.set(0);
        }
        if (gamepad1.getStartButtonPressed()) {
            conv.set(-.5);
        }
        else if (gamepad1.getStartButtonReleased()){
            conv.set(0);
        }
        
        
    }
    

}