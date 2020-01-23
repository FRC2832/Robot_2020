package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public final class TrackerBeam{

    HoloTable table = HoloTable.getInstance();

    DigitalInput infraredCovayor1;
    DigitalInput infraredCovayor2;
    DigitalInput infraredCovayor3;
    DigitalInput infraredCovayor4;
    DigitalInput infraredCovayor5;
    
    WPI_TalonSRX intake;

    public TrackerBeam(){

        infraredCovayor1 = table.getInfraredConvayer1();
        infraredCovayor2 = table.getInfraredConvayer2();
        infraredCovayor3 = table.getInfraredConvayer3();
        infraredCovayor4 = table.getInfraredConvayer4();
        infraredCovayor5 = table.getInfraredConvayer5();

        intake = table.getIntake();
    }

    public String ballCount(DigitalInput dI) {
        if (dI.get()){

            return "On";
        }
        return "Off";
        
    }

    public String countBalls(){
        if (!infraredCovayor1.get()){
            return "One";
        }
        else return "empty";
    }

    public String countBalls2(){
        if (!infraredCovayor2.get()) {
            return "Two";
        }
        else return "empty";
    }

    public String countBalls3(){
        if (!infraredCovayor3.get()) {
            return "Three";
        }
        else return "empty";
    }

    public String countBalls4(){
        if (!infraredCovayor4.get()){
            return "Four!";
        }
        else return "empty";
    }

    public String countBalls5(){
        if (!infraredCovayor5.get()) {
            return "!!!FULL!!!";
        }
        else return "empty";
    }

    public void full(){
        if(infraredCovayor5.get()){
            intake.set(0);
    
        }
    }
}