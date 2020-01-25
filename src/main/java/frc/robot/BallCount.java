package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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

    public String countBalls(){
        if (!infraredHopper1.get()){
            return "One";
        }
        else return "empty";
    }

    public String countBalls2(){
        if (!infraredHopper2.get()) {
            return "Two";
        }
        else return "empty";
    }

    public String countBalls3(){
        if (!infraredHopper3.get()) {
            return "Three";
        }
        else return "empty";
    }

    public String countBalls4(){
        if (!infraredHopper4.get()){
            return "Four!";
        }
        else return "empty";
    }

    public String countBalls5(){
        if (!infraredHopper5.get()) {
            return "!!!FULL!!!";
        }
        else return "empty";
    }

    public void full(){
        if(infraredHopper5.get()){
            intake.set(0);
    
        }
    }

    public void spin(){
        if (!infraredHopper1.get()){
            color.set(.5);
        }
        else {
            color.set(0);
        }

    }
}