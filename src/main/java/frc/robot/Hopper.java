package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.XboxController;

public final class Hopper{

    HoloTable table = HoloTable.getInstance();

    WPI_TalonSRX hopper;
    DigitalInput infraredHopper1;
    DigitalInput infraredHopper2;
    DigitalInput infraredHopper3;
    XboxController gamepad1;

    private Hopper(){
        
    hopper = table.getHopper();
    infraredHopper1 = table.getInfraredHopper1();
    infraredHopper2 = table.getInfraredHopper2();
    infraredHopper3 = table.getInfraredHopper3();
    gamepad1 = table.getGamepad1();


    }

    public String ballCount(DigitalInput dI) {
        if (dI.get()){
            return "On";
        }
        return "Off";
        
    }

    public String countBalls(){
        if (infraredHopper1.get()) {
            return "Two";
        }
        else if (infraredHopper2.get()) {
            return "Three";
        }
        else if (infraredHopper3.get()) {
            return "FULL";
        }
        return "empty...... :((((";
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