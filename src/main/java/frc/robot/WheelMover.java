/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.util.Color;
import com.revrobotics.ColorSensorV3;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorMatch;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class WheelMover extends Subsystem {
  private String lastColor;
  private int wheelCount = 0;
  private int debouncer = 0;
  private boolean checkSame = false;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  private final I2C.Port i2cPort = I2C.Port.kOnboard;
  private final ColorSensorV3 colorSensor = new ColorSensorV3(i2cPort);
  private final ColorMatch m_colorMatcher = new ColorMatch();
  private static WPI_TalonSRX wheelMotor = new WPI_TalonSRX(12);
  private static DigitalInput proxTrigger = new DigitalInput(0);

  /*
   * OLD OFFICIAL COLOR PRESETS private final Color kBlueTarget =
   * ColorMatch.makeColor(0.143, 0.427, 0.429); private final Color kGreenTarget =
   * ColorMatch.makeColor(0.197, 0.561, 0.240); private final Color kRedTarget =
   * ColorMatch.makeColor(0.561, 0.232, 0.114); private final Color kYellowTarget
   * = ColorMatch.makeColor(0.361, 0.524, 0.113);
   * 
   */

  private final Color kBlueTarget = ColorMatch.makeColor(0.19, 0.45, 0.34);
  private final Color kGreenTarget = ColorMatch.makeColor(0.21, 0.51, 0.26);
  private final Color kRedTarget = ColorMatch.makeColor(0.34, 0.43, 0.22);
  private final Color kYellowTarget = ColorMatch.makeColor(0.28, 0.52, 0.19);

  /**
   * Creates a new WheelMover.
   */
  public WheelMover() {
    m_colorMatcher.addColorMatch(kBlueTarget);
    m_colorMatcher.addColorMatch(kGreenTarget);
    m_colorMatcher.addColorMatch(kRedTarget);
    m_colorMatcher.addColorMatch(kYellowTarget);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void moveWheel() {
    if (!proxTrigger.get()) {
      wheelCount = 0;
      while (wheelCount <= 6) {
        final Color detectedColor = colorSensor.getColor();
        /**
         * S Run the color match algorithm on our detected color
         */
        String colorString;
        final ColorMatchResult match = m_colorMatcher.matchClosestColor(detectedColor);

        if (match.color == kBlueTarget) {
          colorString = "Blue";
        } else if (match.color == kRedTarget) {
          colorString = "Red";
        } else if (match.color == kGreenTarget) {
          colorString = "Green";
        } else if (match.color == kYellowTarget) {
          colorString = "Yellow";
        } else {
          colorString = "Unknown";
        }

        // DEBOUNCER ALGORITHM
        if (!checkSame) {
          if (!(colorString.equals(lastColor))) {
            debouncer++;
            checkSame = true;
          } else {
            debouncer = 0;
          }
        }
        if (checkSame) {
          if (colorString.equals(lastColor)) {
            debouncer++;
          }
        }

        if (debouncer >= 3) {
          wheelCount++;
          checkSame = false;
          debouncer = 0;
        }

        /**
         * Open Smart Dashboard or Shuffleboard to see the color detected by the sensor.
         */
        SmartDashboard.putNumber("Red", detectedColor.red);
        SmartDashboard.putNumber("Green", detectedColor.green);
        SmartDashboard.putNumber("Blue", detectedColor.blue);
        SmartDashboard.putNumber("Confidence", match.confidence);
        SmartDashboard.putString("Detected Color", colorString);
        SmartDashboard.putNumber("Count", wheelCount);
        lastColor = colorString;
        wheelMotor.set(0.5);

      }
    }
    if (wheelCount > 6) {
      wheelCount = 0;
      wheelMotor.set(0);
    }
  }

  @Override
  protected void initDefaultCommand() {
    // TODO Auto-generated method stub

  }
}
