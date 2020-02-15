package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;

public final class BallCount{

    HoloTable table = HoloTable.getInstance();

    DigitalInput infraredHopper1;
    DigitalInput infraredHopper2;
    DigitalInput infraredHopper3;
    DigitalInput infraredHopper4;
    DigitalInput infraredHopper5;
    
    WPI_TalonSRX intake;
    WPI_TalonSRX color;

    public BallCount(){

        infraredHopper1 = table.getInfraredHopper1();
        infraredHopper2 = table.getInfraredHopper2();
        infraredHopper3 = table.getInfraredHopper3();
        infraredHopper4 = table.getInfraredHopper4();
        infraredHopper5 = table.getInfraredHopper5();

        intake = table.getIntake();
        color = table.getColor();
    }

    public String ballCount(DigitalInput dI) {
        if (dI.get()){

            return "On";
        }
        return "Off";
        
    }

    public Boolean countBalls(){
        if (!infraredHopper1.get()){
            return true;
        }
        else return false;
    }

    public Boolean countBalls2(){
        if (!infraredHopper2.get()) {
            return true;
        }
        else return false;
    }

    public boolean countBalls3(){
        if (!infraredHopper3.get()) {
            return true;
        }
        else return false;
    }

    public boolean countBalls4(){
        if (!infraredHopper4.get()){
            return true;
        }
        else return false;
    }

    public boolean countBalls5(){
        if (!infraredHopper5.get()) {
            return true;
        }
        else return false;
    }

    public void full(){
        if(infraredHopper5.get()){
            intake.set(0);
    
        }
    }

}