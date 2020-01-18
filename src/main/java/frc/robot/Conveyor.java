package frc.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.XboxController;

public final class Conveyor{

    HoloTable table = HoloTable.getInstance();

    SpeedControllerGroup conv;
    DigitalInput infraredCovayor1;
    DigitalInput infraredCovayor2;
    DigitalInput infraredCovayor3;
    XboxController gamepad1;

    private Conveyor(){
        
    conv = table.getMotors();
    infraredCovayor1 = table.getInfraredConvayer1();
    infraredCovayor2 = table.getInfraredConvayer2();
    infraredCovayor3 = table.getInfraredConvayer3();
    gamepad1 = table.getGamepad1();


    }

    public String ballCount(DigitalInput dI) {
        if (dI.get()){
            return "On";
        }
        return "Off";
        
    }

    public String countBalls(){
        if (infraredCovayor1.get()) {
            return "Two";
        }
        else if (infraredCovayor2.get()) {
            return "Three";
        }
        else if (infraredCovayor3.get()) {
            return "FULL";
        }
        return "empty...... :((((";
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