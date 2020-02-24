package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.XboxController;

public final class Hopper {

    HoloTable table = HoloTable.getInstance();

    WPI_TalonSRX hopper;
    XboxController gamepad1;
    WPI_TalonSRX ejector;
    
    DigitalInput infraredHopper1;
    DigitalInput infraredHopper2;
    DigitalInput infraredIntake;

    Hopper() {

        hopper = table.getHopper();
        gamepad1 = table.getController();
        infraredHopper1 = table.getInfraredHopper1();
        infraredHopper2 = table.getInfraredHopper2();
        infraredIntake = table.getInfraredIntake();
        ejector = table.getEjector();


    }

    public void RunMotors() throws InterruptedException {
        if (gamepad1.getStartButtonPressed()) {
            hopper.set(.5);
        } else if (gamepad1.getStartButtonReleased()) {
            hopper.set(0);
        }
        if (!infraredIntake.get()){
            if (!infraredHopper1.get()){
                if (!infraredHopper2.get()){
                    hopper.set(0);
                }else {
                    hopper.set(-.75);
                    Thread.sleep(100);
                    hopper.set(0);
                    ejector.set(.25);
                    Thread.sleep(100);
                    ejector.set(0);
                }
            }else {
                hopper.set(-.75);
            }
        }else {
            hopper.set(0);
        }
       
    }

}