package frc.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.SpeedControllerGroup;

public final class Conveyor{

    HoloTable table = HoloTable.getInstance();

    SpeedControllerGroup conv;
    DigitalInput infraredCovayor1;
    DigitalInput infraredCovayor2;
    DigitalInput infraredCovayor3;
    private Conveyor(){
        
    conv = table.getMotors();
    infraredCovayor1 = table.getInfraredConvayer1();
    infraredCovayor2 = table.getInfraredConvayer2();
    infraredCovayor3 = table.getInfraredConvayer3();


    }

    public String ballCount(DigitalInput dI) {
        if (dI.get()){
            return "On";
        }
        return "Off";
        
    }
   
    public Boolean movement(){
        if (movement() == null) {
            return null;
        }
        return movement();
    }
    

}