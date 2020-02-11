/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.RobotBase;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.networktables.*;
import edu.wpi.first.wpilibj.tables.*;
import edu.wpi.cscore.*;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;


/**
 * Do NOT add any static variables to this class, or any initialization at all.
 * Unless you know what you are doing, do not modify this file except to change
 * the parameter class to the startRobot call.
 */
public final class Main {
  private Main() {
     // Loads our OpenCV library. This MUST be included
     System.loadLibrary("opencv_java310");

     // Connect NetworkTables, and get access to the publishing table
     NetworkTable.setClientMode();
     // Set your team number here
     NetworkTable.setTeam(2832);
 
     NetworkTable.initialize();
 
 
     // This is the network port you want to stream the raw received image to
     // By rules, this has to be between 1180 and 1190, so 1185 is a good choice
     final int streamPort = 1185;
 
     // This stores our reference to our mjpeg server for streaming the input image
     final MjpegServer inputStream = new MjpegServer("MJPEG Server", streamPort);
  }

  /**
   * Main initialization function. Do not perform any initialization here.
   *
   * <p>
   * If you change your main robot class, change the parameter type.
   */
  public static void main(String... args) {
    RobotBase.startRobot(Robot::new);
  }
}
