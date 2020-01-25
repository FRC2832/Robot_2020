package frc.robot;

//import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
//import java.lang.Thread;
//import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.XboxController;
//import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Shooter{
    HoloTable holo = HoloTable.getInstance();
    //WPI_TalonSRX kickerMotor = holo.getKickerMotor();
    SpeedControllerGroup shooter = holo.getShooter();
    //DoubleSolenoid kickerPiston = holo.getKickerPiston();
    XboxController gamepad1 = holo.getGamepad1(); 

    public void RunShooter() throws InterruptedException {
        if (gamepad1.getXButtonPressed()){
            shooter.set(1);
            /*kickerMotor.set(.25);
            kickerPiston.set(Value.kForward);
            Thread.sleep(20);
            kickerPiston.set(Value.kReverse);*/
        }
        if (gamepad1.getXButtonReleased()){
            shooter.set(0);
            /*kickerMotor.set(0);
            kickerPiston.set(Value.kOff);*/
        }
    }
}