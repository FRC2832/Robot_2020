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

        public void ballcount() {
            if (infraredCovayor1 < 1){
                return hello;
            }
            return null;
        }
    }

    public static class hello {
        public static void main(String[] args) {
            System.out.println("On");
        }
    }
   
    

}