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
import edu.wpi.first.wpilibj.DriverStation;

public class WheelMover extends Subsystem {
  private String lastColor;
  private int wheelCount = 0;
  private int debouncer = 0;
  private double fastSpeed = 0.75;
  private double slowSpeed = -0.25;
  private boolean checkSame = false;
  private String correctColor;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  String gameData = "";
  private final ColorMatch m_colorMatcher = new ColorMatch();
  private HoloTable holo = HoloTable.getInstance();
  private final I2C.Port i2cPort = holo.getI2cPort();
  private final ColorSensorV3 colorSensor = holo.getColorSensor();
  private final WPI_TalonSRX wheelMotor = holo.getWheelMotor();
  private final DigitalInput proxTrigger = holo.getProxTrigger();
  private final DigitalInput proxTrigger1 = holo.getProxTrigger1();

  private final Color kBlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
  private final Color kGreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
  private final Color kRedTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);
  private final Color kYellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);

  // MAIN SET
  /*
   * private final Color kBlueTarget = ColorMatch.makeColor(0.11, 0.45, 0.34);
   * private final Color kGreenTarget = ColorMatch.makeColor(0.21, 0.51, 0.26);
   * private final Color kRedTarget = ColorMatch.makeColor(0.34, 0.43, 0.22);
   * private final Color kYellowTarget = ColorMatch.makeColor(0.28, 0.52, 0.19);
   */

  // BIASED SET
  /*
   * private final Color kBlueTarget = ColorMatch.makeColor(0.05, 0.45, 0.45);
   * private final Color kGreenTarget = ColorMatch.makeColor(0.3, 0.51, 0.25);
   * private final Color kRedTarget = ColorMatch.makeColor(0.34, 0.43, 0.22);
   * private final Color kYellowTarget = ColorMatch.makeColor(0.28, 0.52, 0.19);
   */

  /*
   * private final Color kBlueTarget = ColorMatch.makeColor(0.12, 0.42, 0.36);
   * private final Color kGreenTarget = ColorMatch.makeColor(0.12, 0.42, 0.46);
   * private final Color kRedTarget = ColorMatch.makeColor(0.53, 0.34, 0.13);
   * private final Color kYellowTarget = ColorMatch.makeColor(0.32, 0.57, 0.12);
   * /** Creates a new WheelMover.
   */

  public void setGameData(String updatedGD) {
    gameData = updatedGD;
  }

  public WheelMover() {
    gameData = DriverStation.getInstance().getGameSpecificMessage();
    /*
     * DriverStation.getInstance().getGameSpecificMessage();
     * if(gameData.length()>0){ switch (gameData.charAt(0)) { case 'B': correctColor
     * = "Blue"; break; case 'G': correctColor = "Green"; break; case 'R':
     * correctColor = "Red"; break; case 'Y': correctColor = "Yellow"; break;
     * default: correctColor = "Error!"; break; } }
     */
    wheelCount = 0;
    m_colorMatcher.addColorMatch(kBlueTarget);
    m_colorMatcher.addColorMatch(kGreenTarget);
    m_colorMatcher.addColorMatch(kRedTarget);
    m_colorMatcher.addColorMatch(kYellowTarget);

  }

  @Override
  public void periodic() {
    //gameData = DriverStation.getInstance().getGameSpecificMessage();

  }

  public void moveWheel() { //3 revs in 5.17 sec     35 rpm
    if (!proxTrigger.get()) {
      wheelCount = 0;
      while (wheelCount <= 28) {
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

        if (debouncer >= 100) {
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
        wheelMotor.set(fastSpeed); //0.75

      }
    }
    if (wheelCount > 28) {
      wheelCount = 0;
      wheelMotor.set(0);
    }
  }

  public void setWheel() {

    if (!proxTrigger1.get()) {

      if (gameData.length() > 0) {
        switch (gameData.charAt(0)) {
        case 'B':
          correctColor = "Blue";
          break;
        case 'G':
          correctColor = "Green";
          break;
        case 'R':
          correctColor = "Red";
          break;
        case 'Y':
          correctColor = "Yellow";
          break;
        default:
          correctColor = "Error!";
          break;
        }
      }

      Color detectedColor = colorSensor.getColor();
      /**
       * S Run the color match algorithm on our detected color
       */
      String colorString;
      ColorMatchResult match = m_colorMatcher.matchClosestColor(detectedColor);

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
      while (!colorString.equals(correctColor)) {
        wheelMotor.set(slowSpeed);
        detectedColor = colorSensor.getColor();
        /**
         * S Run the color match algorithm on our detected color
         */
        match = m_colorMatcher.matchClosestColor(detectedColor);

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
        if (colorString.equals(correctColor)) {
          try {
            Thread.sleep(100);
          } catch (Exception e) {
            // TODO: handle exception
          }

          wheelMotor.set(0);
          break;
        }
      }
    }
  }

  @Override
  protected void initDefaultCommand() {
    // TODO Auto-generated method stub

  }

}
