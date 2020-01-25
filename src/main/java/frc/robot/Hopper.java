package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.XboxController;

public final class Hopper{

    HoloTable table = HoloTable.getInstance();

    WPI_TalonSRX hopper;
    XboxController gamepad1;

    private Hopper(){
        
    hopper = table.getHopper();
    gamepad1 = table.getGamepad1();


    }
   
    public void RunMotors(){
        if (gamepad1.getAButtonPressed()) {
            hopper.set(.5);
        }
        else if (gamepad1.getAButtonReleased()) {
            hopper.set(0);
        }
        if (gamepad1.getStartButtonPressed()) {
            hopper.set(-.5);
        }
        else if (gamepad1.getStartButtonReleased()){
            hopper.set(0);
        }
        
        
    }
    

}