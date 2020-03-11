package frc.robot;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;

public final class Climber {

    private HoloTable table = HoloTable.getInstance();

    private CANSparkMax leftClimber;
    private CANSparkMax rightClimber;

    private XboxController gamepad1;

    public Climber() {
        leftClimber = table.getLeftClimber();
        rightClimber = table.getRightClimber();
        gamepad1 = table.getController();
    }

    public void runClimb() {
        rightClimber.set(gamepad1.getY(Hand.kRight));
        leftClimber.set(gamepad1.getY(Hand.kLeft));
    }
}
