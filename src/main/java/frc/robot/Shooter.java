package frc.robot;

import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;

import edu.wpi.first.wpilibj.XboxController;
//import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shooter {
    public boolean shooterOff;
    HoloTable holo = HoloTable.getInstance();
    // WPI_TalonSRX kickerMotor = holo.getKickerMotor();
    //SpeedControllerGroup shooter = holo.getShooter();
    //DoubleSolenoid kickerPiston = holo.getKickerPiston();
    
    
    public void runShooter() throws InterruptedException {

  

        /*if (gamepad1.getXButtonPressed()){
            //topShooter.set(0.38);
            //bottomShooter.set(-0.38);
            topShooter.setSmartCurrentLimit(0, 5700, 4000);
            //speed = 0.38
            /*kickerMotor.set(.25);
            kickerPiston.set(Value.kForward);
            Thread.sleep(20);
            kickerPiston.set(Value.kReverse);*/
        //}
        /*if (gamepad1.getXButtonReleased()){
            topShooter.set(0);
            bottomShooter.set(0);
            /*kickerMotor.set(0);
            kickerPiston.set(Value.kOff);*/
        //}
        
    }
}